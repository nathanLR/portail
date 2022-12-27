package metier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import communs.Erreur;
import communs.database.orm.*;

import communs.dto.*;

public class CommandeMetier {
	private EntityManager em;

	public CommandeMetier(EntityManager em) {
		this.em = em;

	}

	public Erreur verifier(CommandeDto cde) {

		Erreur erreur = new Erreur();

		boolean champNum = cde.getCdeNum().length() > 0 && cde.getCdeNum().startsWith("CDE");
		boolean champClient = cde.getCdeClient().length() > 0;
		boolean champDate = true;
		boolean champIntitule = cde.getCdeIntitule().length() > 0;
		boolean champMontant = cde.getCdeMontant().length() > 0
				&& (Integer) Integer.parseInt(cde.getCdeMontant().replaceAll("\\s+", "")) instanceof Integer;
		boolean champObservation = true;

		if (!champNum) {
			erreur.ajouterMessage("Le numéro de commande doit commencer par \"CDE\"");
		}
		if (!champClient) {
			erreur.ajouterMessage("Le nom du client ne doit pas être une chaîne vide");
		}
		if (!champDate) {
			erreur.ajouterMessage("Le format de la date est incorrect, il doit être au format JJ/MM/AAAA");
		}
		if (!champIntitule) {
			erreur.ajouterMessage("L'intitulé de la commande ne peut pas être vide");
		}
		if (!champMontant) {
			erreur.ajouterMessage("Le montant renseigné doit être un nombre, décimal ou non.");
		}
		if (!champObservation) {

		}

		if (champNum && champClient && champDate && champIntitule && champMontant) {
			System.out.println("verification reussie");
			return null;
		} else {
			System.out.println("verification ratée: ChampNum:"+champNum+"; champClient:"+champClient+"; champIntitule:"+champIntitule+"; champMontant:"+champMontant);
			return erreur;
		}
	}

	public List<CommandeDto> lister() {
		List<Commande> resultatQuery;
		List<CommandeDto> listeARetourner = new ArrayList<CommandeDto>();
		TypedQuery<Commande> q = this.em.createNamedQuery("SelectionneToutesLesCommandes", Commande.class);

		resultatQuery = q.getResultList();

		for (Commande cdeOrm : resultatQuery) {
			listeARetourner.add(convertirUnOrmEnDto(cdeOrm));
		}

		return listeARetourner;
	}

	// select
	public CommandeDto trouver(Integer id) {
		Commande cdeOrm = this.em.find(Commande.class, id);
		return convertirUnOrmEnDto(cdeOrm);
	}

	// put
	public Erreur modifier(CommandeDto cde) {

		// appel de verifier()
		return new Erreur();
	}

	// insert
	public Erreur inserer(CommandeDto cde) {
		// appel de verifier() = convertdtoorm = persit em
		Erreur verification = this.verifier(cde);

		if (verification == null) {
			// insertion
			try {
				this.em.getTransaction().begin();
				this.em.persist(this.convertirUnDtoEnOrm(cde));
				this.em.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		} else {
			
			return verification;
		}

	}

	public void supprimer(int cdeId) {
		System.out.println("commande metier supprimer");
		try {
			
			this.em.remove(this.em.find(Commande.class, new Integer(cdeId)));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public CommandeDto convertirUnOrmEnDto(Commande orm) {
		CommandeDto ormToDto = new CommandeDto();
		List<ObservationDto> observations = new ArrayList<ObservationDto>();

		ormToDto.setCdeId(orm.getCdeId());
		ormToDto.setCdeClient(orm.getCdeClient());
		ormToDto.setCdeDate(orm.getCdeDate().toString());
		ormToDto.setCdeIntitule(orm.getCdeIntitule());
		ormToDto.setCdeMontant(String.valueOf(orm.getCdeMontant()));
		ormToDto.setCdeNum(orm.getCdeNum());

		for (Observation obs : orm.getObservations()) {
			observations.add(ObservationMetier.convertirUnOrmEnDto(obs));
		}

		ormToDto.setCdeObservations(observations);

		return ormToDto;

	}

	public Commande convertirUnDtoEnOrm(CommandeDto dto) {
		// cette methode est appelée quand on créer une nouvelle commande
		Commande dtoToOrm = new Commande();
		dtoToOrm.setCdeNum(dto.getCdeNum());
		dtoToOrm.setCdeClient(dto.getCdeClient());
		dtoToOrm.setCdeIntitule(dto.getCdeIntitule());
		dtoToOrm.setCdeMontant(Double.parseDouble(dto.getCdeMontant()));
		dtoToOrm.setCdeDate(new Date(dto.getCdeDate()));
		if (dto.getCdeObservations().size() > 0) {
			List<Observation> observations = new ArrayList<Observation>();
			for (ObservationDto obsDto : dto.getCdeObservations()) {
				observations.add(ObservationMetier.convertirUnDtoEnOrm(obsDto));
			}
		} else {
			dtoToOrm.setObservations(new ArrayList<Observation>());
		}

		return dtoToOrm;
	}

}

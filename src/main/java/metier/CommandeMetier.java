package metier;

import java.util.ArrayList;
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
		boolean champIntitule = cde.getCdeIntitule().length() >  0;
		boolean champMontant = cde.getCdeMontant().length() > 0 && (Integer)Integer.parseInt(cde.getCdeMontant().replaceAll("\\s+", "")) instanceof Integer;
		boolean champObservation = true;
		
		if(!champNum) {
			erreur.ajouterMessage("Le numéro de commande doit commencer par \"CDE\"");
		}
		if(!champClient) {
			erreur.ajouterMessage("Le nom du client ne doit pas être une chaîne vide");
		}
		if(!champDate) {
			erreur.ajouterMessage("Le format de la date est incorrect, il doit être au format JJ/MM/AAAA");
		}
		if(!champIntitule) {
			erreur.ajouterMessage("L'intitulé de la commande ne peut pas être vide");
		}
		if(!champMontant) {
			erreur.ajouterMessage("Le montant renseigné doit être un nombre, décimal ou non.");
		}
		if(!champObservation) {
			
		}
		
		
		if(champNum && champClient && champDate && champIntitule && champMontant) {
			return null;
		}else {
			return erreur;
		}
	}
	public List<CommandeDto> lister(){
		List<Commande> resultatQuery;
		List<CommandeDto> listeARetourner = new ArrayList<CommandeDto>();
		TypedQuery<Commande> q = em.createNamedQuery("SelectionneToutesLesCommandes", Commande.class);
		
		resultatQuery = (List<Commande>)q.getResultList();
		
		for(Commande cdeOrm: resultatQuery) {
			listeARetourner.add(convertirUnOrmEnDto(cdeOrm));
		}
		
		
		return listeARetourner;
	}
	//select
	public CommandeDto trouver(Integer id) {
		return new CommandeDto();
	}
	//put
	public Erreur modifier(CommandeDto cde) {
		
		//appel de verifier()
		return new Erreur();
	}
	//insert
	public Erreur inserer(CommandeDto cde) {
		//appel de verifier() = convertdtoorm = persit em
		return new Erreur();
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
		
		for(Observation obs: orm.getObservations()) {
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
		
		dtoToOrm.setObservations(null);//je sais pas
		
		
		return dtoToOrm;
	}
	
}

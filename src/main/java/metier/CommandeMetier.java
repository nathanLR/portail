package metier;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.validator.GenericValidator;

import communs.Erreur;
import communs.ErreurType;
import communs.database.orm.Commande;
import communs.database.orm.Observation;
import communs.database.orm.Profil;
import communs.dto.CommandeDto;
import communs.dto.ObservationDto;

public class CommandeMetier {
	private EntityManager em;
	
	public CommandeMetier(EntityManager em) {
		this.em = em;
	}
	
	public List<CommandeDto> lister(){
		List<CommandeDto> listeCommandes = new ArrayList<CommandeDto>();
		TypedQuery<Commande> requete = this.em.createNamedQuery("SelectionneToutesLesCommandes", Commande.class);
		
		for(Commande cde: requete.getResultList()) {
			listeCommandes.add(this.convertirUnOrmEnDto(cde));
		}
		return listeCommandes;
	}
	public List<CommandeDto> listerParFourchetteDeDates(Date dateDebut, Date dateFin){
		TypedQuery<Commande> requete = this.em.createQuery("select c from Commande c where c.cdeDate between :dateDebut and :dateFin", Commande.class);
		List<Commande> resultat = requete.setParameter("dateDebut", dateDebut).setParameter("dateFin", dateFin).getResultList();
		List<CommandeDto> listeCommandes = new ArrayList<CommandeDto>();
		for(Commande cde: resultat) {
			listeCommandes.add(this.convertirUnOrmEnDto(cde));
		}
		return listeCommandes;
	}
	public CommandeDto trouver(Integer cdeId) {
		System.out.println("Trouver cde");
		Commande cde = this.em.find(Commande.class, cdeId);
		if(cde != null) {
			return convertirUnOrmEnDto(cde);
		}else {
			return null;
		}
		
	}
	
	
	public Erreur inserer(CommandeDto cde, int prfId) {
		Erreur erreur = verifier(cde);
		
		if(erreur == null) {
			Commande cdeOrm = this.convetirUnDtoEnOrm(cde, new Commande(), prfId);
			this.em.persist(cdeOrm);
			for(Observation obs: cdeOrm.getObservations()) {
				this.em.persist(obs);
			}
		}
		
		return erreur;
	}
	
	public Erreur modifier(CommandeDto cde, int prfId) {
		Erreur erreur = verifier(cde);
		if(erreur == null) {
			Commande cdeAModifier = this.em.find(Commande.class, new Integer(cde.getCdeId()));
			if(cdeAModifier == null) {
				erreur = new Erreur(ErreurType.BDD);
				erreur.ajouterMessage("Aucune commande trouvée pour l'indentifiant " + cde.getCdeId());
			}else {
				this.convetirUnDtoEnOrm(cde, cdeAModifier, prfId);
			}
		}
		return erreur;
	}
	
	public Erreur supprimer(int cdeId) {
		Erreur erreur = null;
		Commande cdeASupprimer = this.em.find(Commande.class, new Integer(cdeId));
		if(cdeASupprimer != null) {
			
			this.em.remove(cdeASupprimer);
		}else {
			erreur = new Erreur(ErreurType.BDD);
			erreur.ajouterMessage("Aucune commande trouvée pour l'indentifiant " + cdeId);
			
		}
		return erreur;
	}
	
	public Erreur dupliquer(CommandeDto cde, int cdeADupliquerID, int prfId) {
		Erreur erreur = verifier(cde);
		
		if(erreur == null) {
			Commande cdeADupliquer = this.em.find(Commande.class, cdeADupliquerID);
			if(cdeADupliquer == null) {
				erreur = new Erreur(ErreurType.BDD);
				erreur.ajouterMessage("Aucune commande trouvée pour l'identitiant " + cde.getCdeId());
			}else {
				Commande cdeDupliquee = this.dupliquerUnOrm(cde, cdeADupliquer);
				this.em.persist(cdeDupliquee);
				for(Observation obs: cdeDupliquee.getObservations()) {
					this.em.persist(obs);
				}
			}
			
		}
		
		return erreur;
	}
	
	public Commande dupliquerUnOrm(CommandeDto cdeDto, Commande cdeOrm) {
		Commande cde = new Commande();
		cde.setCdeNum(cdeDto.getCdeNum());
		cde.setCdeClient(cdeOrm.getCdeClient());
		cde.setCdeDate(cdeOrm.getCdeDate());
		cde.setCdeIntitule(cdeOrm.getCdeIntitule());
		cde.setCdeMontant(cdeOrm.getCdeMontant());
		List<Observation> observations = new ArrayList<Observation>();
		if(cdeOrm.getObservations().size() > 0) {
			for(Observation observation: cdeOrm.getObservations()) {
				Observation obs = new Observation();
				obs.setCommande(cde);
				obs.setObsDateheure(observation.getObsDateheure());
				obs.setObsTexte(observation.getObsTexte());
				obs.setProfil(observation.getProfil());
				observations.add(observation);
			}
		}
		cde.setObservations(observations);
		
		return cde;
	}
	
	public CommandeDto convertirUnOrmEnDto(Commande cdeOrm) {
		CommandeDto cdeConverti = new CommandeDto();
		cdeConverti.setCdeId(cdeOrm.getCdeId());
		cdeConverti.setCdeNum(cdeOrm.getCdeNum());
		
		SimpleDateFormat traducteur = new SimpleDateFormat("dd/MM/yyyy");
		cdeConverti.setCdeDate(traducteur.format(cdeOrm.getCdeDate()));
		
		cdeConverti.setCdeClient(cdeOrm.getCdeClient());
		cdeConverti.setCdeMontant(cdeOrm.getCdeMontant() + "");
		cdeConverti.setCdeIntitule(cdeOrm.getCdeIntitule());
		
		if(cdeOrm.getObservations().size() > 0) {
			ObservationMetier obsMetier = new ObservationMetier(this.em);
			List<ObservationDto> obsConverties = new ArrayList<ObservationDto>();
			
			for(Observation obs: cdeOrm.getObservations()) {
				obsConverties.add(obsMetier.convertirUnOrmEnDto(obs, cdeConverti));
			}
			cdeConverti.setCdeObservations(obsConverties);
		}else {
			cdeConverti.setCdeObservations(new ArrayList<ObservationDto>());
		}
		
		return cdeConverti;
	}
	
	@SuppressWarnings("deprecation")
	public Commande convetirUnDtoEnOrm(CommandeDto cdeDto, Commande cdeOrm, int prfId) {
		cdeOrm.setCdeNum(cdeDto.getCdeNum());
		cdeOrm.setCdeClient(cdeDto.getCdeClient());
		
		String[] dateSplit = cdeDto.getCdeDate().split("/"); // 0 => jour; 1 => mois; 2 = année;
		cdeOrm.setCdeDate(new Date(Integer.parseInt(dateSplit[2]) - 1900, Integer.parseInt(dateSplit[1]) - 1, Integer.parseInt(dateSplit[0])));
		
		cdeOrm.setCdeIntitule(cdeDto.getCdeIntitule());
		cdeOrm.setCdeMontant(Double.parseDouble(cdeDto.getCdeMontant()));
		
		List<Observation> observations = new ArrayList<Observation>();
		ObservationMetier obsMetier = new ObservationMetier(this.em);
		Profil prfOrm = this.em.find(Profil.class, new Integer(prfId));
		
		for(ObservationDto obs: cdeDto.getCdeObservations()) {
			observations.add(obsMetier.convertirUnDtoEnOrm(obs, new Observation(), cdeOrm, prfOrm)); 
		}
		
		cdeOrm.setObservations(observations);
		
		
		 
		return cdeOrm;
	}
	
	public Erreur verifier(CommandeDto cde) {
		// verifier cdeClient, cdeNum, cdeDate, cdeIntitule, cdeMontant
		boolean client, num, intitule, montant, date;
		
		Erreur erreur = null;
		
		client = cde.getCdeClient().length() > 0;
		num = cde.getCdeNum().startsWith("CDE");
		intitule = cde.getCdeIntitule().length() > 0;
		
		try {
			Double.parseDouble(cde.getCdeMontant());
			montant = true;
		}catch(NumberFormatException nfe) {
			montant = false;
			nfe.printStackTrace();
		}
		
		String[] dateSplit = cde.getCdeDate().split("/");
		if(dateSplit.length != 3) {
			date = false;
		}else {
			date = GenericValidator.isDate(cde.getCdeDate(), "dd/MM/yyyy", true);
		}
		
		if(!(client && num && intitule && montant && date)) {
			erreur  = new Erreur(ErreurType.SAISIE);
			erreur.ajouterMessage("Erreur lors de la vérification des données.");
		}
		
		return erreur;
	}
}

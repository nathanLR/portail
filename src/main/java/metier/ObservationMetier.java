package metier;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import communs.Erreur;
import communs.ErreurType;
import communs.database.orm.Commande;
import communs.database.orm.Observation;
import communs.database.orm.Profil;
import communs.dto.CommandeDto;
import communs.dto.ObservationDto;

public class ObservationMetier {
	private EntityManager em;
	
	public ObservationMetier(EntityManager em) {
		this.em = em;
	}

	
	public Erreur inserer(ObservationDto obs, int cdeId, int prfId) {
		Erreur erreur = verifier(obs);
		if(erreur == null) {
			Profil prf = this.em.find(Profil.class, new Integer(prfId));
			Commande cde = this.em.find(Commande.class, new Integer(cdeId));
			if(prf == null || cde == null) {
				erreur = new Erreur(ErreurType.BDD);
				if(prf == null) erreur.ajouterMessage("Aucun profil trouvé pour l'identifiant " + prfId);
				if(cde == null) erreur.ajouterMessage("Aucune commande trouvée pour l'identifiant " + cdeId);
			}
			this.em.persist(this.convertirUnDtoEnOrm(obs, new Observation(), cde, prf));
		}
		
		return erreur;
	}
	
	public Erreur supprimer(List<ObservationDto> observations) {
		Erreur erreur = null;
		int iterateur = 0;
		while(erreur == null && iterateur < observations.size()) {
				Observation obsOrm = this.em.find(Observation.class, new Integer(observations.get(iterateur).getObsId()));
				if(obsOrm == null) {
					erreur = new Erreur(ErreurType.BDD);
					erreur.ajouterMessage("Aucune observation trouvée pour l'identifiant " + observations.get(iterateur).getObsId());
					
				}else {
					this.em.remove(obsOrm);
				}
				iterateur++;
		}
		return erreur;
	}
	
	public ObservationDto convertirUnOrmEnDto(Observation obsOrm, CommandeDto cde) {
		
		ProfilMetier prfMetier = new ProfilMetier(this.em);
		ObservationDto obsConverti = new ObservationDto();
		obsConverti.setObsId(obsOrm.getObsId());
		obsConverti.setCommande(cde);
		obsConverti.setObsTexte(obsOrm.getObsTexte());
		obsConverti.setProfil(prfMetier.convertirUnOrmEnDto(obsOrm.getProfil()));
		
		SimpleDateFormat traducteur = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		obsConverti.setObsDateHeure(traducteur.format(obsOrm.getObsDateheure()));
		
		return obsConverti;
	}
	
	
	public Observation convertirUnDtoEnOrm(ObservationDto obsDto, Observation obsOrm, Commande cdeOrm, Profil prfOrm) {
		
		
		obsOrm.setObsTexte(obsDto.getObsTexte());
		obsOrm.setObsDateheure(new Date(System.currentTimeMillis()));
		obsOrm.setCommande(cdeOrm);
		obsOrm.setProfil(prfOrm);
		
		return obsOrm;
	}

	public Erreur verifier(ObservationDto obs) {
		Erreur erreur = null;
		boolean texte = obs.getObsTexte().length() > 0;
		if(!texte) {
			erreur = new Erreur(ErreurType.SAISIE);
			erreur.ajouterMessage("Le commentaire de l'observation ne peut pas être une chaîne vide.");
		}
		return erreur;
	}
}

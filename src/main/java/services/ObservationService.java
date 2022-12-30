package services;

import java.util.List;

import javax.persistence.EntityManager;

import communs.Erreur;
import communs.dto.ObservationDto;
import database.Connexion;
import metier.ObservationMetier;

public class ObservationService {
	private EntityManager em;
	
	public ObservationService() {
		this.em = Connexion.getJPAEntityManager();
	}
	
	public Erreur actionCreation(ObservationDto obs, int cdeId, int prfId) {
		this.em.getTransaction().begin();
		ObservationMetier obsMetier = new ObservationMetier(this.em);
		Erreur erreur = obsMetier.inserer(obs, cdeId, prfId);
		if(erreur == null) {
			this.em.getTransaction().commit();
		}else {
			this.em.getTransaction().rollback();
		}
		return erreur;
	}
	
	public Erreur actionSuppression(List<ObservationDto> observations) {
		this.em.getTransaction().begin();
		ObservationMetier obsMetier = new ObservationMetier(this.em);
		Erreur erreur = obsMetier.supprimer(observations);
		if(erreur == null) {
			this.em.getTransaction().commit();
		}else {
			this.em.getTransaction().rollback();
		}
		return erreur;
	}
}

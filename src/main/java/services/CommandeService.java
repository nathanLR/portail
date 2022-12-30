package services;

import java.util.List;

import javax.persistence.EntityManager;

import communs.Erreur;
import communs.dto.CommandeDto;
import database.Connexion;
import metier.CommandeMetier;

public class CommandeService {
	private EntityManager em;
	
	public CommandeService() {
		this.em = Connexion.getJPAEntityManager();
	}
	
	public List<CommandeDto> actionLister(){
		CommandeMetier cdeMetier = new CommandeMetier(this.em);
		return cdeMetier.lister();
	}
	
	public CommandeDto actionTrouver(int cdeId) {
		CommandeMetier cdeMetier = new CommandeMetier(this.em);
		return cdeMetier.trouver(new Integer(cdeId));
	}
	
	public Erreur actionCreation(CommandeDto cde, int prfId) {
		this.em.getTransaction().begin();
		CommandeMetier cdeMetier = new CommandeMetier(this.em);
		Erreur erreur = cdeMetier.inserer(cde, prfId);
		if(erreur == null) {
			this.em.getTransaction().commit();
		}else {
			this.em.getTransaction().rollback();
		}
		
		return erreur;
	}
	
	public Erreur actionModification(CommandeDto cde, int prfId) {
		this.em.getTransaction().begin();
		CommandeMetier cdeMetier = new CommandeMetier(this.em);
		Erreur erreur = cdeMetier.modifier(cde, prfId);
		if(erreur == null) {
			this.em.getTransaction().commit();
		}else {
			this.em.getTransaction().rollback();
		}
		return erreur;
	}
	
	public Erreur actionSuppression(int cdeId) {
		this.em.getTransaction().begin();
		CommandeMetier cdeMetier = new CommandeMetier(this.em);
		Erreur erreur = cdeMetier.supprimer(cdeId);
		if(erreur == null) {
			this.em.getTransaction().commit();
		}else {
			this.em.getTransaction().rollback();
		}
		return erreur;
	}
	
	public Erreur actionDuplication(CommandeDto cde,int cdeADupliquerId, int prfId ) {
		this.em.getTransaction().begin();
		CommandeMetier cdeMetier = new CommandeMetier(this.em);
		Erreur erreur = cdeMetier.dupliquer(cde, cdeADupliquerId, prfId);
		if(erreur == null) {
			this.em.getTransaction().commit();
		}else {
			this.em.getTransaction().rollback();
		}
		return erreur;
	}
}

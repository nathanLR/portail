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
	public Erreur actionCreation(CommandeDto cde) {
		CommandeMetier cdeMetier = new CommandeMetier(this.em);
		return cdeMetier.inserer(cde);
		
	}
	public void actionSuppression(int cdeId) {
		CommandeMetier cdeMetier = new CommandeMetier(this.em);
		cdeMetier.supprimer(cdeId);
	}
	public Erreur actionModification(CommandeDto cde) {
		CommandeMetier cdeMetier = new CommandeMetier(this.em);
		return cdeMetier.modifier(cde);
	}
	public CommandeDto actionRecuperation(int cdeId) {
		CommandeMetier cdeMetier = new CommandeMetier(this.em);
		return cdeMetier.trouver(new Integer(cdeId));
	}
	public List<CommandeDto> actionLister() {
		CommandeMetier cdeMetier = new CommandeMetier(this.em);
		return cdeMetier.lister();
	}
}

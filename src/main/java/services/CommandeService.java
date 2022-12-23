package services;

import java.util.List;

import javax.persistence.EntityManager;

import communs.dto.CommandeDto;
import database.Connexion;
import metier.CommandeMetier;

public class CommandeService {
	private EntityManager em;
	
	public CommandeService() {
		this.em = Connexion.getJPAEntityManager();
		
	}
	public void actionCreation() {
		
	}
	public void actionSuppression() {
		
	}
	public void actionModification() {
		
	}
	public void actionVisualisation() {
		
	}
	public List<CommandeDto> actionLister() {
		CommandeMetier cdeMetier = new CommandeMetier(this.em);
		return cdeMetier.lister();
	}
}

package services;

import javax.persistence.EntityManager;

import communs.dto.ProfilDto;
import database.Connexion;
import metier.ProfilMetier;

public class ProfilService {
	private EntityManager em;
	
	public ProfilService() {
		this.em = Connexion.getJPAEntityManager();
	}
	public ProfilDto actionConnexion(String prfCode) {
		ProfilMetier prfMetier = new ProfilMetier(this.em);
		return prfMetier.trouver(prfCode);
	}
}

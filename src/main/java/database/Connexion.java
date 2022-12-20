package database;

import java.util.Map;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.eclipse.persistence.config.PersistenceUnitProperties;

public class Connexion {
	private static final String URL_BASE_DE_DONNEES = "jdbc:postgresql://localhost:5432/portail";
	private static final String UTILISATEUR = "nathan";
	private static final String MOT_DE_PASSE = "nathan";
	private static final String DRIVER_POSTGRESQL = "org.postgresql.Driver";
	private static final String PERSISTENCE_NAME = "portailnathan";
	
	private static EntityManagerFactory emf = null;
	
	private Connexion() {
		
	}
	public static void creerEntityManagerFactory() {
		if(emf == null) {
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_NAME, getProprietes());
		}
	}
	public static Map<String, String> getProprietes(){
		Map<String, String> proprietes = new HashMap<String, String>();
		proprietes.put(PersistenceUnitProperties.JDBC_URL, URL_BASE_DE_DONNEES + "?searchpath=public");
		proprietes.put(PersistenceUnitProperties.JDBC_DRIVER, DRIVER_POSTGRESQL);
		proprietes.put(PersistenceUnitProperties.JDBC_USER, UTILISATEUR);
		proprietes.put(PersistenceUnitProperties.JDBC_PASSWORD, MOT_DE_PASSE);
		return proprietes;
	}
	public static EntityManager getJPAEntityManager() {
		EntityManager ema = null;
		try {
			ema = emf.createEntityManager(emf.getProperties());
		}catch(PersistenceException pe) {
			pe.printStackTrace();
		}
		return ema;
	}
}

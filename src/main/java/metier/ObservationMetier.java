package metier;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import communs.Erreur;
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
	
	public Erreur verifier(ObservationDto obs) {
		Erreur erreur  = new Erreur();
		boolean champTexte = obs.getObsTexte().length() > 0;
		
		if(!champTexte) {
			erreur.ajouterMessage("Le message concernant l'observation ne peut pas être vide.");
		}
		
		
		return champTexte ? null : erreur;
	}
	//select
	public List<ObservationDto> lister(){
		return new ArrayList<ObservationDto>();
	}
	//select
	public ObservationDto trouver(Integer id) {
		return new ObservationDto();
	}
	//put
	public Erreur modifier(ObservationDto cde) {
		return new Erreur();
	}
	//insert
	public Erreur inserer(ObservationDto cde) {
		return new Erreur();
	}
	
	public static ObservationDto convertirUnOrmEnDto(Observation orm) {
		ObservationDto ormToDto = new ObservationDto();
		
		ormToDto.setObsId(orm.getObsId());
		ormToDto.setObsDateHeure(orm.getObsDateheure().toString());
		ormToDto.setObsTexte(orm.getObsTexte());
		ormToDto.setProfil(ProfilMetier.convertirUnOrmEnDto(orm.getProfil()));
		
		return ormToDto;	
	}
		
	
		
	
	
	public static Observation convertirUnDtoEnOrm(ObservationDto dto) {
		return new Observation();
	}
}

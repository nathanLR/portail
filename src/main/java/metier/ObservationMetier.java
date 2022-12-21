package metier;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import communs.Erreur;
import communs.database.orm.Commande;
import communs.database.orm.Observation;
import communs.dto.CommandeDto;
import communs.dto.ObservationDto;

public class ObservationMetier {
	private EntityManager em;
	
	public ObservationMetier(EntityManager em) {
		this.em = em;
	}
	
	public Erreur verifier(ObservationDto obs) {
		return new Erreur();
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
	
	public ObservationDto convertirUnOrmEnDto(Observation orm) {
		ObservationDto ormToDto = new ObservationDto();
		
		ormToDto.setObsId(orm.getObsId());
		ormToDto.setObsDateHeure(orm.getObsDateheure().toString());
		ormToDto.setObsTexte(orm.getObsTexte());
		
		
		ormToDto.setProfil(null); // ??
		ormToDto.setCommande(null); // ??
		
	return ormToDto;	
	}
		
	
		
	
	
	public Observation convertirUnDtoEnOrm(ObservationDto dto, Observation orm) {
		return new Observation();
	}
}

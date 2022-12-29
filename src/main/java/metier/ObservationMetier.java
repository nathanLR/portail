package metier;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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
	public List<ObservationDto> lister(){
		CommandeMetier cdeMetier = new CommandeMetier(this.em);
		List<ObservationDto> listeObservations = new ArrayList<ObservationDto>();
		TypedQuery<Observation> requete = this.em.createNamedQuery("SelectionneToutesLesObservations", Observation.class);
		
//		for(Observation obs: requete.getResultList()) {
//			listeObservations.add(this.convertirUnOrmEnDto(obs, cdeMetier.convertirUnOrmEnDto(obs)));
//		}
		return listeObservations;
	}
//	public ObservationDto trouver(Integer obsId) {
//		Observation obs = this.em.find(Observation.class, obsId);
//		return convertirUnOrmEnDto(obs);
//	}
	
	public static ObservationDto convertirUnOrmEnDto(Observation obsOrm, CommandeDto cde) {
		ObservationDto obsConverti = new ObservationDto();
		obsConverti.setObsId(obsOrm.getObsId());
		obsConverti.setCommande(cde);
		obsConverti.setObsTexte(obsOrm.getObsTexte());
		obsConverti.setProfil(ProfilMetier.convertirUnOrmEnDto(obsOrm.getProfil()));
		
		SimpleDateFormat traducteur = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		obsConverti.setObsDateHeure(traducteur.format(obsOrm.getObsDateheure()));
		
		return obsConverti;
	}
	
	// === ??? ===
	public static Observation convertirUnDtoEnOrm(ObservationDto obsDto, Observation obsOrm, Commande cdeOrm) {
		
		
		obsOrm.setObsTexte(obsDto.getObsTexte());
		obsOrm.setObsDateheure(new Date(System.currentTimeMillis()));
		obsOrm.setCommande(cdeOrm);
		
		//manque profil
		
		return obsOrm;
	}
	
	public static Observation convertirUnDtoEnOrm(ObservationDto obsDto, Observation obsOrm, Profil prfOrm) {

		obsOrm.setObsTexte(obsDto.getObsTexte());
		obsOrm.setObsDateheure(new Date(System.currentTimeMillis()));
		obsOrm.setProfil(prfOrm);
		//manque commande
		return obsOrm;
	}
	// ==========
}

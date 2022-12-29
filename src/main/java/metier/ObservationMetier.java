package metier;

import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;

import communs.database.orm.Observation;
import communs.dto.ObservationDto;

public class ObservationMetier {
	private EntityManager em;
	
	public ObservationMetier(EntityManager em) {
		this.em = em;
	}
	
	public static ObservationDto convertirUnOrmEnDto(Observation obsOrm) {
		ObservationDto obsConverti = new ObservationDto();
		obsConverti.setObsId(obsOrm.getObsId());
		obsConverti.setCommande(CommandeMetier.convertirUnOrmEnDto(obsOrm.getCommande()));
		obsConverti.setObsTexte(obsOrm.getObsTexte());
		obsConverti.setProfil(ProfilMetier.convertirUnOrmEnDto(obsOrm.getProfil()));
		
		SimpleDateFormat traducteur = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		obsConverti.setObsDateHeure(traducteur.format(obsOrm.getObsDateheure()));
		
		return obsConverti;
	}
//	public static Observation convertirUnDtoEnOrm(ObservationDto obsDto, Observation obsOrm) {
//		
//	}
}

package metier;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import communs.database.orm.Observation;
import communs.database.orm.Profil;
import communs.dto.ObservationDto;
import communs.dto.ProfilDto;

public class ProfilMetier {
	private EntityManager em;
	
	public ProfilMetier(EntityManager em) {
		this.em = em;
	}
	public static ProfilDto convertirUnOrmEnDto(Profil prfOrm) {
		ProfilDto prfConverti = new ProfilDto();
		prfConverti.setPrfId(prfOrm.getPrfId());
		prfConverti.setPrfCode(prfOrm.getPrfCode());
		prfConverti.setPrfMdp(prfOrm.getPrfMdp());
		
		if(prfOrm.getObservations().size() > 0) {
			List<ObservationDto> observationsConverties = new ArrayList<ObservationDto>();
			for(Observation obsOrm: prfOrm.getObservations()) {
				observationsConverties.add(ObservationMetier.convertirUnOrmEnDto(obsOrm));
			}
			prfConverti.setObservations(observationsConverties);
		}else {
			prfConverti.setObservations(new ArrayList<ObservationDto>());
		}
		return prfConverti;
	}
	
//	public static Profil convetirUnDtoEnOrm(ProfilDto prfDto, Profil prfOrm) {
//		
//	}
}

package metier;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import communs.database.orm.Commande;
import communs.database.orm.Observation;
import communs.dto.CommandeDto;
import communs.dto.ObservationDto;

public class CommandeMetier {
	private EntityManager em;
	
	public CommandeMetier(EntityManager em) {
		this.em = em;
	}
	
	public List<CommandeDto> lister(){
		List<CommandeDto> listeCommandes = new ArrayList<CommandeDto>();
		TypedQuery<Commande> requete = this.em.createNamedQuery("SelectionneToutesLesCommandes", Commande.class);
		
		for(Commande cde: requete.getResultList()) {
			listeCommandes.add(convertirUnOrmEnDto(cde));
		}
		return listeCommandes;
	}
	
	public static CommandeDto convertirUnOrmEnDto(Commande cdeOrm) {
		CommandeDto cdeConverti = new CommandeDto();
		cdeConverti.setCdeId(cdeOrm.getCdeId());
		cdeConverti.setCdeNum(cdeOrm.getCdeNum());
		
		SimpleDateFormat traducteur = new SimpleDateFormat("yyyy-MM-dd");
		cdeConverti.setCdeDate(traducteur.format(cdeOrm.getCdeDate()));
		
		cdeConverti.setCdeClient(cdeOrm.getCdeClient());
		cdeConverti.setCdeMontant(cdeOrm.getCdeMontant() + "");
		cdeConverti.setCdeIntitule(cdeOrm.getCdeIntitule());
		
		if(cdeOrm.getObservations().size() > 0) {
			List<ObservationDto> obsConverties = new ArrayList<ObservationDto>();
			
			for(Observation obs: cdeOrm.getObservations()) {
				obsConverties.add(ObservationMetier.convertirUnOrmEnDto(obs));
			}
			cdeConverti.setCdeObservations(obsConverties);
		}else {
			cdeConverti.setCdeObservations(new ArrayList<ObservationDto>());
		}
		
		return cdeConverti;
	}
	
//	public static Commande convetirUnDtoEnOrm(CommandeDto cdeDto, Commande cdeOrm) {
//		
//	}
}

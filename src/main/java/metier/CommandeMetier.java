package metier;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	
	
	public CommandeDto trouver(Integer cdeId) {
		
		Commande cde = this.em.find(Commande.class, cdeId);
		if(cde != null) {
			return convertirUnOrmEnDto(cde);
		}else {
			return null;
		}
		
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
		
		SimpleDateFormat traducteur = new SimpleDateFormat("dd/MM/yyyy");
		cdeConverti.setCdeDate(traducteur.format(cdeOrm.getCdeDate()));
		
		cdeConverti.setCdeClient(cdeOrm.getCdeClient());
		cdeConverti.setCdeMontant(cdeOrm.getCdeMontant() + "");
		cdeConverti.setCdeIntitule(cdeOrm.getCdeIntitule());
		
		if(cdeOrm.getObservations().size() > 0) {
			List<ObservationDto> obsConverties = new ArrayList<ObservationDto>();
			
			for(Observation obs: cdeOrm.getObservations()) {
				obsConverties.add(ObservationMetier.convertirUnOrmEnDto(obs, cdeConverti));
			}
			cdeConverti.setCdeObservations(obsConverties);
		}else {
			cdeConverti.setCdeObservations(new ArrayList<ObservationDto>());
		}
		
		return cdeConverti;
	}
	
	@SuppressWarnings("deprecation")
	public static Commande convetirUnDtoEnOrm(CommandeDto cdeDto, Commande cdeOrm) {
		cdeOrm.setCdeNum(cdeDto.getCdeNum());
		cdeOrm.setCdeClient(cdeDto.getCdeClient());
		
		String[] dateSplit = cdeDto.getCdeDate().split("/"); // 0 => jour; 1 => mois; 2 = année;
		cdeOrm.setCdeDate(new Date(Integer.parseInt(dateSplit[2]) - 1900, Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[0])));
		
		cdeOrm.setCdeIntitule(cdeDto.getCdeIntitule());
		cdeOrm.setCdeMontant(Double.parseDouble(cdeDto.getCdeMontant()));
		
		// === ???? ===
		List<Observation> observations = new ArrayList<Observation>(); 
		for(ObservationDto obs: cdeDto.getCdeObservations()) {
			observations.add(ObservationMetier.convertirUnDtoEnOrm(obs, null, cdeOrm)); // que mettre à la place de null ? comment récupérer l'orm de l'observation ?
		}
		cdeOrm.setObservations(observations);
		// ============
		return cdeOrm;
	}
}

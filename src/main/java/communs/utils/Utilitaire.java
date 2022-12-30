package communs.utils;



import javax.servlet.http.HttpServletRequest;

import communs.dto.CommandeDto;
import communs.dto.ObservationDto;

public class Utilitaire {

	public static CommandeDto peuplerCommandeDepuisRequete(HttpServletRequest request) {
		CommandeDto nouvelleCommandeDto = new CommandeDto();
		nouvelleCommandeDto.setCdeNum(request.getParameter("cdeNum"));
		nouvelleCommandeDto.setCdeClient(request.getParameter("cdeClient"));
		nouvelleCommandeDto.setCdeDate(request.getParameter("cdeDate"));
		nouvelleCommandeDto.setCdeIntitule(request.getParameter("cdeIntitule"));
		nouvelleCommandeDto.setCdeMontant(request.getParameter("cdeMontant"));
		
		return nouvelleCommandeDto;
	}

	public static ObservationDto peuplerObservationDepuisRequete(HttpServletRequest request) {
		ObservationDto nouvelleObservationDto = new ObservationDto();
		nouvelleObservationDto.setObsTexte(request.getParameter("cdeObservation"));

		return nouvelleObservationDto;
	}

}

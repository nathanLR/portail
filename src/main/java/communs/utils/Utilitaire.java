package communs.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		ObservationDto nouvelleObservationDto = new ObservationDto();
		
		nouvelleObservationDto.setObsTexte(request.getParameter("cdeObservation"));
		nouvelleObservationDto.setObsDateHeure(formatter.format(date));
		
		return nouvelleObservationDto;
	}
}

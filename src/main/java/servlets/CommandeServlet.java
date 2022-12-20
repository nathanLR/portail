package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import communs.dto.CommandeDto;
import communs.dto.ObservationDto;
import communs.dto.ProfilDto;

/**
 * Servlet implementation class CommandeServlet
 */
@WebServlet("/commande")
public class CommandeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static List<CommandeDto> commandes = null;
	static ProfilDto profilCommun = null;
	static int identifiantCommandeRequete = -1;
	
	private void initialisationDesDonnees() {
		if (commandes == null) {
			commandes = new ArrayList<CommandeDto>();
			CommandeDto c1 = new CommandeDto();
			c1.setCdeId(0);
			c1.setCdeClient("CMA-CGM");
			c1.setCdeDate("16/12/2022");
			c1.setCdeIntitule("Commande de conteneurs");
			c1.setCdeMontant("500000");
			c1.setCdeNum("CDE4587");
			c1.setCdeObservations(new ArrayList<ObservationDto>());
			commandes.add(c1);

			CommandeDto c2 = new CommandeDto();
			c2.setCdeId(1);
			c2.setCdeClient("BMW Group");
			c2.setCdeDate("10/12/2022");
			c2.setCdeIntitule("Commande de pieces de moteur");
			c2.setCdeMontant("25000");
			c2.setCdeNum("CDE4580");
			c2.setCdeObservations(new ArrayList<ObservationDto>());
			commandes.add(c2);

			CommandeDto c3 = new CommandeDto();
			c3.setCdeId(2);
			c3.setCdeClient("AIRBUS");
			c3.setCdeDate("15/12/2022");
			c3.setCdeIntitule("Commande de vitre d'avion");
			c3.setCdeMontant("1000000");
			c3.setCdeNum("CDE4567");
			c3.setCdeObservations(new ArrayList<ObservationDto>());
			commandes.add(c3);
		}
		if(profilCommun == null) {
			profilCommun = new ProfilDto();
			profilCommun.setPrfId(0);
			profilCommun.setPrfCode("Nathan");
			profilCommun.setPrfMdp("Nathan");
			profilCommun.setObservations(new ArrayList<ObservationDto>());
		}
	}
	
	private CommandeDto commandeDepuisId(int cdeId) {
		return listeDesCommandes().stream().filter(cmd -> cmd.getCdeId() == cdeId).findFirst().get();
	}
	private List<CommandeDto> listeDesCommandes() {
		return commandes;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initialisationDesDonnees();
		String reqAction = request.getParameter("action");
		RequestDispatcher disp;
		if (reqAction != null) {
			// action demandée
			request.setAttribute("action", reqAction);
			if(reqAction.equals("creer")) {
				//action de création => aucun champ n'est désactivé et les observations ne sont pas affichées (il n'y en a pas)
				
				disp = request.getRequestDispatcher("/jsp/saisiecommande.jsp");
				disp.forward(request, response);
			}else if(reqAction.equals("visualiser")) {
				//action de visualisation => tous les champs sont désactivés sauf observation et les observations son affichées.
				int cdeId = Integer.parseInt(request.getParameter("cdeId"));
				request.setAttribute("commande", commandeDepuisId(cdeId));
				identifiantCommandeRequete = cdeId;
				disp = request.getRequestDispatcher("/jsp/saisiecommande.jsp");
				disp.forward(request, response);
			}else if(reqAction.equals("modifier")) {
				// Action de modification => tous les champs sont activés sauf observation et numéro cde et les observations ne sont pas affichées
				int cdeId = Integer.parseInt(request.getParameter("cdeId"));
				identifiantCommandeRequete = cdeId;
				request.setAttribute("commande", commandeDepuisId(cdeId));
				
				disp = request.getRequestDispatcher("/jsp/saisiecommande.jsp");
				disp.forward(request, response);
			}else if(reqAction.equals("supprimer")) {
				//action de suppression 
				int cdeId = Integer.parseInt(request.getParameter("cdeId"));
				commandes.removeIf(cde -> cde.getCdeId() == cdeId);
				request.setAttribute("commandes", listeDesCommandes());
				disp = request.getRequestDispatcher("/jsp/listecommande.jsp");
				disp.forward(request, response);
			}else if(reqAction.equals("dupliquer")){
				
				int cdeId = Integer.parseInt(request.getParameter("cdeId"));
				
				identifiantCommandeRequete = cdeId;
				
				CommandeDto commandeADupliquer = commandeDepuisId(cdeId);
				
				request.setAttribute("commande", commandeADupliquer);
				disp = request.getRequestDispatcher("/jsp/saisiecommande.jsp");
				disp.forward(request, response);
			}else {
				//erreur => 404 ?
			}
		} else {
			// renvoyer les données à listecommande.jsp
			identifiantCommandeRequete = -1;
			request.setAttribute("commandes", listeDesCommandes());
			disp = request.getRequestDispatcher("/jsp/listecommande.jsp");
			disp.forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String typeDeSoumission = request.getParameter("typeDeSoumission");
		CommandeDto nouvelleCde = new CommandeDto();
		if(typeDeSoumission.equals("creer")) {
			
			nouvelleCde.setCdeNum(request.getParameter("cdeNum"));
			nouvelleCde.setCdeDate(request.getParameter("cdeDate"));
			nouvelleCde.setCdeClient(request.getParameter("cdeClient"));
			nouvelleCde.setCdeMontant(request.getParameter("cdeMontant"));
			nouvelleCde.setCdeIntitule(request.getParameter("cdeIntitule"));
			
			if(request.getParameter("cdeObservation").length() == 0) {
				nouvelleCde.setCdeObservations(new ArrayList<ObservationDto>());
			}else {
				List<ObservationDto> cdeObservations = new ArrayList<ObservationDto>();
				ObservationDto cdeObservation = new ObservationDto();
				
				cdeObservation.setObsTexte(request.getParameter("cdeObservation"));
				cdeObservation.setProfil(profilCommun);
				cdeObservation.setObsDateHeure(new Date().toString());
				cdeObservation.setCommande(nouvelleCde);
				cdeObservations.add(cdeObservation);
				nouvelleCde.setCdeObservations(cdeObservations);
			}
			
			
			commandes.add(nouvelleCde);
			request.setAttribute("commandes", listeDesCommandes());
			request.getRequestDispatcher("/jsp/listecommande.jsp").forward(request, response);	
			
			
		}else if(typeDeSoumission.equals("dupliquer")) {
			
			nouvelleCde.setCdeNum(request.getParameter("cdeNum"));
			nouvelleCde.setCdeDate(request.getParameter("cdeDate"));
			nouvelleCde.setCdeClient(request.getParameter("cdeClient"));
			nouvelleCde.setCdeMontant(request.getParameter("cdeMontant"));
			nouvelleCde.setCdeIntitule(request.getParameter("cdeIntitule"));
			//copie des observations
			nouvelleCde.setCdeObservations(commandeDepuisId(identifiantCommandeRequete).getCdeObservations());
			commandes.add(nouvelleCde);
			identifiantCommandeRequete = -1;
			request.setAttribute("commandes", listeDesCommandes());
			request.getRequestDispatcher("/jsp/listecommande.jsp").forward(request, response);	
			
			
		}else if(typeDeSoumission.equals("modifier")){
			
			CommandeDto commandeAModifier = commandeDepuisId(identifiantCommandeRequete);
			commandeAModifier.setCdeNum(request.getParameter("cdeNum"));
			commandeAModifier.setCdeDate(request.getParameter("cdeDate"));
			commandeAModifier.setCdeClient(request.getParameter("cdeClient"));
			commandeAModifier.setCdeMontant(request.getParameter("cdeMontant"));
			commandeAModifier.setCdeIntitule(request.getParameter("cdeIntitule"));
			identifiantCommandeRequete = -1;
			request.setAttribute("commandes", listeDesCommandes());
			request.getRequestDispatcher("/jsp/listecommande.jsp").forward(request, response);
			
		}else if(typeDeSoumission.equals("visualiser")){
			ObservationDto nouvelleObs = new ObservationDto();
			nouvelleObs.setObsTexte(request.getParameter("cdeObservation"));
			nouvelleObs.setProfil(profilCommun);
			nouvelleObs.setObsDateHeure(new Date().toString());
			nouvelleObs.setCommande(commandeDepuisId(identifiantCommandeRequete));
			commandeDepuisId(identifiantCommandeRequete).getCdeObservations().add(nouvelleObs);
			identifiantCommandeRequete = -1;
			request.setAttribute("commandes", listeDesCommandes());
			request.getRequestDispatcher("/jsp/listecommande.jsp").forward(request, response);
			//rajout d'une nouvelle observation
		}else {
			
		}
		
		
		
		
	}
	
	

}

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
			if(reqAction.equals("creer")) {
				//action de création => aucun champ n'est désactivé et les observations ne sont pas affichées (il n'y en a pas)
				request.setAttribute("action", reqAction);
				disp = request.getRequestDispatcher("/jsp/saisiecommande.jsp");
				disp.forward(request, response);
			}else if(reqAction.equals("visualiser")) {
				//action de visualisation => tous les champs sont désactivés sauf observation et les observations son affichées.
				int cdeId = Integer.parseInt(request.getParameter("cdeId"));
				request.setAttribute("commande", commandeDepuisId(cdeId));
				request.setAttribute("action", reqAction);
				disp = request.getRequestDispatcher("/jsp/saisiecommande.jsp");
				disp.forward(request, response);
			}else if(reqAction.equals("modifier")) {
				// Action de modification => tous les champs sont activés sauf observation et numéro cde et les observations ne sont pas affichées
				int cdeId = Integer.parseInt(request.getParameter("cdeId"));
				request.setAttribute("commande", commandeDepuisId(cdeId));
				request.setAttribute("action", reqAction);
				disp = request.getRequestDispatcher("/jsp/saisiecommande.jsp");
				disp.forward(request, response);
			}else if(reqAction.equals("supprimer")) {
				//action de suppression => tout est désactivé
				int cdeId = Integer.parseInt(request.getParameter("cdeId"));
				commandes.removeIf(cde -> cde.getCdeId() == cdeId);
				request.setAttribute("commandes", listeDesCommandes());
				disp = request.getRequestDispatcher("/jsp/listecommande.jsp");
				disp.forward(request, response);
			}else if(reqAction.equals("duppliquer")){
				
			}else {
				//erreur => 404 ?
			}
		} else {
			// renvoyer les données à listecommande.jsp
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
		CommandeDto newCde = new CommandeDto();
		newCde.setCdeNum(request.getParameter("cdeNum"));
		newCde.setCdeDate(request.getParameter("cdeDate"));
		newCde.setCdeClient(request.getParameter("cdeClient"));
		newCde.setCdeMontant(request.getParameter("cdeMontant"));
		newCde.setCdeIntitule(request.getParameter("cdeIntitule"));
		
		if(request.getParameter("cdeObservation").length() == 0) {
			newCde.setCdeObservations(new ArrayList<ObservationDto>());
		}else {
			List<ObservationDto> cdeObservations = new ArrayList<ObservationDto>();
			ObservationDto cdeObservation = new ObservationDto();
			cdeObservation.setOsbId(newCde.getCdeId());
			cdeObservation.setObsTexte(request.getParameter("cdeObservation"));
			cdeObservation.setProfil(profilCommun);
			cdeObservation.setObsDateHeure(new Date().toString());
			cdeObservation.setCommande(newCde);
			cdeObservations.add(cdeObservation);
			newCde.setCdeObservations(cdeObservations);
		}
		
		
		commandes.add(newCde);
		request.setAttribute("commandes", listeDesCommandes());
		request.getRequestDispatcher("/jsp/listecommande.jsp").forward(request, response);
	}
	
	

}

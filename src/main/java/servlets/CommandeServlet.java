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
import javax.servlet.http.HttpSession;

import communs.Erreur;
import communs.dto.CommandeDto;
import communs.dto.ObservationDto;
import communs.dto.ProfilDto;
import communs.utils.Utilitaire;
import database.Connexion;
import services.CommandeService;
import services.ObservationService;

/**
 * Servlet implementation class CommandeServlet
 */
@WebServlet("/commande")
public class CommandeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	
	static ProfilDto tmpProfilDto = null;
	
	
	
	
	
	private void initProfil() {
		if(tmpProfilDto == null) {
			tmpProfilDto = new ProfilDto();
			tmpProfilDto.setPrfId(1);
			tmpProfilDto.setPrfCode("nathan");
			tmpProfilDto.setPrfMdp("Nathandu13");
			tmpProfilDto.setObservations(new ArrayList<ObservationDto>());
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		initProfil();
		
		HttpSession session = request.getSession();
		session.removeAttribute("commande");
		RequestDispatcher disp;
		
		// action demandée par la requête;
		String reqAction = request.getParameter("action");
		CommandeService cdeService = new CommandeService();
		
		if (reqAction != null) {
			// differentes actions possibles: creer; visualiser; modifier; dupliquer; supprimer;
			
			int cdeId;
			request.setAttribute("action", reqAction);
			
			if(reqAction.equals("creer")) {
				disp = request.getRequestDispatcher("/jsp/saisiecommande.jsp");
				disp.forward(request, response);
			}
			
			if(reqAction.equals("visualiser")) {
				cdeId = Integer.parseInt(request.getParameter("cdeId"));
				CommandeDto cde = cdeService.actionTrouver(cdeId);
				if(cde != null) {
					session.setAttribute("commande", cde);
					disp = request.getRequestDispatcher("/jsp/saisiecommande.jsp");
					disp.forward(request, response);
				}else {
					disp = request.getRequestDispatcher("/jsp/404.jsp");
					disp.forward(request, response);
				}
				
			}
			
			if(reqAction.equals("modifier")) {
				cdeId = Integer.parseInt(request.getParameter("cdeId"));
				CommandeDto cde = cdeService.actionTrouver(cdeId);
				if(cde != null) {
					session.setAttribute("commande", cde);
					disp = request.getRequestDispatcher("/jsp/saisiecommande.jsp");
					disp.forward(request, response);
				}else {
					disp = request.getRequestDispatcher("/jsp/404.jsp");
					disp.forward(request, response);
				}
			}
			
			if(reqAction.equals("dupliquer")) {
				cdeId = Integer.parseInt(request.getParameter("cdeId"));
				CommandeDto cde = cdeService.actionTrouver(cdeId);
				if(cde != null) {
					cde.setCdeNum("");
					session.setAttribute("commande", cde);
					disp = request.getRequestDispatcher("/jsp/saisiecommande.jsp");
					disp.forward(request, response);
				}else {
					disp = request.getRequestDispatcher("/jsp/404.jsp");
					disp.forward(request, response);
				}
			}
			
			if(reqAction.equals("supprimer")) {
				cdeId = Integer.parseInt(request.getParameter("cdeId"));
				//rajouter supression obs
				Erreur erreurPotentielle = cdeService.actionSuppression(cdeId);
				session.removeAttribute("commande");
				if(erreurPotentielle != null) {
					disp = request.getRequestDispatcher("/jsp/404.jsp");
					disp.forward(request, response);
				}else {
					request.setAttribute("commandes", cdeService.actionLister());
					disp = request.getRequestDispatcher("/jsp/listecommande.jsp");
					disp.forward(request, response);
				}
				
			}
			

		} else {
			// recuperer les commandes de la BDD et les mettre dans la liste des commandes
			request.setAttribute("commandes", cdeService.actionLister());
			disp = request.getRequestDispatcher("/jsp/listecommande.jsp");
			disp.forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// différents actions possibles : creer; modifier; dupliquer; visualiser;
		
		// creer => cree une commande de 0
		
		// modifier => modifie un chmamp d'une commande existante
		
		// dupliquer => cree une commande sur le modèle d'une autre
		
		// visualiser => rajoute une observation
		
		// selon l'action, utilisation des données du formulaire + la commande qui a ete mise en session
		
		String reqAction = request.getParameter("action");
		RequestDispatcher disp;
		CommandeService cdeService = new CommandeService();
//		ObservationService obsService = new ObservationService();
		HttpSession session = request.getSession();
		
		if(reqAction.equals("creer")) {
			System.out.println("action creation");
			CommandeDto nouvelleCommande = Utilitaire.peuplerCommandeDepuisRequete(request);
			nouvelleCommande.setCdeObservations(new ArrayList<ObservationDto>()); // temporaire
			Erreur potentielleErreur = cdeService.actionCreation(nouvelleCommande);
			if(potentielleErreur != null) {
				// envoie d'une erreur à la vue
				disp = request.getRequestDispatcher("/jsp/404.jsp");
				disp.forward(request, response);
			}else {
				request.setAttribute("commandes", cdeService.actionLister());
				disp = request.getRequestDispatcher("/jsp/listecommande.jsp");
				disp.forward(request, response);
			}
		}
		
		if(reqAction.equals("modifier")) {
			// utilisation de la commande en session
			
			System.out.println("action modification");
			CommandeDto commandeAModifier = (CommandeDto)session.getAttribute("commande");
			CommandeDto commandeModifiee = Utilitaire.peuplerCommandeDepuisRequete(request);
			commandeModifiee.setCdeId(commandeAModifier.getCdeId());
			
			
			Erreur potentielleErreur = cdeService.actionModification(commandeModifiee);
			if(potentielleErreur != null) {
				// envoie d'une erreur à la vue
				disp = request.getRequestDispatcher("/jsp/404.jsp");
				disp.forward(request, response);
			}else {
				session.removeAttribute("commande");
				request.setAttribute("commandes", cdeService.actionLister());
				disp = request.getRequestDispatcher("/jsp/listecommande.jsp");
				disp.forward(request, response);
			}
			
		}
//		if(reqAction.equals("visualiser")) {
//			System.out.println("action visualisation");
//			int cdeIdRajoutObservation = ((CommandeDto)session.getAttribute("commande")).getCdeId();
//			
//		}
//		if(reqAction.equals("dupliquer")) {
//			System.out.println("action duplication");
//			CommandeDto cdeADupliquer = (CommandeDto)session.getAttribute("commande");
//			
//			Erreur potentielleErreur = cdeService.actionDuplication(cdeADupliquer.getCdeId(), request.getParameter("cdeNum"));
//			if(potentielleErreur != null) {
//				//erreur
//			}else {
//				session.removeAttribute("commande");
//				request.setAttribute("commandes", cdeService.actionLister());
//				disp = request.getRequestDispatcher("/jsp/listecommande.jsp");
//				disp.forward(request, response);
//			}
//		}
		
		

	}

}

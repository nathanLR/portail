package servlets;

import java.io.IOException;
import java.util.ArrayList;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import communs.Erreur;
import communs.ErreurType;
import communs.dto.CommandeDto;
import communs.dto.ObservationDto;
import communs.dto.ProfilDto;
import communs.utils.Utilitaire;
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
				System.out.println("action suppression");
				cdeId = Integer.parseInt(request.getParameter("cdeId"));
				ObservationService obsService = new ObservationService();
				Erreur erreurServiceObservation = null;
				Erreur erreurServiceCommande = null;
				Erreur erreurDeRetour = null;
				CommandeDto cde = (CommandeDto)session.getAttribute("commande");
				if(cde.getCdeObservations().size() > 0) {
					erreurServiceObservation = obsService.actionSuppression(cde.getCdeObservations());
				}
				erreurServiceCommande = cdeService.actionSuppression(cdeId);
				
				if(erreurServiceCommande != null && erreurServiceObservation != null) {
					erreurDeRetour = new Erreur(ErreurType.BDD);
					erreurDeRetour.ajouterMessage("Une erreur est survenue lors de la suppression de la commande N° " + cdeId);
				}
				session.removeAttribute("commande");
				if(erreurDeRetour != null) {
					request.setAttribute("erreur", erreurDeRetour);
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
			nouvelleCommande.setCdeObservations(new ArrayList<ObservationDto>());
			if(request.getParameter("cdeObservation").length() > 0) {
				ObservationDto nouvelleObservation = Utilitaire.peuplerObservationDepuisRequete(request);
				nouvelleCommande.getCdeObservations().add(nouvelleObservation);
			}
			Erreur potentielleErreur = cdeService.actionCreation(nouvelleCommande, tmpProfilDto.getPrfId());
			if(potentielleErreur != null) {
				// envoie d'une erreur à la vue
				request.setAttribute("erreur", potentielleErreur);
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
			
			Erreur potentielleErreur = cdeService.actionModification(commandeModifiee, tmpProfilDto.getPrfId());
			
			if(potentielleErreur != null) {
				// envoie d'une erreur à la vue
				request.setAttribute("erreur", potentielleErreur);
				disp = request.getRequestDispatcher("/jsp/404.jsp");
				disp.forward(request, response);
			}else {
				session.removeAttribute("commande");
				request.setAttribute("commandes", cdeService.actionLister());
				disp = request.getRequestDispatcher("/jsp/listecommande.jsp");
				disp.forward(request, response);
			}
			
		}
		
		if(reqAction.equals("visualiser")) {
			System.out.println("action visualisation");
			int cdeIdRajoutObservation = ((CommandeDto) session.getAttribute("commande")).getCdeId();
			int prfIdRajoutObservation = tmpProfilDto.getPrfId();
			ObservationDto obs = Utilitaire.peuplerObservationDepuisRequete(request);
			ObservationService obsService = new ObservationService();
			Erreur potentielleErreur = obsService.actionCreation(obs, cdeIdRajoutObservation, prfIdRajoutObservation);
			
			if(potentielleErreur == null) {
				
				session.removeAttribute("commande");
				request.setAttribute("commandes", cdeService.actionLister());
				disp = request.getRequestDispatcher("/jsp/listecommande.jsp");
				disp.forward(request, response);		
			}else {
				request.setAttribute("erreur", potentielleErreur);
				disp = request.getRequestDispatcher("/jsp/404.jsp");
				disp.forward(request, response);
				
			}
		}
		
		if(reqAction.equals("dupliquer")) {
			System.out.println("action duplication");
			CommandeDto cdeDupliquee = Utilitaire.peuplerCommandeDepuisRequete(request); 
			int cdeADupliquerID = ((CommandeDto)session.getAttribute("commande")).getCdeId();
			Erreur potentielleErreur = cdeService.actionDuplication(cdeDupliquee,cdeADupliquerID, tmpProfilDto.getPrfId());
			if(potentielleErreur != null) {
				request.setAttribute("erreur", potentielleErreur);
				disp = request.getRequestDispatcher("/jsp/404.jsp");
				disp.forward(request, response);
			}else {
				session.removeAttribute("commande");
				request.setAttribute("commandes", cdeService.actionLister());
				disp = request.getRequestDispatcher("/jsp/listecommande.jsp");
				disp.forward(request, response);
			}
		}
		
		

	}

}

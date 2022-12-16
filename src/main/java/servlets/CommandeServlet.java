package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import communs.dto.CommandeDto;
import communs.dto.ObservationDto;

/**
 * Servlet implementation class CommandeServlet
 */
@WebServlet("/commande")
public class CommandeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static List<CommandeDto> commandes = null;

	private List<CommandeDto> listeDesCommandes() {
		if (commandes == null) {
			commandes = new ArrayList<CommandeDto>();
			CommandeDto c1 = new CommandeDto();
			c1.setCdeId(0);
			c1.setCdeClient("CMA-CGM");
			c1.setCdeDate("16/12/2022");
			c1.setCdeIntitule("Commande de conteneurs");
			c1.setCdeMontant("500000");
			c1.setCdeNum("CDE4587");
			c1.setObservations(new ArrayList<ObservationDto>());
			commandes.add(c1);

			CommandeDto c2 = new CommandeDto();
			c2.setCdeId(1);
			c2.setCdeClient("BMW Group");
			c2.setCdeDate("10/12/2022");
			c2.setCdeIntitule("Commande de pieces de moteur");
			c2.setCdeMontant("25000");
			c2.setCdeNum("CDE4580");
			c2.setObservations(new ArrayList<ObservationDto>());
			commandes.add(c2);

			CommandeDto c3 = new CommandeDto();
			c3.setCdeId(2);
			c3.setCdeClient("AIRBUS");
			c3.setCdeDate("15/12/2022");
			c3.setCdeIntitule("Commande de vitre d'avion");
			c3.setCdeMontant("1000000");
			c3.setCdeNum("CDE4567");
			c3.setObservations(new ArrayList<ObservationDto>());
			commandes.add(c3);
		}
		return commandes;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String reqAction = request.getParameter("action");
		RequestDispatcher disp;
		if (reqAction != null) {
			// action demandée
			if(reqAction.equals("creer")) {
				disp = request.getRequestDispatcher("/jsp/saisiecommande.jsp");
				disp.forward(request, response);
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}

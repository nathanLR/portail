package servlets;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import communs.Erreur;
import communs.ErreurType;
import communs.dto.ProfilDto;
import services.ProfilService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProfilService prfService = new ProfilService();
		Erreur erreur;
		HttpSession session;
		String code = request.getParameter("code");
		String mdp =  request.getParameter("mdp");
		ProfilDto prfDto = prfService.actionConnexion(code);
		if(prfDto != null) {
			System.out.println("info: id => "+prfDto.getPrfCode()+"; mdp => "+prfDto.getPrfMdp()+";");
			if(mdp.equals(prfDto.getPrfMdp())) {
				session = request.getSession();
				session.setAttribute("profil", prfDto);
				response.sendRedirect("commande");
//				request.getRequestDispatcher("/commande").forward(request, response);
			}else {
				erreur = new Erreur(ErreurType.LOGIN);
				erreur.ajouterMessage("Mot de passe incorrect");
				request.setAttribute("erreur", erreur);
				request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
			}
		}else {
			erreur = new Erreur(ErreurType.LOGIN);
			erreur.ajouterMessage("Le nom d'utilisateur renseigné n'est pas connu de la base de données");
			request.setAttribute("erreur", erreur);
			request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
			
		}
		
		
		
	}

}

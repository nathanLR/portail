package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import database.Connexion;

import org.eclipse.persistence.exceptions.TransactionException;
import org.eclipse.persistence.internal.jpa.EJBQueryImpl;


import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Servlet implementation class JpqlServlet
 */
@WebServlet("/jpql")
public class JpqlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String UTF8 = "UTF-8";
	private static int PROFONDEUR_MAX = 3;
	
	
	
	public String objetToString(Object obj, int profondeur, boolean affichageTable) {
		StringBuilder sb = new StringBuilder();
		Class<?> classe = obj.getClass();
		
		if(
			classe.toString().startsWith("class java.lang")
			|| obj.toString().contains("not instantiated")
			|| profondeur == PROFONDEUR_MAX
				) {
			
			return obj.toString();	
			
		}
		
		if(classe.isArray()) {
			
			sb.append(classe.getComponentType() + "[]{");
			
			for(int i = 0; i < Array.getLength(obj); i++) {
				if(i > 0) {
				
					sb.append(",");
					
				}
				// recursion possible
				Object val = Array.get(obj, i);
				sb.append(val == null ? "null" : objetToString(obj, profondeur + 1, affichageTable));
			}
			sb.append("}");
			
		}else {
			do {
				
				if(affichageTable && sb.length() > 0) {
					sb.append("<br/>");
				}
				
				if(affichageTable) {
					sb.append("<b>" + classe.getName() + "</b>");
				}
				
				sb.append(affichageTable ? "<table>" : "<ul>");
				StringBuilder sb1 = new StringBuilder();
				StringBuilder sb2 = new StringBuilder();
				
				if(affichageTable) {
					sb1.append("<tr><td><i>");
					sb2.append("<tr><td>");
				}
				
				Field[] attrs = classe.getDeclaredFields();
				AccessibleObject.setAccessible(attrs, true);
				boolean premierChamp = true;
				
				for(Field attr: attrs) {
					if(!Modifier.isStatic(attr.getModifiers())) { // ???
						
						if(!premierChamp) {
							if(affichageTable) {
								sb1.append("</i></td><td><i>");
								sb2.append("</td><td>");
							}
						}
						
						if(!affichageTable) {
							sb1.append("<li><i>");
						}
						
						sb1.append(attr.getName());
						
						if(!affichageTable) {
							sb1.append("</li> :");
						}
						
						try {
							
							Object val = attr.get(obj);
							if(val == null) {
								if(affichageTable) {
									sb2.append("null");
								}else {
									sb1.append("null");
								}
							}else {
								if(affichageTable) {
									sb2.append(objetToString(val, profondeur +1, affichageTable));
								}else {
									sb1.append(objetToString(val, profondeur +1, affichageTable));
								}
							}
							
						}catch(Exception e) {
							if(affichageTable) {
								sb2.append("???");
							}else {
								sb1.append("???");
							}
						}
						
						premierChamp = false;
						
						if(!affichageTable) {
							sb1.append("</li>");
						}
						
					}
				}
				if(affichageTable) {
					sb1.append("</i></td></tr>");
					sb2.append("</td></tr>");
				}
				sb.append(sb1);
				sb.append(sb2);
				sb.append(affichageTable ? "</table>" : "</ul>");
				classe = classe.getSuperclass();
				
			}while(classe != null && !classe.getName().equals("java.lang.Object"));
		}
		return sb.toString();
	}
	
	
	
	
	private void select(StringBuilder html, String requete, EntityManager em, boolean affichageTable, boolean couleur, int maxResults) {
		Query query = em.createQuery(requete);
		System.out.println(query.unwrap(EJBQueryImpl.class).getDatabaseQuery().getSQLString()); // ???
		long debut = System.currentTimeMillis();
		
		if(maxResults > 0) {
			query.setMaxResults(maxResults);
		}
		
		@SuppressWarnings("unchecked")
		List<Object> liste = (List<Object>)query.getResultList();
		
		long fin = System.currentTimeMillis();
		
		if(affichageTable) {
			if(couleur) {
				html.append((fin - debut) + "ms");
			}
			html.append("<table style=\"\">");
		}else {
			html.append("<ul style=\"\">");
		}
		
		for(int i = 0; i < liste.size(); i++) {
			Object ligne = liste.get(i);
			String classeCss = couleur ? "class=\"td" + (i % 2) + "\"" : "";
			html.append("<tr>");
			if(ligne instanceof Object[]) { 
				for(Object cell:(Object[])ligne) {
					if(cell == null) {
						html.append("<td" + classeCss + "><b style=\"color:red\">null</b></td>");
					}else {
						html.append("<td" + classeCss + ">" + objetToString(cell, 1, affichageTable) + "</td>");
					}
				}
			}else {
				html.append("<td" + classeCss + ">" + objetToString(ligne, 1, affichageTable) + "</td>");
			}
			
			html.append("</tr>");
			
			if(couleur) {
				html.append("<tr><td>&nbsp;</td></tr>");
			}
		}
		html.append("</table>");
		
	}
	
	
	private void updateDelete(StringBuilder html, String requete, EntityManager em) {
		Query query = em.createQuery(requete);
		System.out.println(query.unwrap(EJBQueryImpl.class).getDatabaseQuery().getSQLString());
		
		em.getTransaction().begin();
		int nb = query.executeUpdate();
		
		try {
			em.getTransaction().commit();
			em.getEntityManagerFactory().getCache().evictAll(); // vide le cache
			html.append("<br />" + nb + "objets impactés.");
		}catch (TransactionException te) {
			em.getTransaction().rollback();
			html.append("erreur sur le commit" + te.getMessage());
		}
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding(UTF8);
		response.setCharacterEncoding(UTF8);
		StringBuilder html = new StringBuilder(660);
		String requete = request.getParameter("requete");
		Integer maxResults = 0;
		
		try {
			Integer.valueOf(request.getParameter("maxResults"));
		}catch(NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
		String action = request.getParameter("action");
		html.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\"http://www.w3.org/TR/html4/loose.dtd\">"
				+ "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\">"
				+ "<title>Requêtes JPQL</title>"
				+ "<style>table{border-spacing:0;border-collapse:collapse;}td{border:1px solid black;}td.td0{background:#F3FFFF;}td.td1{background:#EEEEEE;}</style>"
				+ "<body>"
				+ "<form action=\"jpql\" method=\"get\">"
				+ "<textarea name=\"requete\"rows=\"5\" cols=\"128\">"
				+ (requete == null ? "" : requete)
				+ "</textarea>&nbsp;maxResults<input type=\"text\" name=\"maxResults\" value=\""+maxResults+"\">"
				+ "<input type=\"submit\" name=\"action\" value=\"Executer\">"
				+ "<input type=\"submit\"name=\"action\" value=\"Configuration\">");
		
		if(requete != null) {
			try {
				requete = requete.trim();
				EntityManager em = Connexion.getJPAEntityManager();
				
				if(action.equals("Executer")) {
					if(requete.toUpperCase().startsWith("SELECT")) {
						
						select(html, requete, em, true, true, maxResults);
						
					}else if(requete.toUpperCase().startsWith("UPDATE") || requete.toUpperCase().startsWith("DELETE")){
						
						updateDelete(html, requete, em);
						
					}
				}
			}catch(Exception e) {
				html.append("<br/><b style=\"color:red\"><pre>" + e.getMessage() + "</pre></b><br/><br/>" + e.getCause());
			}
		}
		
		html.append("</body></html>");
		response.getOutputStream().write(html.toString().getBytes(UTF8));
		response.getOutputStream().flush();
	}
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

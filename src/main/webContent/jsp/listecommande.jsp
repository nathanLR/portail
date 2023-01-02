<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import = "java.text.SimpleDateFormat" %>
<%@ page import = "java.util.Date" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/datatables.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
	<c:if test="${requestScope.erreur != null}">
		<div class="modal fade in" id="erreurModal" tabindex="-1"
			role="dialog" aria-labelledby="basicModal" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h2 class="modal-title" id="myModalLabel">Une erreur est
							survenue</h2>
					</div>
					<div class="modal-body">
						<p>
							Type de l'erreur : <strong>${requestScope.erreur.erreurType}</strong>
						</p>
						<p>
							Message d'erreur : <strong>${requestScope.erreur.message}</strong>
						</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
					</div>
				</div>
			</div>
		</div>
	</c:if>
	<div class="container">
		<div class="row">
			<div class="col col-md-10 col-md-offset-1 commandesPage">
				<div class="row">
					<div class="col col-md-3">
						<p>
							<a class="btn btn-primary" href="commande?action=creer">Ajouter
								une commande</a>
						</p>
					</div>
					<div class="col col-md-9 text-right">
							<form method="GET" action="commande" class="form-inline" id="dateForm">
								<div class="form-group">
									<label class="sr-only" for=dateDebut>Date de début</label>
    								<input 
    									type="text" 
    									class="form-control" 
    									id="dateDebut" 
    									placeholder="JJ/MM/AAAA" 
    									name="dateDebut"
    									value="<%=new SimpleDateFormat("dd/MM/YYYY").format(((Date[])session.getAttribute("datesDeRecherche"))[0]) %>"
    								>
								</div>
								<div class="form-group">
									<label class="sr-only" for=dateFin>Date de fin</label>
    								<input 
    									type="text" 
    									class="form-control" 
    									id="dateFin" 
    									placeholder="JJ/MM/AAAA" 
    									name="dateFin"
    									value="<%=new SimpleDateFormat("dd/MM/YYYY").format(((Date[])session.getAttribute("datesDeRecherche"))[1]) %>"
    								>
								</div>
								<button type="submit" class="btn btn-primary" name="action" value="lister" id="lister">LISTER</button>
							</form>
					</div>
				</div>
				<div class="row">
					<div class="col col-md-12">

						<table id="commandesListe"
							class="display customTableStyle cell-border" style="width: 100%">
							<thead>
								<tr>
									<th>N°</th>
									<th>Client</th>
									<th>Date Référence</th>
									<th>Total H.T</th>
									<th>Actions</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${requestScope.commandes}" var="commande">
									<tr>
										<td><a
											href="commande?action=modifier&cdeId=${commande.cdeId}">${commande.cdeNum}</a>
										</td>
										<td>${commande.cdeClient}</td>
										<td class="text-right">${commande.cdeDate}</td>
										<td class="text-right">${commande.cdeMontant}</td>
										<td><a
											href="commande?action=visualiser&cdeId=${commande.cdeId}"
											class="btn btn-primary"> <span
												class="glyphicon glyphicon-zoom-in"></span>
										</a> <a href="commande?action=modifier&cdeId=${commande.cdeId}"
											class="btn btn-primary"> <span
												class="glyphicon glyphicon-pencil"></span>
										</a> <a href="commande?action=dupliquer&cdeId=${commande.cdeId}"
											class="btn btn-primary"> <span
												class="glyphicon glyphicon-file"></span>
										</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>



			</div>
		</div>
	</div>







	<script
		src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/datatables.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/validate-date.js"></script>
	<script src="${pageContext.request.contextPath}/js/listePage.js"></script>
	<script src="${pageContext.request.contextPath}/js/commandesPage.js"></script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	<div class="container">
		<div class="row">
			<div class="col col-md-8 col-md-offset-2">
				<h2>Saisir une nouvelle commande</h2>
				<p>${requestScope.commande.cdeNum }</p>
				<div class="row">
					<div class="col col-md-8 col-md-offset-2">
						<form method="POST" action="commande">
							<div class="form-group">
								<label class="sr-only" for="numero_cde">N° de Cde</label>
								<div class="input-group">
									<div class="input-group-addon">N° de Cde</div>
									<input type="text" class="form-control" id="numero_cde"
										name="cdeNum"
										<%=!request.getAttribute("action").equals("creer")? "disabled" : ""%>
										value="${requestScope.commande.cdeNum}">
								</div>
							</div>
							<div class="form-group">
								<label class="sr-only" for="date_creation">Date réf.</label>
								<div class="input-group">
									<div class="input-group-addon">Date réf.</div>
									<input type="text" class="form-control" id="date_creation"
									
										name="cdeDate" value="${requestScope.commande.cdeDate }">
								</div>
							</div>
							<div class="form-group">
								<label class="sr-only" for="client">Client</label>
								<div class="input-group">
									<div class="input-group-addon">Client</div>
									<input type="text" class="form-control" id="client"
										name="cdeClient" value="${requestScope.commande.cdeClient }">
								</div>
							</div>
							<div class="form-group">
								<label class="sr-only" for="montant">Montant</label>
								<div class="input-group">
									<div class="input-group-addon">Montant</div>
									<input type="text" class="form-control" id="montant"
										name="cdeMontant" value="${requestScope.commande.cdeMontant }">
								</div>
							</div>
							<div class="form-group">
								<label for="intitule">Intitulé :</label> <input type="text"
									class="form-control" id="intitule" name="cdeIntitule"
									value="${requestScope.commande.cdeIntitule }">
							</div>
							<div class="form-group">
								<label for="observation">Observation :</label>
								<textarea class="form-control" rows="3" id="observation"
									name="cdeObservation"></textarea>
							</div>
							<div class="form-group">
								<a class="btn btn-primary" href="commande"><span
									class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span></a>
								<button type="submit" class="btn btn-success"
									<%=request.getAttribute("action").equals("visualiser") ? "disabled" : ""%>>
									<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
								</button>
								<a class="btn btn-danger"
									href="commande?action=supprimer&cdeId=${requestScope.commande.cdeId }"><span
									class="glyphicon glyphicon-trash" aria-hidden="true"></span></a>
							</div>

						</form>
					</div>
				</div>
			</div>
		</div>
		<c:if test="${requestScope.action != 'creer'}">
			<div class="row">
				<div class="col-md-8 col-md-offset-2">
					<h2>Historique des observations</h2>
					<c:forEach items="${requestScope.commande.cdeObservations}" var="observation">
					
					
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label for="observationHistorique">Observation :</label>
								<textarea class="form-control" rows="3" id="observationHistorique" disabled>${observation.obsTexte}</textarea>
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-group">
								<label for="intituleHistorique">Profil :</label> <input type="text"
									class="form-control" id="intituleHistorique" value="${observation.profil.prfCode}" disabled>
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-group">
								<label for="dateHistorique">Date :</label> <input type="text"
									class="form-control" id="dateHistorique"  value="${observation.obsDateHeure}" disabled>
							</div>
						</div>
					</div>
					</c:forEach>
				</div>
			</div>
		</c:if>
	</div>
	<script
		src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/datatables.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/commandesPage.js"></script>
</body>
</html>
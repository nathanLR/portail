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
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col col-md-8 col-md-offset-2 commandesPage">
				<p><a class="btn btn-primary" href="commande?action=creer">Ajouter une commande</a></p>
				<table id="commandesListe" class="display customTableStyle cell-border" style="width: 100%">
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
								<td><a href="commande?action=modifier&cdeId=${commande.cdeId}">${commande.cdeNum}</a>
								</td>
								<td>${commande.cdeClient}</td>
								<td>${commande.cdeDate}</td>
								<td>${commande.cdeMontant}</td>
								<td>
									<a href="commande?action=visualiser&cdeId=${commande.cdeId}" class="btn btn-primary">
										<span class="glyphicon glyphicon-zoom-in"></span>
									</a>
									<a href="commande?action=modifier&cdeId=${commande.cdeId}" class="btn btn-primary">
										<span class="glyphicon glyphicon-pencil"></span>
									</a>
									<a href="commande?action=dupliquer&cdeId=${commande.cdeId}" class="btn btn-primary">
										<span class="glyphicon glyphicon-file"></span>
									</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>







	<script
		src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/datatables.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/commandesPage.js"></script>
</body>
</html>
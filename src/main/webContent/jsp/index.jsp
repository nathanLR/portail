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
</head>
<body style="padding-top: 200px">
	<c:if test="${requestScope.erreur != null}">
		<div class="modal fade in" id="erreurModal" tabindex="-1" role="dialog"
			aria-labelledby="basicModal" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h2 class="modal-title" id="myModalLabel">Une erreur est survenue</h2>
					</div>
					<div class="modal-body">
						<p>Type de l'erreur : <strong>${requestScope.erreur.erreurType}</strong></p>
						<p>Message d'erreur : <strong>${requestScope.erreur.message}</strong></p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
					</div>
				</div>
			</div>
		</div>
	</c:if>
	<div class="container">
		<div class="row ">
			<div class="col col-md-4 col-md-push-4" style="margin:auto">
				<form action="login" method="POST">
					<div class="mb-3">
						<label for="code" class="form-label">Utilisateur</label> <input
							type="text" class="form-control" id="code" name="code"
							value="${requestScope.code}">
					</div>
					<div class="mb-3">
						<label for="mdp" class="form-label">Mot de Passe</label> <input
							type="password" class="form-control" id="mdp" name="mdp">
					</div>
					<button type="submit" class="btn btn-primary btn-sm btn-block" style="margin-top:10px">SE CONNECTER</button>
				</form>
			</div>
		</div>
	</div>
	<script	src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/listePage.js"></script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
				<div class="row">
					<div class="col col-md-8 col-md-offset-2">
						<form method="POST" action="commande">
							<div class="form-group">
								<label class="sr-only" for="numero_cde">N° de Cde</label>
								<div class="input-group">
									<div class="input-group-addon">N° de Cde</div>
									<input type="text" class="form-control" id="numero_cde"
										name="cdeId">
								</div>
							</div>
							<div class="form-group">
								<label class="sr-only" for="date_creation">Date réf.</label>
								<div class="input-group">
									<div class="input-group-addon">Date réf.</div>
									<input type="text" class="form-control" id="date_creation"
										name="cdeDate">
								</div>
							</div>
							<div class="form-group">
								<label class="sr-only" for="client">Client</label>
								<div class="input-group">
									<div class="input-group-addon">Client</div>
									<input type="text" class="form-control" id="client"
										name="cdeClient">
								</div>
							</div>
							<div class="form-group">
								<label class="sr-only" for="montant">Montant</label>
								<div class="input-group">
									<div class="input-group-addon">Montant</div>
									<input type="text" class="form-control" id="montant"
										name="cdeMontant">
								</div>
							</div>
							<div class="form-group">
								<label for="intitule">Intitulé :</label>
								<input type="text" class="form-control" id="intitule" name="cdeIntitule">
							</div>
							<div class="form-group">
								<label for="observation">Observation :</label>
								<textarea class="form-control" rows="3" id="observation" name="cdeObservation"></textarea>
							</div>
							<div class="form-group">
							<a class="btn btn-primary"><span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span></a>
							<button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span></button>
							<button type="submit" class="btn btn-danger"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button>
							</div>
							
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-8 col-md-offset-2">
				<h2>Historique des observations</h2>
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
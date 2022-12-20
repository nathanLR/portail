$(document).ready(function() {
	$("#commandesListe").DataTable({
		searching: false,
		paging: false
	});
	
	let boutonDeSoumission = $("#boutonDeSoumission");
	console.log(boutonDeSoumission);
	boutonDeSoumission.click(function(event) {
		event.preventDefault();
		console.log("cliqué!")
		$.ajax({
			url:"commande",
			method: "POST",
			data:$("form").serialize(),
			success: () => {console.log("succes")}
		});
	});
	
});
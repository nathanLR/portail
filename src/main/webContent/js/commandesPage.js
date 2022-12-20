$(document).ready(function() {
	$("#commandesListe").DataTable({
		searching: false,
		paging: false
	});
	
	let bouton = document.getElementById("boutonDeSoumission");
	let formulaire = document.getElementById("formulaireDeSaisie");
	
	let numeroCde, dateCde, clientCde, montantCde, intituleCde;
	
	
	if(bouton != null){
			bouton.addEventListener("click", (event) => {
				event.preventDefault();
				numeroCde = document.getElementById("numero_cde").value;
				dateCde = document.getElementById("date_creation").value;
				clientCde = document.getElementById("client").value;
				montantCde = document.getElementById("montant").value;
				intituleCde = document.getElementById("intitule").value;
				
				if(numeroCde.length > 0 && formatDate(dateCde) && clientCde.length > 0 && intituleCde.length > 0 & montantCde.length > 0){
					if(formulaire.requestSubmit){
						formulaire.requestSubmit(bouton);
					}	
				}else{
					alert("erreur dans le formulaire")
				}
				
			
			
		})
	}
	
	console.log(bouton, formulaire);
	
});

const formatDate = (date) =>{
	const dateSplit = date.split("/");
	const jour = parseInt(dateSplit[0])>=1 && parseInt(dateSplit[0])<=31;
	const mois = parseInt(dateSplit[1])>=1 && parseInt(dateSplit[1])<=12;
	const annee = parseInt(dateSplit[2])>=2022;
	if(jour && mois && annee){
		return true;
	}else{
		return false;
	}
}
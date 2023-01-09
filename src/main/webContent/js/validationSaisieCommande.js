$("#formulaireDeSaisie").submit(function(event){
	
	const cdeNum = $("#numero_cde").val().slice(0,3) == "CDE";
	const cdeDate = validateDate($("#date_creation").val(), "boolean", "dd/mm/yyyy");
	const cdeClient = $("#client").val().length > 0;
	const cdeMontant = parseMontant($("#montant").val());
	const cdeIntitule = $("#intitule").val().length > 0;
	
	if(!(cdeNum && cdeDate && cdeClient && cdeMontant & cdeIntitule)){
		event.preventDefault();
		alert("Erreur de saisie dans le formulaire");
	}
});

const parseMontant = (valeur) =>{
	let pm = parseInt(valeur, 10);
	if(isNaN(pm)) return false;
	if(pm <= 0) return false;
	return true;
}
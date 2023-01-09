
$( "#formulaireDate" ).submit(function( event ) {

  const dd = validateDate($("#dateDebut").val(), "boolean", "dd/mm/yyyy");
  const df = validateDate($("#dateFin").val(), "boolean", "dd/mm/yyyy");
  if(!(dd && df)){
	  event.preventDefault();
	  alert("dates incorrectes");
  }
});
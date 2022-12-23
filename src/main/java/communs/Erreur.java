package communs;




public class Erreur {
	private String messageErreur;
	public Erreur() {
		this.messageErreur = "";
	}
	public String getMessageErreur() {
		return messageErreur;
	}

	public void setMessageErreur(String messageErreur) {
		this.messageErreur = messageErreur;
	}
	public void ajouterMessage(String nouveauMessage) {
		this.messageErreur = this.messageErreur.concat(";"+nouveauMessage);
	}
	
}

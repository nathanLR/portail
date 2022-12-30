package communs;




public class Erreur {
	private String messageErreur;
	private ErreurType erreurType;
	public Erreur(ErreurType erreurType) {
		this.messageErreur = "";
		this.erreurType = erreurType;
	}
	public String getMessageErreur() {
		return messageErreur;
	}

	public ErreurType getErreurType() {
		return erreurType;
	}
	public void setErreurType(ErreurType erreurType) {
		this.erreurType = erreurType;
	}
	public void setMessageErreur(String messageErreur) {
		this.messageErreur = messageErreur;
	}
	public void ajouterMessage(String nouveauMessage) {
		this.messageErreur = this.messageErreur.concat(";"+nouveauMessage);
	}
	public String getMessage() {
		return this.messageErreur;
	}
	
}

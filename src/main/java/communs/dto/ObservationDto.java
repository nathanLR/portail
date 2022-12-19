package communs.dto;

public class ObservationDto {
	private int osbId;
	private String obsDateHeure;
	private String obsTexte;
	private CommandeDto commande;
	private ProfilDto profil;
	public ObservationDto() {
		
	}
	public int getOsbId() {
		return osbId;
	}
	public ProfilDto getProfil() {
		return profil;
	}
	public void setProfil(ProfilDto profil) {
		this.profil = profil;
	}
	public void setOsbId(int osbId) {
		this.osbId = osbId;
	}
	public String getObsDateHeure() {
		return obsDateHeure;
	}
	public void setObsDateHeure(String obsDateHeure) {
		this.obsDateHeure = obsDateHeure;
	}
	public String getObsTexte() {
		return obsTexte;
	}
	public void setObsTexte(String obsTexte) {
		this.obsTexte = obsTexte;
	}
	public CommandeDto getCommande() {
		return commande;
	}
	public void setCommande(CommandeDto commande) {
		this.commande = commande;
	}
}

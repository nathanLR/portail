package communs.dto;

public class ObservationDto {
	private static int incrementation = 0;
	private int obsId;
	private String obsDateHeure;
	private String obsTexte;
	private CommandeDto commande;
	private ProfilDto profil;
	public ObservationDto() {
		this.obsId = incrementation++;
	}
	public int getObsId() {
		return obsId;
	}
	public ProfilDto getProfil() {
		return profil;
	}
	public void setProfil(ProfilDto profil) {
		this.profil = profil;
	}
	public void setObsId(int osbId) {
		this.obsId = osbId;
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

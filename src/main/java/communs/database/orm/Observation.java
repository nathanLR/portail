package communs.database.orm;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;


/**
 * The persistent class for the observation database table.
 * 
 */
@Table(name ="observation", schema = "portail")
@Entity
@NamedQuery(name="Observation.findAll", query="SELECT o FROM Observation o")
public class Observation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="obs_id")
	private Integer obsId;

	@Column(name="obs_dateheure")
	private Time obsDateheure;

	@Column(name="obs_texte")
	private String obsTexte;

	//bi-directional many-to-one association to Commande
	@ManyToOne
	@JoinColumn(name="cde_id")
	private Commande commande;

	//bi-directional many-to-one association to Profil
	@ManyToOne
	@JoinColumn(name="prf_id")
	private Profil profil;

	public Observation() {
	}

	public Integer getObsId() {
		return this.obsId;
	}

	public void setObsId(Integer obsId) {
		this.obsId = obsId;
	}

	public Time getObsDateheure() {
		return this.obsDateheure;
	}

	public void setObsDateheure(Time obsDateheure) {
		this.obsDateheure = obsDateheure;
	}

	public String getObsTexte() {
		return this.obsTexte;
	}

	public void setObsTexte(String obsTexte) {
		this.obsTexte = obsTexte;
	}

	public Commande getCommande() {
		return this.commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	public Profil getProfil() {
		return this.profil;
	}

	public void setProfil(Profil profil) {
		this.profil = profil;
	}

}
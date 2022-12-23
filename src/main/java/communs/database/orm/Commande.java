package communs.database.orm;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the commande database table.
 * 
 */
@Table(name = "commande", schema = "portail")
@Entity
public class Commande implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cde_id")
	private Integer cdeId;

	@Column(name="cde_client")
	private String cdeClient;

	@Temporal(TemporalType.DATE)
	@Column(name="cde_date")
	private Date cdeDate;

	@Column(name="cde_intitule")
	private String cdeIntitule;

	@Column(name="cde_montant")
	private double cdeMontant;

	@Column(name="cde_num")
	private String cdeNum;

	//bi-directional many-to-one association to Observation
	@OneToMany(mappedBy="commande")
	private List<Observation> observations;

	public Commande() {
	}

	public Integer getCdeId() {
		return this.cdeId;
	}

	public void setCdeId(Integer cdeId) {
		this.cdeId = cdeId;
	}

	public String getCdeClient() {
		return this.cdeClient;
	}

	public void setCdeClient(String cdeClient) {
		this.cdeClient = cdeClient;
	}

	public Date getCdeDate() {
		return this.cdeDate;
	}

	public void setCdeDate(Date cdeDate) {
		this.cdeDate = cdeDate;
	}

	public String getCdeIntitule() {
		return this.cdeIntitule;
	}

	public void setCdeIntitule(String cdeIntitule) {
		this.cdeIntitule = cdeIntitule;
	}

	public double getCdeMontant() {
		return this.cdeMontant;
	}

	public void setCdeMontant(double cdeMontant) {
		this.cdeMontant = cdeMontant;
	}

	public String getCdeNum() {
		return this.cdeNum;
	}

	public void setCdeNum(String cdeNum) {
		this.cdeNum = cdeNum;
	}

	public List<Observation> getObservations() {
		return this.observations;
	}

	public void setObservations(List<Observation> observations) {
		this.observations = observations;
	}

	public Observation addObservation(Observation observation) {
		getObservations().add(observation);
		observation.setCommande(this);

		return observation;
	}

	public Observation removeObservation(Observation observation) {
		getObservations().remove(observation);
		observation.setCommande(null);

		return observation;
	}

}
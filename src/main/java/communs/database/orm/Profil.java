package communs.database.orm;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the profil database table.
 * 
 */
@Entity
@NamedQuery(name="Profil.findAll", query="SELECT p FROM Profil p")
public class Profil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="prf_id")
	private Integer prfId;

	@Column(name="prf_code")
	private String prfCode;

	@Column(name="prf_mdp")
	private String prfMdp;

	//bi-directional many-to-one association to Observation
	@OneToMany(mappedBy="profil")
	private List<Observation> observations;

	public Profil() {
	}

	public Integer getPrfId() {
		return this.prfId;
	}

	public void setPrfId(Integer prfId) {
		this.prfId = prfId;
	}

	public String getPrfCode() {
		return this.prfCode;
	}

	public void setPrfCode(String prfCode) {
		this.prfCode = prfCode;
	}

	public String getPrfMdp() {
		return this.prfMdp;
	}

	public void setPrfMdp(String prfMdp) {
		this.prfMdp = prfMdp;
	}

	public List<Observation> getObservations() {
		return this.observations;
	}

	public void setObservations(List<Observation> observations) {
		this.observations = observations;
	}

	public Observation addObservation(Observation observation) {
		getObservations().add(observation);
		observation.setProfil(this);

		return observation;
	}

	public Observation removeObservation(Observation observation) {
		getObservations().remove(observation);
		observation.setProfil(null);

		return observation;
	}

}
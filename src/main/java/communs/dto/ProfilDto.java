package communs.dto;

import java.util.List;

public class ProfilDto {
	private int prfId;
	private String prfCode;
	private String prfMdp;
	private List<ObservationDto> observations;
	public int getPrfId() {
		return prfId;
	}
	public void setPrfId(int prfId) {
		this.prfId = prfId;
	}
	public String getPrfCode() {
		return prfCode;
	}
	public void setPrfCode(String prfCode) {
		this.prfCode = prfCode;
	}
	public String getPrfMdp() {
		return prfMdp;
	}
	public void setPrfMdp(String prfMdp) {
		this.prfMdp = prfMdp;
	}
	public List<ObservationDto> getObservations() {
		return observations;
	}
	public void setObservations(List<ObservationDto> observations) {
		this.observations = observations;
	}
	@Override
	public String toString() {
		return "ID: " + this.prfId + "; Code: " + this.prfCode + "; MDP: " + this.prfMdp;
	}
}

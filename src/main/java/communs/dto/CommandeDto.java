package communs.dto;

import java.util.List;

public class CommandeDto {
	private int cdeId;
	private String cdeClient;
	private String cdeDate;
	private String cdeIntitule;
	private String cdeMontant;
	private String cdeNum;
	private List<ObservationDto> observations;
	
	public CommandeDto() {
		
	}
	public List<ObservationDto> getObservations() {
		return observations;
	}
	public void setObservations(List<ObservationDto> observations) {
		this.observations = observations;
	}
	public int getCdeId() {
		return cdeId;
	}
	public void setCdeId(int cdeId) {
		this.cdeId = cdeId;
	}
	public String getCdeClient() {
		return cdeClient;
	}
	public void setCdeClient(String cdeClient) {
		this.cdeClient = cdeClient;
	}
	public String getCdeDate() {
		return cdeDate;
	}
	public void setCdeDate(String cdeDate) {
		this.cdeDate = cdeDate;
	}
	public String getCdeIntitule() {
		return cdeIntitule;
	}
	public void setCdeIntitule(String cdeIntitule) {
		this.cdeIntitule = cdeIntitule;
	}
	public String getCdeMontant() {
		return cdeMontant;
	}
	public void setCdeMontant(String cdeMontant) {
		this.cdeMontant = cdeMontant;
	}
	public String getCdeNum() {
		return cdeNum;
	}
	public void setCdeNum(String cdeNum) {
		this.cdeNum = cdeNum;
	}
}

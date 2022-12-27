package metier;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import communs.Erreur;
import communs.database.orm.Profil;
import communs.dto.ProfilDto;


public class ProfilMetier {
	private EntityManager em;
	
	public ProfilMetier(EntityManager em) {
		this.em = em;
	}
	
	public Erreur verifier(ProfilDto obs) {
		return new Erreur();
	}
	public List<ProfilDto> lister(){
		return new ArrayList<ProfilDto>();
	}
	//select
	public ProfilDto trouver(Integer id) {
		return new ProfilDto();
	}
	//put
	public Erreur modifier(ProfilDto cde) {
		return new Erreur();
	}
	//insert
	public Erreur inserer(ProfilDto cde) {
		return new Erreur();
	}
	
	public static ProfilDto convertirUnOrmEnDto(Profil orm) {
		ProfilDto ormToDto = new ProfilDto();
		ormToDto.setPrfId(orm.getPrfId());
		ormToDto.setPrfCode(orm.getPrfCode());
		ormToDto.setPrfMdp(orm.getPrfMdp());
		
		
		ormToDto.setObservations(null); // ??
		
		
		
		return ormToDto;
		
	}
	public Profil convertirUnDtoEnOrm(ProfilDto dto, Profil orm) {
		return new Profil();
	}
}

package metier;



import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;


import communs.database.orm.Profil;

import communs.dto.ProfilDto;

public class ProfilMetier {
	private EntityManager em;
	
	public ProfilMetier(EntityManager em) {
		this.em = em;
	}
	
	public ProfilDto trouver(Integer prfId) {
		Profil prf = this.em.find(Profil.class, prfId);
		
		return this.convertirUnOrmEnDto(prf);
	}
	public ProfilDto trouver(String prfCode) {
		ProfilDto prf = null;
		Profil prfOrm;
		TypedQuery<Profil> requete = this.em.createQuery("select p from Profil p where p.prfCode = :prfCode", Profil.class);
		try {
			prfOrm = requete.setParameter("prfCode", prfCode).getSingleResult();
			prf = this.convertirUnOrmEnDto(prfOrm);
		}catch(NoResultException nre) {
			// ?
		}
		return prf;
		
	}
	
	public ProfilDto convertirUnOrmEnDto(Profil prfOrm) {
		ProfilDto prfConverti = new ProfilDto();
		prfConverti.setPrfId(prfOrm.getPrfId());
		prfConverti.setPrfCode(prfOrm.getPrfCode());
		prfConverti.setPrfMdp(prfOrm.getPrfMdp());
		
		return prfConverti;
	}
	
	public Profil convetirUnDtoEnOrm(ProfilDto prfDto, Profil prfOrm) {
		prfOrm.setPrfId(prfDto.getPrfId());
		prfOrm.setPrfCode(prfDto.getPrfCode());
		prfOrm.setPrfMdp(prfDto.getPrfMdp());
		
		
		
		return prfOrm;
	}

}

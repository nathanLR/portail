package metier;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import communs.Erreur;
import communs.database.orm.Commande;
import communs.dto.CommandeDto;

public class CommandeMetier {
	private EntityManager em;
	
	public CommandeMetier(EntityManager em) {
		this.em = em;
	}
	
	public Erreur verifier(CommandeDto cde) {
		boolean champNum = cde.getCdeNum().length() > 0 && cde.getCdeNum().startsWith("CDE");
		boolean champClient = cde.getCdeClient().length() > 0;
		boolean champDate; //?? quel genre de verif pour la date ? (similaire a celle de javascript?)
		boolean champIntitule = cde.getCdeIntitule().length() >  0;
		boolean champMontant = cde.getCdeMontant().length() > 0 && (Integer)Integer.parseInt(cde.getCdeMontant().replaceAll("\\s+", "")) instanceof Integer;
		//moyennement sur de celle du dessus
		
		
		return new Erreur();
	}
	public List<CommandeDto> lister(){
		return new ArrayList<CommandeDto>();
	}
	//select
	public CommandeDto trouver(Integer id) {
		return new CommandeDto();
	}
	//put
	public Erreur modifier(CommandeDto cde) {
		
		//appel de verifier()
		return new Erreur();
	}
	//insert
	public Erreur inserer(CommandeDto cde) {
		//appel de verifier()
		return new Erreur();
	}
	
	public CommandeDto convertirUnOrmEnDto(Commande orm) {
		CommandeDto ormToDto = new CommandeDto();
		ormToDto.setCdeId(orm.getCdeId());
		ormToDto.setCdeClient(orm.getCdeClient());
		ormToDto.setCdeDate(orm.getCdeDate().toString());
		ormToDto.setCdeIntitule(orm.getCdeIntitule());
		ormToDto.setCdeMontant(String.valueOf(orm.getCdeMontant()));
		ormToDto.setCdeNum(orm.getCdeNum());
		
		
		ormToDto.setCdeObservations(null); // ??
		
		
		return ormToDto;
		
	}
	public Commande convertirUnDtoEnOrm(CommandeDto dto, Commande orm) {
		return new Commande();
	}
	
}

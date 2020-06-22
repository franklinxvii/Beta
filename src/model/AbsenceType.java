package model;

/**
 * Class Absencetype
 * @author Franklin AGBIKOSSI, Colombe KOUTCHAKPO
 * @version 1.0
 * */
public class AbsenceType {
	private int id;
	private String entitle;
	
	/**
	 * AbsenceType Constructor 
	 * @param id
	 * @param entitle
	 */
	public AbsenceType(int id, String entitle) {
		this.id = id;
		this.entitle = entitle;
	}

	/**Constructor with only entitle
	 * @param entitle
	 */
	public AbsenceType(String entitle) {
		this.entitle = entitle;
	}

	/**
	 * Id Getter
	 * 	 * @return id 
	 */
	public int getId() {
		return id;
	}

	/**
	 * Id Setter
	 * 
	 * @param  id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * AbsenceType title Getter
	 * 
	 * @return Entitle
	 */
	public String getEntitle() {
		return entitle;
	}

	/**
	 *AbsenceType Title setter
	 * 
	 * @param entitle
	 */
	public void setEntitle(String entitle) {
		this.entitle = entitle;
	}
	

	@Override
	public String toString() {
		return entitle;
	}

	
}

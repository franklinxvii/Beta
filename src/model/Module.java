package model;

/**
 * Class Module
 * @author Franklin AGBIKOSSI, Colombe KOUTCHAKPO
 * @version 1.3
 * */
public class Module {
	/**
	 * id
	 */
	private int id;
	
	/**
	 * name
	 */
	private String name;
	
	/**Constructor of Module
	 * @param id
	 * @param name
	 */
	public Module(int id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * getter of attribute id
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * setter of attribute id
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * getter of attribute name
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * getter of attribute name
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Override of the method to use in frame
	 */
	@Override
	public String toString() {
		return name;
	}
	
}

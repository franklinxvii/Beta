package model;

import java.util.ArrayList;

/**
 * Class Course
 * @author Franklin AGBIKOSSI, Colombe KOUTCHAKPO
 * @version 1.0
 * */
public class Course {
	private int id;
	private String name;
	private int hourlyMass;
	private int module;
	private ArrayList<Teacher> list;

	/**
	 * Constructor of Course
	 * @param id 
	 * @param name 
	 * @param hourlyMass 
	 * @param module 
	 */
	public Course(int id, String name, int hourlyMass, int module) {
		this.id = id;
		this.name = name;
		this.hourlyMass = hourlyMass;
		this.module = module;
	}

	/**
	 * Constructor with teacher list
	 * @param id
	 * @param name
	 * @param hourlyMass
	 * @param module
	 * @param list
	 */
	public Course(int id, String name, int hourlyMass, int module, ArrayList<Teacher> list) {
		this.id = id;
		this.name = name;
		this.hourlyMass = hourlyMass;
		this.module = module;
		this.list = list;
	}

	/**
	 * id course getter 
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * id course setter  
	 * @param id 
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 *  name getter 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * name setter  
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * hourlyMass getter  
	 * @return the hourlyMass
	 */
	public int getHourlyMass() {
		return hourlyMass;
	}

	/**
	 *  hourlyMass setter 
	 * @param hourlyMass 
	 */
	public void setHourlyMass(int hourlyMass) {
		this.hourlyMass = hourlyMass;
	}

	/**
	 * module id getter 
	 * @return the module
	 */
	public int getModule() {
		return module;
	}

	/**
	 * setter of attribute module
	 * @param module the module to set
	 */
	public void setModule(int module) {
		this.module = module;
	}

	/**
	 * teacher list getter
	 * @return the list
	 */
	public ArrayList<Teacher> getList() {
		return list;
	}

	/**
	 * teacher list  setter
	 * @param list 
	 */
	public void setList(ArrayList<Teacher> list) {
		this.list = list;
	}
	
	@Override
	public String toString() {
		return name;
	}
}

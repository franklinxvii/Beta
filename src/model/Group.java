package model;
import java. util.*; 

/**
 * ClassGroup
 * @author Franklin AGBIKOSSI, Colombe KOUTCHAKPO
 * @version 1.3
 * */
public class Group {
	private int id;
	private int maxCapacity;
	private ArrayList<Student> studentList;
	
	/**
	 * Group constructor
	 * @param id
	 * @param maxCapacity
	 * @param studentList
	 */
	public Group(int id, int maxCapacity, ArrayList<Student> studentList) {
		this.id = id;
		this.maxCapacity = maxCapacity;
		this.studentList = studentList;
	}

	/**
	 * id getter
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 *   id setter
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * maxCapacity getter
	 * @return the maxCapacity
	 */
	public int getMaxCapacity() {
		return maxCapacity;
	}

	/**
	 * maxCapacity setter
	 * @param maxCapacity the maxCapacity to set
	 */
	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	/**
	 * student list getter
	 * @return the studentList
	 */
	public ArrayList<Student> getStudentList() {
		return studentList;
	}

	/**
	 * students list setter
	 * @param studentList the studentList to set
	 */
	public void setStudentList(ArrayList<Student> studentList) {
		this.studentList = studentList;
	}

	@Override
	public String toString() {
		return String.valueOf(id);
	}
}

package model;

import java.util.ArrayList;

/**
 * Class Teacher
 * @author Franklin AGBIKOSSI, Colombe KOUTCHAKPO
 * @version 1.3
 * */
public class Teacher extends User{
	private String phoneNumber;
	private ArrayList<Course> courses; //the list of Teacher courses 
	
	/**
	 * @param id
	 * @param lastName
	 * @param firstName
	 * @param password
	 * @param phoneNumber
	 * @param courses
	 */
	public Teacher(int id, String lastName, String firstName, String password, String phoneNumber,
			ArrayList<Course> courses) {
		super(id, lastName, firstName, password);
		this.phoneNumber = phoneNumber;
		this.courses = courses;
	}

	/**
	 * getter of teacher phoneNumber
	 * @return  phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * setter of  teacher phoneNumber
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * getter of teacher courses list
	 * @return  courses
	 */
	public ArrayList<Course> getCourses() {
		return courses;
	}

	/**
	 * setter of teacher courses list
	 * @param courses the course to set
	 */
	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}
	
	
	
}

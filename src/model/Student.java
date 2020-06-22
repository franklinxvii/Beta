package model;

import java.util.*;

/**
 * Class Student
 * @author Franklin AGBIKOSSI, Colombe KOUTCHAKPO
 * @version 1.0
 * */
public class Student extends User{
	private String email;
	private String faculty;
	private int group;
	private ArrayList<Course> courses;
	
	/**
	 * first constructor without parameter group 
	 * @param id
	 * @param lastName
	 * @param firstName
	 * @param password
	 * @param email
	 * @param faculty
	 */
	public Student(int id, String lastName, String firstName, String password, String email, String faculty) {
		super(id, lastName, firstName, password);
		this.email = email;
		this.faculty = faculty;
	}
	
	
	/**
	 * 2nd constructor with parameter group 
	 * @param id
	 * @param lastName
	 * @param firstName
	 * @param password
	 * @param email
	 * @param faculty
	 * @param group
	 */
	public Student(int id, String lastName, String firstName, String password, String email, String faculty,
			int group) {
		super(id, lastName, firstName, password);
		this.email = email;
		this.faculty = faculty;
		this.group = group;
	}


	/**
	 * email getter 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/** email setter
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * faculty getter
	 * @return the faculty
	 */
	public String getFaculty() {
		return faculty;
	}
	/**
	 * faculty setter
	 * @param faculty the faculty to set
	 */
	public void setFilliere(String faculty) {
		this.faculty = faculty;
	}


	/**
	 * group getter
	 * @return the group
	 */
	public int getGroup() {
		return group;
	}


	/**
	 * group setter
	 * @param group the group to set
	 */
	public void setGroup(int group) {
		this.group = group;
	}
	
	/**
	 *  affiliate courses getter
	 * @return the courses
	 */
	public ArrayList<Course> getCourses() {
		return courses;
	}


	/**
	 * affiliate courses setter
	 * @param courses the courses to set
	 */
	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}
}

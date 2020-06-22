package model;

/**
 * Class User
 * @author Franklin AGBIKOSSI, Colombe KOUTCHAKPO
 * @version 1.3
 * */
public class User {
	protected int id;
	protected String lastName;
	protected String firstName;
	protected String password;
	
	/**
	 * @param id
	 * @param lastName
	 * @param firstName
	 * @param password
	 */
	public User(int id, String lastName, String firstName, String password) {
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.password = password;
	}

	/**
	 * getter of user id
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * setter of user id
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * getter of user lastName
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the  user lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * getter of  user firstName 
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the  user firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * getter of user password
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * setter of user password
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
}

package dao;
import java.sql.*;
import javax.swing.*;
import java.util.*;
import model.*;

public class StudentDAO extends ConnectionDAO {


	/**
	 * Access class to the data contained in the supplier table
	 * 
	 * @author Franklin AGBIKOSSI, Colombe KOUTCHAKPO
	 * @version 1.0
	 * */
		/**
		 * Constructor
		 * 
		 */
		public StudentDAO() {
			super();
		}

		/**
		 * Add student to the table student
		 * Auto-commit by default : each add is effective
		 * 
		 * @param student the student to add
		 * @return return the number of lines added to the table
		 */
		public int add(Student student) {
			Connection con = null;
			PreparedStatement ps = null;
			int returnValue = 0;
			// connection to the database
			try {

				// connection attempt
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				// preparation of the SQL statement, each ? represents a value
				// to communicate in the insertion..
				// the getters allow to recover the values of the desired attributes
				ps = con.prepareStatement("INSERT INTO student (firstname, lastname, email, password, faculty) VALUES (?, ?, ?, ?, ?)");
				ps.setString(1, student.getFirstName());
				ps.setString(2, student.getLastName());
				ps.setString(3, student.getEmail());
				ps.setString(4, student.getPassword());
				ps.setString(5, student.getFaculty());
				// Execution of the request
				returnValue = ps.executeUpdate();
			} catch (Exception e) {
				if (e.getMessage().contains("ORA-00001"))
					JOptionPane.showMessageDialog(null,"Students can't have common First name-Lastname/Id/Email. Impossible to add !");
				else if(e.getMessage().contains("ORA-01400"))
					JOptionPane.showMessageDialog(null,"You must fill all empty spaces.");
				else
					e.printStackTrace();
			} finally {
				// closing the preparedStatement and connection
				try {
					if (ps != null) {
						ps.close();
					}
				} catch (Exception ignore) {
				}
				try {
					if (con != null) {
						con.close();
					}
				} catch (Exception ignore) {
				}
			}
			return returnValue;
		}

		/**
		 * Edit a student in the table student.
		 * Auto-commit by default : each update is effective
		 * 
		 * @param student the student to update
		 * @return return the number of lines updated in the table
		 */
		public int update(Student student) {
			Connection con = null;
			PreparedStatement ps = null;
			int returnValue = 0;

			// connection to the database
			try {

				// connection attempt
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				// preparation of the SQL statement, each ? represents a value
				// to communicate in the modification.
				// the getters allow to recover the values of the desired attributes
				ps = con.prepareStatement("UPDATE student set firstname = ?, lastname = ?, email = ?, password = ?, faculty = ?  WHERE id = ?");
				ps.setString(1, student.getFirstName());
				ps.setString(2, student.getLastName());
				ps.setString(3, student.getEmail());
				ps.setString(4, student.getPassword());
				ps.setString(5, student.getFaculty());
				ps.setInt(6, student.getId());
				
				// Execution of the request
				returnValue = ps.executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// closing the preparedStatement and connection
				try {
					if (ps != null) {
						ps.close();
					}
				} catch (Exception ignore) {
				}
				try {
					if (con != null) {
						con.close();
					}
				} catch (Exception ignore) {
				}
			}
			return returnValue;
		}

		/**
		 * Delete a student from the table student.
		 * If the student is associated to groups, the deletion won't be done
		 * Auto-commit by default: each deletion is effective
		 * 
		 * @param student the student to delete
		 * @return return the number of lines deleted from the table
		 */
		public int delete(Student student) {
			Connection con = null;
			PreparedStatement ps = null;
			int returnValue = 0;
			// connection to the database
			try {

				// connection attempt
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				// preparation of the SQL statement, each ? represents the value of ID
				// to communicate in the deletion.
				// the getter retrieves the value of the student's ID
				ps = con.prepareStatement("DELETE FROM student WHERE id = ?");
				ps.setInt(1, student.getId());

				// Execution of the request
				returnValue = ps.executeUpdate();

			} catch (Exception e) {
				if (e.getMessage().contains("ORA-02292"))
					JOptionPane.showMessageDialog(null,"This student is associated to a group !"
							         + "Unnassociate the student from the group first.");
				else
					e.printStackTrace();
			} finally {
				// closing the preparedStatement and connection
				try {
					if (ps != null) {
						ps.close();
					}
				} catch (Exception ignore) {
				}
				try {
					if (con != null) {
						con.close();
					}
				} catch (Exception ignore) {
				}
			}
			return returnValue;
		}

		
		
		/**
		 * Retrieve a student from the database using his id
		 * 
		 * @param id : the student's id
		 * @return the retrieved student;
		 * 			null if there is no student with this id
		 */
		public Student get(int id) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			Student returnValue = null;

			// connection to the database
			try {

				// connection attempt
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM student WHERE id = ?");
				ps.setInt(1, id);
				// we execute the request
				// rs contains a pointer located just before the first line returned
				rs = ps.executeQuery();
				// go to the first (and only) line returned
				if (rs.next()) {
					returnValue = new Student(rs.getInt("id"),
										       rs.getString("lastname"),
										       rs.getString("firstname"),
										       rs.getString("password"),
										       rs.getString("email"),
										       rs.getString("faculty")
										    );
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			} finally {
				// closing the ResultSet, PreparedStatement and Connection
				try {
					if (rs != null) {
						rs.close();
					}
				} catch (Exception ignore) {
				}
				try {
					if (ps != null) {
						ps.close();
					}
				} catch (Exception ignore) {
				}
				try {
					if (con != null) {
						con.close();
					}
				} catch (Exception ignore) {
				}
			}
			return returnValue;
		}
		
		/**
		 * Retrieve a student from the database using his name (first name and last name) and password
		 * @param name : the student's name (first name and last name) as written during creation
		 * @param password : the student's password
		 * @return the retrieved student;
		 * 			null if there is no student with this id and password
		 */
		public Student get(String name, String password) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			Student returnValue = null;

			// connection to the database
			try {

				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM student WHERE firstName || ' ' || lastName = ? AND password = ?");
				ps.setString(1, name);
				ps.setString(2, password);

				// we execute the request
				// rs contains a pointer located right before the first line returned
				rs = ps.executeQuery();
				// go to the first (and only) line returned
				if (rs.next()) {
					returnValue = new Student(rs.getInt("id"),
						       rs.getString("lastname"),
						       rs.getString("firstname"),
						       rs.getString("password"),
						       rs.getString("email"),
						       rs.getString("faculty"),
						       rs.getInt("id_group")
						    );
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			} finally {
				// closing the ResultSet, PreparedStatement and connection
				try {
					if (rs != null) {
						rs.close();
					}
				} catch (Exception ignore) {
				}
				try {
					if (ps != null) {
						ps.close();
					}
				} catch (Exception ignore) {
				}
				try {
					if (con != null) {
						con.close();
					}
				} catch (Exception ignore) {
				}
			}
			return returnValue;
		}

		/**
		 * Retrieve all student from the database in a list
		 * @return an ArrayList of students 
		 */
		public ArrayList<Student> getList() {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<Student> returnValue = new ArrayList<>();

			// connection to the database
			try {
				// connection attempt
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM student ORDER BY id");

				// we execute the request
				rs = ps.executeQuery();
				// we run through the result lines
				while (rs.next()) {
					returnValue.add(new Student(rs.getInt("id"),
						       rs.getString("lastname"),
						       rs.getString("firstname"),
						       rs.getString("password"),
						       rs.getString("email"),
						       rs.getString("faculty")
									));
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			} finally {
				// closing the ResultSet, PreparedStatement and Connection
				try {
					if (rs != null)
						rs.close();
				} catch (Exception ignore) {
				}
				try {
					if (ps != null)
						ps.close();
				} catch (Exception ignore) {
				}
				try {
					if (con != null)
						con.close();
				} catch (Exception ignore) {
				}
			}
			return returnValue;
		}

		/**
		 * Retrieve students according to their group
		 * @param idGroup : the group's id
		 * @return an ArrayList of Students
		 */
		public ArrayList<Student> getListForGroup(int idGroup) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<Student> returnValue = new ArrayList<>();

			// connexion a la base de donnees
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM student WHERE id_group = ? ORDER BY id");
				ps.setInt(1, idGroup);
				
				// on execute la requete
				rs = ps.executeQuery();
				// on parcourt les lignes du resultat
				while (rs.next()) {
					returnValue.add(new Student(rs.getInt("id"),
						       rs.getString("firstname"),
						       rs.getString("lastname"),
						       rs.getString("password"),
						       rs.getString("email"),
						       rs.getString("faculty"),
						       rs.getInt("id_group"))
						    );
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			} finally {
				// fermeture du rs, du preparedStatement et de la connexion
				try {
					if (rs != null)
						rs.close();
				} catch (Exception ignore) {
				}
				try {
					if (ps != null)
						ps.close();
				} catch (Exception ignore) {
				}
				try {
					if (con != null)
						con.close();
				} catch (Exception ignore) {
				}
			}
			return returnValue;
		}
		
		/**
		 * Retrieve students following a lesson
		 * @param idLesson the lesson's id
		 * @return  returnValue an ArrayList of students
		 */
		public ArrayList<Student> getListForLesson(int idLesson) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<Student> returnValue = new ArrayList<>();

			// connexion a la base de donnees
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM student WHERE id_group = (SELECT tc_group.id_group FROM tc_group INNER JOIN lesson ON (tc_group.id = lesson.id_tc_group) WHERE lesson.id = ?) ORDER BY id");
				ps.setInt(1, idLesson);
				
				// on execute la requete
				rs = ps.executeQuery();
				// on parcourt les lignes du resultat
				while (rs.next()) {
					returnValue.add(new Student(rs.getInt("id"),
						       rs.getString("firstname"),
						       rs.getString("lastname"),
						       rs.getString("password"),
						       rs.getString("email"),
						       rs.getString("faculty"),
						       rs.getInt("id_group"))
						    );
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			} finally {
				// fermeture du rs, du preparedStatement et de la connexion
				try {
					if (rs != null)
						rs.close();
				} catch (Exception ignore) {
				}
				try {
					if (ps != null)
						ps.close();
				} catch (Exception ignore) {
				}
				try {
					if (con != null)
						con.close();
				} catch (Exception ignore) {
				}
			}
			return returnValue;
		}
		
		/**
		 * Retrieve students in this group those  who don't have a group
		 * @param idGroup : the group's id
		 * @return an ArrayList of Students
		 */
		public ArrayList<Student> getListForGroupEdit(int idGroup) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<Student> returnValue = new ArrayList<>();

			// connexion a la base de donnees
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM student WHERE id_group = ? OR id_group IS NULL ORDER BY id");
				ps.setInt(1, idGroup);
				
				// on execute la requete
				rs = ps.executeQuery();
				// on parcourt les lignes du resultat
				while (rs.next()) {
					returnValue.add(new Student(rs.getInt("id"),
						       rs.getString("firstname"),
						       rs.getString("lastname"),
						       rs.getString("password"),
						       rs.getString("email"),
						       rs.getString("faculty"),
						       rs.getInt("id_group"))
						    );
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			} finally {
				// fermeture du rs, du preparedStatement et de la connexion
				try {
					if (rs != null)
						rs.close();
				} catch (Exception ignore) {
				}
				try {
					if (ps != null)
						ps.close();
				} catch (Exception ignore) {
				}
				try {
					if (con != null)
						con.close();
				} catch (Exception ignore) {
				}
			}
			return returnValue;
		}
		
		/**
		 * Retrieve students who don't have a group
		 * @return an ArrayList of Students
		 */
		public ArrayList<Student> getListOfStudentsWithoutGroup() {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<Student> returnValue = new ArrayList<>();

			// connexion a la base de donnees
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM student WHERE id_group IS NULL ORDER BY id");
				
				// on execute la requete
				rs = ps.executeQuery();
				// on parcourt les lignes du resultat
				while (rs.next()) {
					returnValue.add(new Student(rs.getInt("id"),
						       rs.getString("firstname"),
						       rs.getString("lastname"),
						       rs.getString("password"),
						       rs.getString("email"),
						       rs.getString("faculty"),
						       rs.getInt("id_group"))
						    );
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			} finally {
				// fermeture du rs, du preparedStatement et de la connexion
				try {
					if (rs != null)
						rs.close();
				} catch (Exception ignore) {
				}
				try {
					if (ps != null)
						ps.close();
				} catch (Exception ignore) {
				}
				try {
					if (con != null)
						con.close();
				} catch (Exception ignore) {
				}
			}
			return returnValue;
		}
}

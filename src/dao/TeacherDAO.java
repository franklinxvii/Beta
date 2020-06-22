package dao;
import java.sql.*;
import javax.swing.*;
import java.util.*;
import model.*;

public class TeacherDAO extends ConnectionDAO {


	/**
	 * Access class to the data contained in the Teacher table
	 * 
	 * @author Franklin AGBIKOSSI, Colombe KOUTCHAKPO
	 * @version 1.0
	 * */
		/**
		 * Constructor
		 * 
		 */
		public TeacherDAO() {
			super();
		}

		/**
		 * Add teacher to the table teacher
		 * Auto-commit by default : each add is effective
		 * @param teacher the teacher to add
		 * @return return the number of lines added to the table
		 */
		public int add(Teacher teacher) {
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
				ps = con.prepareStatement("INSERT INTO teacher (firstname, lastname, phonenumber, password) VALUES (?, ?, ?, ?)");
				ps.setString(1, teacher.getFirstName());
				ps.setString(2, teacher.getLastName());
				ps.setString(3, teacher.getPhoneNumber());
				ps.setString(4, teacher.getPassword());
				// Execution of the request
				returnValue = ps.executeUpdate();
				associateTeacherCourse(teacher.getFirstName(), teacher.getLastName(), teacher.getCourses());
			} catch (Exception e) {
				if (e.getMessage().contains("ORA-00001"))
					JOptionPane.showMessageDialog(null,"Teachers with common First name-Lastname/Id/Phone number is not allowed. Impossible to add !");
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
		 * Edit a teacher in the table teacher.
		 * Auto-commit by default : each update is effective
		 * 
		 * @param teacher the teacher to update
		 * @return return the number of lines updated in the table
		 */
		public int update(Teacher teacher) {
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
				ps = con.prepareStatement("UPDATE teacher set firstname = ?, lastname = ?, phonenumber = ?, password = ?  WHERE id = ?");
				ps.setString(1, teacher.getFirstName());
				ps.setString(2, teacher.getLastName());
				ps.setString(3, teacher.getPhoneNumber());
				ps.setString(4, teacher.getPassword());
				ps.setInt(5, teacher.getId());
				
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
		 * Delete a teacher from the table teacher.
		 * If the teacher is associated to courses, the deletion won't be done
		 * Auto-commit by default: each deletion is effective
		 * 
		 * @param teacher the teacher to delete
		 * @return return the number of lines deleted from the table
		 */
		public int delete(Teacher teacher) {
			Connection con = null;
			PreparedStatement ps = null;
			int returnValue = 0;
			// connection to the database
			try {

				// connection attempt
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				// preparation of the SQL statement, each ? represents the value of ID
				// to communicate in the deletion.
				// the getter retrieves the value of the teacher's ID
				ps = con.prepareStatement("DELETE FROM teacher WHERE id = ?");
				ps.setInt(1, teacher.getId());

				// Execution of the request
				returnValue = ps.executeUpdate();

			} catch (Exception e) {
				if (e.getMessage().contains("ORA-02292"))
					JOptionPane.showMessageDialog(null,"This teacher is associated to at least a course !"
							         + "Unnassociate the teacher from the course first.");
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
		 * Delete courses associated to a teacher from the table teacher_course knowing the teacher's id
		 * Auto-commit by default: each deletion is effective
		 * @param id the teacher whose courses are deleted
		 * @return return the number of lines deleted from the table
		 */
		public int delete(int id) {
			Connection con = null;
			PreparedStatement ps = null;
			int returnValue = 0;

			// connection to the database
			try {

				// connection attempt
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				// preparation of the SQL statement, each ? represents the value of ID
				// to communicate in the deletion.
				// the getter retrieves the value of the teacher's ID
				ps = con.prepareStatement("DELETE FROM teacher_course WHERE id_teacher = ?");
				ps.setInt(1, id);
				// Execution of the request
				returnValue = ps.executeUpdate();
				ps.close();
				// preparation of the SQL statement, each ? represents the value of ID
				// to communicate in the deletion.
				// the getter retrieves the value of the teacher's ID
				ps = con.prepareStatement("UPDATE tc_group SET id_teacher = NULL WHERE id_teacher = ?");
				ps.setInt(1, id);
				// Execution of the request
				returnValue = ps.executeUpdate();
			} catch (Exception e) {
					e.printStackTrace();
			} finally {
				// closing preparedStatement and connection
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
		 * Retrieve a teacher from the database using his id
		 * @param id : the teacher's id
		 * @return the retrieved teacher;
		 * 			null if there is no teacher with this id
		 */
		public Teacher get(int id) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			Teacher returnValue = null;

			// connection to the database
			try {

				// connection attempt
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM teacher WHERE id = ?");
				ps.setInt(1, id);
				// we execute the request
				// rs contains a pointer located just before the first line returned
				rs = ps.executeQuery();
				// go to the first (and only) line returned
				if (rs.next()) {
					returnValue = new Teacher(rs.getInt("id"),
										       rs.getString("lastname"),
										       rs.getString("firstname"),
										       rs.getString("password"),
										       rs.getString("phonenumber"),
										       null
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
		 * Retrieve a teacher from the database using his id and password
		 * @param name the teacher's name
		 * @param password : the teacher's password
		 * @return returnValue the retrieved teacher;
		 * 			null if there is no teacher with this id and password
		 */
		public Teacher get(String name, String password) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			Teacher returnValue = null;

			// connection to the database
			try {

				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM teacher WHERE firstName || ' ' || lastName = ? AND password = ?");
				ps.setString(1, name);
				ps.setString(2, password);

				// we execute the request
				// rs contains a pointer located right before the first line returned
				rs = ps.executeQuery();
				// go to the first (and only) line returned
				if (rs.next()) {
					returnValue = new Teacher(rs.getInt("id"),
						       rs.getString("lastname"),
						       rs.getString("firstname"),
						       rs.getString("password"),
						       rs.getString("phonenumber"),
						       null
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
		 * Retrieve a teacher from the database using his group and course
		 * @param group the teacher's group
		 * @param course the teacher's course
		 * @return returnValue the retrieved teacher;
		 * 			null if there is no teacher with this id
		 */
		public Teacher get(Group group, Course course) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			Teacher returnValue = null;

			// connection to the database
			try {

				// connection attempt
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM teacher INNER JOIN tc_group ON (teacher.id = tc_group.id_teacher) WHERE id_group = ? AND id_course = ?");
				ps.setInt(1, group.getId());
				ps.setInt(2, course.getId());
				
				// we execute the request
				// rs contains a pointer located just before the first line returned
				rs = ps.executeQuery();
				// go to the first (and only) line returned
				if (rs.next()) {
					returnValue = new Teacher(rs.getInt("id"),
										       rs.getString("lastname"),
										       rs.getString("firstname"),
										       rs.getString("password"),
										       rs.getString("phonenumber"),
										       null
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
		 * Retrieve a teacher from the database knowing a lesson
		 * @param lesson the lesson given by the teacher
		 * @return the retrieved teacher;
		 * 			null if there is no teacher with this id
		 */
		public Teacher get(Lesson lesson) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			Teacher returnValue = null;

			// connection to the database
			try {

				// connection attempt
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM teacher INNER JOIN tc_group ON (teacher.id = tc_group.id_teacher) INNER JOIN lesson ON(tc_group.id = lesson.id_tc_group) WHERE lesson.id=?");
				ps.setInt(1, lesson.getId());
				
				// we execute the request
				// rs contains a pointer located just before the first line returned
				rs = ps.executeQuery();
				// go to the first (and only) line returned
				if (rs.next()) {
					returnValue = new Teacher(rs.getInt("id"),
										       rs.getString("lastname"),
										       rs.getString("firstname"),
										       rs.getString("password"),
										       rs.getString("phonenumber"),
										       null
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
		 * Retrieve all teacher from the database in a list
		 * @return an ArrayList of teachers 
		 */
		public ArrayList<Teacher> getList() {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<Teacher> returnValue = new ArrayList<>();

			// connection to the database
			try {
				// connection attempt
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM teacher ORDER BY id");

				// we execute the request
				rs = ps.executeQuery();
				// we run through the result lines
				while (rs.next()) {
					returnValue.add(new Teacher(rs.getInt("id"),
						       rs.getString("firstname"),
						       rs.getString("lastname"),
						       rs.getString("password"),
						       rs.getString("phonenumber"),
						       null
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
		 * Associate a teacher to a course
		 * Auto-commit by default : each add is effective
		 * @param firstName the teacher's first name
		 * @param lastName the teacher's last name
		 * @param list the list of courses
		 * @return the number of lines added to the table
		 */
		private int associateTeacherCourse(String firstName, String lastName, ArrayList<Course> list) {
			Connection con = null;
			PreparedStatement ps = null;
			int returnValue = 0;

			// connection to the database
			try {

				// connection attempt
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				// preparation of the SQL statement, each ? represents the value of ID
				// to communicate in the deletion.
				// the getter retrieves the value of the teacher's ID
				for(Course course: list) {
					ps = con.prepareStatement("INSERT INTO teacher_course (id_teacher, id_course) VALUES ((SELECT id FROM teacher WHERE firstname = ? AND lastname = ?), ?)");
					ps.setString(1, firstName);
					ps.setString(2, lastName);
					ps.setInt(3, course.getId());
					// Execution of the request
					ps.executeUpdate();
					returnValue++;
				}
				
			} catch (Exception e) {
				if (e.getMessage().contains("ORA-00001"))
					JOptionPane.showMessageDialog(null,"This teacher can't have common information !");
				else
					e.printStackTrace();
			} finally {
				// closing preparedStatement connection
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
		 * Associate a teacher knowing his id to a list of courses
		 * @param id the teacher's id
		 * @param list the list of courses
		 * @return the number of lines added to the table
		 */
		public int associateTeacherCourse(int id,ArrayList<Course> list) {
			Connection con = null;
			PreparedStatement ps = null;
			int returnValue = 0;

			// connection to the database
			try {

				// connection attempt
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				// preparation of the SQL statement, each ? represents the value of ID
				// to communicate in the deletion.
				// the getter retrieves the value of the teacher's ID
				for(Course course: list) {
					ps = con.prepareStatement("INSERT INTO teacher_course (id_teacher, id_course) VALUES (?, ?)");
					ps.setInt(1, id);
					ps.setInt(2, course.getId());
					// Execution of the request
					ps.executeUpdate();
					returnValue++;
				}
				
			} catch (Exception e) {
				if (e.getMessage().contains("ORA-00001"))
					JOptionPane.showMessageDialog(null, "Teacher can't share common information !");
				else
					e.printStackTrace();
			} finally {
				// closing preparedStatement and connection
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
		 * Retrieve the list of teachers for the given group
		 * @param idGroup : the group's id
		 * @return an ArrayList of Teachers
		 */
		public ArrayList<Teacher> getListForGroup(int idGroup) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<Teacher> returnValue = new ArrayList<>();

			// connection to the database
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM tc_group INNER JOIN teacher ON (tc_group.id_teacher = teacher.id) WHERE id_group = ? ORDER BY id_course");
				ps.setInt(1, idGroup);
				
				// we execute the request
				rs = ps.executeQuery();
				// we run through result lines
				while (rs.next()) {
					returnValue.add(new Teacher(rs.getInt("id"),
						       rs.getString("firstname"),
						       rs.getString("lastname"),
						       rs.getString("password"),
						       rs.getString("phonenumber"),
							   null)
							);
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			} finally {
				// closing rs, preparedStatement and connection
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
		 * Retrieve all teachers associated to a course
		 * @param idCourse : the course's id
		 * @return an ArrayList of teacher
		 */
		public ArrayList<Teacher> getListForCourse(int idCourse) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<Teacher> returnValue = new ArrayList<>();

			// connection to the database
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM teacher_course INNER JOIN teacher ON (teacher_course.id_teacher=teacher.id) WHERE id_course = ? ORDER BY id_teacher");
				ps.setInt(1, idCourse);
				
				// we execute the request
				rs = ps.executeQuery();
				// we run through result lines
				while (rs.next()) {
					returnValue.add(new Teacher(rs.getInt("id"),
						       rs.getString("firstname"),
						       rs.getString("lastname"),
						       rs.getString("password"),
						       rs.getString("phonenumber"),
						       null
								));
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			} finally {
				// closing rs, preparedStatement and connection
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

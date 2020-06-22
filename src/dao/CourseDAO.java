package dao;

import java.sql.*;
import javax.swing.*;
import java.util.*;
import model.*;

public class CourseDAO extends ConnectionDAO {


	/**
	 * Access class to the data contained in the course table
	 * 
	 * @author Franklin AGBIKOSSI, Colombe KOUTCHAKPO
	 * @version 1.0
	 * */
		/**
		 * Constructor
		 * 
		 */
		public CourseDAO() {
			super();
		}

		/**
		 * Add course to the table course
		 * Auto-commit by default : each add is effective
		 * 
		 * @param course the course to add
		 * @return return the number of lines added to the table
		 */
		public int add(Course course) {
			Connection con = null;
			PreparedStatement ps = null;
			int returnValue = 0;

			// connection to the database
			try {

				// connection attempt
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				// preparation of the SQL statement, each ? represents a value
				// to communicate on insertion.
				// the getters allow to recover the values of the desired attributes
				ps = con.prepareStatement("INSERT INTO course (name, hourlyMass, module) VALUES (?, ?, ?)");
				ps.setString(1, course.getName());
				ps.setInt(2, course.getHourlyMass());
				ps.setInt(3, course.getModule());

				// Execution of the request
				returnValue = ps.executeUpdate();

			} catch (Exception e) {
				if (e.getMessage().contains("ORA-00001"))
					JOptionPane.showMessageDialog(null,"This course's name/id already exist. Impossible to add !");
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
		 * Edit a course in the table course.
		 * Auto-commit by default : each update is effective
		 * 
		 * @param course the course to update
		 * @return return the number of lines updated in the table
		 */
		public int update(Course course) {
			Connection con = null;
			PreparedStatement ps = null;
			int returnValue = 0;

			// connection to the database
			try {

				// tentative de connexion
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				// preparation of the SQL statement, each ? represents a value
				// to communicate on modification.
				// the getters allow to recover the values of the desired attributes
				ps = con.prepareStatement("UPDATE course set name = ?, hourlyMass = ?, module = ?  WHERE id = ?");
				ps.setString(1, course.getName());
				ps.setInt(2, course.getHourlyMass());
				ps.setInt(3, course.getModule());
				ps.setInt(4, course.getId());

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
		 * Delete a course from the table course.
		 * If the course is associated to a module, the deletion won't be done
		 * Auto-commit by default: each deletion is effective
		 * 
		 * @param course the course to delete
		 * @return return the number of lines deleted from the table
		 */
		public int delete(Course course) {
			Connection con = null;
			PreparedStatement ps = null;
			int returnValue = 0;
			// connection to the database
			try {

				// tentative de connexion
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				// preparation of the SQL statement, each ? represents the value of ID
				// to communicate on deletion.
				// the getter retrieves the value of the course's ID
				ps = con.prepareStatement("DELETE FROM course WHERE id = ?");
				ps.setInt(1, course.getId());

				// Execution of the request
				returnValue = ps.executeUpdate();

			} catch (Exception e) {
				if (e.getMessage().contains("ORA-02292"))
					JOptionPane.showMessageDialog(null,"This course is associated to at least a teacher, impossible to delete !"
							         + " Unassociate the course first");
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
		 * Retrieve a course from the database using his id
		 * 
		 * @param id : the course's id
		 * @return the retrieved course;
		 * 			null if there is no course with this id
		 */
		public Course get(int id) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			Course returnValue = null;

			// connection to the database
			try {

				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM course WHERE id = ?");
				ps.setInt(1, id);

				// we execute the request
				// rs contains a pointer located right before the first line returned
				rs = ps.executeQuery();
				// go to the first (and only) line returned
				if (rs.next()) {
					returnValue = new Course(rs.getInt("id"),
										       rs.getString("name"),
										       rs.getInt("hourlyMass"),
										       rs.getInt("module")
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
		 * Retrieve a course from the database using the lesson's id
		 * 
		 * @param id : the group's id
		 * @return the retrieved group;
		 * 			null if there is no group with this id
		 */
		public int getCourseId(int idLesson) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			int returnValue = 0;

			// connection to the database
			try {

				// connection attempt
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM lesson INNER JOIN tc_group ON (lesson.id_tc_group = tc_group.id) WHERE lesson.id = ?");
				ps.setInt(1, idLesson);
				// we execute the request
				// rs contains a pointer located just before the first line returned
				rs = ps.executeQuery();
				// go to the first (and only) line returned
				if (rs.next()) {
					returnValue = rs.getInt("id_course");
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
		 * Retrieve all course from the database in a list
		 * 
		 * @return an ArrayList of courses 
		 */
		public ArrayList<Course> getList() {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<Course> returnValue = new ArrayList<>();

			// connection to the database
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM course ORDER BY id");

				// we execute the request
				rs = ps.executeQuery();
				// we run through the result lines
				while (rs.next()) {
					returnValue.add(new Course(rs.getInt("id"),
						       					rs.getString("name"),
						       					rs.getInt("hourlyMass"),
						       					rs.getInt("module")
									));
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			} finally {
				// closing the rs, preparedStatement and connection
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
		 * Retrieve a course knowing a lesson's id
		 * @return a Course
		 */
		public Course getCourse(int idLesson) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			Course returnValue = null;

			// connection to the database
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT name, hourlyMass, module FROM course INNER JOIN tc_group ON (course.id = tc_group.id_course) WHERE id_course = (SELECT id_course FROM tc_group  INNER JOIN lesson ON (tc_group.id = lesson.id_tc_group) WHERE lesson.id = ?)");
				ps.setInt(1, idLesson);
				// we execute the request
				rs = ps.executeQuery();
				// we run through the result lines
				while (rs.next()) {
					returnValue = new Course(0,
							 					rs.getString("name"),
							 					rs.getInt("hourlyMass"),
							 					rs.getInt("module")
						    				);
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			} finally {
				// closing the rs, preparedStatement and connection
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
		 * Retrieve all courses given by a teacher
		 * @param idTeacher: the teacher's id
		 * @return an ArrayList of courses
		 */
		public ArrayList<Course> getListForTeacher(int idTeacher) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<Course> returnValue = new ArrayList<>();

			// connection to the database
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM teacher_course INNER JOIN course ON (teacher_course.id_course=course.id) WHERE id_teacher = ? ORDER BY id_course");
				ps.setInt(1, idTeacher);
				
				// we execute the request
				rs = ps.executeQuery();
				// we run through the result lines
				while (rs.next()) {
					returnValue.add(new Course(rs.getInt("id"),
						       rs.getString("name"),
						       rs.getInt("hourlyMass"),
						       rs.getInt("module")
								));
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			} finally {
				// closing the rs, preparedStatement and connection
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
		 * Retrieve all courses followed by a group
		 * @param idGroup: the group's id
		 * @return an ArrayList of courses
		 */
		public ArrayList<Course> getListForGroup(int idGroup) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<Course> returnValue = new ArrayList<>();

			// connection to the database
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM tc_group INNER JOIN course ON (tc_group.id_course=course.id) WHERE id_group = ? ORDER BY id_course");
				ps.setInt(1, idGroup);
				
				// we execute the request
				rs = ps.executeQuery();
				// we run through the result lines
				while (rs.next()) {
					returnValue.add(new Course(rs.getInt("id"),
						       rs.getString("name"),
						       rs.getInt("hourlyMass"),
						       rs.getInt("module")
								));
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			} finally {
				// closing the rs, preparedStatement and connection
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

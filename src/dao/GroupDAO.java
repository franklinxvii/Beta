package dao;
import java.sql.*;
import javax.swing.*;
import java.util.*;
import model.*;

public class GroupDAO extends ConnectionDAO {


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
		public GroupDAO() {
			super();
		}

		/**
		 * Add group to the table group and associate with course and teacher
		 * Auto-commit disabled default : all modifications are performed before commit
		 * @param group the group to add
		 * @param  idCourse the list of course's id to associate
		 * @param  idTeacher the list of teacher's id to associate
		 * @return returnValue the number of lines added to the table
		 */
		public int add(Group group, ArrayList<Integer> idCourse, ArrayList<Integer> idTeacher) {
			Connection con = null;
			PreparedStatement ps = null;
			int returnValue = 0;
			// connection to the database
			try {
				// connection attempt
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				con.setAutoCommit(false);
				// preparation of the SQL statement, each ? represents a value
				// to communicate in the insertion..
				// the getters allow to recover the values of the desired attributes
				ps = con.prepareStatement("INSERT INTO groupe (maxCapacity) VALUES (?)");
				ps.setInt(1, group.getMaxCapacity());
				// Execution of the request
				ps.executeUpdate();
				returnValue++;
				for(Student student: group.getStudentList()) {
					ps = con.prepareStatement("UPDATE student SET id_group = group_seq.currval WHERE id = ?");
					ps.setInt(1, student.getId());
					// Execution of the request
					ps.executeUpdate();
					returnValue++;
				}
				if(!idCourse.isEmpty() && !idTeacher.isEmpty()) {
					for(int i = 0; i < idCourse.size(); i++) {
						if(idTeacher.get(i) == 0) {
							ps = con.prepareStatement("INSERT INTO tc_group(id_group, id_course) VALUES (group_seq.currval, ?)");
							ps.setInt(1, idCourse.get(i));
							// Execution of the request
							ps.executeUpdate();
							returnValue++;
						}
						else {
							ps = con.prepareStatement("INSERT INTO tc_group(id_group, id_course, id_teacher) VALUES (group_seq.currval, ?, ?)");
							ps.setInt(1, idCourse.get(i));
							ps.setInt(2, idTeacher.get(i));
							// Execution of the request
							ps.executeUpdate();
							returnValue++;
						}	
					}
				}
				con.commit();
			} catch (Exception e) {
				if (e.getMessage().contains("ORA-00001"))
					JOptionPane.showMessageDialog(null,"Groups can't have common First name-Lastname/Id/Phone number. Impossible to add !");
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
		 * Update group to the table group and his association with course and teacher
		 * Auto-commit disabled default : all modifications are performed before commit
		 * @param group the group to add
		 * @param  idCourse the list of course's id to associate
		 * @param  idTeacher the list of teacher's id to associate
		 * @return return the number of lines update in the table
		 */
		public int update(Group group, ArrayList<Integer> idCourse, ArrayList<Integer> idTeacher) {
			Connection con = null;
			PreparedStatement ps = null;
			int returnValue = 0;

			// connection to the database
			try {

				// connection attempt
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				con.setAutoCommit(false);
				// preparation of the SQL statement, each ? represents a value
				// to communicate in the modification.
				// the getters allow to recover the values of the desired attributes
				ps = con.prepareStatement("UPDATE groupe set maxCapacity = ?  WHERE id = ?");
				ps.setInt(1, group.getMaxCapacity());
				ps.setInt(2, group.getId());
				// Execution of the request
				ps.executeUpdate();
				returnValue++;
				ps.close();
				ps = con.prepareStatement("UPDATE student SET id_group = NULL WHERE id_group = ?");
				ps.setInt(1, group.getId());
				// Execution of the request
				ps.executeUpdate();
				ps.close();
				for(Student student: group.getStudentList()) {
					ps = con.prepareStatement("UPDATE student SET id_group = ? WHERE id = ?");
					ps.setInt(1, group.getId());
					ps.setInt(2, student.getId());
					// Execution of the request
					ps.executeUpdate();
					returnValue++;
				}
				ps.close();
				if(!idCourse.isEmpty() && !idTeacher.isEmpty()) {
					for(int i = 0; i < idCourse.size(); i++) {
						if(idTeacher.get(i) == 0) {
							ps = con.prepareStatement("UPDATE tc_group SET id_teacher = NULL WHERE id_group = ? AND id_course = ?");
							ps.setInt(1, group.getId());
							ps.setInt(2, idCourse.get(i));
							// Execution of the request
							ps.executeUpdate();
							returnValue++;
						}
						else {
							ps = con.prepareStatement("UPDATE tc_group SET id_teacher = ? WHERE id_group = ? AND id_course = ?");
							ps.setInt(1, idTeacher.get(i));
							ps.setInt(2, group.getId());
							ps.setInt(3, idCourse.get(i));
							// Execution of the request
							ps.executeUpdate();
							returnValue++;
						}
					}
				}		
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
		 * Delete a group from the table group.
		 * If the group is associated to students, the deletion won't be done
		 * Auto-commit by default: each deletion is effective
		 * @param idGroup the id of the group to delete
		 * @return returnValue the number of lines deleted from the table
		 */
		public int delete(int idGroup) {
			Connection con = null;
			PreparedStatement ps = null;
			int returnValue = 0;
			// connection to the database
			try {

				// connection attempt
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				// preparation of the SQL statement, each ? represents the value of ID
				// to communicate in the deletion.
				// the getter retrieves the value of the group's ID
				ps = con.prepareStatement("DELETE FROM groupe WHERE id = ?");
				ps.setInt(1, idGroup);

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
		 * Retrieve a group from the database using his id
		 * @param id : the group's id
		 * @return the retrieved group;
		 * 			null if there is no group with this id
		 */
		public Group get(int id) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			Group returnValue = null;

			// connection to the database
			try {

				// connection attempt
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM groupe WHERE id = ?");
				ps.setInt(1, id);
				// we execute the request
				// rs contains a pointer located just before the first line returned
				rs = ps.executeQuery();
				// go to the first (and only) line returned
				if (rs.next()) {
					returnValue = new Group(rs.getInt("id"),
										    rs.getInt("maxCapacity"),
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
		 * Retrieve a group's id from the database using the lesson's id
		 * @param idLesson : the group's id
		 * @return returnValue  the retrieved group;
		 * 						null if there is no group with this id
		 */
		public int getGroupId(int idLesson) {
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
					returnValue = rs.getInt("id_group");
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
		 * Retrieve all group from the database in a list
		 * @return returnValuean ArrayList of groups 
		 */
		public ArrayList<Group> getList() {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<Group> returnValue = new ArrayList<>();

			// connection to the database
			try {
				// connection attempt
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM groupe ORDER BY id");

				// we execute the request
				rs = ps.executeQuery();
				// we run through the result lines
				while (rs.next()) {
					returnValue.add(new Group(rs.getInt("id"),
						    				  rs.getInt("maxcapacity"),
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
		 * Retrieve all group associated to a teacher for a course from the database in a list
		 * @param teacher : the teacher
		 * @param course : the course
		 * @return an ArrayList of groups for this teacher and course
		 */
		public ArrayList<Group> getList(Teacher teacher, Course course) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<Group> returnValue = new ArrayList<>();

			// connection to the database
			try {
				// connection attempt
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT groupe.id, maxcapacity FROM groupe INNER JOIN tc_group ON (groupe.id = tc_group.id_group) WHERE id_teacher = ? AND id_course = ? ORDER BY id");
				ps.setInt(1, teacher.getId());
				ps.setInt(2, course.getId());
				// we execute the request
				rs = ps.executeQuery();
				// we run through the result lines
				while (rs.next()) {
					returnValue.add(new Group(rs.getInt("id"),
						    				  rs.getInt("maxcapacity"),
						    				  null)
							);
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

}

package dao;

import java.sql.*;
import javax.swing.*;
import java.util.*;
import model.*;


public class LessonDAO extends ConnectionDAO {


	/**
	 * Access class to the data contained in the lesson table
	 * 
	 * @author Franklin AGBIKOSSI,Colombe KOUTCHAKPO
	 * @version 1.0
	 * */
		/**
		 * Constructor
		 * 
		 */
		public LessonDAO() {
			super();
		}

		/**
		 * Add lesson to the table lesson
		 *Auto-commit disabled default : all modifications are performed before commit
		 * @param lesson the lesson to add
		 * @param group the group associate to this lesson
		 * @param course the course associated to this lesson
		 * @return return the number of lines added to the table
		 */
		public int add(Group group, Course course, Lesson lesson) {
			Connection con = null;
			PreparedStatement ps = null;
			int returnValue = 0;

			// connection to the database
			try {

				//  connection attempt
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				
				con.setAutoCommit(false);
				// preparation of the SQL statement, each ? represents a value
				// to communicate on insertion.
				// the getters allow to recover the values of the desired attributes
				if(lesson.getType().equals("Exam")) {
					ps = con.prepareStatement("INSERT INTO lesson (id_tc_group, typelesson, theDay, startTime, endTime, rate) VALUES ((SELECT id FROM TC_GROUP WHERE id_course = ? AND id_group = ?), ?, ?, ?, ?, 50.0)");
					ps.setInt(1, course.getId());
					ps.setInt(2, group.getId());
					ps.setString(3, lesson.getType());
					ps.setInt(4, lesson.getDay());
					ps.setDouble(5, lesson.getStartTime());
					ps.setDouble(6, lesson.getEndTime());
					returnValue = ps.executeUpdate();
					ps.close();
					ps = con.prepareStatement("INSERT INTO lesson_week (id_lesson, week) VALUES (lesson_seq.currval, 10)");
					ps.executeUpdate();
					returnValue++;
				}
				else {
					ps = con.prepareStatement("INSERT INTO lesson (id_tc_group, typelesson, theDay, startTime, endTime, rate) VALUES ((SELECT id FROM TC_GROUP WHERE id_course = ? AND id_group = ?), ?, ?, ?, ?, 50.0)");
					ps.setInt(1, course.getId());
					ps.setInt(2, group.getId());
					ps.setString(3, lesson.getType());
					ps.setInt(4, lesson.getDay());
					ps.setDouble(5, lesson.getStartTime());
					ps.setDouble(6, lesson.getEndTime());
					ps.executeUpdate();
					returnValue++;
					ps.close();
					for (int week = 1; week < 10; week++) {
						ps = con.prepareStatement("INSERT INTO lesson_week (id_lesson, week) VALUES (lesson_seq.currval, ?)");
						ps.setInt(1, week);
						ps.executeUpdate();
						returnValue++;
					}
				}
				// Execution of the request
				con.commit();

			} catch (Exception e) {
				if (e.getMessage().contains("ORA-00001"))
					JOptionPane.showMessageDialog(null,"This id already exists. Impossible to add !");
				else if(e.getMessage().contains("ORA-01400"))
					JOptionPane.showMessageDialog(null,"You must fill all empty spaces!");
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
		 * Edit a lesson in the table lesson.
		 * Auto-commit by default : each update is effective
		 * @param group 
		 * @param course the course associated to a this lesson
		 * @param oldLesson the lesson with the previous information
		 * @param newLesson the lesson with the new information
		 * @return return the number of lines updated in the table
		 */
		public int update(Group group, Course course, Lesson oldLesson, Lesson newLesson) {
			Connection con = null;
			PreparedStatement ps = null;
			int returnValue = 0;

			// connection to the database
			try {

				// connection attempt
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				// preparation of the SQL statement, each ? represents a value
				// to communicate on modification.
				// the getters allow to recover the values of the desired attributes
				ps = con.prepareStatement("UPDATE lesson set id_tc_group = (SELECT id FROM tc_group WHERE id_course = ? AND id_group = ?), typeLesson = ?, theDay = ?, startTime = ?, endTime = ? WHERE id = ?");
				ps.setInt(1, course.getId());
				ps.setInt(2, group.getId());
				ps.setString(3, newLesson.getType());
				ps.setInt(4, newLesson.getDay());
				ps.setDouble(5, newLesson.getStartTime());
				ps.setDouble(6, newLesson.getEndTime());
				ps.setInt(7, oldLesson.getId());

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
		 * Delete a lesson from the table lesson.
		 * If the lesson is associated to a lesson, the deletion won't be done
		 * Auto-commit by default: each deletion is effective
		 * @param lesson the lesson to delete
		 * @return return the number of lines deleted from the table
		 */
		public int delete(Lesson lesson) {
			Connection con = null;
			PreparedStatement ps = null;
			int returnValue = 0;

			// connection to the database
			try {

				// connection attempt
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				// preparation of the SQL statement, each ? represents the value of ID
				// to communicate on deletion.
				// the getter retrieves the value of the lesson's ID
				ps = con.prepareStatement("DELETE FROM lesson WHERE id = ?");
				ps.setInt(1, lesson.getId());

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
		 * Retrieve a lesson from the database using his id
		 * 
		 * @param id : the lesson's id
		 * @return the retrieved lesson;
		 * 			null if there is no lesson with this id
		 */
		public Lesson get(int id) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			Lesson returnValue = null;

			// connection to the database
			try {

				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM lesson WHERE id = ?");
				ps.setInt(1, id);

				// we execute the request
				// rs contains a pointer located right before the first line returned
				rs = ps.executeQuery();
				// go to the first (and only) line returned
				if (rs.next()) {
					returnValue = new Lesson(rs.getInt("id"),
											 rs.getInt("theDay"),
										     rs.getString("TypeLesson"),
										     rs.getDouble("startTime"),
									         rs.getDouble("endTime")
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
		 * Retrieve all lesson associated to a group for a day
		 * @param idGroup the group's id
		 * @param idDay the day of the week
		 * @return an ArrayList of lesson 
		 */
		public ArrayList<Lesson> getListForDayGroup(int idGroup, int idDay) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<Lesson> returnValue = new ArrayList<>();

			// connection to the database
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT lesson.id, typeLesson, theDay, startTime, endTime FROM lesson INNER JOIN tc_group ON (lesson.id_tc_group = tc_group.id) WHERE id_tc_group IN (SELECT id FROM tc_group WHERE id_group = ?) AND theDay = ? ORDER BY theDay, startTime");
				ps.setInt(1, idGroup);
				ps.setInt(2, idDay);
				// we execute the request
				rs = ps.executeQuery();
				// we run through the result lines
				while (rs.next()) {
					returnValue.add(new Lesson(rs.getInt("id"),
							 					rs.getInt("theDay"),
							 					rs.getString("TypeLesson"),
							 					rs.getDouble("startTime"),
							 					rs.getDouble("endTime")
						    				)
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
		 * Retrieve all lesson associated to a teacher for a day
		 * @param idTeacher the teacher's id
		 * @param idDay the day of the week
		 * @return returnValue list of lesson 
		 */
		public ArrayList<Lesson> getListForDayTeacher(int idTeacher, int idDay) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<Lesson> returnValue = new ArrayList<>();

			// connection to the database
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT lesson.id, typeLesson, theDay, startTime, endTime FROM lesson INNER JOIN tc_group ON (lesson.id_tc_group = tc_group.id) WHERE id_tc_group IN (SELECT id FROM tc_group WHERE id_teacher = ? AND id_group IS NOT NULL) AND theDay = ? ORDER BY theDay, startTime");
				ps.setInt(1, idTeacher);
				ps.setInt(2, idDay);
				// we execute the request
				rs = ps.executeQuery();
				// we run through the result lines
				while (rs.next()) {
					returnValue.add(new Lesson(rs.getInt("id"),
							 					rs.getInt("theDay"),
							 					rs.getString("TypeLesson"),
							 					rs.getDouble("startTime"),
							 					rs.getDouble("endTime")
						    				)
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
		 * Retrieve a special case lesson knowing his id and week
		 * @param idLesson the lesson's id
		 * @param idWeek the lesson's week
		 * @return a lesson 
		 */
		public Lesson getSpecialCase(int idLesson, int idWeek) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			Lesson returnValue = null;

			// connection to the database
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM lesson_week INNER JOIN LESSON ON (lesson_week.id_lesson = lesson.id) WHERE absence_rate > rate AND id = ? AND week = ?");
				ps.setInt(1, idLesson);
				ps.setInt(2, idWeek);
				// we execute the request
				rs = ps.executeQuery();
				// we go to the first and only line
				if (rs.next()) {
					returnValue = new Lesson(rs.getInt("id"),
							rs.getInt("theDay"),
							rs.getInt("week"),
							rs.getDouble("absence_rate"),
							rs.getString("typelesson"),
							rs.getDouble("starttime"),
							rs.getDouble("endtime")
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
		 * Retrieve all lesson associated to a group for a course from the database in a list
		 * @param idGroup the group's id
		 * @param idCourse the course's id
		 * @return an ArrayList of modules 
		 */
		public ArrayList<Lesson> getList(int idGroup, int idCourse) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<Lesson> returnValue = new ArrayList<>();

			// connection to the database
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM lesson WHERE id_tc_group = (SELECT id FROM tc_group WHERE id_group = ? AND id_course = ?) ORDER BY id");
				ps.setInt(1, idGroup);
				ps.setInt(2, idCourse);
				// we execute the request
				rs = ps.executeQuery();
				// we run through the result lines
				while (rs.next()) {
					returnValue.add(new Lesson(rs.getInt("id"),
							 					rs.getInt("theDay"),
							 					rs.getString("TypeLesson"),
							 					rs.getDouble("startTime"),
							 					rs.getDouble("endTime")
						    				)
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
		 * Check if there the teacher and group's availability for this course hours
		 * @param group the group whose availability is checked
		 * @param course the course's whose teacher's availability is checked
		 * @param lesson the lesson we want to check availability for
		 * @param idLesson the lesson's id
		 * @return true if there is any lesson interfering
		 * 			false if there is no lesson interfering
		 */
		public boolean interferes(Group group, Course course, Lesson lesson, int idLesson) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			int returnValue = 0;

			// connection to the database
			try {

				con = DriverManager.getConnection(URL, LOGIN, PASS);
				if(lesson.getType().equals("Amphi") || lesson.getType().equals("TD") || lesson.getType().equals("TP"))
					ps = con.prepareStatement("SELECT COUNT(*) FROM lesson WHERE id_tc_group IN ( SELECT id FROM tc_group WHERE id_teacher = ( SELECT id_teacher FROM tc_group WHERE id_group = ? AND id_course = ?) OR id_group = ? ) AND theDay = ? AND (((startTime > ? AND  startTime < ?) OR (endTime > ? AND  endTime < ?)) OR (startTime <= ? AND endTime >= ?)) AND typeLesson != 'Exam' AND id != ?");
				else
					ps = con.prepareStatement("SELECT COUNT(*) FROM lesson WHERE id_tc_group IN ( SELECT id FROM tc_group WHERE id_teacher = ( SELECT id_teacher FROM tc_group WHERE id_group = ? AND id_course = ?) OR id_group = ? ) AND theDay = ? AND (((startTime > ? AND  startTime < ?) OR (endTime > ? AND  endTime < ?)) OR (startTime <= ? AND endTime >= ?)) AND typeLesson = 'Exam' AND id != ?");
				ps.setInt(1, group.getId());
				ps.setInt(2, course.getId());
				ps.setInt(3, group.getId());
				ps.setInt(4, lesson.getDay());
				ps.setDouble(5, lesson.getStartTime());
				ps.setDouble(6, lesson.getEndTime());
				ps.setDouble(7, lesson.getStartTime());
				ps.setDouble(8, lesson.getEndTime());
				ps.setDouble(9, lesson.getStartTime());
				ps.setDouble(10, lesson.getEndTime());
				ps.setInt(11, idLesson);
				
				// we execute the request
				// rs contains a pointer located right before the first line returned
				rs = ps.executeQuery();
				// go to the first (and only) line returned
				if (rs.next()) {
					returnValue = rs.getInt("COUNT(*)");
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
			if (returnValue == 0)
				return false;
			else
				return true;
		}
		
		/**
		 * Retrieve all lessons with presence rate lower than the default rate from the database in a list
		 * @return an ArrayList of courses 
		 */
		public ArrayList<Lesson> getSpecialCasesList() {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<Lesson> returnValue = new ArrayList<>();

			// connection to the database
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM lesson_week INNER JOIN lesson ON (lesson_week.id_lesson = lesson.id) WHERE absence_rate > rate and absence_conserved IS NULL");
				// we execute the request
				rs = ps.executeQuery();
				// we run through the result lines
				while (rs.next()) {
					returnValue.add(new Lesson(rs.getInt("id"),
							rs.getInt("theDay"),
							rs.getInt("week"),
							rs.getDouble("absence_rate"),
							rs.getString("typelesson"),
							rs.getDouble("starttime"),
							rs.getDouble("endtime")
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

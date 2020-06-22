package dao;

import java.sql.*;
import java.util.*;
import model.*;

public class AbsenceDAO extends ConnectionDAO{
	/**
	 * Access class to the data contained in the table absence and his join with other table
	 * 
	 * @author Franklin AGBIKOSSI, Colombe KOUTCHAKPO
	 * @version 1.3
	 * */
		/**
		 * Constructor
		 * 
		 */
		public AbsenceDAO() {
			super();
		}
		
		/**
		 * Retrieve lessons with a presence rate lower than the default specified
		 * 
		 * @return an ArrayList of lesson 
		 */
		public boolean getSpecialCases() {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			int returnValue = 0;

			// connection to the database
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT COUNT(*) FROM lesson_week INNER JOIN LESSON ON (lesson_week.id_lesson = lesson.id) WHERE absence_rate > rate AND absence_conserved IS NULL");
				// we execute the request
				rs = ps.executeQuery();
				// we run through the result lines
				while (rs.next()) {
					returnValue = new Integer(rs.getInt("COUNT(*)"));
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
			if(returnValue != 0)
				return true;
			else
				return false;
		}
		
		/**
		 * Retrieve from database students with absence to a lesson
		 * @param the lesson's id
		 * @return an ArrayList of students
		 */
		public boolean wasAbsent(int idStudent, int idLesson, int idWeek) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			int returnValue = 0 ;

			// connexion a la base de donnees
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT COUNT(*) FROM student INNER JOIN absence ON (student.id = absence.id_student) WHERE id_lesson = ? AND id_student = ? AND id_week = ? ORDER BY id");
				ps.setInt(1, idLesson);
				ps.setInt(2, idStudent);
				ps.setInt(3, idWeek);
				// on execute la requete
				rs = ps.executeQuery();
				// on parcourt les lignes du resultat
				while (rs.next()) {
					returnValue = new Integer(rs.getInt("COUNT(*)"));
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
			if(returnValue != 0)
				return true;
			else
				return false;
		}
		
		/**
		 * Update absence table
		 * Auto commit disabled by default : every modification will be done before commit
		 * @param 
		 * @return return the number of lines added to the table
		 */
		public int update(Lesson lesson, int week, ArrayList<Student> list, double absenceRate) {
			Connection con = null;
			PreparedStatement ps = null;
			int returnValue = 0;

			// connection to the database
			try {

				// connection attempt
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				con.setAutoCommit(false);

				returnValue++;
				for(Student student: list) {
					ps = con.prepareStatement("INSERT INTO absence(id_lesson, id_week, id_student) VALUES (?, ?, ?)");
					ps.setInt(1, lesson.getId());
					ps.setInt(2, week);
					ps.setInt(3, student.getId());
					ps.executeUpdate();
					ps.close();
					returnValue++;
				}
				ps.close();
				if(absenceRate > 50) {
					ps = con.prepareStatement("UPDATE lesson_week SET absence_rate = ? WHERE id_lesson = ? AND week = ?");
				}
				else {
					ps = con.prepareStatement("UPDATE lesson_week SET absence_rate = ?, absence_conserved = 1 WHERE id_lesson = ? AND week = ?");
				}
				ps.setDouble(1, absenceRate);
				ps.setInt(2, lesson.getId());
				ps.setInt(3, week);
				ps.executeUpdate();
				returnValue++;
				// Execution of the request
				con.commit();

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
		 * Set the column isJustified in absence table to true(1)
		 * @param the proof associated to the absence
		 * @return return the number of lines updated in the table
		 */
		public int validateProof(Proof proof) {
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
				ps = con.prepareStatement("UPDATE absence set isJustified = 1 WHERE id_proof = ?");
				ps.setInt(1, proof.getId());
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
		 * Set the column isJustified in absence table to false(0)
		 * @param the proof associated to the absence
		 * @return return the number of lines updated in the table
		 */
		public int rejectProof(Proof proof) {
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
				ps = con.prepareStatement("UPDATE absence set isJustified = 0 WHERE id_proof = ?");
				ps.setInt(1, proof.getId());
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
		 * Delete all absence related to a lesson
		 * Auto-commit by default: each deletion is effective
		 * @param lesson the lesson whose absences must be deleted
		 * @return return the number of lines deleted from the table
		 */
		private int delete(Lesson lesson) {
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
				ps = con.prepareStatement("DELETE FROM absence WHERE id_lesson = ?");
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
		 * Maintain all absence related to a lesson
		 * Auto-commit by default: each update is effective
		 * @param lesson the lesson whose absences must be maintained
		 * @return return the number of lines updated from the table
		 */
		public int maintainAbsence(Lesson lesson) {
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
				ps = con.prepareStatement("UPDATE lesson_week SET absence_conserved = 1 WHERE id_lesson = ? AND week = ?");
				ps.setInt(1, lesson.getId());
				ps.setInt(2, lesson.getWeek());

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
		 * Retrieve all absences for a student from the database in a list
		 * @param the student's whose absences are returned
		 * @return an ArrayList of absences
		 */
		public ArrayList<Absence> getAbsenceForStudent(Student student) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<Absence> returnValue = new ArrayList<>();

			// connection to the database
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM absence INNER JOIN lesson ON (absence.id_lesson = lesson.id) WHERE id_student = ? ORDER BY id_week, theDay");
				ps.setInt(1, student.getId());
				// we execute the request
				rs = ps.executeQuery();
				// we run through the result lines
				while (rs.next()) {
					returnValue.add(new Absence(new Lesson(rs.getInt("id"),
							rs.getInt("theDay"),
							rs.getInt("id_week"),
							rs.getString("typelesson"),
							rs.getDouble("starttime"),
							rs.getDouble("endtime")
							)));
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
		 * Retrieve all absences of a student for a teacher for all his courses with him from the database in a list
		 * @param student the student whose absences must be returned
		 * @param teacher the teacher who want the student's absences
		 * @return an ArrayList of absences 
		 */
		public ArrayList<Absence> getAbsenceForCourses(Student student, Teacher teacher) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<Absence> returnValue = new ArrayList<>();

			// connection to the database
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM absence INNER JOIN lesson ON (absence.id_lesson = lesson.id) WHERE id_student = ? AND id_tc_group = (SELECT id FROM tc_group WHERE id_teacher = ? AND id_group IS NOT NULL)");
				ps.setInt(1, student.getId());
				ps.setInt(2, teacher.getId());
				// we execute the request
				rs = ps.executeQuery();
				// we run through the result lines
				while (rs.next()) {
					returnValue.add(new Absence(new Lesson(rs.getInt("id"),
							rs.getInt("theDay"),
							rs.getInt("id_week"),
							rs.getString("typelesson"),
							rs.getDouble("starttime"),
							rs.getDouble("endtime")
							)));
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
		 * Retrieve all absences for a student to justify from the database in a list
		 * @param student the student whose unjustified absence are returned
		 * @return an ArrayList of absences 
		 */
		public ArrayList<Absence> getAbsencesToJustify(Student student) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<Absence> returnValue = new ArrayList<>();

			// connection to the database
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM absence INNER JOIN lesson ON (absence.id_lesson = lesson.id) WHERE id_student = ? AND id_proof is NULL ORDER BY id_week, theDay");
				ps.setInt(1, student.getId());
				// we execute the request
				rs = ps.executeQuery();
				// we run through the result lines
				while (rs.next()) {
					returnValue.add(new Absence(new Lesson(rs.getInt("id"),
							rs.getInt("theDay"),
							rs.getInt("id_week"),
							rs.getString("typelesson"),
							rs.getDouble("starttime"),
							rs.getDouble("endtime")
							)));
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
		 * Retrieve all absences associated to a proof from the database in a list
		 * @param proof the proof whose associated absence are returned
		 * @return an ArrayList of absences 
		 */
		public ArrayList<Absence> getAbsenceForProof(Proof proof) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<Absence> returnValue = new ArrayList<>();

			// connection to the database
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM absence INNER JOIN proof ON (absence.id_proof = proof.id) INNER JOIN lesson ON (absence.id_lesson = lesson.id) WHERE id_proof = ?");
				ps.setInt(1, proof.getId());
				// we execute the request
				rs = ps.executeQuery();
				// we run through the result lines
				while (rs.next()) {
					returnValue.add(new Absence(new Lesson(rs.getInt("id"),
							rs.getInt("theDay"),
							rs.getInt("id_week"),
							rs.getString("typelesson"),
							rs.getDouble("starttime"),
							rs.getDouble("endtime")
							)));
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
		 * Retrieve all absences for a student which can be associated to a proof from the database in a list
		 * @param proof the proof we want to associate to an absence
		 * @return an ArrayList of absences 
		 */
		public ArrayList<Absence> getAbsenceToAssociate(Proof proof) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<Absence> returnValue = new ArrayList<>();

			// connection to the database
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM absence INNER JOIN lesson ON (absence.id_lesson = lesson.id) WHERE id_student = (SELECT id_student FROM proof WHERE id = ?)");
				ps.setInt(1, proof.getId());
				// we execute the request
				rs = ps.executeQuery();
				// we run through the result lines
				while (rs.next()) {
					returnValue.add(new Absence(new Lesson(rs.getInt("id"),
							rs.getInt("theDay"),
							rs.getInt("id_week"),
							rs.getString("typelesson"),
							rs.getDouble("starttime"),
							rs.getDouble("endtime")
							)));
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
		 * Remove all absence related to a lesson and 
		 * @param lesson the lesson whose absences must be maintained
		 * @return return the number of lines updated from the table
		 */
		public int removeAbsence(Lesson lesson) {
			Connection con = null;
			PreparedStatement ps = null;
			int returnValue = 0;
			// connection to the database
			try {
				returnValue+=delete(lesson);
				if(returnValue != 0) {
					// connection attempt
					con = DriverManager.getConnection(URL, LOGIN, PASS);
					// preparation of the SQL statement, each ? represents the value of ID
					// to communicate on deletion.
					// the getter retrieves the value of the course's ID
					ps = con.prepareStatement("UPDATE lesson_week SET absence_conserved = 0 WHERE id_lesson = ? AND week = ?");
					ps.setInt(1, lesson.getId());
					ps.setInt(2, lesson.getWeek());
	
					// Execution of the request
					returnValue = ps.executeUpdate();
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
		 * Check if this lesson's absences have been handled already 
		 * 
		 * @return an ArrayList of lesson 
		 */
		public boolean processed(Lesson lesson, int idWeek) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			String returnValue = null;

			// connection to the database
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM lesson_week WHERE id_lesson = ? AND week = ?");
				ps.setInt(1, lesson.getId());
				ps.setInt(2, idWeek);
				// we execute the request
				rs = ps.executeQuery();
				// we run through the result lines
				while (rs.next()) {
					returnValue = rs.getString("absence_conserved");
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
			if(returnValue != null)
				return true;
			else
				return false;
		}
}

package dao;
import java.sql.*;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.*;

/**
 * Class that give access to the table Type_absence
 * 
 * @author Franklin AGBIKOSSI, Colombe KOUTCHAKPO
 * @version 1.0
 * */

public class AbsenceTypeDAO extends ConnectionDAO {

		/**
		 * Constructor
		 * 
		 */
		public AbsenceTypeDAO() {
			super();
		}

		/**
		 * Adds a type_absence into the table Type_absence
		 * auto-commit by default : each insertion are validated
		 * 
		 * @param type_absence the Type_absence to add
		 * @return return the number of lines added into the table
		 */
		public int add(AbsenceType absenceType) {
			Connection con = null;
			PreparedStatement ps = null;
			int returnValue = 0;

			// connection to BDD
			try {

				// Trying to connect
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				// preparing SQL instruction, each ? represents a value
				// that needs to be told to be inserted
				// using getters to get the value
				ps = con.prepareStatement("INSERT INTO type_absence (entitle) VALUES (?)");
				ps.setString(1, absenceType.getEntitle());

				// Executing request
				returnValue = ps.executeUpdate();

			} catch (Exception e) {
					e.printStackTrace();
			} finally {
				// closing preparedStatement and the connection
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
		 * Updates a type_absence in the table Type_absence
		 * auto-commit by default : each insertion are validated
		 * 
		 * @param type_absence the Type_absence to update
		 * @return return the number of lines updated
		 */
		public int update(AbsenceType absenceType) {
			Connection con = null;
			PreparedStatement ps = null;
			int returnValue = 0;

			// connection to BDD
			try {

				// Trying to connect
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				// preparing SQL instruction, each ? represents a value
				// that needs to be told to be inserted
				// using getters to get the value
				ps = con.prepareStatement("UPDATE type_absence set entitle = ? WHERE id = ?");
				ps.setString(1, absenceType.getEntitle());
				ps.setInt(2, absenceType.getId());

				// Executing request
				returnValue = ps.executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// closing preparedStatement and the connection
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
		 * Removes a type_absence from the table Type_absence.
		 * If it is used by a Teacher, then this will not occur.
		 * Auto-commit by default : each insertion are validated
		 * 
		 * @param type_absence the Type_absence to remove
		 * @return return the number of lines removed
		 */
		public int delete(AbsenceType absenceType) {
			Connection con = null;
			PreparedStatement ps = null;
			int returnValue = 0;

			// connection to BDD
			try {

				// Trying to connect
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				// preparing SQL instruction, each ? represents a value
				// that needs to be told to be inserted
				// using getters to get the value
				ps = con.prepareStatement("DELETE FROM type_absence WHERE id = ?");
				ps.setInt(1, absenceType.getId());

				// Executing request
				returnValue = ps.executeUpdate();

			} catch (Exception e) {
				if (e.getMessage().contains("ORA-02292"))
					JOptionPane.showMessageDialog(null, "Type of absence associated to absence");
				else
					e.printStackTrace();
			} finally {
				// closing preparedStatement and Connection
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
		 * Retrieves a type_absence from Type_absence
		 * 
		 * @param id of the type_absence 
		 * @return the type_absence found;
		 * 			null if no type_absence corresponding to the id
		 */
		public AbsenceType get(int id) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			AbsenceType returnValue = null;

			// connection to BDD
			try {

				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM type_absence WHERE id = ?");
				ps.setInt(1, id);

				// Execting request
				// rs contain a pointer just before the first returned line
				rs = ps.executeQuery();
				// Go to the next line
				if (rs.next()) {
					returnValue = new AbsenceType(rs.getInt("id"),
										       rs.getString("entitle")
										    );
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			} finally {
				// Closing ResultSet, PreparedStatement and Connection
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
		 * Retrieves all type_absence in table Type_absence
		 * 
		 * @return An ArrayList of Type_absence
		 */
		public ArrayList<AbsenceType> getList() {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<AbsenceType> returnValue = new ArrayList<>();

			// connection to BDD
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM type_absence ORDER BY id");

				// Executing request
				rs = ps.executeQuery();
				// Going from line to line
				while (rs.next()) {
					returnValue.add(new AbsenceType(rs.getInt("id"),
							                     rs.getString("entitle")
							                     )
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
		 * Retrieves a type_absence from Type_absence with lesson
		 * 
		 * @param id of the lesson 
		 * @return the type_absence found;
		 * 			null if no type_absence corresponding to the lesson's id
		 */
		public AbsenceType get(Lesson lesson, Student student) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			AbsenceType returnValue = null;

			// connection to BDD
			try {

				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM type_absence INNER JOIN absence ON(type_absence.id = absence.id_type_absence) WHERE id_lesson = ? AND id_student = ?");
				ps.setInt(1, lesson.getId());
				ps.setInt(2, student.getId());

				// Execting request
				// rs contain a pointer just before the first returned line
				rs = ps.executeQuery();
				// Go to the next line
				if (rs.next()) {
					returnValue = new AbsenceType(rs.getInt("id"),
										       rs.getString("entitle")
										    );
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			} finally {
				// Closing ResultSet, PreparedStatement and Connection
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

}

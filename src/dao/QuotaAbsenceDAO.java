package dao;

import java.sql.*;


import java.util.ArrayList;

import model.QuotaAbsence;

/**
 * Class that give access to the table Quota_absence
 * 
 * @author Franklin AGBIKOSSI, Colombe KOUTCHAKPO
 * @version 2.0
 * */

public class QuotaAbsenceDAO extends ConnectionDAO {

		/**
		 * Constructor
		 * 
		 */
		public QuotaAbsenceDAO() {
			super();
		}

		/**
		 * Adds a quota into the table Quota_absence
		 * auto-commit by default : each insertion are validated
		 * 
		 * @param quota the Quota_absence to add
		 * @return return the number of lines added into the table
		 */
		public int add(QuotaAbsence quota) {
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
				ps = con.prepareStatement("INSERT INTO quota_absence (hours) VALUES (?)");
				ps.setInt(1, quota.getHours());

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
		 * Updates a quota in the table Quota_absence
		 * auto-commit by default : each insertion are validated
		 * 
		 * @param quota the Quota_absence to update
		 * @return return the number of lines updated
		 */
		public int update(QuotaAbsence quota) {
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
				ps = con.prepareStatement("UPDATE quota_absence set hours = ? WHERE id = ?");
				ps.setInt(1, quota.getHours());
				ps.setInt(2, quota.getId());

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
		 * Removes a quota from the table Quota_absence.
		 * If it is used by a Teacher, then this will not occur.
		 * Auto-commit by default : each insertion are validated
		 * 
		 * @param quota the Quota_absence to remove
		 * @return return the number of lines removed
		 */
		public int delete(QuotaAbsence quota) {
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
				ps = con.prepareStatement("DELETE FROM quota_absence WHERE id = ?");
				ps.setInt(1, quota.getId());

				// Executing request
				returnValue = ps.executeUpdate();

			} catch (Exception e) {
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
		 * Retrieves a quota from Quota_absence
		 * 
		 * @param id of the quota 
		 * @return the quota found;
		 * 			null if no quota corresponding to the id
		 */
		public QuotaAbsence get(int id) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			QuotaAbsence returnValue = null;

			// connection to BDD
			try {

				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM quota_absence WHERE id = ?");
				ps.setInt(1, id);

				// Executing request
				// rs contain a pointer just before the first returned line
				rs = ps.executeQuery();
				// Go to the next line
				if (rs.next()) {
					returnValue = new QuotaAbsence(rs.getInt("id"),
												rs.getInt("hours")
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
		 * Retrieves all quota in table Quota_absence
		 * 
		 * @return An ArrayList of Quota_absence
		 */
		public ArrayList<QuotaAbsence> getList() {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<QuotaAbsence> returnValue = new ArrayList<>();

			// connection to BDD
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM quota_absence ORDER BY id");

				// Executing request
				rs = ps.executeQuery();
				// Going from line to line
				while (rs.next()) {
					returnValue.add(new QuotaAbsence(rs.getInt("id"),
							rs.getInt("hours"))
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

}
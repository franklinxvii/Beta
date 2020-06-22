package dao;

import java.sql.*;
import javax.swing.*;
import java.util.*;
import model.*;

public class ModuleDAO extends ConnectionDAO {


	/**
	 * Access class to the data contained in the module table
	 * 
	 * @author Franklin AGBIKOSSI, Colombe KOUTCHAKPO
	 * @version 1.0
	 * */
		/**
		 * Constructor
		 * 
		 */
		public ModuleDAO() {
			super();
		}

		/**
		 * Add module to the table module
		 * Auto-commit by default : each add is effective
		 * 
		 * @param module the module to add
		 * @return return the number of lines added to the table
		 */
		public int add(Module module) {
			Connection con = null;
			PreparedStatement ps = null;
			int returnValue = 0;

			// connection to the database
			try {

				//  connection attempt
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				// preparation of the SQL statement, each ? represents a value
				// to communicate on insertion.
				// the getters allow to recover the values of the desired attributes
				ps = con.prepareStatement("INSERT INTO module (name) VALUES (?)");
				ps.setString(1, module.getName());

				// Execution of the request
				returnValue = ps.executeUpdate();

			} catch (Exception e) {
				if (e.getMessage().contains("ORA-00001"))
					JOptionPane.showMessageDialog(null,"This id/name already exists. Impossible to add !");
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
		 * Edit a module in the table module.
		 * Auto-commit by default : each update is effective
		 * 
		 * @param module the module to update
		 * @return return the number of lines updated in the table
		 */
		public int update(Module module) {
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
				ps = con.prepareStatement("UPDATE module set name = ? WHERE id = ?");
				ps.setString(1, module.getName());
				ps.setInt(2, module.getId());

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
		 * Delete a module from the table module.
		 * If the module is associated to a module, the deletion won't be done
		 * Auto-commit by default: each deletion is effective
		 * 
		 * @param module the module to delete
		 * @return return the number of lines deleted from the table
		 */
		public int delete(Module module) {
			Connection con = null;
			PreparedStatement ps = null;
			int returnValue = 0;

			// connection to the database
			try {

				// connection attempt
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				// preparation of the SQL statement, each ? represents the value of ID
				// to communicate on deletion.
				// the getter retrieves the value of the module's ID
				ps = con.prepareStatement("DELETE FROM module WHERE id = ?");
				ps.setInt(1, module.getId());

				// Execution of the request
				returnValue = ps.executeUpdate();

			} catch (Exception e) {
				if (e.getMessage().contains("ORA-02292"))
					JOptionPane.showMessageDialog(null,"This module is associated to at least a course. Impossible to delete !"
							         + " Delete the course first.");
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
		 * Retrieve a module from the database using his id
		 * 
		 * @param id : the module's id
		 * @return the retrieved module;
		 * 			null if there is no module with this id
		 */
		public Module get(int id) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			Module returnValue = null;

			// connection to the database
			try {

				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM module WHERE id = ?");
				ps.setInt(1, id);

				// we execute the request
				// rs contains a pointer located right before the first line returned
				rs = ps.executeQuery();
				// go to the first (and only) line returned
				if (rs.next()) {
					returnValue = new Module(rs.getInt("id"),
										       rs.getString("name")
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
		 * Retrieve all module from the database in a list
		 * 
		 * @return an ArrayList of modules 
		 */
		public ArrayList<Module> getList() {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<Module> returnValue = new ArrayList<>();

			// connection to the database
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM module ORDER BY id");

				// we execute the request
				rs = ps.executeQuery();
				// we run through the result lines
				while (rs.next()) {
					returnValue.add(new Module(rs.getInt("id"),
							                     rs.getString("name")
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

}

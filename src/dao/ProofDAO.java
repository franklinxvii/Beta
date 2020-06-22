package dao;

import java.sql.*;
import javax.swing.*;
import java.util.*;
import model.*;

public class ProofDAO extends ConnectionDAO {


	/**
	 * Access class to the data contained in the proof table
	 * 
	 * @author Franklin AGBIKOSSI, Colombe KOUTCHAKPO
	 * @version 1.0
	 * */
		/**
		 * Constructor
		 * 
		 */
		public ProofDAO() {
			super();
		}

		/**
		 * Add create proof and associate it to absences
		 * Auto-commit deactivated 
		 * 
		 * @param proof the proof to add
		 * @param student the student who add the proof
		 * @param absences the absences to associate
		 * @return return the number of lines added to the table
		 */
		public int add(Proof proof, Student student, ArrayList<Absence> absences) {
			Connection con = null;
			PreparedStatement ps = null;
			int returnValue = 0;

			// connection to the database
			try {
				// connection attempt
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				con.setAutoCommit(false);
				// preparation of the SQL statement, each ? represents a value
				// to communicate on insertion.
				// the getters allow to recover the values of the desired attributes
				ps = con.prepareStatement("INSERT INTO proof (id_student,proof_path) VALUES (?,?)");
				ps.setInt(1, student.getId());
				ps.setString(2, proof.getProofPath());
				// Execution of the request
				ps.executeUpdate();
				returnValue++;
				if(!absences.isEmpty()) {
					for(Absence absence: absences) {
						if(proof.getId() == 0) {
							ps = con.prepareStatement("UPDATE absence set id_proof = proof_seq.currval WHERE id_lesson = ? AND id_week = ? AND id_student = ?");
							ps.setInt(1, absence.getLesson().getId());
							ps.setInt(2, absence.getLesson().getWeek());
							ps.setInt(3, student.getId());
						}
						else{
							ps = con.prepareStatement("UPDATE absence set id_proof = ? WHERE id_lesson = ? AND id_week = ? AND id_student = ?");
							ps.setInt(1, absence.getLesson().getId());
							ps.setInt(2, absence.getLesson().getWeek());
							ps.setInt(3, student.getId());
						}
						ps.executeUpdate();
						returnValue++;
					}
				}
				con.commit();

			} catch (Exception e) {
				if(e.getMessage().contains("ORA-00001"))
					JOptionPane.showMessageDialog(null, "A proof with this name already exist");
				else if(e.getMessage().contains("ORA-01400")) {
					JOptionPane.showMessageDialog(null, "You didn't select any file");
				}
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
		 * Retrieve a proof from the database using his id
		 * 
		 * @param id : the proof's id
		 * @return the retrieved proof;
		 * 			null if there is no proof with this id
		 */
		public Proof get(int id) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			Proof returnValue = null;

			// connection to the database
			try {

				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT * FROM proof WHERE id = ?");
				ps.setInt(1, id);

				// we execute the request
				// rs contains a pointer located right before the first line returned
				rs = ps.executeQuery();
				// go to the first (and only) line returned
				if (rs.next()) {
					returnValue = new Proof(rs.getInt("id"),
										       rs.getString("proof_path")
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
		 * Retrieve all proof from the database in a list
		 * 
		 * @return returnValue an ArrayList of proofs 
		 */
		public ArrayList<Proof> getList() {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<Proof> returnValue = new ArrayList<>();

			// connection to the database
			try {
				con = DriverManager.getConnection(URL, LOGIN, PASS);
				ps = con.prepareStatement("SELECT DISTINCT id, proof_path FROM proof LEFT OUTER JOIN absence ON (proof.id_student = absence.id_student) WHERE isJustified IS NULL ORDER BY id");
				// we execute the request
				rs = ps.executeQuery();
				// we run through the result lines
				while (rs.next()) {
					returnValue.add(new Proof(rs.getInt("id"),
						       					rs.getString("proof_path")
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

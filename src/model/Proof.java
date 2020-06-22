
package model;

/**
 * Class Proof
 * @author Franklin AGBIKOSSI, Colombe KOUTCHAKPO
 * @version 1.0
 * */
public class Proof {
	private int id;
	private String proofPath;
	/**
	 * Constructor without id parameter
	 * @param proofPath
	 */
	public Proof(String proofPath) {
		this.proofPath = proofPath;
	}
	/**
	 * constructor with id parameter
	 * @param id
	 * @param proofPath
	 */
	public Proof(int id, String proofPath) {
		this.id = id;
		this.proofPath = proofPath;
	}
	/**
	 * proof id getter
	 * @return the  proof id
	 */
	public int getId() {
		return id;
	}
	/**
	 * proof id setter
	 * @param id the proof id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * proofPath getter
	 * @return the proofPath
	 */
	public String getProofPath() {
		return proofPath;
	}
	/**
	 * proofPath setter
	 * @param proofPath the proofPath to set
	 */
	public void setProofPath(String proofPath) {
		this.proofPath = proofPath;
	}
	
}

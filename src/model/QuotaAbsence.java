package model;

/**
 * Class Module
 * @author Franklin AGBIKOSSI, Colombe KOUTCHAKPO
 * @version 1.0
 * */
public class QuotaAbsence {
	private int id;
	private int hours;
	
	/**
	 * Constructor
	 * @param id
	 * @param hours
	 */
	public QuotaAbsence(int id, int hours) {
		this.id = id;
		this.hours = hours;
	}

	/**
	 * Constructor with only hours 
	 * @param hours
	 */
	public QuotaAbsence(int hours) {
		this.hours = hours;
	}


	/**
	 *  quotaAbsence  id Getter
	 * 
	 * @return Id
	 */
	public int getId() {
		return id;
	}

	/**
	 * new id Setter
	 * 
	 * @param  id
	 */
	public void setId_qta(int id) {
		this.id = id;
	}

	/**
	 * hours getter
	 * 
	 * @return hours
	 */
	public int getHours() {
		return hours;
	}

	/**
	 * hours setter
	 * 
	 * @param  hours
	 */
	public void setHours(int hours) {
		this.hours = hours;
	}
	
}

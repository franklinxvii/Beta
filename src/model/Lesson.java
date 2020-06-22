/**
 * 
 */
package model;

/**
 * Class Lesson
 * @author Franklin AGBIKOSSI, Colombe KOUTCHAKPO
 * @version 1.0
 * */
public class Lesson {
	private int id;
	private int day;
	private int week;
	private double effectiveRate;
	private String type;
	private double startTime;
	private double endTime;
	
	/**
	 * Constructor of Lesson
	 * @param day
	 * @param type
	 * @param startTime
	 * @param endTime
	 */
	public Lesson(int day, String type, double startTime, double endTime) {
		this.day = day;
		this.type = type;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * Lesson Constructor with parameter id 
	 * @param id
	 * @param day
	 * @param type
	 * @param startTime
	 * @param endTime
	 */
	public Lesson(int id, int day, String type, double startTime, double endTime) {
		this.id = id;
		this.day = day;
		this.type = type;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	

	/**
	 * Lesson Constructor with additional week attribute
	 * @param id
	 * @param day
	 * @param week
	 * @param type
	 * @param startTime
	 * @param endTime
	 */
	public Lesson(int id, int day, int week, String type, double startTime, double endTime) {
		this.id = id;
		this.day = day;
		this.week = week;
		this.type = type;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * Lesson Constructor that involved effectivedRate of work
	 * @param id
	 * @param day
	 * @param week
	 * @param effectiveRate
	 * @param type
	 * @param startTime
	 * @param endTime
	 */
	public Lesson(int id, int day, int week, double effectiveRate, String type, double startTime, double endTime) {
		this.id = id;
		this.day = day;
		this.week = week;
		this.effectiveRate = effectiveRate;
		this.type = type;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * lesson id getter
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * lesson id setter
	 * @param id the id 
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * days getter
	 * @return the day
	 */
	public int getDay() {
		return day;
	}

	/**
	 * days setter
	 * @param day the day 
	 */
	public void setDay(int day) {
		this.day = day;
	}

	/**
	 * type getter
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * type setter
	 * @param type the type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * startTime getter
	 * @return the startTime
	 */
	public double getStartTime() {
		return startTime;
	}

	/**
	 * startTime setter
	 * @param startTime the startTime 
	 */
	public void setStartTime(double startTime) {
		this.startTime = startTime;
	}

	/**
	 * endTime getter
	 * @return the endTime
	 */
	public double getEndTime() {
		return endTime;
	}

	/**
	 * endTime setter
	 * @param endTime the endTime 
	 */
	public void setEndTime(double endTime) {
		this.endTime = endTime;
	}

	/**
	 * week getter
	 * @return the week
	 */
	public int getWeek() {
		return week;
	}

	/**
	 * week setter
	 * @param week the week 
	 */
	public void setWeek(int week) {
		this.week = week;
	}

	/**
	 * effectiveRate getter
	 * @return the effectiveRate
	 */
	public double getEffectiveRate() {
		return effectiveRate;
	}

	/**
	 * effectiveRate setter
	 * @param effectiveRate 
	 */
	public void setEffectiveRate(double effectiveRate) {
		this.effectiveRate = effectiveRate;
	}
	
	
}

/**
 * 
 */
package model;

/**
 * Class Absence
 * @author Franklin AGBIKOSSI, Colombe KOUTCHAKPO
 * @version 1.0
 * */
public class Absence {
	private Lesson lesson;
	private AbsenceType absenceType;
	private Proof proof;
	
	/**
	 * Absence Constructor
	 * @param lesson
	 */
	public Absence(Lesson lesson) {
		this.lesson = lesson;
	}

	/**
	 * Absence constructor completed by absenceType
	 * @param lesson
	 * @param absenceType
	 */
	public Absence(Lesson lesson, AbsenceType absenceType) {
		this.lesson = lesson;
		this.absenceType = absenceType;
	}

	/**
	 * absence Constructor with affiliate proof
	 * @param lesson
	 * @param absenceType
	 * @param proof
	 */
	public Absence(Lesson lesson, AbsenceType absenceType, Proof proof) {
		this.lesson = lesson;
		this.absenceType = absenceType;
		this.proof = proof;
	}

	/**
	 * absence's lesson  getter
	 * @return the lesson
	 */
	public Lesson getLesson() {
		return lesson;
	}

	/**
	 * absence's lesson setter
	 * @param lesson the lesson to set
	 */
	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}

	/**
	 * absenceType getter
	 * @return the absenceType
	 */
	public AbsenceType getAbsenceType() {
		return absenceType;
	}

	/**
	 * absenceType setter
	 * @param absenceType the absenceType to set
	 */
	public void setAbsenceType(AbsenceType absenceType) {
		this.absenceType = absenceType;
	}

	/**
	 * absence proof getter
	 * @return the proof
	 */
	public Proof getProof() {
		return proof;
	}

	/**
	 * absence proof setter
	 * @param proof the proof to set
	 */
	public void setProof(Proof proof) {
		this.proof = proof;
	}	
}


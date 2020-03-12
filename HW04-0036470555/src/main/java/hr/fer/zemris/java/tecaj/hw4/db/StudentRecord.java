package hr.fer.zemris.java.tecaj.hw4.db;
/**
 * Student record is class that describes single student. It has 4 read-only properties:
 * <li> jmbag
 * <li> lastName
 * <li> firstName
 * <li> finalGrade
 * @author Ivan Pavić
 */
public class StudentRecord {
	/**
	 * Property jmbag, unique student id
	 */
	private final String jmbag;
	/*
	 * Property last name
	 */
	private final String lastName;
	/*
	 * Property first name
	 */
	private final String firstName;
	/*
	 * Grade of certain student, for reason is string because there are different types
	 * of grades. A, B, C, 1, 2, 3 etc.
	 */
	private final String finalGrade;
	/**
	 * Contructs student record from given fields.
	 * @param jmbag unique student id
	 * @param lastName student's last name
	 * @param firstName student's first name
	 * @param finalGrade student's grade
	 */
	public StudentRecord(final String jmbag, final String lastName, final String firstName,
			final String finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}
	/**
	 * Getter for Jmbag.
	 * @return string jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}
	/**
	 * Getter for last name.
	 * @return string lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * Getter for first name.
	 * @return string first name
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * Getter for final grade
	 * @return string final grade
	 */
	public String getFinalGrade() {
		return finalGrade;
	}
	/**
	 * Check if students are equal by criteria of jmbag.
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null) {
				return false;
			}
		} else if (!jmbag.equals(other.jmbag)) {
			return false;
		}
		return true;
	}
	/**
	 * Generates hash code from jmbag.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}
}

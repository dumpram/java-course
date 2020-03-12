package hr.fer.zemris.java.tecaj.hw4.db;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Class StudentDatabase's constructor gets a list of String(the content of database.txt).
 * It creates an internal list of student records. Additionally, it creates an index for
 *  fast retrieval of student records when jmbag is known.
 * @author Ivan Pavić
 */
public class StudentDatabase {
	/**
	 * records of all students.
	 */
	List<StudentRecord> records;
	/**
	 * hashmap for fast retrieval.
	 */
	HashMap<String, StudentRecord> map;
	/**
	 * Constructs class with parameter input.
	 * @param input represents document from which database is created
	 */
	public StudentDatabase(final List<String> input) {
		if (input == null) {
			throw new IllegalArgumentException("Argument can't be null");
		}
		String[] parts;
		records = new ArrayList<>();
		map = new HashMap<>();
		StudentRecord newOne;
		for (final String i : input) {
			parts = i.split("\\t");
			newOne = new StudentRecord(parts[0], parts[1], parts[2], parts[3]);
			records.add(newOne);
			map.put(newOne.getJmbag(), newOne);
		}
	}
	/**
	 * Method for fast retrieval of StudentRecord with key jmbag
	 * @param jmbag given unique student id
	 * @return StudentRecord which is stored under key jmbag
	 */
	public StudentRecord forJMBAG(final String jmbag) {
		if (jmbag == null) {
			throw new IllegalArgumentException("Argument can't be null");
		}
		return map.get(jmbag);
	}
	/**
	 * Method for retrieval of StudentRecord(s) which met IFiler criteria.
	 * @param filter specifies criteria
	 * @return list of students that met criteria
	 */
	public List<StudentRecord> filter(final IFilter filter) {
		final ArrayList<StudentRecord> newOne = new ArrayList<>();
		for (final StudentRecord i : records) {
			if (filter.accepts(i)) {
				newOne.add(i);
			}
		}
		return newOne;
	}
}


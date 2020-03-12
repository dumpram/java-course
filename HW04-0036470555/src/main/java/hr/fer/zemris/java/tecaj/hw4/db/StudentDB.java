package hr.fer.zemris.java.tecaj.hw4.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * StudentDB is "test class" for emulated database. It reads all lines from
 * database.txt delegates work to StudentDatabase which creates new database.
 * Than there is some sort of emulated prompt with following commands:
 * <li> query jmbag = "wanted jmbag"
 * <li> query lastName = "wanted last name" (you can also use '*' for expanded search,
 * 		but only once.
 * <li> quit (obviously for leaving the prompt).
 * @author Ivan Pavić
 */
public class StudentDB {
	/**
	 * Main method;
	 * @param args
	 * @throws IOException
	 */
	public static void main(final String[] args) {

		List<String> lines = null;
		try {
			lines = Files.readAllLines(
					Paths.get("database.txt"),
					StandardCharsets.UTF_8
					);
		} catch (final IOException e) {
			System.out.println("File couldn't be read");
			e.printStackTrace();
		}
		/*REGEXES*/
		final String lastNameRegex = "\\s*query\\s+lastName\\s*=\\s*\"([a-zA-Z*]+)\"\\s*";
		final String jmbagRegex = "\\s*query\\s+jmbag\\s*=\\s*\\\"([0-9]+)\\\"\\s*";
		final String quitRegex = "\\s*quit\\s*";
		/*PATTERNS*/
		final Pattern lastNamePattern = Pattern.compile(lastNameRegex);
		final Pattern jmbagPattern = Pattern.compile(jmbagRegex);
		/*MATCHER*/
		Matcher m;
		/*FILTERED STUDENTS*/
		List<StudentRecord> filtered;
		final StudentDatabase newOne = new StudentDatabase(lines);
		/*SCANNER && UNOS*/
		Scanner scanner;
		String unos;
		while (true) {
			System.out.print(">");
			scanner = new Scanner(System.in);
			unos = scanner.nextLine();
			if (unos.matches(lastNameRegex)) {
				m = lastNamePattern.matcher(unos);
				m.matches();
				filtered = newOne.filter(new LastNameFilter(m.group(1)));
				printResults(filtered);
			} else if (unos.matches(jmbagRegex)) {
				m = jmbagPattern.matcher(unos);
				m.matches();
				filtered = new ArrayList<>();
				filtered.add(newOne.forJMBAG(m.group(1)));
				printResults(filtered);
			} else if (unos.matches(quitRegex)){
				break;
			} else {
				System.out.println("Invalid Command");
			}
		}
		scanner.close();
	}
	/**
	 * Method which formats output of database.
	 * @param filtered students that met criteria of filtration
	 */
	private static void printResults(final List<StudentRecord> filtered) {
		if (filtered.size() == 0) {
			System.out.println("Records selected: 0");
			return;
		}
		final int[] columnSize = new int[4];
		for (final StudentRecord i : filtered) {
			if (columnSize[0] < i.getJmbag().length()) {
				columnSize[0] = i.getJmbag().length();
			}
			if (columnSize[1] < i.getLastName().length()) {
				columnSize[1] = i.getLastName().length();
			}
			if (columnSize[2] < i.getFirstName().length()) {
				columnSize[2] = i.getFirstName().length();
			}
			if (columnSize[3] < i.getFinalGrade().length()) {
				columnSize[3] = i.getFinalGrade().length();
			}
			}
		final String format = "| %-" + columnSize[0] + "s | %-" + columnSize[1]
				+ "s | %-" + columnSize[2] + "s | %-" + columnSize[3] + "s |%n";
		String border = "+";
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < columnSize[i] + 2; j++) {
				border += "=";
			}
		border +="+";
		}
		System.out.println(border);
		for (final StudentRecord i : filtered) {
			System.out.format(format, i.getJmbag(), i.getLastName(), i.getFirstName(), i.getFinalGrade());
		}
		System.out.println(border);
		System.out.println("Records selected: " + filtered.size());
	}
}

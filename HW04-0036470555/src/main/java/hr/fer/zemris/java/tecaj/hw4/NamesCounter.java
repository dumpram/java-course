package hr.fer.zemris.java.tecaj.hw4;

import java.util.HashMap;
import java.util.Scanner;
/**
 * Class with no purpose except practicing maps. It is
 * very simple. It has main method which prints all
 * names in map with number of times certain name
 * appeared.
 * @author Ivan Pavić
 */
public class NamesCounter {
	/**
	 * Returns value in map which is stored on position of key.
	 * @param key certain value
	 * @param map given map
	 * @return value in map with key parameter "key"
	 */
	static int counter(final String key, final HashMap<String,Integer> map) {
		if (map.get(key) == null) {
			return 0;
		} else {
			return map.get(key);
		}
	}
	/**
	 * Main method does as described above.
	 * @param args
	 */
	public static void main(final String[] args) {
		final Scanner scanner = new Scanner(System.in);
		String pom;
		final HashMap<String,Integer> names = new HashMap<>();
		while (!scanner.hasNext("quit")) {
			pom = scanner.next();
			names.put(pom, 1 + counter(pom, names));
		}
		scanner.close();
		for (final String i : names.keySet()) {
			System.out.println(i + " " + names.get(i));
		}

	}

}

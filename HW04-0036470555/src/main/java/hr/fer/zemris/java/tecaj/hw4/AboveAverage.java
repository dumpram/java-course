package hr.fer.zemris.java.tecaj.hw4;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
/**
 * Class AboveAverage has no purpose. It is here only because
 * of practice of using list, collections, bulk methods... Furthermore
 * it is solution for 2. problem in homework.
 * @author Ivan Pavić
 */
public class AboveAverage {
	/**
	 * Calculates average in double list.
	 * @param list given list of doubles
	 * @return average value of double
	 */
	static Double calcAverageList(final List<Double> list) {
		if (list == null) {
			throw new IllegalArgumentException();
		}
		double sum = 0;
		for (final Double i : list) {
			sum += i;
		}
		return sum/list.size();
	}
	/**
	 * Main method prints out numbers greater than average.
	 * @param args
	 */
	public static void main (final String[] args) {
		final Scanner scanner  = new Scanner(System.in);
		final ArrayList<Double> newOne = new ArrayList<>();

		while (scanner.hasNextDouble()) {
			newOne.add(scanner.nextDouble());
		}
		if (scanner.next().compareToIgnoreCase("quit") != 0) {
			scanner.close();
			throw new IllegalArgumentException();
		}
		scanner.close();
		final double limit = calcAverageList(newOne)*1.2;
		Collections.sort(newOne);
		for (final Double i : newOne) {
			if (i.compareTo(limit) == 1) {
				System.out.println(i);
			}
		}

	}
}

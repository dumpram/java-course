package hr.fer.zemris.java.sorters;

import java.util.Random;

/**
 * Example class for quick sort implementation.
 * 
 * @author Ivan Pavić
 * 
 */
public class Glavni {
    /**
     * Main method.
     * 
     * @param args
     *            non required
     */
    public static void main(String[] args) {
	final int SIZE = 150_000;
	Random rand = new Random();
	int[] data = new int[SIZE];
	for (int i = 0; i < data.length; i++) {
	    data[i] = rand.nextInt();
	}
	long t0 = System.currentTimeMillis();
	QSortParallel.sort(data);
	long t1 = System.currentTimeMillis();
	System.out.println("Sortirano: " + QSortParallel.isSorted(data));
	System.out.println("Vrijeme: " + (t1 - t0) + " ms");
    }
}

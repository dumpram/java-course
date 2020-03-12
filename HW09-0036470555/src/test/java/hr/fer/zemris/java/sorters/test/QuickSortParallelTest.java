package hr.fer.zemris.java.sorters.test;

import static org.junit.Assert.assertTrue;
import hr.fer.zemris.java.sorters.QSortParallel;

import java.util.Random;

import org.junit.Test;

public class QuickSortParallelTest {

    @Test
    public void OfficialTest() {
	final int SIZE = 150_000;
	Random rand = new Random();
	int[] data = new int[SIZE];
	for (int i = 0; i < data.length; i++) {
	    data[i] = rand.nextInt();
	}
	QSortParallel.sort(data);
	assertTrue(QSortParallel.isSorted(data));
    }
}

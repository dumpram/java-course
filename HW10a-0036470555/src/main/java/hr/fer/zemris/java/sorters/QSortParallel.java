package hr.fer.zemris.java.sorters;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
/**
 * Does parallel sort of given array by delegating it to QSortJob instances
 * using ForkJoinPool class.
 * 
 * @author Ivan Pavić
 * 
 */
public class QSortParallel {
	/**
	 * Prag koji govori koliko elemenata u podpolju minimalno mora biti da bi se
	 * sortiranje nastavilo paralelno; ako elemenata ima manje, algoritam
	 * prelazi na klasično rekurzivno (slijedno) sortiranje.
	 */
	private final static int P_THRESHOLD = 16;
	/**
	 * Prag za prekid rekurzije. Ako elemenata ima više od ovoga, quicksort
	 * nastavlja rekurzivnu dekompoziciju. U suprotnom ostatak sortira
	 * algoritmom umetanja.
	 */
	private final static int CUT_OFF = 5;
	/**
	 * Sučelje prema klijentu: prima polje i vraća se tek kada je polje
	 * sortirano. Primjenjujući gornje pragove najprije posao paralelizira a
	 * kada posao postane dovoljno mali, rješava ga slijedno.
	 * 
	 * @param array
	 *            polje koje treba sortirati
	 */
	public static void sort(int[] array) {
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(new QSortJob(array, 0, array.length - 1));
		pool.shutdown();
	}

	/**
	 * Model posla sortiranja podpolja čiji su elementi na pozicijama koje su
	 * veće ili jednake <code>startIndex</code> i manje ili jednake
	 * <code>endIndex</code>.
	 */
	static class QSortJob extends RecursiveAction {
		/**
		 * Serial version.
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * Private array reference.
		 */
		private final int[] array;
		/**
		 * Start index.
		 */
		private final int startIndex;
		/**
		 * End index.
		 */
		private final int endIndex;
		/**
		 * Constructor for single QSortJob takes array reference and indexes
		 * which control domain of sorting in given array which QSortJob needs
		 * to sort.
		 * 
		 * @param array
		 *            array reference
		 * @param startIndex
		 *            beginning index
		 * @param endIndex
		 *            end index (inclusive)
		 */
		public QSortJob(int[] array, int startIndex, int endIndex) {
			this.array = array;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
		}

		@Override
		protected void compute() {
			if (endIndex - startIndex + 1 > CUT_OFF) {
				boolean doInParallel = endIndex - startIndex + 1 > P_THRESHOLD;
				int p = selectPivot();
				swap(array, p, endIndex);
				int pivot = array[endIndex];
				int i = startIndex;
				int j = endIndex - 1;
				for (;;) {
					while (i < j && array[i] < pivot) {
						i++;
					}
					while (i < j && array[j] > pivot) {
						j--;
					}
					if (i >= j) {
						break;
					}
					swap(array, i++, j--);
				}
				if (array[i] > pivot) {
					swap(array, i, endIndex);
				}
				QSortJob job1 = null;
				QSortJob job2 = null;
				if (startIndex < i) {
					job1 = new QSortJob(array, startIndex, i);
				}
				if (endIndex > i) {
					job2 = new QSortJob(array, i + 1, endIndex);
				}
				if (!doInParallel) {
					if (job1 != null) {
						job1.compute();
					}
					if (job2 != null) {
						job2.compute();
					}
				} else {
					invokeAll(job1, job2);
				}
			} else {
				insertionSort();
			}
		}
		/**
		 * Odabir pivota metodom medijan-od-tri u dijelu polja
		 * <code>array</code> koje je ograđeno indeksima <code>startIndex</code>
		 * i <code>endIndex</code> (oba uključena).
		 * 
		 * @return vraća indeks na kojem se nalazi odabrani pivot
		 */
		public int selectPivot() {
			int middle = (startIndex + endIndex) / 2;
			if (array[startIndex] > array[middle]) {
				swap(array, startIndex, middle);
			}
			if (array[startIndex] > array[endIndex]) {
				swap(array, startIndex, endIndex);
			}
			if (array[middle] > array[endIndex]) {
				swap(array, middle, endIndex);
			}
			return middle;
		}

		/**
		 * U predanom polju <code>array</code> zamjenjuje elemente na pozicijama
		 * <code>i</code> i <code>j</code>.
		 * 
		 * @param array
		 *            polje u kojem treba zamijeniti elemente
		 * @param i
		 *            prvi indeks
		 * @param j
		 *            drugi indeks
		 */
		public static void swap(int[] array, int i, int j) {
			int t = array[i];
			array[i] = array[j];
			array[j] = t;
		}
		/**
		 * Obavlja sort umetanjem.
		 */
		public void insertionSort() {
			for (int i = startIndex + 1; i < endIndex + 1; i++) {
				for (int j = i; j > startIndex && array[j - 1] > array[j]; j--) {
					swap(array, j, j - 1);
				}
			}
		}
	}
	/**
	 * Pomoćna metoda koja provjerava je li predano polje doista sortirano
	 * uzlazno.
	 * 
	 * @param array
	 *            polje * @return <code>true</code> ako je, <code>false</code>
	 *            inače
	 */
	public static boolean isSorted(int[] array) {
		for (int i = 0; i < array.length - 1; i++) {
			if (array[i] > array[i + 1]) {
				return false;
			}
		}
		return true;
	}

}

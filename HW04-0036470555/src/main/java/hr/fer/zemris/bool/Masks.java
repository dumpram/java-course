package hr.fer.zemris.bool;
import java.util.ArrayList;
import java.util.List;
/**
 * Class Masks provides a static factory methods for creation of multiple masks in a
 * single method call. For example:
 * <li>List<Mask> masks = Masks.fromStrings("0x1x", "1110", "x00x");
 * <li>List<Mask> masks = Masks.fromIndexes(3, 0, 1, 7); <br>
 * The latter call produces masks of size 3: "000", "001", "111".
 * @author Ivan Pavić
 *
 */
public class Masks {
	/**
	 * Private unused constructor.
	 */
	private Masks() {
	}
	/**
	 * Creates multiple masks and returns them in list. Masks are created from variable
	 * number of strings in input.
	 * @param masks strings to parse
	 * @return new list containing masks
	 */
	public static List<Mask> fromStrings(final String... masks) {
		final ArrayList<Mask> maskList = new ArrayList<>();

		for (int i = 0; i < masks.length; i++) {
			maskList.add(Mask.parse(masks[i]));
		}
		return maskList;
	}
	/**
	 * Creates multiple masks and returns them in list. Masks are created from variable
	 * number of indexes. Masks have size given.s
	 * @param size size of masks that will be created
	 * @param index indexes from which masks are created
	 * @return new list containing masks
	 */
	public static List<Mask> fromIndexes(final int size, final int... index) {
		if (size < 0) {
			throw new IllegalArgumentException();
		}
		final ArrayList<Mask> newOne = new ArrayList<>();
		for (int i = 0; i < index.length; i++) {
			newOne.add(Mask.fromIndex(size, index[i]));
		}
		return newOne;
	}
}

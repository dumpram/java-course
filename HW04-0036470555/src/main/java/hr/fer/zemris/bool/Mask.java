package hr.fer.zemris.bool;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Instances of class Mask represent a single mask. Each instance stores mask as
 * an array of MaskValue. Class Mask offers a single public constructor which
 * accepts an array of MaskValue. It also offers two public static factory
 * methods: parse which accepts a string representation of a mask and
 * fromIndexwhich accepts two arguments: mask size and index. The idea is that
 * you can write the following (both examples generate the same mask):
 * <p>
 * Mask m1 = new Mask(new MaskValue[] {<br>
 * MaskValue.ZERO, MaskValue.DONT_CARE, MaskValue.ONE, MaskValue.DONT_CARE<br>
 * });<br>
 * Mask m2 = Mask.parse("0x1x");
 * </p>
 *
 * @author Ivan Pavić
 */
public class Mask {
	/**
	 * Variable is array that contains MaskValue(s) which "this" masks has.
	 */
	private final MaskValue[] values;

	/**
	 * Constructs Mask from given values.
	 *
	 * @param values
	 *            variable number of MaskValue(s) or array.
	 */
	public Mask(final MaskValue... values) {
		this.values = values;
	}

	/**
	 * Static factory method that parses string and creates Mask.
	 *
	 * @param mask
	 *            given string
	 * @return new instance of mask which was represented by given string
	 */
	public static Mask parse(String mask) {
		if (mask == null) {
			throw new IllegalArgumentException("Argument can't be null");
		}
		mask = mask.trim();
		mask = mask.replaceAll(" ", "");
		if (mask.length() == 0) {
			throw new IllegalArgumentException("Unable to parse empty String");
		}
		final MaskValue[] values = new MaskValue[mask.length()];

		for (int i = 0; i < mask.length(); i++) {
			if (mask.charAt(i) == '1') {
				values[i] = MaskValue.ONE;
			} else if (mask.charAt(i) == '0') {
				values[i] = MaskValue.ZERO;
			} else if (mask.charAt(i) == 'x' || mask.charAt(i) == 'X') {
				values[i] = MaskValue.DONT_CARE;
			} else {
				throw new IllegalArgumentException(mask.charAt(i)
						+ " is not legal MaskValue");
			}
		}
		return new Mask(values);
	}

	/**
	 * Getter for number of zeros in mask.
	 *
	 * @return number of zeros
	 */
	public int getNumberOfZeros() {
		return getNumberOf(MaskValue.ZERO);
	}

	/**
	 * Getter for number of ones in mask.
	 *
	 * @return number of ones.
	 */
	public int getNumberOfOnes() {
		return getNumberOf(MaskValue.ONE);
	}

	/**
	 * Getter for number of anything. In fact this is auxiliary method for
	 * getters of ones and zeros.
	 *
	 * @param value
	 *            one or zero
	 * @return number of ones or zeros
	 */
	private int getNumberOf(final MaskValue value) {
		int counter = 0;
		for (int i = 0; i < values.length; i++) {
			if (values[i] == value) {
				counter++;
			}
		}
		return counter;
	}

	/**
	 * Static factory method for creating Mask from given size and index.
	 *
	 * @param size
	 *            size of mask
	 * @param index
	 *            given index
	 * @return mask representation of index
	 */
	public static Mask fromIndex(final int size, final int index) {
		if (index >= Math.pow(2, size) || index < 0 || size <= 0) {
			throw new IllegalArgumentException();
		}
		final ArrayList<MaskValue> newOne = new ArrayList<>();
		String represent = Integer.toBinaryString(index);
		while (represent.length() != size) {
			represent = "0" + represent;
		}
		for (int i = 0; i < represent.length(); i++) {
			if (represent.charAt(i) == '1') {
				newOne.add(MaskValue.ONE);
			} else {
				newOne.add(MaskValue.ZERO);
			}
		}
		final MaskValue[] forExport = new MaskValue[newOne.size()];
		for (int i = 0; i < newOne.size(); i++) {
			forExport[i] = newOne.get(i);
		}
		return new Mask(forExport);
	}

	/**
	 * Getter for value on given index in mask.
	 *
	 * @param index
	 *            given index
	 * @return MaskValue ZERO, ONE or DON'T CARE
	 */
	public MaskValue getValue(final int index) {
		if (index < 0 || index > (values.length - 1)) {
			throw new IllegalArgumentException();
		}
		return values[index];
	}

	/**
	 * Getter for size mask.
	 *
	 * @return size of mask
	 */
	public int getSize() {
		return values.length;
	}

	/**
	 * Check if masks are equal but they don't have to be equal in all values.
	 * Specifically if value of first one on some index is ONE and other is
	 * DONT_CARE that is considered equal.
	 *
	 * @param other
	 * @return true if they are equal, false otherwise.
	 */
	public boolean dontCareEquals(final Mask other) {
		if (other == null) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < values.length; i++) {
			if (values[i] != other.values[i]
					&& other.values[i] != MaskValue.DONT_CARE
					&& values[i] != MaskValue.DONT_CARE) {
				return false;
			}
		}
		if (values.length != other.values.length) {
			return false;
		}
		return true;
	}

	/**
	 * HashCode method for class Mask.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(values);
		return result;
	}

	/**
	 * Equals method for Mask. Masks are equal if they arrays of values are
	 * equal.
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
		if (this.hashCode() != obj.hashCode()) {
			return false;
		}
		final Mask other = (Mask) obj;
		if (!Arrays.equals(values, other.values)) {
			return false;
		}
		return true;
	}

	/**
	 * Method examines is this Mask more general than some other. If so returns
	 * true, otherwise false. This mask is more general if other mask can be
	 * made by making this mask more specific(changing DONT_CARE value to ONE or
	 * ZERO)
	 *
	 * @param other
	 * @return
	 */
	public boolean isMoreGeneralThan(final Mask other) {
		if (other == null) {
			throw new IllegalArgumentException();
		}
		if (getSize() != other.getSize()) {
			return false;
		}
		for (int i = 0; i < getSize(); i++) {
			if ((getValue(i) != MaskValue.DONT_CARE && other.getValue(i) == MaskValue.DONT_CARE)
					|| (getValue(i) != MaskValue.DONT_CARE
							&& other.getValue(i) != MaskValue.DONT_CARE && getValue(i) != other
							.getValue(i))) {
				return false;
			}
		}
		if (equals(other)) {
			return false;
		}
		return true;
	}

	/**
	 * The method Mask.combinegenerates a new mask if this is possible. For
	 * example, you can combine "0x01" and "0x00" into "0x0x" because expanding
	 * "0x0x" would produce exactly "0x01" and "0x00" and nothing more. If given
	 * masks are not "combineable" method returns null.
	 *
	 * @param first
	 *            Mask
	 * @param second
	 *            Mask
	 * @return new Mask which is combination of first and second or null.
	 */
	public static Mask combine(final Mask first, final Mask second) {
		if (first == null || second == null) {
			throw new IllegalArgumentException();
		}
		if (first.getSize() != second.getSize()) {
			return null;
		}

		final MaskValue[] forExport = new MaskValue[first.getSize()];
		for (int i = 0; i < first.getSize(); i++) {
			if (first.getValue(i) == MaskValue.DONT_CARE
					&& second.getValue(i) != MaskValue.DONT_CARE
					|| first.getValue(i) != MaskValue.DONT_CARE
					&& second.getValue(i) == MaskValue.DONT_CARE) {
				return null;
			} else if (first.getValue(i) == second.getValue(i)) {
				forExport[i] = first.getValue(i);
			} else {
				forExport[i] = MaskValue.DONT_CARE;
			}
		}
		return new Mask(forExport);
	}

	/**
	 * Mask to String.
	 */
	@Override
	public String toString() {
		String forExport = "";
		for (int i = 0; i < values.length; i++) {
			if (values[i] == MaskValue.ONE) {
				forExport += '1';
			} else if (values[i] == MaskValue.ZERO) {
				forExport += '0';
			} else {
				forExport += 'x';
			}
		}
		return forExport;
	}
}

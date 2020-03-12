package hr.fer.zemris.linearna;

import java.util.Arrays;

/**
 * Class is concrete implementation of AbstractVector. Stores elements in array.
 * 
 * @author Ivan Pavić
 * 
 */
public class Vector extends AbstractVector {
    /**
     * Private array of elements.
     */
    private final double[] elements;
    /**
     * Private dimension value.
     */
    private int dimension;
    /**
     * Read only flag, true if vector can't be modified.
     */
    private final boolean readOnly;

    /**
     * Constructs vector from given array of elements.
     * 
     * @param elements
     *            given array of elements.
     */
    public Vector(double... elements) {
	this(false, false, elements);
    }

    /**
     * Constructs vector from given array of elements and properties.
     * 
     * @param readOnly
     *            true if read only
     * @param isForUseOnlyInVector
     *            if false new array is allocated
     * @param elements
     *            given array
     */
    public Vector(boolean readOnly, boolean isForUseOnlyInVector,
	    double... elements) {
	this.readOnly = readOnly;
	if (isForUseOnlyInVector) {
	    this.elements = elements;
	    this.dimension = elements.length;
	} else {
	    this.elements = Arrays.copyOf(elements, elements.length);
	    this.dimension = elements.length;
	}
    }

    @Override
    public IVector copy() {
	return new Vector(this.toArray());
    }

    @Override
    public double get(int index) {
	checkInputIndex(index);
	return elements[index];
    }

    @Override
    public int getDimension() {
	return dimension;
    }

    @Override
    public IVector set(int index, double value)
	    throws UnmodifiableObjectException {
	if (readOnly) {
	    throw new UnmodifiableObjectException();
	}
	checkInputIndex(index);
	this.elements[index] = value;
	return this;
    }

    /**
     * Checks if input index is legal.
     * 
     * @param index
     *            given index.
     */
    private void checkInputIndex(int index) {
	if (index < 0) {
	    throw new IllegalArgumentException("Index must be non-negative!");
	}
	if (index >= dimension) {
	    throw new IllegalArgumentException(
		    "Index given exceeds vector dimensions!");
	}
    }

    @Override
    public IVector newInstance(int dimension) {
	if (dimension < 0) {
	    throw new IllegalArgumentException("Dimension must be positive!");
	}
	return new Vector(new double[dimension]);
    }

    /**
     * Parses given String to Vector.
     * 
     * @param vectorToParse
     *            given String
     * @return Vector representation of string.
     */
    public static Vector parseSimple(String vectorToParse) {
	if (vectorToParse == null) {
	    throw new IllegalArgumentException(
		    "Input vectorToParse string can't be null");
	}
	vectorToParse = vectorToParse.trim();
	String[] components = vectorToParse.split(" ");
	double[] numComponents = new double[components.length];
	for (int i = 0; i < components.length; i++) {
	    numComponents[i] = Double.parseDouble(components[i]);
	}
	return new Vector(numComponents);
    }

}

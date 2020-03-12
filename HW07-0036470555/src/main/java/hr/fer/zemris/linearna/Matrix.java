package hr.fer.zemris.linearna;

import java.util.Arrays;

/**
 * Class Matrix is concrete implementation of AbstractMatrix.
 * 
 * @author Ivan Pavić
 * 
 */
public class Matrix extends AbstractMatrix {
    /**
     * Elements of matrix.
     */
    protected double[][] elements;
    /**
     * Number of rows in matrix.
     */
    protected int rows;
    /**
     * Number of columns in matrix.
     */
    protected int cols;

    /**
     * Constructor takes number of rows and columns as arguments returns matrix
     * filled with zeros.
     * 
     * @param rows
     *            number of rows
     * @param cols
     *            number of columns
     */
    public Matrix(int rows, int cols) {
	this(rows, cols, new double[rows][cols], false);
    }

    /**
     * Constructor takes number of rows and columns as arguments returns matrix
     * with content of given array.
     * 
     * @param rows2
     *            number of rows
     * @param cols2
     *            number of columns
     * @param array
     *            given array
     * @param isForUseOnlyInMatrix
     *            true if array is is only for use in matrix, if false new
     *            private array is created.
     */
    public Matrix(int rows2, int cols2, double[][] array,
	    boolean isForUseOnlyInMatrix) {
	rows = rows2;
	cols = cols2;
	if (isForUseOnlyInMatrix) {
	    elements = array;
	} else {
	    elements = new double[rows][cols];
	    for (int i = 0; i < rows; i++) {
		elements[i] = Arrays.copyOf(array[i], cols);
	    }
	}
    }

    @Override
    public int getRowsCount() {
	return rows;
    }

    @Override
    public int getColsCount() {
	return cols;
    }

    @Override
    public double get(int row, int col) {
	checkRowColIndex(row, col);
	return elements[row][col];
    }

    private void checkRowColIndex(int row, int col) {
	if (row >= rows || row < 0 || col >= cols || col < 0) {
	    throw new IllegalArgumentException(
		    "Invalid column or row provided!");
	}

    }

    @Override
    public IMatrix set(int row, int col, double value) {
	checkRowColIndex(row, col);
	elements[row][col] = value;
	return this;
    }

    @Override
    public IMatrix copy() {
	return new Matrix(rows, cols, this.toArray(), false);
    }

    @Override
    public IMatrix newInstance(int rows, int cols) {
	return new Matrix(rows, cols);
    }

    /**
     * Parses input String to Matrix.
     * 
     * @param input
     *            given String.
     * @return Matrix representation of given String.
     */
    public static Matrix parseSimple(String input) {
	String matrixString = input.trim();
	String[] rows = matrixString.split("\\|");
	String[][] matrixStringValues = new String[rows.length][];
	for (int i = 0; i < rows.length; i++) {
	    rows[i] = rows[i].trim();
	    matrixStringValues[i] = rows[i].split(" ");
	}
	double[][] matrixValues = new double[rows.length][matrixStringValues[0].length];
	for (int i = 0; i < rows.length; i++) {
	    for (int j = 0; j < matrixStringValues[0].length; j++) {
		matrixValues[i][j] = Double
			.parseDouble(matrixStringValues[i][j]);
	    }
	}
	return new Matrix(rows.length, matrixValues[0].length, matrixValues,
		true);
    }
}

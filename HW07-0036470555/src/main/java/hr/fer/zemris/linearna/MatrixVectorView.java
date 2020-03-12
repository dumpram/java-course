package hr.fer.zemris.linearna;

/**
 * Class is used for representation of IVector as IMatrix.
 * 
 * @author Ivan Pavić
 * 
 */
public class MatrixVectorView extends AbstractMatrix implements IMatrix {
    /**
     * If true matrix is row matrix.
     */
    private final boolean asRowMatrix;
    /**
     * Private reference to original vector.
     */
    private final IVector original;

    /**
     * Constructor takes orginal vector, and flag which is true if matrix is row
     * and false if matrix is column vector.
     * 
     * @param original
     *            given original IVector
     * @param asRowMatrix
     *            flag which functions as described above
     */
    public MatrixVectorView(IVector original, boolean asRowMatrix) {
	if (original == null) {
	    throw new IllegalArgumentException();
	}
	this.asRowMatrix = asRowMatrix;
	this.original = original;
    }

    @Override
    public int getRowsCount() {
	if (asRowMatrix) {
	    return 1;
	}
	return original.getDimension();
    }

    @Override
    public int getColsCount() {
	if (!asRowMatrix) {
	    return 1;
	}
	return original.getDimension();
    }

    @Override
    public double get(int row, int col) {
	if (row != 0 && col != 0) {
	    throw new IllegalArgumentException("Invalid input arguments!");
	}
	if (asRowMatrix) {
	    return original.get(col);
	}
	return original.get(row);
    }

    @Override
    public IMatrix set(int row, int col, double value) {
	if (row != 1 && col != 1) {
	    throw new IllegalArgumentException("Invalid input arguments!");
	}
	if (asRowMatrix) {
	    original.set(col, value);
	} else {
	    original.set(row, value);
	}
	return this;
    }

    @Override
    public IMatrix copy() {
	return new MatrixVectorView(original.copy(), asRowMatrix);
    }

    @Override
    public IMatrix newInstance(int rows, int cols) {
	if (asRowMatrix) {
	    return new MatrixVectorView(original.newInstance(cols), asRowMatrix);
	} else {
	    return new MatrixVectorView(original.newInstance(rows), asRowMatrix);
	}
    }
}

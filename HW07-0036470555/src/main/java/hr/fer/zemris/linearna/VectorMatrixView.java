package hr.fer.zemris.linearna;

/**
 * Class enables to view one row or one column of IMatrix as IVector.
 * 
 * @author Ivan Pavić
 * 
 */
public class VectorMatrixView extends AbstractVector implements IVector {
    /**
     * Dimension of vector.
     */
    private int dimension;
    /**
     * True if matrix is row.
     */
    private boolean rowMatrix;
    /**
     * Private reference to original matrix.
     */
    private final IMatrix original;

    /**
     * Constructs VectorMatrixView from given matrix.
     * 
     * @param original
     *            given original matrix.
     */
    public VectorMatrixView(IMatrix original) {
	int row = original.getRowsCount();
	int col = original.getColsCount();
	if (!(row == 1 && col >= 1 || col == 1 && row >= 1)) {
	    throw new IncompatibleOperandException("Must be 1 row or 1 column");
	}
	if (row == 1) {
	    rowMatrix = true;
	    dimension = col;
	} else {
	    dimension = row;
	}
	this.original = original;
    }

    @Override
    public double get(int index) {
	if (index < 0 || index > dimension) {
	    throw new IllegalArgumentException("Invalid index provided!");
	}
	if (rowMatrix) {
	    return original.get(0, index);
	}
	return original.get(index, 0);
    }

    @Override
    public IVector set(int index, double value) {
	if (index < 0 || index > dimension) {
	    throw new IllegalArgumentException("Invalid index provided!");
	}
	if (rowMatrix) {
	    original.set(0, index, value);
	} else {
	    original.set(index, 0, value);
	}
	return this;
    }

    @Override
    public int getDimension() {
	return dimension;
    }

    @Override
    public IVector copy() {
	return new VectorMatrixView(original.copy());
    }

    @Override
    public IVector newInstance(int dimension) {
	if (rowMatrix) {
	    return new VectorMatrixView(original.newInstance(1, dimension));
	} else {
	    return new VectorMatrixView(original.newInstance(dimension, 1));
	}

    }
}

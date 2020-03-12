package hr.fer.zemris.linearna;

/**
 * Class defines abstract matrix and implements IMatrix.
 * 
 * @author Ivan Pavić
 * 
 */
public abstract class AbstractMatrix implements IMatrix {
    /**
     * Default constructor.
     */
    public AbstractMatrix() {

    }

    @Override
    public abstract int getRowsCount();

    @Override
    public abstract int getColsCount();

    @Override
    public abstract double get(int row, int col);

    @Override
    public abstract IMatrix set(int row, int col, double value);

    @Override
    public abstract IMatrix copy();

    @Override
    public abstract IMatrix newInstance(int rows, int cols);

    @Override
    public IMatrix nTranspose(boolean liveView) {
	IMatrix newOne = newInstance(getColsCount(), getRowsCount());
	if (!liveView) {
	    for (int i = 0; i < getRowsCount(); i++) {
		for (int j = 0; j < getColsCount(); j++) {
		    newOne.set(j, i, get(i, j));
		}
	    }
	} else {
	    return new MatrixTransposeView(this);
	}
	return newOne;
    }

    @Override
    public IMatrix add(IMatrix other) {
	checkDimOfGivenMatrix(other);
	int rows = getRowsCount();
	int cols = getColsCount();
	for (int i = 0; i < rows; i++) {
	    for (int j = 0; j < cols; j++) {
		set(i, j, get(i, j) + other.get(i, j));
	    }
	}
	return this;
    }

    /**
     * Checks if dimension of given matrix is same as current matrix.
     * 
     * @param other
     *            given matrix
     * @throws IllegalArgumentException
     *             if given matrix is null
     * @throws IncompatibleOperandException
     *             if dimension don't match.
     */
    private void checkDimOfGivenMatrix(IMatrix other)
	    throws IllegalArgumentException, IncompatibleOperandException {
	if (other == null) {
	    throw new IllegalArgumentException("Given matrix can't be null!");
	}
	if (getColsCount() != other.getColsCount()) {
	    throw new IncompatibleOperandException();
	}
	if (getRowsCount() != other.getRowsCount()) {
	    throw new IncompatibleOperandException();
	}
    }

    @Override
    public IMatrix nAdd(IMatrix other) {
	return this.copy().add(other);
    }

    @Override
    public IMatrix sub(IMatrix other) {
	checkDimOfGivenMatrix(other);
	int rows = getRowsCount();
	int cols = getColsCount();
	for (int i = 0; i < rows; i++) {
	    for (int j = 0; j < cols; j++) {
		set(i, j, get(i, j) - other.get(i, j));
	    }
	}
	return this;
    }

    @Override
    public IMatrix nSub(IMatrix other) {
	return this.copy().sub(other);
    }

    @Override
    public IMatrix nMultiply(IMatrix other) {
	if (other == null) {
	    throw new IllegalArgumentException("Given matrix can't be null!");
	}
	if (getColsCount() != other.getRowsCount()) {
	    throw new IncompatibleOperandException(
		    "Matrixes of given dimensions can't be multiplied");
	}
	IMatrix forExport = newInstance(getRowsCount(), other.getColsCount());
	double value = 0;
	for (int i = 0; i < getRowsCount(); i++) {
	    for (int j = 0; j < other.getColsCount(); j++) {
		for (int k = 0; k < getColsCount(); k++) {
		    value += get(i, k) * other.get(k, j);
		}
		forExport.set(i, j, value);
		value = 0;
	    }
	}
	return forExport;
    }

    @Override
    public double determinant() throws IncompatibleOperandException {
	if (getRowsCount() != getColsCount()) {
	    throw new IncompatibleOperandException();
	}
	if (getRowsCount() == 1) {
	    return get(0, 0);
	}
	if (getRowsCount() == 2) {
	    return get(0, 0) * get(1, 1) - get(0, 1) * get(1, 0);
	}
	double value = 0;
	for (int i = 0; i < getColsCount(); i++) {
	    value = value + get(0, i) * Math.pow(-1, i)
		    * subMatrix(0, i, true).determinant();
	}
	return value;
    }

    @Override
    public IMatrix subMatrix(int row, int col, boolean liveView) {
	IMatrix sub = newInstance(getRowsCount() - 1, getColsCount() - 1);
	double[] data = new double[getRowsCount() * getColsCount()];
	int counter = 0;
	if (!liveView) {
	    for (int i = 0; i < getRowsCount(); i++) {
		for (int j = 0; j < getColsCount(); j++) {
		    if (i != row && j != col) {
			data[counter++] = get(i, j);
		    }
		}
	    }
	    counter = 0;
	    for (int i = 0; i < getRowsCount() - 1; i++) {
		for (int j = 0; j < getColsCount() - 1; j++) {
		    sub.set(i, j, data[counter++]);
		}
	    }
	} else {
	    return new MatrixSubMatrixView(this, row, col);
	}
	return sub;
    }

    /**
     * Algoritam pronadjen guglajuci! Uglavnom sam gledao po vikipediji!
     */
    @Override
    public IMatrix nInvert() {
	double det = determinant();
	if (det == 0) {
	    throw new IncompatibleOperandException(
		    "Can't invert singular matrix!");
	}
	if (getRowsCount() == 1) {
	    return copy();
	}
	IMatrix cofactor = newInstance(getRowsCount(), getColsCount());
	for (int i = 0; i < getRowsCount(); i++) {
	    for (int j = 0; j < getColsCount(); j++) {
		cofactor.set(i, j, Math.pow(-1, j) * Math.pow(-1, i)
			* subMatrix(i, j, true).determinant());
	    }
	}
	return cofactor.nTranspose(false).scalarMultiply(1 / det);
    }

    @Override
    public double[][] toArray() {
	double[][] forExport = new double[getRowsCount()][getColsCount()];
	for (int i = 0; i < getRowsCount(); i++) {
	    for (int j = 0; j < getColsCount(); j++) {
		forExport[i][j] = get(i, j);
	    }
	}
	return forExport;
    }

    @Override
    public IVector toVector(boolean liveView) {
	if (!liveView) {
	    return new VectorMatrixView(copy());
	}
	return new VectorMatrixView(this);
    }

    @Override
    public IMatrix nScalarMultiply(double value) {
	return this.copy().scalarMultiply(value);
    }

    @Override
    public IMatrix scalarMultiply(double value) {
	for (int i = 0; i < getRowsCount(); i++) {
	    for (int j = 0; j < getColsCount(); j++) {
		set(i, j, get(i, j) * value);
	    }
	}
	return this;
    }

    @Override
    public IMatrix makeIdentity() {
	if (getRowsCount() != getColsCount()) {
	    throw new IncompatibleOperandException(
		    "Can't make identity matrix of matrix which isn't square!");
	}
	for (int i = 0; i < getRowsCount(); i++) {
	    for (int j = 0; j < getColsCount(); j++) {
		if (i == j) {
		    set(i, j, 1);
		} else {
		    set(i, j, 0);
		}
	    }
	}
	return this;
    }

    @Override
    public String toString() {
	return toString(3);
    }

    /**
     * Prints matrix with given precision.
     * 
     * @param precision
     *            given precision
     * @return string representing matrix
     */
    private String toString(int precision) {
	StringBuffer sb = new StringBuffer();
	int max = 4;
	for (int i = 0; i < getRowsCount(); i++) {
	    for (int j = 0; j < getColsCount(); j++) {
		if (String.format("%." + precision + "f", get(i, j)).length() > max) {
		    max = String.format("%." + precision + "f", get(i, j))
			    .length();
		}
	    }
	}
	for (int i = 0; i < getRowsCount(); i++) {
	    sb.append("[ ");
	    for (int j = 0; j < getColsCount(); j++) {
		sb.append(String.format("%" + max + "." + precision + "f ",
			get(i, j)));
	    }
	    sb.append("]");
	    sb.append("\n");
	}
	return sb.toString();
    }

    @Override
    public int hashCode() {
	return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (!(obj instanceof AbstractMatrix)) {
	    return false;
	}
	if ((((AbstractMatrix) obj).getRowsCount() != getRowsCount())) {
	    return false;
	}
	if ((((AbstractMatrix) obj).getColsCount() != getColsCount())) {
	    return false;
	}
	IMatrix other = ((AbstractMatrix) obj);
	for (int i = 0; i < getRowsCount(); i++) {
	    for (int j = 0; j < getColsCount(); j++) {
		if (Math.abs(get(i, j) - other.get(i, j)) >= 1e-15) {
		    return false;
		}
	    }
	}
	return true;
    }
}

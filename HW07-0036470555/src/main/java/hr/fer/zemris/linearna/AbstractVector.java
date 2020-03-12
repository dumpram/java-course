package hr.fer.zemris.linearna;

/**
 * Class is abstraction of vector and implements interface IVector.
 * 
 * @author Ivan Pavić
 * 
 */
public abstract class AbstractVector implements IVector {
    /**
     * Default constructor.
     */
    public AbstractVector() {

    }

    @Override
    public abstract double get(int index);

    @Override
    public abstract IVector set(int index, double value);

    @Override
    public abstract int getDimension();

    @Override
    public abstract IVector copy();

    @Override
    public IVector copyPart(int n) {
	if (n <= 0) {
	    throw new IllegalArgumentException("Give size must be positive");
	}
	IVector newOne = newInstance(n);
	for (int i = 0; i < n; i++) {
	    if (i < this.getDimension()) {
		newOne.set(i, this.get(i));
	    } else {
		newOne.set(i, 0);
	    }
	}
	return newOne;
    }

    @Override
    public abstract IVector newInstance(int dimension);

    @Override
    public IVector add(IVector other) throws IncompatibleOperandException {
	if (this.getDimension() != other.getDimension()) {
	    throw new IncompatibleOperandException();
	}
	for (int i = this.getDimension() - 1; i >= 0; i--) {
	    this.set(i, this.get(i) + other.get(i));
	}
	return this;
    }

    @Override
    public IVector nAdd(IVector other) throws IncompatibleOperandException {
	return this.copy().add(other);
    }

    @Override
    public IVector sub(IVector other) throws IncompatibleOperandException {
	if (this.getDimension() != other.getDimension()) {
	    throw new IncompatibleOperandException();
	}
	for (int i = this.getDimension() - 1; i >= 0; i--) {
	    this.set(i, this.get(i) - other.get(i));
	}
	return this;
    }

    @Override
    public IVector nSub(IVector other) throws IncompatibleOperandException {
	return this.copy().sub(other);
    }

    @Override
    public IVector scalarMultiply(double byValue) {
	for (int i = this.getDimension() - 1; i >= 0; i--) {
	    this.set(i, this.get(i) * byValue);
	}
	return this;
    }

    @Override
    public IVector nScalarMultiply(double byValue) {
	return this.copy().scalarMultiply(byValue);
    }

    @Override
    public double norm() {
	double forExport = 0;
	for (int i = this.getDimension() - 1; i >= 0; i--) {
	    forExport += this.get(i) * this.get(i);
	}
	return Math.sqrt(forExport);
    }

    @Override
    public IVector normalize() {
	double norm = norm();
	for (int i = this.getDimension() - 1; i >= 0; i--) {
	    this.set(i, this.get(i) / norm);
	}
	return this;
    }

    @Override
    public IVector nNormalize() {
	return this.copy().normalize();
    }

    @Override
    public double cosine(IVector other) throws IncompatibleOperandException {
	return scalarProduct(other) / (this.norm() * other.norm());
    }

    @Override
    public double scalarProduct(IVector other)
	    throws IncompatibleOperandException {
	if (this.getDimension() != other.getDimension()) {
	    throw new IncompatibleOperandException();
	}
	double forExport = 0;
	for (int i = this.getDimension() - 1; i >= 0; i--) {
	    forExport += this.get(i) * other.get(i);
	}
	return forExport;
    }

    @Override
    public IVector nVectorProduct(IVector other)
	    throws IncompatibleOperandException {
	if (this.getDimension() != 3 || other.getDimension() != 3) {
	    throw new IncompatibleOperandException();
	}
	IVector forExport = newInstance(3);
	forExport.set(0,
		this.get(1) * other.get(2) - this.get(2) * other.get(1));
	forExport.set(1,
		this.get(2) * other.get(0) - this.get(0) * other.get(2));
	forExport.set(2,
		this.get(0) * other.get(1) - this.get(1) * other.get(0));
	return forExport;
    }

    @Override
    public IVector nFromHomogeneus() {
	if (getDimension() <= 1) {
	    throw new IncompatibleOperandException(
		    "Size must be greater than one!");
	}
	return copyPart(getDimension() - 1).scalarMultiply(
		1 / get(getDimension() - 1));
    }

    @Override
    public IMatrix toRowMatrix(boolean liveView) {
	if (!liveView) {
	    return new MatrixVectorView(copy(), true);
	}
	return new MatrixVectorView(this, true);
    }

    @Override
    public IMatrix toColumnMatrix(boolean liveView) {
	if (!liveView) {
	    return new MatrixVectorView(copy(), false);
	}
	return new MatrixVectorView(this, false);
    }

    @Override
    public double[] toArray() {
	int length = this.getDimension();
	double[] forExport = new double[length];
	for (int i = 0; i < length; i++) {
	    forExport[i] = this.get(i);
	}
	return forExport;
    }

    @Override
    public String toString() {
	return toString(3);
    }

    /**
     * Prints Vector with precision.
     * 
     * @param precision
     *            given precision
     * @return string representing vector.
     */
    private String toString(int precision) {
	StringBuffer sb = new StringBuffer();
	for (int i = 0; i < getDimension(); i++) {
	    sb.append(String.format("%." + precision + "f ", get(i)));
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
	if (!(obj instanceof AbstractVector)) {
	    return false;
	}
	if ((((AbstractVector) obj).getDimension() != getDimension())) {
	    return false;
	}
	for (int i = 0; i < getDimension(); i++) {
	    if (Math.abs(get(i) - ((AbstractVector) obj).get(i)) >= 1e-15) {
		return false;
	    }
	}
	return true;

    }
}

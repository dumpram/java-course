package hr.fer.zemris.linearna;

/**
 * Class is used for transposed view of IMatrix.
 * 
 * @author Ivan Pavić
 * 
 */
public class MatrixTransposeView extends AbstractMatrix implements IMatrix {
    /**
     * Private original matrix.
     */
    private final IMatrix original;

    /**
     * Constructor takes original matrix.
     * 
     * @param original
     *            given original matrix.
     */
    public MatrixTransposeView(IMatrix original) {
	this.original = original;
    }

    @Override
    public int getRowsCount() {
	return original.getColsCount();
    }

    @Override
    public int getColsCount() {
	return original.getRowsCount();
    }

    @Override
    public double get(int row, int col) {
	return original.get(col, row);
    }

    @Override
    public IMatrix set(int row, int col, double value) {
	original.set(col, row, value);
	return this;
    }

    @Override
    public IMatrix copy() {
	return new MatrixTransposeView(original.copy());
    }

    @Override
    public IMatrix newInstance(int rows, int cols) {
	return new MatrixTransposeView(original.newInstance(rows, cols));
    }

}

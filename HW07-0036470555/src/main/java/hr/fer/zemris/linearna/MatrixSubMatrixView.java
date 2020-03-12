package hr.fer.zemris.linearna;

/**
 * Class is used for live view of IMatrix subMatrix.
 * 
 * @author Ivan Pavić
 * 
 */
public class MatrixSubMatrixView extends AbstractMatrix implements IMatrix {
    /**
     * Row indexes of original matrix are stored.
     */
    private final int[] rowIndexes;
    /**
     * Column indexes of original matrix are stored.
     */
    private final int[] colIndexes;
    /**
     * Original matrix.
     */
    private final IMatrix original;

    /**
     * Constructor for MatrixSubMatrixView. Takes original IMatrix index of
     * discarded row and column.
     * 
     * @param original
     *            original IMatrix given
     * @param row
     *            index of discarded row
     * @param col
     *            index of discarded column
     */
    public MatrixSubMatrixView(IMatrix original, int row, int col) {
	this(original, getRowIndexes(original, row), getColIndexes(original,
		col));
    }

    /**
     * Constructor for MatrixSubMatrixView. Takes original IMatrix and indexes
     * of remaining rows and columns.
     * 
     * @param original
     *            original IMatrix given
     * @param row
     *            indexes of remaining rows
     * @param col
     *            indexes of remaining cols
     */
    private MatrixSubMatrixView(IMatrix original, int[] row, int[] col) {
	this.rowIndexes = row;
	this.colIndexes = col;
	this.original = original;
    }

    @Override
    public int getRowsCount() {
	return rowIndexes.length;
    }

    @Override
    public int getColsCount() {
	return colIndexes.length;
    }

    @Override
    public double get(int row, int col) {
	return original.get(rowIndexes[row], colIndexes[col]);
    }

    @Override
    public IMatrix set(int row, int col, double value) {
	original.set(rowIndexes[row], colIndexes[col], value);
	return this;
    }

    @Override
    public IMatrix copy() {
	return new MatrixSubMatrixView(original.copy(), rowIndexes, colIndexes);
    }

    @Override
    public IMatrix newInstance(int rows, int cols) {
	return new MatrixSubMatrixView(original, rows, cols);
    }

    /**
     * Gets row indexes from given discarded index.
     * 
     * @param original
     *            given original IMatrix
     * @param row
     *            given index
     * @return row indexes
     */
    private static int[] getRowIndexes(IMatrix original, int row) {
	int[] rowArray = new int[original.getRowsCount() - 1];
	int counter = 0;
	for (int i = 0; i < original.getRowsCount(); i++) {
	    if (i != row) {
		rowArray[counter++] = i;
	    }
	}
	return rowArray;
    }

    /**
     * Gets column indexes from given discarded index.
     * 
     * @param original
     *            given original IMatrix
     * @param col
     *            given index
     * @return col indexes
     */
    private static int[] getColIndexes(IMatrix original, int col) {
	int[] colArray = new int[original.getColsCount() - 1];
	int counter = 0;
	for (int j = 0; j < original.getColsCount(); j++) {
	    if (j != col) {
		colArray[counter++] = j;
	    }
	}
	return colArray;
    }
}

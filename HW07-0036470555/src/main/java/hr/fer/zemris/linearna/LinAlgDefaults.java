package hr.fer.zemris.linearna;

/**
 * Class LinAlgDefaults contains static factory methods for default
 * implementations of vectors and matrixes.
 * 
 * @author Ivan Pavić
 * 
 */
public class LinAlgDefaults {
    /**
     * Default matrix. In this case class Matrix.
     * 
     * @param rows
     *            row number
     * @param cols
     *            col number
     * @return new IMatrix
     */
    public static IMatrix defaultMatrix(int rows, int cols) {
	return new Matrix(rows, cols);
    }

    /**
     * Default vector. In this case class Vector.
     * 
     * @param dimension
     *            given dimension
     * @return new IVector
     */
    public static IVector defaultVector(int dimension) {
	return new Vector(false, false, new double[dimension]);
    }

}

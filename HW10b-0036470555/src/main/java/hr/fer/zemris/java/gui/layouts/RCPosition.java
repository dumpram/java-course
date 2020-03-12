package hr.fer.zemris.java.gui.layouts;

/**
 * Class RCPosition represents constraints for CalcLayout. IMPORTANT! It has two
 * constructors. One accepts integer coordinates. Other one String representing
 * coordinates. String format is strictly determined by regular expression
 * "\\d,\\d". For exceptions that are thrown consult {@link CalcLayout}
 * documentation.
 * 
 * @author Ivan Pavić
 * 
 */
public class RCPosition {
    /**
     * Regular expression.
     */
    private final static String REGEX = "\\d,\\d";
    /**
     * Integer representing row coordinate.
     */
    private int row;
    /**
     * Integer representing column coordinate.
     */
    private int col;

    /**
     * Constructor accepts String as described in class {@link RCPosition}
     * documentation.
     * 
     * @param constraints
     *            given String
     */
    public RCPosition(String constraints) {
	if (!constraints.matches(REGEX)) {
	    throw new IllegalArgumentException();
	}
	parseConstrains(constraints);
	checkRowCol();
    }

    /**
     * Constructor accepts Integer row and column coordinates.
     * 
     * @param i
     *            row coordinate
     * @param j
     *            column coordinate
     */
    public RCPosition(int i, int j) {
	row = i;
	col = j;
	checkRowCol();
    }

    /**
     * Makes sure that row and columns have right values.
     */
    private void checkRowCol() {
	if (row == 1 && !(col == 6 || col == 7 || col == 1)) {
	    throw new IllegalArgumentException(
		    "Position isn't allowed! Check documentation!");
	}

	if (row < 1 || row > 5) {
	    throw new IllegalArgumentException(
		    "Position isn't allowed! Check documentation!");
	}

	if (col < 1 || col > 7) {
	    throw new IllegalArgumentException(
		    "Position isn't allowed! Check documentation!");
	}

    }

    /**
     * Method parses constraints to integer row and column coordinates.
     * 
     * @param constrains
     *            given String
     */
    private void parseConstrains(String constrains) {
	String[] dim = constrains.split(",");
	row = Integer.parseInt(dim[0]);
	col = Integer.parseInt(dim[1]);
    }

    /**
     * Getter for row coordinate.
     * 
     * @return the row
     */
    public int getRow() {
	return row;
    }

    /**
     * Getter for column coordinate.
     * 
     * @return the column
     */
    public int getColumn() {
	return col;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + col;
	result = prime * result + row;
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	RCPosition other = (RCPosition) obj;
	if (col != other.col) {
	    return false;
	}
	if (row != other.row) {
	    return false;
	}
	return true;
    }

}

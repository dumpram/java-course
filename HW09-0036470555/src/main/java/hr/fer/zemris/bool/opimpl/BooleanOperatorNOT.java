package hr.fer.zemris.bool.opimpl;

import hr.fer.zemris.bool.BooleanOperator;
import hr.fer.zemris.bool.BooleanSource;
import hr.fer.zemris.bool.BooleanValue;

import java.util.Arrays;

/**
 * Class represents boolean operator NOT. It is extension of abstract
 * BooleanOperator.
 * 
 * @author Ivan Pavić
 */
public class BooleanOperatorNOT extends BooleanOperator {
    /**
     * Constructs operator NOT.
     * 
     * @param source
     *            list of sources that can be interpreted as TRUE, FALSE,
     *            DONT_CARE.
     */
    public BooleanOperatorNOT(final BooleanSource source) {
	super(Arrays.asList(source));
    }

    @Override
    /**
     * Returns value of operation NOT over BooleanSource.
     */
    public BooleanValue getValue() {
	final BooleanSource element = getSources().get(0);
	if (element.getValue() == BooleanValue.FALSE) {
	    return BooleanValue.TRUE;
	} else if (element.getValue() == BooleanValue.TRUE) {
	    return BooleanValue.FALSE;
	} else {
	    return BooleanValue.DONT_CARE;
	}

    }

}

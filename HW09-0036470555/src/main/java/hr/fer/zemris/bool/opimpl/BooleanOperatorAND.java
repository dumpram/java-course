package hr.fer.zemris.bool.opimpl;
import hr.fer.zemris.bool.BooleanOperator;
import hr.fer.zemris.bool.BooleanSource;
import hr.fer.zemris.bool.BooleanValue;

import java.util.List;
/**
 * Class represents boolean operator AND. It is extension of abstract BooleanOperator.
 * @author Ivan Pavić
 */
public class BooleanOperatorAND extends BooleanOperator {
	/**
	 * Constructs operator AND.
	 * @param sources list of sources that can be interpreted as TRUE, FALSE, DONT_CARE.
	 */
	public BooleanOperatorAND(final List<BooleanSource> sources) {
		super(sources);
	}
	@Override
	/**
	 * Returns value of operation AND over BooleanSource(s).
	 */
	public BooleanValue getValue() {
		boolean flagTrue, flagFalse, flagDontCare;
		flagTrue  = flagFalse = flagDontCare = false;
		for (final BooleanSource i : getSources()) {
			if (i.getValue() == BooleanValue.FALSE) {
				flagFalse = true;
			} else if (i.getValue() == BooleanValue.TRUE) {
				flagTrue = true;
			} else if (i.getValue() == BooleanValue.DONT_CARE) {
				flagDontCare = true;
			}
		}
		if (flagFalse) {
			return BooleanValue.FALSE;
		} else if (flagTrue && !flagDontCare) {
			return BooleanValue.TRUE;
		} else {
			return BooleanValue.DONT_CARE;
		}
	}

}

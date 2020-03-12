package hr.fer.zemris.bool.opimpl;
import hr.fer.zemris.bool.BooleanOperator;
import hr.fer.zemris.bool.BooleanSource;
import hr.fer.zemris.bool.BooleanValue;

import java.util.List;
/**
 * Class represents boolean operator OR. It is extension of abstract BooleanOperator.
 * @author Ivan Pavić
 */
public class BooleanOperatorOR extends BooleanOperator {
	/**
	 * Constructs operator OR.
	 * @param sources list of sources that can be interpreted as TRUE, FALSE, DONT_CARE.
	 */
	public BooleanOperatorOR(final List<BooleanSource> sources) {
		super(sources);
	}
	@Override
	/**
	 * Returns value of operation OR over BooleanSource(s).
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
		if (flagTrue) {
			return BooleanValue.TRUE;
		} else if (flagFalse && !flagDontCare) {
			return BooleanValue.FALSE;
		} else {
			return BooleanValue.DONT_CARE;
		}
	}
}

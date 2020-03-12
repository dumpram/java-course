package hr.fer.zemris.bool.opimpl;

import hr.fer.zemris.bool.BooleanOperator;
import hr.fer.zemris.bool.BooleanSource;

import java.util.Arrays;

public class BooleanOperators {
    /**
     * Private constructor.
     */
    private BooleanOperators() {
    }

    /**
     * @param sources
     *            variable number of BooleanSource(s).
     * @return BooleanOperatorAND which represents AND operation over sources.
     */
    public static BooleanOperator and(BooleanSource... sources) {
	return new BooleanOperatorAND(Arrays.asList(sources));
    }

    /**
     * @param sources
     *            variable number of BooleanSource(s).
     * @return BooleanOperatorOR which represents OR operation over sources.
     */
    public static BooleanOperator or(BooleanSource... sources) {
	return new BooleanOperatorOR(Arrays.asList(sources));
    }

    /**
     * @param source
     *            variable number of BooleanSource(s).
     * @return BooleanOperatorOR which represents OR operation over sources.
     */
    public static BooleanOperator not(BooleanSource source) {
	return new BooleanOperatorNOT(source);
    }
}

package hr.fer.zemris.bool;
import java.util.ArrayList;
import java.util.List;
/**
 * Abstract class representing abstract BooleanOperator which
 * by interface designed as BooleanSource. It has private list of
 * sources based on which final result is calculated; this list must
 * be provided through BooleanOperator's protected constructor as
 * its only argument. Operators determine their domain by inspecting
 * domains of given sources and producing an union (here, the ordering
 * of boolean variables is not important).
 * @author Ivan Pavić
 */
public abstract class BooleanOperator implements BooleanSource {
	/**
	 * Sources to work with.
	 */
	private List<BooleanSource> sources;
	/**
	 * Protected constructor accept sources.
	 * @param sources some source capable of returning domain as list
	 * of BooleanVariable and returning BooleanValue.
	 */
	protected BooleanOperator(List<BooleanSource> sources) {
		this.sources = sources;
	}
	/**
	 * Getter for list of sources.
	 * @return sources
	 */
	protected List<BooleanSource> getSources() {
		return sources;
	}	
	@Override
	/**
	 * Gets domain for certain list of sources.
	 */
	public List<BooleanVariable> getDomain() {
		List<BooleanVariable> forExport = new ArrayList<>();
		for (BooleanSource i : sources) {
			forExport.addAll(i.getDomain());
		}
		return forExport;
	}
	@Override
	/**
	 * Gets value, actually method lies. But it doesn't matter
	 * because it is overridden in all subclasses.
	 */
	public BooleanValue getValue() {
		return BooleanValue.FALSE;
	}
}

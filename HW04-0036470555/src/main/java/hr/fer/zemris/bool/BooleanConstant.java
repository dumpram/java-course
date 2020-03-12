package hr.fer.zemris.bool;
import java.util.ArrayList;
import java.util.List;
/**
 * Class defines BooleanConstant which is BooleanSource and has
 * static variables which can be BooleanValue TRUE or
 * FALSE.
 * @author Ivan Pavić
 *
 */
public class BooleanConstant implements BooleanSource {
	/**
	 * Private variable which contains value.
	 */
	private BooleanValue value;
	/**
	 * Static constant True.
	 */
	public final static BooleanConstant TRUE = new BooleanConstant(BooleanValue.TRUE);
	/**
	 * Static constant False.
	 */
	public final static BooleanConstant FALSE = new BooleanConstant(BooleanValue.FALSE);
	/**
	 * Static constant Dont Care.
	 */
	public final static BooleanConstant DONT_CARE = new BooleanConstant(BooleanValue.DONT_CARE);
	/**
	 * <p>
	 * Method is constructor with one parameter.
	 * </p>
	 * @param value BooleanValue
	 */
	private  BooleanConstant(BooleanValue value) {
		this.value = value;
	}
	/**
	 * Method returns value of instance of BooleanConstant.
	 */
	@Override
	public BooleanValue getValue() {
		return this.value;
	}
	/**
	 * Method returns domain of BooleanConstant as empty collection.
	 */
	@Override
	public List<BooleanVariable> getDomain() {
		ArrayList<BooleanVariable> forExport = new ArrayList<>();
		return forExport;
	}
}

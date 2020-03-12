package hr.fer.zemris.bool;
import java.util.ArrayList;
import java.util.List;
/**
 * Class BooleanVariable is an implementation of NamedBooleanSource.
 * It has a single public constructor which accepts name; by default,
 * variable's value is set to FALSE. It offers getter and setter for variable's
 * value. Variable's domain is a collection containing the variable itself.
 * @author Ivan Pavić
 *
 */
public class BooleanVariable implements NamedBooleanSource {
	/**
	 * Represents variable's value. Default value is FALSE.
	 */
	private BooleanValue value = BooleanValue.FALSE;
	/**
	 * Represents variable's name.
	 */
	private String name;
	/**
	 * It counts how many untitled variables are created.
	 */
	private static int untitledCounter = 0;
	/**
	 * Constructs BooleanVariable with name.
	 * @param name variable name
	 */
	public BooleanVariable(String name) {
		this.name = name;
	}
	/**
	 * Constructs untitled BooleanVariable with value.
	 * @param value given value
	 */
	public BooleanVariable(BooleanValue value) {
		this.value = value;
		this.name = "untitled" + untitledCounter;
		untitledCounter++;
	}
	/**
	 * Variable name getter.
	 * @return variable's name.
	 */
	@Override
	public String getName() {
		return name;
	}
	/**
	 * Variable value getter.
	 * @return variable's value.
	 */
	public BooleanValue getValue() {
		return value;
	}
	/**
	 * Variable value setter. Sets value of this variable.
	 * @param value given value
	 */
	public void setValue(BooleanValue value) {
		this.value = value;
	}
	/**
	 * Getter for domain which returns list that contains variable.
	 * @return list containing this variable.
	 */
	public List<BooleanVariable> getDomain() {
		List<BooleanVariable> domain =  new ArrayList<>();
		domain.add(this);
		return domain;
	}
}

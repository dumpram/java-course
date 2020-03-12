package hr.fer.zemris.java.custom.scripting.exec;

/**
 * ValueWrapper class enables arithmetic operations with object that can be
 * arithmetically operated with. Object can be String which can be parsed to
 * Integer or Double, or it can be Integer or Double. If wrapped value is null
 * is treated as Integer of value zero. Operations are: <li>increment</li> <li>
 * decrement</li> <li>multiply</li> <li>numComapre</li>
 * 
 * @author Ivan Pavić
 * 
 */
public class ValueWrapper {
	/**
	 * Private variable containing object value.
	 */
	Object value;

	public ValueWrapper(Object obj) {
		value = obj;
	}

	/**
	 * Increments value of this ValueWrapper by incValue. IncValue can be
	 * Double, Integer or String which can be parsed into Double or Integer.
	 * 
	 * @param incValue
	 *            given increment value
	 */
	public void increment(Object incValue) {
		if (isNumber(value) && isNumber(incValue)) {
			if (isInteger(value) && isInteger(incValue)) {
				value = (Integer) valueOf(value) + (Integer) valueOf(incValue);
			} else if (isInteger(value) && isDouble(incValue)) {
				value = (Integer) valueOf(value) + (Double) valueOf(incValue);
			} else if (isDouble(value) && isInteger(incValue)) {
				value = (Double) valueOf(value) + (Integer) valueOf(incValue);
			} else if (isDouble(value) && isDouble(incValue)) {
				value = (Double) valueOf(value) + (Double) valueOf(incValue);
			}
		} else {
			throw new IllegalArgumentException("Can't do arithmetic operations with provided objects!");
		}
	}

	/**
	 * Decrements value of this ValueWrapper by decValue. DecValue can be
	 * Double, Integer or String which can be parsed into Double or Integer.
	 * 
	 * @param decValue
	 *            given decrement value
	 */
	public void decrement(Object decValue) {
		if (isInteger(decValue)) {
			increment((Integer) valueOf(decValue) * (-1));
		} else if (isDouble(decValue)) {
			increment((Double) valueOf(decValue) * (-1));
		} else {
			throw new IllegalArgumentException("Can't do arithmetic operations with provided objects!");
		}
	}

	/**
	 * Multiplies value of this ValueWrapper by mulValue. MulValue can be
	 * Double, Integer or String which can be parsed into Double or Integer.
	 * 
	 * @param mulValue
	 *            given value to multiply with
	 */
	public void multiply(Object mulValue) {
		if (isNumber(value) && isNumber(mulValue)) {
			if (isInteger(value) && isInteger(mulValue)) {
				value = (Integer) valueOf(value) * (Integer) valueOf(mulValue);
			} else if (isInteger(value) && isDouble(mulValue)) {
				value = (Integer) valueOf(value) * (Double) valueOf(mulValue);
			} else if (isDouble(value) && isInteger(mulValue)) {
				value = (Double) valueOf(value) * (Integer) valueOf(mulValue);
			} else if (isDouble(value) && isDouble(mulValue)) {
				value = (Double) valueOf(value) * (Double) valueOf(mulValue);
			}
		} else {
			throw new IllegalArgumentException("Can't do arithmetic operations with provided objects!");
		}

	}

	/**
	 * Divides value of this ValueWrapper by divValue. DivValue can be Double,
	 * Integer or String which can be parsed into Double or Integer.
	 * 
	 * @param divValue
	 *            given value to divide with
	 */
	public void divide(Object divValue) {
		if (isNumber(value) && isNumber(divValue)) {
			if (isInteger(value) && isInteger(divValue)) {
				value = (Integer) valueOf(value) / (Integer) valueOf(divValue);
			} else if (isInteger(value) && isDouble(divValue)) {
				value = (Integer) valueOf(value) / (Double) valueOf(divValue);
			} else if (isDouble(value) && isInteger(divValue)) {
				value = (Double) valueOf(value) / (Integer) valueOf(divValue);
			} else if (isDouble(value) && isDouble(divValue)) {
				value = (Double) valueOf(value) / (Double) valueOf(divValue);
			}
		} else {
			throw new IllegalArgumentException("Can't do arithmetic operations with provided objects!");
		}

	}

	/**
	 * Compares numerical values of this and given ValueWrapper. Comparing is
	 * delegated to Double compare.
	 * 
	 * @param withValue
	 *            given value to compare with
	 * @return returns 1, 0, -1 depending on equality of objects
	 */

	public int numCompare(Object withValue) {
		int forExport = 0;
		Object oldValue = value;
		if (isNumber(value) && isNumber(withValue)) {
			decrement(withValue);
			if (isInteger(value)) {
				forExport = Integer.compare((Integer) valueOf(value), 0);
			} else if (isDouble(value)) {
				forExport = Double.compare((Double) valueOf(value), 0.0);
			}
		} else {
			throw new IllegalArgumentException("Can't compare objects that are not numbers!");
		}
		value = oldValue;
		return forExport;
	}

	/**
	 * Checks if object is Integer or String which can be parsed to Integer.
	 * 
	 * @param value
	 *            given object
	 * @return true if the statement above is true, false otherwise.
	 */
	private boolean isInteger(Object value) {
		if (value instanceof Integer || isStringInteger(value) || value == null) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if object is Double or String which can be parsed to Double.
	 * 
	 * @param value
	 *            given object
	 * @return true if the statement above is true, false otherwise.
	 */
	private boolean isDouble(Object value) {
		if (value instanceof Double || isStringDouble(value)) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if object is String which can be parsed to Integer.
	 * 
	 * @param value
	 *            given object
	 * @return true if the statement above is true, false otherwise.
	 */
	private boolean isStringInteger(Object value) {
		try {
			Integer.parseInt((String) value);
		} catch (NumberFormatException e) {
			return false;
		} catch (ClassCastException e) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if object is String which can be parsed to Double.
	 * 
	 * @param value
	 *            given object
	 * @return true if the statement above is true, false otherwise.
	 */
	private boolean isStringDouble(Object value) {
		try {
			Double.parseDouble((String) value);
		} catch (NumberFormatException e) {
			return false;
		} catch (ClassCastException e) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if object is number. Delegates work to methods isInteger and
	 * isDouble. For more information see their documentation.
	 * 
	 * @param value
	 *            given object value
	 * @return true if object is number of any kind, false otherwise.
	 */
	private boolean isNumber(Object value) {
		if (isInteger(value) || isDouble(value)) {
			return true;
		}
		return false;
	}

	/**
	 * Returns numerical value of object. If value is string it will be parsed
	 * to] Integer or Double if possible.
	 * 
	 * @param value
	 *            given value
	 * @return numerical value(Double or Integer)
	 */
	private Object valueOf(Object value) {
		if (value == null) {
			return Integer.valueOf(0);
		}
		if (isStringInteger(value)) {
			return Integer.parseInt((String) value);
		} else if (isStringDouble(value)) {
			return Double.parseDouble((String) value);
		}
		return value;
	}

	/**
	 * Getter for value of ValueWrapper.
	 * 
	 * @return value value that is wrapped
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Setter for value of ValueWrapper. Value is changed to given value.
	 * 
	 * @param value
	 *            given value
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	/**
	 * Calculates sin function of numerical value in degrees of value wrapper.
	 */
	public void sinFunction() {
		Object val = valueOf(value);
		if (isDouble(val)) {
			value = Math.sin(Math.toRadians((Double) valueOf(value)));
		} else {
			value = Math.sin(Math.toRadians((Integer) valueOf(value)));
		}
	}
}

package hr.fer.zemris.java.custom.scripting.exec;

public class ValueWrapper {

    Object value;

    public ValueWrapper(Object obj) {
	value = obj;
    }

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
	}
    }

    public void decrement(Object decValue) {
	if (isNumber(value) && isNumber(decValue)) {
	    if (isInteger(value) && isInteger(decValue)) {
		value = (Integer) valueOf(value) - (Integer) valueOf(decValue);
	    } else if (isInteger(value) && isDouble(decValue)) {
		value = (Integer) valueOf(value) - (Double) valueOf(decValue);
	    } else if (isDouble(value) && isInteger(decValue)) {
		value = (Double) valueOf(value) - (Integer) valueOf(decValue);
	    } else if (isDouble(value) && isDouble(decValue)) {
		value = (Double) valueOf(value) - (Double) valueOf(decValue);
	    }
	}
    }

    public void multiply(Object mulValue) {
	if (isNumber(value) && isNumber(mulValue)) {
	    if (value == null || mulValue == null) {
		value = 0;
	    } else if (isInteger(value) && isInteger(mulValue)) {
		value = (Integer) valueOf(value) * (Integer) valueOf(mulValue);
	    } else if (isInteger(value) && isDouble(mulValue)) {
		value = (Integer) valueOf(value) * (Double) valueOf(mulValue);
	    } else if (isDouble(value) && isInteger(mulValue)) {
		value = (Double) valueOf(value) * (Integer) valueOf(mulValue);
	    } else if (isDouble(value) && isDouble(mulValue)) {
		value = (Double) valueOf(value) * (Double) valueOf(mulValue);
	    }
	}

    }

    public void divide(Object divValue) {
	if (isNumber(value) && isNumber(divValue)) {
	    if (divValue == null) {
		throw new IllegalArgumentException(
			"Division by zero is forbbiden!");
	    } else if (value == null) {
		value = 0;
	    } else if (isInteger(value) && isInteger(divValue)) {
		value = (Integer) valueOf(value) / (Integer) valueOf(divValue);
	    } else if (isInteger(value) && isDouble(divValue)) {
		value = (Integer) valueOf(value) / (Double) valueOf(divValue);
	    } else if (isDouble(value) && isInteger(divValue)) {
		value = (Double) valueOf(value) / (Integer) valueOf(divValue);
	    } else if (isDouble(value) && isDouble(divValue)) {
		value = (Double) valueOf(value) / (Double) valueOf(divValue);
	    }
	}

    }

    public int numCompare(Object withValue) {
	if (isNumber(value) && isNumber(withValue)) {
	    if (value == withValue) {
		return 0;
	    } else {
		return Double.compare((Double) valueOf(value),
			(Double) valueOf(value));
	    }
	}
	return -1;
    }

    private boolean isInteger(Object value) {
	if (value instanceof Integer || isStringInteger(value)) {
	    return true;
	}
	return false;
    }

    private boolean isDouble(Object value) {
	if (value instanceof Double || isStringDouble(value)) {
	    return true;
	}
	return false;
    }

    private boolean isStringInteger(Object value) {
	try {
	    Integer.parseInt((String) value);
	} catch (NumberFormatException e) {
	    return false;
	} catch (NullPointerException e) {
	    return true;
	} catch (ClassCastException e) {
	    return false;
	}
	return true;
    }

    private boolean isStringDouble(Object value) {
	try {
	    Double.parseDouble((String) value);
	} catch (NumberFormatException e) {
	    return false;
	} catch (NullPointerException e) {
	    return true;
	} catch (ClassCastException e) {
	    return false;
	}
	return true;
    }

    private boolean isNumber(Object value) {
	if (isInteger(value) || isDouble(value)) {
	    return true;
	}
	return false;
    }

    private Object valueOf(Object value) {
	if (value == null) {
	    return 0;
	}
	if (isStringInteger(value)) {
	    return Integer.parseInt((String) value);
	} else if (isStringDouble(value)) {
	    return Double.parseDouble((String) value);
	}
	return value;
    }

    public Object getValue() {
	return value;
    }

    public void setValue(Object value) {
	this.value = value;
    }
}

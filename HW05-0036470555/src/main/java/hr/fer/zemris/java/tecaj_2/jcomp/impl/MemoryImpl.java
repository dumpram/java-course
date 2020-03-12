package hr.fer.zemris.java.tecaj_2.jcomp.impl;

import hr.fer.zemris.java.tecaj_2.jcomp.Memory;
/**
 * Implementation of computer memory. Implements interface memory.
 * Contains array of objects which are in fact memory values.
 * @author Ivan Pavić
 *
 */
public class MemoryImpl implements Memory {
	/**
	 * Array of values stored in memory.
	 */
	Object[] memoryValues;
	/**
	 * Creates new memory which can contain number of objects less than or
	 * equal to size.
	 * @param size
	 */
	public MemoryImpl(int size) {
		if (size < 0) {
			throw new IllegalArgumentException("Size of memory can't be negative!");
		}
		memoryValues = new Object[size];
	}

	@Override
	public void setLocation(int location, Object value) {
		if (location < 0) {
			throw new IllegalArgumentException("Illegal memory access");
		}
		if (location >= memoryValues.length) {
			throw new IllegalArgumentException("Illegal memory access");
		}
		memoryValues[location] = value;
	}
	@Override
	public Object getLocation(int location) {
		if (location < 0) {
			throw new IllegalArgumentException("Illegal memory access");
		}
		if (location >= memoryValues.length) {
			throw new IllegalArgumentException("Illegal memory access");
		}
		return memoryValues[location];
	}
}

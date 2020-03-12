package hr.fer.zemris.java.tecaj_2.jcomp.impl;

import hr.fer.zemris.java.tecaj_2.jcomp.Registers;
/**
 * RegisterImpl is implementation of interface Registers. Contains
 * values of Register, flag, and imaginary ProgramCounter register.
 * @author Ivan Pavić
 *
 */
public class RegistersImpl implements Registers {
	/**
	 * Array of register values.
	 */
	private final Object[] registerValues;
	/**
	 * Imaginary register programCounter.
	 */
	private int programCounter;
	/**
	 * Flag which can be set due to some instructions like "TestEquals".
	 */
	private boolean flag;
	/**
	 * Constructor which accepts number of registers to create.
	 * @param regsLen number of registers
	 */
	public RegistersImpl(int regsLen) {
		if (regsLen < 0) {
			throw new IllegalArgumentException("Number of registers must be positive");
		}
		registerValues = new Object[regsLen];
		programCounter = 0;
		flag = false;
	}
	@Override
	public Object getRegisterValue(int index) {
		if (index  < 0) {
			throw new IllegalArgumentException("Invalid index provided");
		}
		if (index >= registerValues.length) {
			throw new IllegalArgumentException("Invalid index provided");
		}
		return registerValues[index];
	}
	@Override
	public void setRegisterValue(int index, Object value) {
		if (index  < 0) {
			throw new IllegalArgumentException("Invalid index provided");
		}
		if (index >= registerValues.length) {
			throw new IllegalArgumentException("Invalid index provided");
		}
		registerValues[index] = value;
	}
	@Override
	public int getProgramCounter() {
		return programCounter;
	}
	@Override
	public void setProgramCounter(int value) {
		programCounter = value;
	}
	@Override
	public void incrementProgramCounter() {
		programCounter++;
	}
	@Override
	public boolean getFlag() {
		return flag;
	}
	@Override
	public void setFlag(boolean value) {
		flag = value;
	}
}

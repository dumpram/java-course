package hr.fer.zemris.java.tecaj_2.jcomp.test;

import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

public class InstructionArgumentImpl implements InstructionArgument {
	private final int value;
	private boolean flag;

	public InstructionArgumentImpl(int value) {
		this.value = value;
	}
	public InstructionArgumentImpl(int value, boolean flag) {
		this.value = value;
		this.flag = flag;
	}
	@Override
	public boolean isRegister() {
		return !flag;
	}

	@Override
	public boolean isString() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isNumber() {
		return flag;
	}

	@Override
	public Object getValue() {

		return value;
	}

}

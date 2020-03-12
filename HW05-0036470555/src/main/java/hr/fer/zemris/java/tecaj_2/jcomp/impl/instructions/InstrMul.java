package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

import java.util.List;

/**
 * InstrMul implements interface Instruction. Multiplies value of second
 * register with first register and saves result in third register.
 * Registers are provided in list of arguments in constructor.
 * @author Marko Čupić
 *
 */
public class InstrMul implements Instruction {
	/**
	 * Index of first register.
	 */
	private final int indexRegistra1;
	/**
	 * Index of second register.
	 */
	private final int indexRegistra2;
	/**
	 * Index of third register.
	 */
	private final int indexRegistra3;
	/**
	 * Constructor for InstrMul. Arguments provided must be registers.
	 * @param arguments list of registers.
	 */
	public InstrMul(List<InstructionArgument> arguments) {
		if (arguments.size() != 3) {
			throw new IllegalArgumentException("Expected 3 arguments!");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		if (!arguments.get(1).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}
		if (!arguments.get(2).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 2!");
		}
		this.indexRegistra1 = ((Integer) arguments.get(0).getValue())
				.intValue();
		this.indexRegistra2 = ((Integer) arguments.get(1).getValue())
				.intValue();
		this.indexRegistra3 = ((Integer) arguments.get(2).getValue())
				.intValue();
	}
	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters()
				.getRegisterValue(indexRegistra2);
		Object value2 = computer.getRegisters()
				.getRegisterValue(indexRegistra3);
		computer.getRegisters().setRegisterValue(
				indexRegistra1,
				Integer.valueOf(((Integer) value1).intValue()
						* ((Integer) value2).intValue()));
		return false;
	}

}

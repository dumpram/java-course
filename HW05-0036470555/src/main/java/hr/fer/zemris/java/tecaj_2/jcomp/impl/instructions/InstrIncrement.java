package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

import java.util.List;
/**
 * InstrIncrement implements interface Instruction. Increments value
 * of register provided in constructor.
 * @author Ivan Pavić
 *
 */
public class InstrIncrement implements Instruction {
	/**
	 * Given register index.
	 */
	private final int regIndex;
	/**
	 * InstrIncrements constructor accepts one register in list of
	 * arguments.
	 * @param arguments in this case only one argument(register).
	 */
	public InstrIncrement(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Excpected 1 argument!");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		regIndex = (Integer) arguments.get(0).getValue();
	}

	@Override
	public boolean execute(Computer computer) {
		try {
			int currentValue = (Integer)computer.getRegisters().getRegisterValue(regIndex);
			computer.getRegisters().setRegisterValue(regIndex, currentValue + 1);
		} catch (Exception e) {
			System.out.println("Processor has stopped: " + e.getMessage());
			throw new IllegalArgumentException();
		}
		return false;
	}

}

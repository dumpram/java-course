package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

import java.util.List;
/**
 * InstrMove implements interface Instruction. Moves value of
 * second register to first register leaving second register as it is.
 * @author Ivan Pavić
 *
 */
public class InstrMove implements Instruction {
	/**
	 * Index of first register.
	 */
	private final int regIndex1;
	/**
	 * Index of second register.
	 */
	private final int regIndex2;
	/**
	 * InstrMove constructor accepts to arguments. First and second
	 * must be registers.
	 * @param arguments must be two registers.
	 */
	public InstrMove(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		if (!arguments.get(1).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}
		regIndex1 = (Integer)arguments.get(0).getValue();
		regIndex2 = (Integer)arguments.get(1).getValue();
	}
	@Override
	public boolean execute(Computer computer) {
		computer.getRegisters().setRegisterValue(regIndex1, computer.getRegisters().getRegisterValue(regIndex2));
		return false;
	}

}

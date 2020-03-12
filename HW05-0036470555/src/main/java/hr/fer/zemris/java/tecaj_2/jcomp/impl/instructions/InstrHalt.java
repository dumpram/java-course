package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

import java.util.List;
/**
 * InstrHalt implements interface Instruction. Stops program
 * by returning true.
 * @author Ivan Pavić
 *
 */
public class InstrHalt implements Instruction {
	/**
	 * Arguments are given but not used.
	 * @param arguments list must be empty
	 */
	public InstrHalt(List<InstructionArgument> arguments) {
		if (arguments.size() != 0) {
			throw new IllegalArgumentException();
		}
	}
	@Override
	public boolean execute(Computer computer) {
		return true;
	}
}

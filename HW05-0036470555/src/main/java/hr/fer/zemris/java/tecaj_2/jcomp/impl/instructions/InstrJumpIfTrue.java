package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

import java.util.List;
/**
 * InstrJumpIfTrue implements interface Instruction. Jumps to
 * memory location provided in constructor but only if flag in
 * registers is set to true. In fact changes the value of program counter.
 * @author Ivan Pavić
 *
 */
public class InstrJumpIfTrue implements Instruction {
	/**
	 * Memory address to jump to.
	 */
	private final int jumpIndex;
	/**
	 * InstrJump constructor accepts memory address as
	 * argument.
	 * @param arguments must have only one argument which is number.
	 */
	public InstrJumpIfTrue(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Excpected 1 argument!");
		}
		if (!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}

		jumpIndex = (Integer) arguments.get(0).getValue();
	}
	@Override
	public boolean execute(Computer computer) {
		if (computer.getRegisters().getFlag()) {
			computer.getRegisters().setProgramCounter(jumpIndex);
		}
		return false;
	}
}

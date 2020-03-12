package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

import java.util.List;
/**
 * InstrLoad implements interface Instruction. Instruction
 * loads register with value from memory location given in constructor.
 * In fact changes the value of program counter.
 * @author Ivan Pavić
 *
 */
public class InstrLoad implements Instruction {
	/**
	 * Index of register given.
	 */
	private final int regIndex;
	/**
	 * Memory location index.
	 */
	private final int memIndex;
	/**
	 * InstrLoad constructor accepts 2 arguments. First argument is register
	 * second number is memory location.
	 * @param arguments
	 */
	public InstrLoad(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		if (!arguments.get(1).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}
		regIndex = (Integer) arguments.get(0).getValue();
		memIndex = (Integer) arguments.get(1).getValue();
	}
	@Override
	public boolean execute(Computer computer) {
		try {
			computer.getRegisters().setRegisterValue(regIndex, computer.getMemory().getLocation(memIndex));
		} catch (Exception e) {
			System.out.println("Processor has stopped: " + e.getMessage());
			throw new IllegalArgumentException();
		}
		return false;
	}
}

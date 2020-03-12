package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

import java.util.List;
/**
 * InstrEcho implements interface Instruction. Prints content of
 * register. Register is provided in list of arguments in constructor.
 * @author Ivan Pavić
 *
 */
public class InstrEcho implements Instruction {
	/**
	 * Register to print.
	 */
	private final int regIndex;
	/**
	 * InstrEcho constructor accepts only one register as argument.
	 * @param arguments list which contains only one register.
	 */
	public InstrEcho(List<InstructionArgument> arguments) {
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
			System.out.print(computer.getRegisters().getRegisterValue(regIndex));
		} catch (Exception e) {
			System.out.println("Processor has stopped: " + e.getMessage());
			throw new IllegalArgumentException();
		}
		return false;
	}
}

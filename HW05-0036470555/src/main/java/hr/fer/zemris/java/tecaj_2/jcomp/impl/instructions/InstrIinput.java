package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

import java.util.List;
import java.util.Scanner;
/**
 * InstrIinput implements interface Instruction. As argument in
 * constructor memory index is provided. Input from keyboard is saved
 * on location of memory index.
 * @author Ivan Pavić
 *
 */
public class InstrIinput implements Instruction {
	/**
	 * Memory index.
	 */
	private final int memIndex;
	/**
	 * InstIinput constructor accepts one argument which represents memory index
	 * where input will be saved.
	 * @param arguments
	 */
	public InstrIinput(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Excpected 1 argument!");
		}
		if (!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		memIndex = (Integer) arguments.get(0).getValue();
	}
	@Override
	public boolean execute(Computer computer) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		scanner = new Scanner(input);
		if (scanner.hasNextInt()) {
			computer.getMemory().setLocation(memIndex, scanner.nextInt());
			computer.getRegisters().setFlag(true);
		} else {
			computer.getRegisters().setFlag(false);
		}
		return false;
	}
}

package hr.fer.zemris.java.tecaj_2.jcomp.impl;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.ExecutionUnit;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
/**
 * ExecutionUnit class represents execution unit of computer
 * and has task of performing instructions from memory and
 * increasing program counter.
 * @author Ivan Pavić
 *
 */
public class ExecutionUnitImpl implements ExecutionUnit {

	@Override
	public boolean go(Computer computer) {
		computer.getRegisters().setProgramCounter(0);
		Instruction next;
		while(true) {
			next = (Instruction) computer.getMemory().getLocation(computer.getRegisters().getProgramCounter());
			computer.getRegisters().incrementProgramCounter();
			if (next.execute(computer)) {
				break;
			}
		}
		return true;
	}

}

package hr.fer.zemris.java.tecaj_2.jcomp.impl;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Memory;
import hr.fer.zemris.java.tecaj_2.jcomp.Registers;
/**
 * Implementation of Computer interface. Represents simple
 * computer with registers and memory.
 * @author Ivan Pavić
 *
 */
public class ComputerImpl implements Computer {
	/**
	 * Computer registers.
	 */
	RegistersImpl registers;
	/**
	 * Computer memory.
	 */
	MemoryImpl memory;
	/**
	 * ComputerImpl constructor. Memory size, and number of registers must be
	 * provided.
	 * @param size memory size
	 * @param regsLen number of registers
	 */
	public ComputerImpl(int size, int regsLen) {
		if (size <= 0 || regsLen <= 0) {
			throw new IllegalArgumentException("Computer must have positive memory size && number of registers!");
		}
		registers = new RegistersImpl(regsLen);
		memory = new MemoryImpl(size);
	}
	@Override
	public Registers getRegisters() {
		return registers;
	}
	@Override
	public Memory getMemory() {
		return memory;
	}
}

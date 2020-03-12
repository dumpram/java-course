package hr.fer.zemris.java.tecaj_2.jcomp.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
import hr.fer.zemris.java.tecaj_2.jcomp.Memory;
import hr.fer.zemris.java.tecaj_2.jcomp.Registers;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions.InstrAdd;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions.InstrDecrement;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions.InstrEcho;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions.InstrHalt;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions.InstrIncrement;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions.InstrJump;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions.InstrJumpIfTrue;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions.InstrLoad;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions.InstrMove;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions.InstrMul;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions.InstrTestEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class JCompIllegalArgumentTest {

	@Test(expected = IllegalArgumentException.class)
	public void instrMulMockException() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getRegisterValue(1)).thenReturn(new Integer(20));
		when(computer.getRegisters().getRegisterValue(2)).thenReturn(new Integer(10));
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl first = new InstructionArgumentImpl(0, true);
		InstructionArgumentImpl second = new InstructionArgumentImpl(1);
		InstructionArgumentImpl third = new InstructionArgumentImpl(2);


		parametri.add( first );
		parametri.add( second );
		parametri.add( third );

		@SuppressWarnings("unused")
		InstrMul mul = new InstrMul(parametri);
	}
	@Test(expected = IllegalArgumentException.class)
	public void instrMulMockException2() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getRegisterValue(1)).thenReturn(new Integer(20));
		when(computer.getRegisters().getRegisterValue(2)).thenReturn(new Integer(10));
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl first = new InstructionArgumentImpl(0);
		InstructionArgumentImpl second = new InstructionArgumentImpl(1, true);
		InstructionArgumentImpl third = new InstructionArgumentImpl(2);


		parametri.add( first );
		parametri.add( second );
		parametri.add( third );

		@SuppressWarnings("unused")
		InstrMul mul = new InstrMul(parametri);
	}
	@Test(expected = IllegalArgumentException.class)
	public void instrMulMockException3() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getRegisterValue(1)).thenReturn(new Integer(20));
		when(computer.getRegisters().getRegisterValue(2)).thenReturn(new Integer(10));
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl first = new InstructionArgumentImpl(0);
		InstructionArgumentImpl second = new InstructionArgumentImpl(1);
		InstructionArgumentImpl third = new InstructionArgumentImpl(2, true);


		parametri.add( first );
		parametri.add( second );
		parametri.add( third );

		@SuppressWarnings("unused")
		InstrMul mul = new InstrMul(parametri);
	}
	@Test(expected = IllegalArgumentException.class)
	public void instrMulMockException4() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getRegisterValue(1)).thenReturn(new Integer(20));
		when(computer.getRegisters().getRegisterValue(2)).thenReturn(new Integer(10));
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl first = new InstructionArgumentImpl(0);
		InstructionArgumentImpl second = new InstructionArgumentImpl(1);
		InstructionArgumentImpl third = new InstructionArgumentImpl(2);
		InstructionArgumentImpl fourth = new InstructionArgumentImpl(2);



		parametri.add( first );
		parametri.add( second );
		parametri.add( third );
		parametri.add( fourth );

		@SuppressWarnings("unused")
		InstrMul mul = new InstrMul(parametri);
	}
	@Test(expected = IllegalArgumentException.class)
	public void instrAddMockException() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getRegisterValue(1)).thenReturn(new Integer(20));
		when(computer.getRegisters().getRegisterValue(2)).thenReturn(new Integer(10));
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl first = new InstructionArgumentImpl(0, true);
		InstructionArgumentImpl second = new InstructionArgumentImpl(1);
		InstructionArgumentImpl third = new InstructionArgumentImpl(2);


		parametri.add( first );
		parametri.add( second );
		parametri.add( third );

		@SuppressWarnings("unused")
		InstrAdd add = new InstrAdd(parametri);
	}
	@Test(expected = IllegalArgumentException.class)
	public void instrAddMockExecption1() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getRegisterValue(1)).thenReturn(new Integer(20));
		when(computer.getRegisters().getRegisterValue(2)).thenReturn(new Integer(10));
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl first = new InstructionArgumentImpl(0);
		InstructionArgumentImpl second = new InstructionArgumentImpl(1, true);
		InstructionArgumentImpl third = new InstructionArgumentImpl(2);


		parametri.add( first );
		parametri.add( second );
		parametri.add( third );

		@SuppressWarnings("unused")
		InstrAdd add = new InstrAdd(parametri);
	}
	@Test(expected = IllegalArgumentException.class)
	public void instrAddMockException2() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getRegisterValue(1)).thenReturn(new Integer(20));
		when(computer.getRegisters().getRegisterValue(2)).thenReturn(new Integer(10));
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl first = new InstructionArgumentImpl(0);
		InstructionArgumentImpl second = new InstructionArgumentImpl(1);
		InstructionArgumentImpl third = new InstructionArgumentImpl(2, true);


		parametri.add( first );
		parametri.add( second );
		parametri.add( third );

		@SuppressWarnings("unused")
		InstrAdd add = new InstrAdd(parametri);
	}
	@Test(expected = IllegalArgumentException.class)
	public void instrAddMockException3() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getRegisterValue(1)).thenReturn(new Integer(20));
		when(computer.getRegisters().getRegisterValue(2)).thenReturn(new Integer(10));
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl first = new InstructionArgumentImpl(0);
		InstructionArgumentImpl second = new InstructionArgumentImpl(1);
		InstructionArgumentImpl third = new InstructionArgumentImpl(2);
		InstructionArgumentImpl fourth = new InstructionArgumentImpl(3);


		parametri.add( first );
		parametri.add( second );
		parametri.add( third );
		parametri.add( fourth );
		@SuppressWarnings("unused")
		InstrAdd add = new InstrAdd(parametri);
	}
	@Test(expected = IllegalArgumentException.class)
	public void instrDecrementMockException() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getRegisterValue(0)).thenReturn(new Integer(20));
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl first = new InstructionArgumentImpl(0, true);

		parametri.add( first );

		@SuppressWarnings("unused")
		InstrDecrement dec = new InstrDecrement(parametri);
	}
	@Test(expected = IllegalArgumentException.class)
	public void instrIncrementMockException() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getRegisterValue(0)).thenReturn(new Integer(20));
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl first = new InstructionArgumentImpl(0, true);

		parametri.add( first );

		@SuppressWarnings("unused")
		InstrIncrement inc = new InstrIncrement(parametri);
	}
	@Test(expected = IllegalArgumentException.class)
	public void instrEchoMockException() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getRegisterValue(0)).thenReturn("Success!");
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl first = new InstructionArgumentImpl(0, true);

		parametri.add( first );

		@SuppressWarnings("unused")
		InstrEcho echo = new InstrEcho(parametri);
	}
	@Test(expected = IllegalArgumentException.class)
	public void instrHaltMockException() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getRegisterValue(0)).thenReturn(new Integer(20));
		List<InstructionArgument> parametri = new ArrayList<>();
		InstructionArgumentImpl first = new InstructionArgumentImpl(0, true);
		parametri.add(first);

		@SuppressWarnings("unused")
		InstrHalt halt = new InstrHalt(parametri);
	}
	@Test(expected = IllegalArgumentException.class)
	public void instrJumpMockException() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl first = new InstructionArgumentImpl(90, false);
		parametri.add( first );
		@SuppressWarnings("unused")
		InstrJump jump = new InstrJump(parametri);
	}
	@Test(expected = IllegalArgumentException.class)
	public void instrJumpIfTrueMockException() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getFlag()).thenReturn(true);
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl first = new InstructionArgumentImpl(90, false);
		parametri.add( first );
		@SuppressWarnings("unused")
		InstrJumpIfTrue jump = new InstrJumpIfTrue(parametri);
	}
	@Test(expected = IllegalArgumentException.class)
	public void instrLoadMockException() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		Memory memory = mock(Memory.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getMemory().getLocation(20)).thenReturn("Success!");
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl register = new InstructionArgumentImpl(0, true);
		InstructionArgumentImpl memIndex = new InstructionArgumentImpl(20, false);
		parametri.add(register);
		parametri.add(memIndex);
		@SuppressWarnings("unused")
		InstrLoad load = new InstrLoad(parametri);
	}
	@Test(expected = IllegalArgumentException.class)
	public void instrLoadMockException2() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		Memory memory = mock(Memory.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getMemory().getLocation(20)).thenReturn("Success!");
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl register = new InstructionArgumentImpl(0, false);
		InstructionArgumentImpl memIndex = new InstructionArgumentImpl(20, false);
		parametri.add(register);
		parametri.add(memIndex);
		@SuppressWarnings("unused")
		InstrLoad load = new InstrLoad(parametri);
	}
	@Test(expected = IllegalArgumentException.class)
	public void instrMoveMockException() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getRegisterValue(1)).thenReturn("Success!");
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl register1 = new InstructionArgumentImpl(0, true);
		InstructionArgumentImpl register2 = new InstructionArgumentImpl(1, false);
		parametri.add(register1);
		parametri.add(register2);
		@SuppressWarnings("unused")
		InstrMove move = new InstrMove(parametri);
	}
	@Test(expected = IllegalArgumentException.class)
	public void instrMoveMockException2() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getRegisterValue(1)).thenReturn("Success!");
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl register1 = new InstructionArgumentImpl(0, false);
		InstructionArgumentImpl register2 = new InstructionArgumentImpl(1, true);
		parametri.add(register1);
		parametri.add(register2);
		@SuppressWarnings("unused")
		InstrMove move = new InstrMove(parametri);
	}
	@Test(expected = IllegalArgumentException.class)
	public void instrTestEqualsMockException() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getRegisterValue(0)).thenReturn("Success!");
		when(computer.getRegisters().getRegisterValue(1)).thenReturn("Success!");
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl register1 = new InstructionArgumentImpl(0, true);
		InstructionArgumentImpl register2 = new InstructionArgumentImpl(1, false);
		parametri.add(register1);
		parametri.add(register2);
		@SuppressWarnings("unused")
		InstrTestEquals test = new InstrTestEquals(parametri);
	}
	@Test(expected = IllegalArgumentException.class)
	public void instrTestEqualsMockException2() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getRegisterValue(0)).thenReturn("Success!");
		when(computer.getRegisters().getRegisterValue(1)).thenReturn("Success!");
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl register1 = new InstructionArgumentImpl(0, false);
		InstructionArgumentImpl register2 = new InstructionArgumentImpl(1, true);
		parametri.add(register1);
		parametri.add(register2);
		@SuppressWarnings("unused")
		InstrTestEquals test = new InstrTestEquals(parametri);
	}
	@Test(expected = IllegalArgumentException.class)
	public void instrMoveMockException3() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getRegisterValue(1)).thenReturn("Success!");
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl register1 = new InstructionArgumentImpl(0, false);
		InstructionArgumentImpl register2 = new InstructionArgumentImpl(1, true);
		InstructionArgumentImpl register3 = new InstructionArgumentImpl(1, true);
		parametri.add(register1);
		parametri.add(register2);
		parametri.add(register3);
		@SuppressWarnings("unused")
		InstrMove move = new InstrMove(parametri);
	}
	@Test(expected = IllegalArgumentException.class)
	public void instrTestEqualsMockException3() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getRegisterValue(0)).thenReturn("Success!");
		when(computer.getRegisters().getRegisterValue(1)).thenReturn("Success!");
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl register1 = new InstructionArgumentImpl(0, false);
		InstructionArgumentImpl register2 = new InstructionArgumentImpl(1, true);
		InstructionArgumentImpl register3 = new InstructionArgumentImpl(1, true);
		parametri.add(register1);
		parametri.add(register2);
		parametri.add(register3);
		@SuppressWarnings("unused")
		InstrTestEquals test = new InstrTestEquals(parametri);
	}
	@Test(expected = IllegalArgumentException.class)
	public void instrEchoMockException2() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getRegisterValue(0)).thenReturn("Success!");
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl first = new InstructionArgumentImpl(0, true);
		InstructionArgumentImpl register1 = new InstructionArgumentImpl(1, true);
		parametri.add(register1);
		parametri.add( first );
		@SuppressWarnings("unused")
		InstrEcho echo = new InstrEcho(parametri);
	}
	@Test(expected = IllegalArgumentException.class)
	public void instrDecrementMockException2() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getRegisterValue(0)).thenReturn(new Integer(20));
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl first = new InstructionArgumentImpl(0);
		InstructionArgumentImpl second = new InstructionArgumentImpl(1);
		parametri.add( first );
		parametri.add( second );

		@SuppressWarnings("unused")
		InstrDecrement dec = new InstrDecrement(parametri);
	}
	@Test(expected = IllegalArgumentException.class)
	public void instrIncrementMockException2() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getRegisterValue(0)).thenReturn(new Integer(20));
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl first = new InstructionArgumentImpl(0);
		InstructionArgumentImpl second = new InstructionArgumentImpl(1);
		parametri.add( first );
		parametri.add( second );
		@SuppressWarnings("unused")
		InstrIncrement inc = new InstrIncrement(parametri);
	}
	@Test(expected = IllegalArgumentException.class)
	public void instrLoadMockException3() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		Memory memory = mock(Memory.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getMemory().getLocation(20)).thenReturn("Success!");
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl register = new InstructionArgumentImpl(0, false);
		InstructionArgumentImpl memIndex = new InstructionArgumentImpl(20, true);
		InstructionArgumentImpl memIndex2 = new InstructionArgumentImpl(30, true);
		parametri.add(register);
		parametri.add(memIndex);
		parametri.add(memIndex2);
		@SuppressWarnings("unused")
		InstrLoad load = new InstrLoad(parametri);
	}
	@Test(expected = IllegalArgumentException.class)
	public void instrJumpMockException2() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl first = new InstructionArgumentImpl(90, true);
		InstructionArgumentImpl second = new InstructionArgumentImpl(90, true);
		parametri.add( first );
		parametri.add( second );
		@SuppressWarnings("unused")
		InstrJump jump = new InstrJump(parametri);
	}
	@Test(expected = IllegalArgumentException.class)
	public void instrJumpIfTrueMockException2() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getFlag()).thenReturn(true);
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl first = new InstructionArgumentImpl(90, true);
		InstructionArgumentImpl second = new InstructionArgumentImpl(90, true);
		parametri.add( first );
		parametri.add( second );
		@SuppressWarnings("unused")
		InstrJumpIfTrue jump = new InstrJumpIfTrue(parametri);
	}
}

package hr.fer.zemris.java.tecaj_2.jcomp.test;

import static org.junit.Assert.assertTrue;
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
import org.mockito.Mockito;

public class JCompMockTest {

	@Test
	public void instrMulMock() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getRegisterValue(1)).thenReturn(new Integer(20));
		when(computer.getRegisters().getRegisterValue(2)).thenReturn(new Integer(10));
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl first = new InstructionArgumentImpl(0);
		InstructionArgumentImpl second = new InstructionArgumentImpl(1);
		InstructionArgumentImpl third = new InstructionArgumentImpl(2);


		parametri.add( first );
		parametri.add( second );
		parametri.add( third );

		InstrMul mul = new InstrMul(parametri);

		mul.execute(computer);

		Mockito.verify(registers).setRegisterValue(0, 200);
		Mockito.verify(computer, Mockito.atLeastOnce()).getRegisters();
		Mockito.verify(registers).getRegisterValue(1);
		Mockito.verify(registers).getRegisterValue(2);
	}

	@Test
	public void instrAddMock() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getRegisterValue(1)).thenReturn(new Integer(20));
		when(computer.getRegisters().getRegisterValue(2)).thenReturn(new Integer(10));
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl first = new InstructionArgumentImpl(0);
		InstructionArgumentImpl second = new InstructionArgumentImpl(1);
		InstructionArgumentImpl third = new InstructionArgumentImpl(2);


		parametri.add( first );
		parametri.add( second );
		parametri.add( third );

		InstrAdd add = new InstrAdd(parametri);

		add.execute(computer);

		Mockito.verify(registers).setRegisterValue(0, 30);
		Mockito.verify(computer, Mockito.atLeastOnce()).getRegisters();
		Mockito.verify(registers).getRegisterValue(1);
		Mockito.verify(registers).getRegisterValue(2);
	}

	@Test
	public void instrDecrementMock() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getRegisterValue(0)).thenReturn(new Integer(20));
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl first = new InstructionArgumentImpl(0);

		parametri.add( first );

		InstrDecrement dec = new InstrDecrement(parametri);

		dec.execute(computer);
		Mockito.verify(registers).getRegisterValue(0);
		Mockito.verify(registers).setRegisterValue(0, 19);
		Mockito.verify(computer, Mockito.atLeastOnce()).getRegisters();
	}
	@Test
	public void instrIncrementMock() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getRegisterValue(0)).thenReturn(new Integer(20));
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl first = new InstructionArgumentImpl(0);

		parametri.add( first );

		InstrIncrement inc = new InstrIncrement(parametri);

		inc.execute(computer);
		Mockito.verify(registers).getRegisterValue(0);
		Mockito.verify(registers).setRegisterValue(0, 21);
		Mockito.verify(computer, Mockito.atLeastOnce()).getRegisters();
	}
	@Test
	public void instrEchoMock() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getRegisterValue(0)).thenReturn("Success!");
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl first = new InstructionArgumentImpl(0);

		parametri.add( first );

		InstrEcho echo = new InstrEcho(parametri);

		echo.execute(computer);
		Mockito.verify(registers).getRegisterValue(0);
		Mockito.verify(computer, Mockito.atLeastOnce()).getRegisters();
	}
	@Test
	public void instrHaltMock() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getRegisterValue(0)).thenReturn(new Integer(20));
		List<InstructionArgument> parametri = new ArrayList<>();

		InstrHalt halt = new InstrHalt(parametri);

		assertTrue(halt.execute(computer));
		Mockito.verify(registers, Mockito.times(0)).getRegisterValue(0);
		Mockito.verify(registers, Mockito.times(0)).setRegisterValue(0, 19);
	}
	@Test
	public void instrJumpMock() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl first = new InstructionArgumentImpl(90, true);
		parametri.add( first );
		InstrJump jump = new InstrJump(parametri);
		jump.execute(computer);
		Mockito.verify(registers).setProgramCounter(90);
		Mockito.verify(computer, Mockito.atLeastOnce()).getRegisters();
	}
	@Test
	public void instrJumpIfTrueMock() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getFlag()).thenReturn(true);
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl first = new InstructionArgumentImpl(90, true);
		parametri.add( first );
		InstrJumpIfTrue jump = new InstrJumpIfTrue(parametri);
		jump.execute(computer);
		Mockito.verify(registers).setProgramCounter(90);
		Mockito.verify(registers).getFlag();
		Mockito.verify(computer, Mockito.atLeastOnce()).getRegisters();
	}
	@Test
	public void instrLoadMock() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		Memory memory = mock(Memory.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getMemory().getLocation(20)).thenReturn("Success!");
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl register = new InstructionArgumentImpl(0, false);
		InstructionArgumentImpl memIndex = new InstructionArgumentImpl(20, true);
		parametri.add(register);
		parametri.add(memIndex);
		InstrLoad load = new InstrLoad(parametri);
		load.execute(computer);
		Mockito.verify(registers).setRegisterValue(0, "Success!");
		Mockito.verify(computer, Mockito.atLeastOnce()).getRegisters();
		Mockito.verify(computer, Mockito.atLeastOnce()).getMemory();
	}
	@Test
	public void instrMoveMock() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getRegisterValue(1)).thenReturn("Success!");
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl register1 = new InstructionArgumentImpl(0, false);
		InstructionArgumentImpl register2 = new InstructionArgumentImpl(1, false);
		parametri.add(register1);
		parametri.add(register2);
		InstrMove move = new InstrMove(parametri);
		move.execute(computer);
		Mockito.verify(registers).setRegisterValue(0, "Success!");
		Mockito.verify(computer, Mockito.atLeastOnce()).getRegisters();
	}
	@Test
	public void instrTestEqualsMock() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getRegisterValue(0)).thenReturn("Success!");
		when(computer.getRegisters().getRegisterValue(1)).thenReturn("Success!");
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl register1 = new InstructionArgumentImpl(0, false);
		InstructionArgumentImpl register2 = new InstructionArgumentImpl(1, false);
		parametri.add(register1);
		parametri.add(register2);
		InstrTestEquals test = new InstrTestEquals(parametri);
		test.execute(computer);
		Mockito.verify(registers).setFlag(true);
		Mockito.verify(computer, Mockito.atLeastOnce()).getRegisters();
	}
	@Test
	public void instrTestEquals2Mock() {
		Computer computer = mock(Computer.class);
		Registers registers = mock(Registers.class);
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getRegisters().getRegisterValue(0)).thenReturn("Success!");
		when(computer.getRegisters().getRegisterValue(1)).thenReturn("Success1!");
		List<InstructionArgument> parametri = new ArrayList<>();

		InstructionArgumentImpl register1 = new InstructionArgumentImpl(0, false);
		InstructionArgumentImpl register2 = new InstructionArgumentImpl(1, false);
		parametri.add(register1);
		parametri.add(register2);
		InstrTestEquals test = new InstrTestEquals(parametri);
		test.execute(computer);
		Mockito.verify(registers).setFlag(false);
		Mockito.verify(computer, Mockito.atLeastOnce()).getRegisters();
	}

}

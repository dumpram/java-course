package hr.fer.zemris.java.tecaj_2.jcomp.test;

import static org.junit.Assert.assertTrue;
import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.ExecutionUnit;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionCreator;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.ComputerImpl;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.ExecutionUnitImpl;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.MemoryImpl;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.RegistersImpl;
import hr.fer.zemris.java.tecaj_2.jcomp.parser.InstructionCreatorImpl;
import hr.fer.zemris.java.tecaj_2.jcomp.parser.ProgramParser;

import org.junit.Test;

public class JCompStandardTest {

	@Test
	public void OfficalTestWithOutMocks1() throws Exception {
				Computer comp = new ComputerImpl(256, 16);
				InstructionCreator creator = new InstructionCreatorImpl(
				 "hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions"
				);
				ProgramParser.parse(
				 "examples/asmProgram1.txt",
				 comp,
				 creator
				);
				ExecutionUnit exec = new ExecutionUnitImpl();
				assertTrue(exec.go(comp));
	}
	@Test
	public void OfficalTestWithOutMocks2() throws Exception {
				Computer comp = new ComputerImpl(256, 16);
				InstructionCreator creator = new InstructionCreatorImpl(
				 "hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions"
				);
				ProgramParser.parse(
				 "examples/asmProgram2.txt",
				 comp,
				 creator
				);
				ExecutionUnit exec = new ExecutionUnitImpl();
				assertTrue(exec.go(comp));
	}
	@Test
	public void OfficalTestWithOutMocks3() throws Exception {
				Computer comp = new ComputerImpl(256, 16);
				InstructionCreator creator = new InstructionCreatorImpl(
				 "hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions"
				);
				ProgramParser.parse(
				 "examples/prim1.txt",
				 comp,
				 creator
				);
				ExecutionUnit exec = new ExecutionUnitImpl();
				assertTrue(exec.go(comp));
	}
	@Test(expected = IllegalArgumentException.class)
	public void OfficalTestWithOutMocks4() throws Exception {
				Computer comp = new ComputerImpl(256, 16);
				InstructionCreator creator = new InstructionCreatorImpl(
				 "hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions"
				);
				ProgramParser.parse(
				 "examples/prim3.txt",
				 comp,
				 creator
				);
				ExecutionUnit exec = new ExecutionUnitImpl();
				exec.go(comp);
	}
	@Test(expected = IllegalArgumentException.class)
	public void OfficalTestWithOutMocks5() throws Exception {
				Computer comp = new ComputerImpl(256, 16);
				InstructionCreator creator = new InstructionCreatorImpl(
				 "hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions"
				);
				ProgramParser.parse(
				 "examples/prim4.txt",
				 comp,
				 creator
				);
				ExecutionUnit exec = new ExecutionUnitImpl();
				exec.go(comp);
	}
	@Test(expected = IllegalArgumentException.class)
	public void OfficalTestWithOutMocks6() throws Exception {
				Computer comp = new ComputerImpl(256, 16);
				InstructionCreator creator = new InstructionCreatorImpl(
				 "hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions"
				);
				ProgramParser.parse(
				 "examples/prim5.txt",
				 comp,
				 creator
				);
				ExecutionUnit exec = new ExecutionUnitImpl();
				exec.go(comp);
	}
	@Test(expected = IllegalArgumentException.class)
	public void OfficalTestWithOutMocks7() throws Exception {
				Computer comp = new ComputerImpl(256, 16);
				InstructionCreator creator = new InstructionCreatorImpl(
				 "hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions"
				);
				ProgramParser.parse(
				 "examples/prim6.txt",
				 comp,
				 creator
				);
				ExecutionUnit exec = new ExecutionUnitImpl();
				exec.go(comp);
	}
	@Test(expected = IllegalArgumentException.class)
	public void OfficalTestWithOutMocks8() throws Exception {
				Computer comp = new ComputerImpl(-4, 16);
				InstructionCreator creator = new InstructionCreatorImpl(
				 "hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions"
				);
				ProgramParser.parse(
				 "examples/prim6.txt",
				 comp,
				 creator
				);
				ExecutionUnit exec = new ExecutionUnitImpl();
				exec.go(comp);
	}
	@Test(expected = IllegalArgumentException.class)
	public void OfficalTestWithOutMocks9() throws Exception {
		Computer comp = new ComputerImpl(-4, -10);
		InstructionCreator creator = new InstructionCreatorImpl(
		 "hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions"
		);
		ProgramParser.parse(
		 "examples/prim6.txt",
		 comp,
		 creator
		);
		ExecutionUnit exec = new ExecutionUnitImpl();
		exec.go(comp);
	}
	@Test(expected = IllegalArgumentException.class)
	public void OfficalTestWithOutMocks10() throws Exception {
		Computer comp = new ComputerImpl(4, -10);
		InstructionCreator creator = new InstructionCreatorImpl(
		 "hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions"
		);
		ProgramParser.parse(
		 "examples/prim6.txt",
		 comp,
		 creator
		);
		ExecutionUnit exec = new ExecutionUnitImpl();
		exec.go(comp);
	}

	@Test(expected = IllegalArgumentException.class)
	public void memoryNegativeSize() throws Exception {
		@SuppressWarnings("unused")
		MemoryImpl mem = new MemoryImpl(-2);
	}
	@Test(expected = IllegalArgumentException.class)
	public void memoryNegativeLocation() throws Exception {
		MemoryImpl mem = new MemoryImpl(10);
		mem.getLocation(-2);
	}
	@Test(expected = IllegalArgumentException.class)
	public void memoryOutOfBoundsLocation() throws Exception {
		MemoryImpl mem = new MemoryImpl(10);
		mem.getLocation(20);
	}
	@Test(expected = IllegalArgumentException.class)
	public void registersNegativeSize() throws Exception {
		@SuppressWarnings("unused")
		RegistersImpl reg = new RegistersImpl(-4);
	}
	@Test(expected = IllegalArgumentException.class)
	public void registersNegativeIndex() throws Exception {
		RegistersImpl reg = new RegistersImpl(10);
		reg.getRegisterValue(-2);
	}
	@Test(expected = IllegalArgumentException.class)
	public void registersSetNegativeIndex() throws Exception {
		RegistersImpl reg = new RegistersImpl(15);
		reg.setRegisterValue(-2, 10);
	}
	@Test(expected = IllegalArgumentException.class)
	public void registersSetOutOfBoundsIndex() throws Exception {
		RegistersImpl reg = new RegistersImpl(15);
		reg.setRegisterValue(15, 10);
	}
	@Test(expected = IllegalArgumentException.class)
	public void memorySetOutOfBoundsLocation() throws Exception {
		MemoryImpl mem = new MemoryImpl(10);
		mem.setLocation(20, 10);
	}
	@Test(expected = IllegalArgumentException.class)
	public void memorySetNegativefBoundsLocation() throws Exception {
		MemoryImpl mem = new MemoryImpl(10);
		mem.setLocation(-2, 10);
	}
}

package hr.fer.zemris.bool.test;

import static org.junit.Assert.assertEquals;
import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanVariable;
import hr.fer.zemris.bool.Mask;
import hr.fer.zemris.bool.fimpl.IndexedBF;
import hr.fer.zemris.bool.fimpl.MaskBasedBF;
import hr.fer.zemris.bool.qmc.QMCMinimizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class OfficialSimpleTest {

    @Test
    public void FirstExampleTest() {
	BooleanVariable varA = new BooleanVariable("A");
	BooleanVariable varB = new BooleanVariable("B");
	BooleanVariable varC = new BooleanVariable("C");
	BooleanVariable varD = new BooleanVariable("D");

	BooleanFunction f1 = new IndexedBF("f1", Arrays.asList(varA, varB,
		varC, varD), true, Arrays.asList(0, 1, 4, 5, 11, 15),
		new ArrayList<Integer>());

	List<Mask> solution = new ArrayList<>();
	solution.add(Mask.parse("0x0x"));
	solution.add(Mask.parse("1x11"));
	MaskBasedBF[] solutions = QMCMinimizer.minimize(f1, true);

	assertEquals(solution, solutions[0].getMasks());
    }

    @Test
    public void SecondExampleTest() {
	BooleanVariable varA = new BooleanVariable("A");
	BooleanVariable varB = new BooleanVariable("B");
	BooleanVariable varC = new BooleanVariable("C");
	BooleanVariable varD = new BooleanVariable("D");

	BooleanFunction f1 = new IndexedBF("f1", Arrays.asList(varA, varB,
		varC, varD), true, Arrays.asList(0, 1, 4, 5, 9, 11, 15),
		new ArrayList<Integer>());

	List<Mask> solution = new ArrayList<>();
	solution.add(Mask.parse("10x1"));
	solution.add(Mask.parse("0x0x"));
	solution.add(Mask.parse("1x11"));

	MaskBasedBF[] solutions = QMCMinimizer.minimize(f1, true);
	assertEquals(solution, solutions[0].getMasks());
	solution.set(0, Mask.parse("x001"));
	assertEquals(solution, solutions[1].getMasks());
    }

    @Test
    public void ThirdExampleTest() {
	BooleanVariable varA = new BooleanVariable("A");
	BooleanVariable varB = new BooleanVariable("B");
	BooleanVariable varC = new BooleanVariable("C");
	BooleanVariable varD = new BooleanVariable("D");

	BooleanFunction f1 = new IndexedBF("f1", Arrays.asList(varA, varB,
		varC, varD), true, Arrays.asList(0, 1, 4, 5, 9, 15),
		Arrays.asList(11));

	List<Mask> solution = new ArrayList<>();
	solution.add(Mask.parse("10x1"));
	solution.add(Mask.parse("0x0x"));
	solution.add(Mask.parse("1x11"));

	MaskBasedBF[] solutions = QMCMinimizer.minimize(f1, true);
	assertEquals(solution, solutions[0].getMasks());
	solution.set(0, Mask.parse("x001"));
	assertEquals(solution, solutions[1].getMasks());
    }

    @Test
    public void FourthExampleTest() {
	BooleanVariable varA = new BooleanVariable("A");
	BooleanVariable varB = new BooleanVariable("B");
	BooleanVariable varC = new BooleanVariable("C");
	BooleanVariable varD = new BooleanVariable("D");

	BooleanFunction f1 = new IndexedBF("f1", Arrays.asList(varA, varB,
		varC, varD), true, Arrays.asList(4, 5, 6, 7, 8, 9, 10, 11, 13,
		14), new ArrayList<Integer>());

	MaskBasedBF[] solutions = QMCMinimizer.minimize(f1, true);

	assertEquals(4, solutions.length);

    }

    @Test
    public void FifthExampleTest() {
	BooleanVariable varA = new BooleanVariable("A");
	BooleanVariable varB = new BooleanVariable("B");
	BooleanVariable varC = new BooleanVariable("C");
	BooleanVariable varD = new BooleanVariable("D");

	BooleanFunction f1 = new IndexedBF("f1", Arrays.asList(varA, varB,
		varC, varD), true, Arrays.asList(4, 5, 7, 9, 10, 11, 13, 14),
		Arrays.asList(6, 8));

	MaskBasedBF[] solutions = QMCMinimizer.minimize(f1, true);

	assertEquals(4, solutions.length);
    }

    @Test
    public void SixthExampleTest() {
	BooleanVariable varA = new BooleanVariable("A");
	BooleanVariable varB = new BooleanVariable("B");
	BooleanVariable varC = new BooleanVariable("C");
	BooleanVariable varD = new BooleanVariable("D");

	BooleanFunction f1 = new IndexedBF("f1", Arrays.asList(varA, varB,
		varC, varD), true, Arrays.asList(0, 1, 4, 5, 11, 15),
		Arrays.asList(2, 6, 10));

	List<Mask> solution = new ArrayList<>();
	solution.add(Mask.parse("0x0x"));
	solution.add(Mask.parse("1x11"));
	MaskBasedBF[] solutions = QMCMinimizer.minimize(f1, true);

	assertEquals(solution, solutions[0].getMasks());

    }

}

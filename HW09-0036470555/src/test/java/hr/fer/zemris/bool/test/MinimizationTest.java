package hr.fer.zemris.bool.test;

import static org.junit.Assert.assertTrue;
import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanVariable;
import hr.fer.zemris.bool.Masks;
import hr.fer.zemris.bool.fimpl.IndexedBF;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

public class MinimizationTest {

    MinimizationHelp help = new MinimizationHelp(new BooleanVariable(
	    "A"), new BooleanVariable("B"), new BooleanVariable("C"),
	    new BooleanVariable("D"), new BooleanVariable("E"));

    @Test
    public void function1Test() {
	final BooleanFunction f1 = new IndexedBF("f", Arrays.asList(help.varA,
		help.varB, help.varC, help.varD), true, Arrays.asList(1, 2, 3,
		4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14),
		Collections.<Integer> emptyList());

	assertTrue(help.verifyFunction(f1, 1,
		Arrays.asList(Masks.fromStrings("1111", "0000")), false));

	assertTrue(help.verifyFunction(f1, 6, Arrays.asList(
		Masks.fromStrings("x01x", "0xx1", "x1x0", "1x0x"),
		Masks.fromStrings("x10x", "0xx1", "xx10", "10xx"),
		Masks.fromStrings("x10x", "0x1x", "x0x1", "1xx0"),
		Masks.fromStrings("x0x1", "01xx", "xx10", "1x0x"),
		Masks.fromStrings("0x1x", "xx01", "x1x0", "10xx"),
		Masks.fromStrings("x01x", "01xx", "xx01", "1xx0")), true));
    }

    @Test
    public void function2Test() {
	final BooleanFunction f2 = new IndexedBF("f", Arrays.asList(help.varA,
		help.varB, help.varC, help.varD), true, Arrays.asList(0, 1, 2,
		3, 4, 6, 7, 8, 9, 11, 12, 13, 14, 15),
		Collections.<Integer> emptyList());

	assertTrue(help.verifyFunction(f2, 1,
		Arrays.asList(Masks.fromStrings("0101", "1010")), false));

	assertTrue(help.verifyFunction(f2, 6, Arrays.asList(
		Masks.fromStrings("00xx", "xx00", "1xx1", "x11x"),
		Masks.fromStrings("00xx", "xx11", "x1x0", "1x0x"),
		Masks.fromStrings("0xx0", "xx11", "x00x", "11xx"),
		Masks.fromStrings("0x1x", "1xx1", "x1x0", "x00x"),
		Masks.fromStrings("x0x1", "0xx0", "x11x", "1x0x"),
		Masks.fromStrings("0x1x", "x0x1", "xx00", "11xx")), true));
    }

    @Test
    public void function3Test() {
	final BooleanFunction f3 = new IndexedBF("f", Arrays.asList(help.varA,
		help.varB, help.varC, help.varD), true, Arrays.asList(0, 1, 2,
		3, 4, 5, 6, 9, 10, 11, 12, 13, 14),
		Collections.<Integer> emptyList());

	assertTrue(help.verifyFunction(f3, 1,
		Arrays.asList(Masks.fromStrings("x111", "1000")), false));

	assertTrue(help.verifyFunction(f3, 6, Arrays.asList(
		Masks.fromStrings("x10x", "xx10", "x0x1", "00xx"),
		Masks.fromStrings("x10x", "xx10", "x0x1", "0x0x"),
		Masks.fromStrings("x10x", "xx10", "x0x1", "0xx0"),
		Masks.fromStrings("x1x0", "x01x", "xx01", "00xx"),
		Masks.fromStrings("x1x0", "x01x", "xx01", "0x0x"),
		Masks.fromStrings("x1x0", "x01x", "xx01", "0xx0")), true));
    }

    @Test
    public void function4Test() {
	final BooleanFunction f4 = new IndexedBF("f", Arrays.asList(help.varA,
		help.varB, help.varC, help.varD), true, Arrays.asList(2, 3, 4,
		5, 6, 7, 8, 9, 10, 11, 12, 13, 15),
		Collections.<Integer> emptyList());

	assertTrue(help.verifyFunction(f4, 1,
		Arrays.asList(Masks.fromStrings("000x", "1110")), false));

	assertTrue(help.verifyFunction(f4, 6, Arrays.asList(
		Masks.fromStrings("xx11", "10xx", "x10x", "0x1x"),
		Masks.fromStrings("xx11", "1x0x", "01xx", "x01x"),
		Masks.fromStrings("x1x1", "10xx", "x10x", "0x1x"),
		Masks.fromStrings("x1x1", "1x0x", "01xx", "x01x"),
		Masks.fromStrings("1xx1", "10xx", "x10x", "0x1x"),
		Masks.fromStrings("1xx1", "1x0x", "01xx", "x01x")), true));
    }

    @Test
    public void function5Test() {
	final BooleanFunction f5 = new IndexedBF("f", Arrays.asList(help.varA,
		help.varB, help.varC, help.varD), true, Arrays.asList(2, 3, 4,
		5, 6, 7, 8, 9, 10, 11, 12, 13, 14),
		Collections.<Integer> emptyList());

	assertTrue(help.verifyFunction(f5, 1,
		Arrays.asList(Masks.fromStrings("000x", "1111")), false));

	assertTrue(help.verifyFunction(f5, 6, Arrays.asList(
		Masks.fromStrings("10xx", "x10x", "xx10", "0x1x"),
		Masks.fromStrings("10xx", "x1x0", "x10x", "0x1x"),
		Masks.fromStrings("1x0x", "01xx", "xx10", "x01x"),
		Masks.fromStrings("1x0x", "x1x0", "01xx", "x01x"),
		Masks.fromStrings("1xx0", "10xx", "x10x", "0x1x"),
		Masks.fromStrings("1xx0", "1x0x", "01xx", "x01x")), true));
    }

    @Test
    public void function6Test() {
	final BooleanFunction f6 = new IndexedBF("f", Arrays.asList(help.varA,
		help.varB, help.varC, help.varD), true, Arrays.asList(1, 3, 4,
		5, 6, 7, 8, 9, 10, 11, 12, 13, 14),
		Collections.<Integer> emptyList());

	assertTrue(help.verifyFunction(f6, 6, Arrays.asList(
		Masks.fromStrings("10xx", "x1x0", "xx01", "0xx1"),
		Masks.fromStrings("10xx", "x1x0", "x10x", "0xx1"),
		Masks.fromStrings("1x0x", "10xx", "x1x0", "0xx1"),
		Masks.fromStrings("1xx0", "01xx", "xx01", "x0x1"),
		Masks.fromStrings("1xx0", "x10x", "01xx", "x0x1"),
		Masks.fromStrings("1xx0", "1x0x", "01xx", "x0x1")), true));
    }

    @Test
    public void fiveVariablesTest() {
	final BooleanFunction f = new IndexedBF("f", Arrays.asList(help.varA,
		help.varB, help.varC, help.varD, help.varE), true,
		Arrays.asList(0, 1, 2, 5, 6, 7, 8, 9, 10, 12, 13, 14, 15, 17,
			18, 19, 20, 21, 22, 23, 24, 25, 27, 28, 30, 31),
		Collections.<Integer> emptyList());

	assertTrue(help.verifyFunction(f, 2, Arrays.asList(Masks.fromStrings(
		"101xx", "0x0x0", "1x0x1", "0xx01", "x0x10", "xx11x", "x1x00"),
		Masks.fromStrings("101xx", "0x0x0", "1x0x1", "0xx01", "xx11x",
			"10x1x", "x1x00")), true));

    }

    @Test
    public void minimalLogicGatesTest() {
	BooleanFunction f = new IndexedBF("f3", Arrays.asList(help.varA,
		help.varB, help.varC, help.varD), true, Arrays.asList(4, 5, 6,
		7, 8, 9, 11), Arrays.asList(2, 3, 12, 15));

	assertTrue(help.verifyFunction(f, 1,
		Arrays.asList(Masks.fromStrings("100x", "xx11", "01xx"))));
    }

    @Test
    public void dontCaresTest() {
	BooleanFunction f = new IndexedBF("f3", Arrays.asList(help.varA,
		help.varB, help.varC, help.varD), true, Arrays.asList(0, 1, 2,
		3, 4, 5, 6, 7, 8, 9, 10, 12, 13, 14, 15),
		Collections.<Integer> emptyList());

	assertTrue(help.verifyFunction(f, 1,
		Arrays.asList(Masks.fromStrings("1011")), false));
    }

}

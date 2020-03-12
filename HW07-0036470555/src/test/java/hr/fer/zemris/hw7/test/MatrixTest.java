package hr.fer.zemris.hw7.test;

import static org.junit.Assert.assertEquals;
import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Matrix;
import hr.fer.zemris.linearna.Vector;

import org.junit.Test;

public class MatrixTest {

    double DELTA = 1e-15;

    @Test
    public void matrixParseSimpleTest() {
	Matrix newOne = Matrix.parseSimple("1 2 3 | 4 5 6");
	assertEquals(2, newOne.getRowsCount());
	assertEquals(3, newOne.getColsCount());
	assertEquals(2, newOne.get(0, 1), DELTA);
    }

    @Test
    public void matrixSetTest() {
	Matrix newOne = Matrix.parseSimple("2 2 1 | 5 1 4 | 7 7 2");
	newOne.set(2, 1, 0);
	assertEquals(0, newOne.get(2, 1), DELTA);
    }

    @Test
    public void matrixEqualsTest() {
	Matrix newOne = Matrix.parseSimple("2 2 1 | 5 1 4 | 7 7 2");
	Matrix result = Matrix.parseSimple("2 2 1 | 5 1 4 | 7 7 2");
	assertEquals(newOne, result);
    }

    @Test
    public void makeIdentity() {
	IMatrix first = Matrix.parseSimple("2 2 1 | 5 1 4 | 7 7 2");
	IMatrix result = Matrix.parseSimple(" 1 0 0 | 0 1 0 | 0 0 1");
	assertEquals(result, first.makeIdentity());
    }

    @Test
    public void matrixAddTest() {
	IMatrix first = Matrix.parseSimple("2 2 1 | 5 1 4 | 7 7 2");
	IMatrix second = first.copy().makeIdentity();
	IMatrix result = Matrix.parseSimple("3 2 1 | 5 2 4 | 7 7 3");
	assertEquals(result, first.add(second));
    }

    @Test
    public void matrixSubTest() {
	IMatrix first = Matrix.parseSimple("2 2 1 | 5 1 4 | 7 7 2");
	IMatrix second = first.copy().makeIdentity();
	IMatrix result = Matrix.parseSimple("1 2 1 | 5 0 4 | 7 7 1");
	assertEquals(result, first.sub(second));
    }

    @Test
    public void matrixAdd2Test() {
	IMatrix first = Matrix.parseSimple("2 2 1 | 5 1 4 | 7 7 2");
	IMatrix second = first.copy().makeIdentity();
	IMatrix result = Matrix.parseSimple("3 2 1 | 5 2 4 | 7 7 3");
	assertEquals(result, first.nAdd(second));
    }

    @Test
    public void matrixSub1Test() {
	IMatrix first = Matrix.parseSimple("2 2 1 | 5 1 4 | 7 7 2");
	IMatrix second = first.copy().makeIdentity();
	IMatrix result = Matrix.parseSimple("1 2 1 | 5 0 4 | 7 7 1");
	assertEquals(result, first.nSub(second));
    }

    @Test(expected = IncompatibleOperandException.class)
    public void matrixSubIllegalArgumentTest() {
	IMatrix first = Matrix.parseSimple("2 2 1 3 | 5 1 4 3 | 7 7 2 3");
	IMatrix second = first.copy().makeIdentity();
	IMatrix result = Matrix.parseSimple("1 2 1 | 5 0 4 | 7 7 1");
	assertEquals(result, first.nSub(second));
    }

    @Test
    public void matrixTransposeNotLiveViewTest() {
	IMatrix first = Matrix.parseSimple("2 2 1 3 | 5 1 4 3 | 7 7 2 3");
	IMatrix result = Matrix.parseSimple("2 5 7 | 2 1 7 | 1 4 2 | 3 3 3");
	assertEquals(result, first.nTranspose(false));
    }

    @Test
    public void matrixTransposeLiveViewTemperWithTest() {
	IMatrix first = Matrix.parseSimple("2 2 1 3 | 5 1 4 3 | 7 7 2 3");
	IMatrix result = Matrix.parseSimple("2 5 7 | 2 8 7 | 1 4 2 | 3 3 3");
	IMatrix live = first.nTranspose(true);
	assertEquals(result, live.set(1, 1, 8));
	assertEquals(8, first.get(1, 1), DELTA);
    }

    @Test
    public void matrixTransposeLiveViewTest() {
	IMatrix first = Matrix.parseSimple("2 2 1 3 | 5 1 4 3 | 7 7 2 3");
	IMatrix result = Matrix.parseSimple("2 5 7 | 2 1 7 | 1 4 2 | 3 3 3");
	assertEquals(result, first.nTranspose(true));
    }

    @Test
    public void matrixMultiplyTest() {
	IMatrix first = Matrix.parseSimple("2 2 1 3 | 5 1 4 3 | 7 7 2 3");
	IMatrix second = Matrix.parseSimple("2 5 7 | 2 1 7 | 1 4 2 | 3 3 3");
	IMatrix result = Matrix.parseSimple("18 25 39 | 25 51 59 | 39 59 111");
	assertEquals(result, first.nMultiply(second));
    }

    @Test
    public void scalarMultiplyTest() {
	IMatrix first = Matrix.parseSimple("2 2 1 3 | 5 1 4 3 | 7 7 2 3");
	IMatrix result = Matrix.parseSimple("4 4 2 6 | 10 2 8 6 | 14 14 4 6");
	assertEquals(result, first.scalarMultiply(2));
    }

    @Test
    public void nScalarMultiplyTest() {
	IMatrix first = Matrix.parseSimple("2 2 1 3 | 5 1 4 3 | 7 7 2 3");
	IMatrix result = Matrix.parseSimple("4 4 2 6 | 10 2 8 6 | 14 14 4 6");
	assertEquals(result, first.nScalarMultiply(2));
    }

    @Test
    public void subMatrixNotLiveTest() {
	IMatrix first = Matrix.parseSimple("2 2 1 3 | 5 1 4 3 | 7 7 2 3");
	IMatrix result = Matrix.parseSimple("1 4 3 | 7 2 3");
	assertEquals(result, first.subMatrix(0, 0, false));
    }

    @Test
    public void subMatrixLiveTest() {
	IMatrix first = Matrix.parseSimple("2 2 1 3 | 5 1 4 3 | 7 7 2 3");
	IMatrix result = Matrix.parseSimple("1 4 3 | 7 2 3");
	assertEquals(result, first.subMatrix(0, 0, true));
    }

    @Test
    public void subMatrixLiveTemperWithTest() {
	IMatrix first = Matrix.parseSimple("2 2 1 3 | 5 1 4 3 | 7 7 2 3");
	IMatrix result = Matrix.parseSimple("1 4 3 | 7 2 5");
	IMatrix liveView = first.subMatrix(0, 0, true);
	assertEquals(result, liveView.set(1, 2, 5));
	assertEquals(5, first.get(2, 3), DELTA);
    }

    @Test
    public void determinantTest() {
	IMatrix first = Matrix.parseSimple("18 25 39 | 25 51 59 | 39 59 111");
	double result = 7.344e+3;
	assertEquals(result, first.determinant(), DELTA);
    }

    @Test
    public void unitMatrixTest() {
	IMatrix first = Matrix.parseSimple("4");
	IMatrix second = Matrix.parseSimple("9");
	assertEquals(Matrix.parseSimple("36"), first.nMultiply(second));
	assertEquals(first, first.nInvert());
    }

    @Test
    public void InvertTest() {
	double a = 0.5;
	double b = -0.25;
	double c = -0.1;
	double d = 0.15;
	IMatrix first = Matrix.parseSimple("3 5 | 2 10");
	assertEquals(Matrix.parseSimple(a + " " + b + "  | " + c + " " + d),
		first.nInvert());
    }

    @Test
    public void Invert2Test() {
	IMatrix first = Matrix.parseSimple("3 5 6 | 2 10 1 | 1 1 2");
	System.out.println(first.nInvert());
	System.out.println("Checked by MATLAB!");
    }

    @Test
    public void VectorMatrixViewNotLiveViewTest() {
	IMatrix first = Matrix.parseSimple("3 5 6");
	IVector view = first.toVector(false);
	IVector result = Vector.parseSimple("3 5 6");
	assertEquals(result, view);
    }

    @Test
    public void VectorMatrixViewLiveViewTest() {
	IMatrix first = Matrix.parseSimple("3 5 6");
	IVector view = first.toVector(true);
	IVector result = Vector.parseSimple("3 5 6");
	assertEquals(result, view);
    }

    @Test
    public void VectorMatrixViewLiveViewTemperWithTest() {
	IMatrix first = Matrix.parseSimple("3 5 6");
	IVector view = first.toVector(true);
	view.set(1, 10);
	IVector result = Vector.parseSimple("3 10 6");
	assertEquals(result, view);
	assertEquals(10, first.get(0, 1), DELTA);
    }

    @Test
    public void VectorMatrixColViewNotLiveViewTest() {
	IMatrix first = Matrix.parseSimple("3 | 5 | 6");
	IVector view = first.toVector(false);
	IVector result = Vector.parseSimple("3 5 6");
	assertEquals(result, view);
    }

    @Test
    public void VectorMatrixColViewLiveViewTest() {
	IMatrix first = Matrix.parseSimple("3 | 5 | 6");
	IVector view = first.toVector(true);
	IVector result = Vector.parseSimple("3 5 6");
	assertEquals(result, view);
    }

    @Test
    public void VectorMatrixViewColLiveViewTemperWithTest() {
	IMatrix first = Matrix.parseSimple("3 | 5 | 6");
	IVector view = first.toVector(true);
	view.set(1, 10);
	IVector result = Vector.parseSimple("3 10 6");
	assertEquals(result, view);
	assertEquals(10, first.get(1, 0), DELTA);
    }

    @Test
    public void VectorMatrixViewNewInstanceTest() {
	IMatrix first = Matrix.parseSimple("3 | 5 | 6");
	assertEquals(Vector.parseSimple("0 0 0"), first.toVector(false)
		.newInstance(3));
    }

    @Test
    public void VectorMatrixRowViewNewInstanceTest() {
	IMatrix first = Matrix.parseSimple("3 5 6");
	assertEquals(Vector.parseSimple("0 0 0"), first.toVector(false)
		.newInstance(3));
    }

    @Test
    public void VectorMatrixRowViewCopy() {
	IMatrix first = Matrix.parseSimple("3 5 6");
	assertEquals(first.toVector(false), first.toVector(false).copy());
    }
}

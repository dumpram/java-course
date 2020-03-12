package hr.fer.zemris.hw7.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Matrix;
import hr.fer.zemris.linearna.UnmodifiableObjectException;
import hr.fer.zemris.linearna.Vector;

import java.util.Arrays;

import org.junit.Test;

public class VectorTest {

    double DELTA = 1e-15;

    @Test
    public void parseVectorTest() {
	Vector newOne = Vector.parseSimple("2 3 1");
	Vector newOne2 = Vector.parseSimple(" 2 3 1 ");
	assertEquals(newOne, newOne2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseNullStringTest() {
	@SuppressWarnings("unused")
	Vector newOne = Vector.parseSimple(null);
    }

    @Test
    public void vectorGetElementTest() {
	Vector newOne = new Vector(2, 4, 5, 1, 1, 4);
	assertEquals(5, newOne.get(2), DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void vectorGetElementWithNegativeIndexTest() {
	Vector newOne = new Vector(2, 4, 5, 1, 1, 4);
	assertEquals(5, newOne.get(-2), DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void vectorGetElementWithIndexOutOfBoundsIndexTest() {
	Vector newOne = new Vector(2, 4, 5, 1, 1, 4);
	assertEquals(5, newOne.get(6), DELTA);
    }

    @Test
    public void vectorSetElementTest() {
	Vector newOne = new Vector(2, 4, 5, 1, 1, 4);
	newOne.set(2, -3);
	assertEquals(-3, newOne.get(2), DELTA);
    }

    @Test(expected = UnmodifiableObjectException.class)
    public void vectorSetElementIfReadOnlyTest() {
	Vector newOne = new Vector(true, false, 2, 4, 5, 1, 1, 4);
	newOne.set(4, -2);
    }

    @Test
    public void useOnlyInVectorConstructorTest() {
	double[] array = new double[] { 2, 4, 5, 1, 1, 4 };
	Vector newOne = new Vector(false, true, array);
	array[2] = 4;
	assertEquals(4, newOne.get(2), DELTA);
    }

    @Test
    public void useNotOnlyInVectorConstructorTest() {
	double[] array = new double[] { 2, 4, 5, 1, 1, 4 };
	Vector newOne = new Vector(false, false, array);
	array[2] = 4;
	assertEquals(5, newOne.get(2), DELTA);
    }

    @Test
    public void getDimensionTest() {
	double[] array = new double[] { 2, 4, 5, 1, 1, 4 };
	Vector newOne = new Vector(false, false, array);
	assertEquals(array.length, newOne.getDimension());
    }

    @Test
    public void copyVectorTest() {
	double[] array = new double[] { 2, 4, 5, 1, 1, 4 };
	Vector newOne = new Vector(false, false, array);
	assertEquals(newOne.copy(), newOne);
    }

    @Test(expected = IllegalArgumentException.class)
    public void newInstanceIllegalDimensionTest() {
	double[] array = new double[] { 2, 4, 5, 1, 1, 4 };
	Vector newOne = new Vector(false, false, array);
	newOne.newInstance(-2);
    }

    @Test
    public void newInstanceTest() {
	double[] array = new double[] { 2, 4, 5, 1, 1, 4 };
	Vector newOne = new Vector(false, false, array);
	assertEquals(0, newOne.newInstance(5).get(3), DELTA);
    }

    @Test
    public void copyPartTest() {
	double[] array = new double[] { 2, 4, 5, 1, 1, 4 };
	Vector newOne = new Vector(false, false, array);
	double[] array1 = new double[] { 2, 4, 5 };
	double[] array2 = new double[] { 2, 4, 5, 1, 1, 4, 0, 0, 0 };
	assertTrue(Arrays.equals(array1, newOne.copyPart(3).toArray()));
	assertTrue(Arrays.equals(array2, newOne.copyPart(9).toArray()));
    }

    @Test
    public void vectorAddTest() {
	Vector first = new Vector(2, 1, 2, 3);
	Vector second = new Vector(3, 1, -2, 4);
	Vector result = new Vector(5, 2, 0, 7);
	assertEquals(result, first.add(second));
	first = new Vector(2, 1, 2, 3);
	assertEquals(result, first.nAdd(second));
    }

    @Test
    public void vectorSubTest() {
	Vector first = new Vector(2, 1, 2, 3);
	Vector second = new Vector(3, 1, -2, 4);
	Vector result = new Vector(-1, 0, 4, -1);
	assertEquals(result, first.sub(second));
	first = new Vector(2, 1, 2, 3);
	assertEquals(result, first.nSub(second));
    }

    @Test
    public void vectorScalarMultiplyTest() {
	Vector first = new Vector(2, 1, 2, 3);
	Vector result = new Vector(8, 4, 8, 12);
	double scalar = 4;
	assertEquals(first.scalarMultiply(scalar), result);
	first = new Vector(2, 1, 2, 3);
	assertEquals(first.nScalarMultiply(scalar), result);
    }

    @Test
    public void vectorNormTest() {
	Vector first = new Vector(2, 1, 2);
	Vector result = new Vector(2. / 3, 1. / 3, 2. / 3);
	assertEquals(3, first.norm(), DELTA);
	assertEquals(result, first.normalize());
	first = new Vector(2, 1, 2);
	assertEquals(result, first.nNormalize());
    }

    @Test
    public void cosineTest() {
	Vector first = new Vector(2, 1, 2);
	Vector second = new Vector(2, 1, 2);
	assertEquals(1, first.cosine(second), DELTA);
    }

    @Test(expected = IncompatibleOperandException.class)
    public void scalarProductIllegalDimensionsTest() {
	Vector first = new Vector(2, 1, 2);
	Vector second = new Vector(4, 4, 1, 2);
	first.scalarProduct(second);
    }

    @Test
    public void vectorProductTest() {
	Vector first = new Vector(2, 1, 2);
	Vector second = new Vector(3, 4, 1);
	Vector result = new Vector(-7, 4, 5);
	assertEquals(result, first.nVectorProduct(second));
    }

    @Test(expected = IncompatibleOperandException.class)
    public void vectorProductIllegalInputTest() {
	Vector first = new Vector(2, 1, 2);
	Vector second = new Vector(3, 4, 1, 2);
	first.nVectorProduct(second);
    }

    @Test(expected = IncompatibleOperandException.class)
    public void vectorProductIllegalInput2Test() {
	Vector first = new Vector(2, 1, 2, 2);
	Vector second = new Vector(3, 4, 1, 2);
	first.nVectorProduct(second);
    }

    @Test(expected = IncompatibleOperandException.class)
    public void vectorProductIllegalInput3Test() {
	Vector first = new Vector(2, 1, 2, 2);
	Vector second = new Vector(3, 4, 2);
	first.nVectorProduct(second);
    }

    @Test
    public void vectorFromHomogeneusTest() {
	Vector first = new Vector(2, 1, 2, 2);
	Vector result = new Vector(1, 1. / 2, 1);
	assertEquals(result, first.nFromHomogeneus());
    }

    @Test(expected = IncompatibleOperandException.class)
    public void vectorFromHomogeneusIllegalArgumentTest() {
	Vector first = new Vector(2);
	Vector result = new Vector(1, 1. / 2, 1);
	assertEquals(result, first.nFromHomogeneus());
    }

    @Test
    public void toStringTest() {
	Vector first = new Vector(2, 1, 2);
	double a = 2;
	double b = 1;
	assertEquals(String.format("%.3f ", a) + String.format("%.3f ", b)
		+ String.format("%.3f ", a), first.toString());
    }

    @Test(expected = IncompatibleOperandException.class)
    public void addIncompatibleExceptionTest() {
	Vector first = new Vector(2, 2, 3);
	Vector second = new Vector(3, 1, -2, 4);
	Vector result = new Vector(5, 2, 0, 7);
	assertEquals(result, first.add(second));
	first = new Vector(2, 1, 2, 3);
	assertEquals(result, first.nAdd(second));
    }

    @Test(expected = IncompatibleOperandException.class)
    public void subIncompatibleExceptionTest() {
	Vector first = new Vector(2, 2, 3);
	Vector second = new Vector(3, 1, -2, 4);
	Vector result = new Vector(5, 2, 0, 7);
	assertEquals(result, first.sub(second));
	first = new Vector(2, 1, 2, 3);
	assertEquals(result, first.nSub(second));
    }

    @Test(expected = IllegalArgumentException.class)
    public void copyPartIllegalExceptionTest() {
	Vector first = new Vector(2, 2, 3);
	first.copyPart(0);
    }

    @Test
    public void toRowMatrixNotLiveViewTest() {
	Vector first = new Vector(2, 5, 3);
	IMatrix firstMatrix = first.toRowMatrix(false);
	IMatrix result = Matrix.parseSimple("2 5 3");
	assertEquals(result, firstMatrix);
    }

    @Test
    public void toRowMatrixLiveViewTest() {
	Vector first = new Vector(2, 5, 3);
	IMatrix firstMatrix = first.toRowMatrix(true);
	IMatrix result = Matrix.parseSimple("2 5 3");
	assertEquals(result, firstMatrix);
    }

    @Test
    public void toColMatrixNotLiveViewTest() {
	Vector first = new Vector(2, 5, 3);
	IMatrix firstMatrix = first.toColumnMatrix(false);
	IMatrix result = Matrix.parseSimple("2 | 5 | 3");
	assertEquals(result, firstMatrix);
    }

    @Test
    public void toColMatrixLiveViewTest() {
	Vector first = new Vector(2, 5, 3);
	IMatrix firstMatrix = first.toColumnMatrix(true);
	IMatrix result = Matrix.parseSimple("2 | 5 | 3");
	assertEquals(result, firstMatrix);
    }

    @Test
    public void toRowMatrixLiveViewTemperWithTest() {
	Vector first = new Vector(2, 5, 3);
	IMatrix firstMatrix = first.toRowMatrix(true);
	IMatrix result = Matrix.parseSimple("2 6 3");
	firstMatrix.set(0, 1, 6);
	assertEquals(result, firstMatrix);
	assertEquals(6, first.get(1), DELTA);
    }

    @Test
    public void toColMatrixLiveViewTemperWithTest() {
	Vector first = new Vector(2, 5, 3);
	IMatrix firstMatrix = first.toColumnMatrix(true);
	IMatrix result = Matrix.parseSimple("2 | 6 | 3");
	firstMatrix.set(1, 0, 6);
	assertEquals(result, firstMatrix);
	assertEquals(6, first.get(1), DELTA);
    }

    @Test
    public void matrixVectorViewNewInstanceTest() {
	Vector first = new Vector(2, 5, 3);
	IMatrix firstMatrix = first.toColumnMatrix(true);
	assertEquals(Matrix.parseSimple("0 | 0 | 0 | 0"),
		firstMatrix.newInstance(4, 1));
    }

    @Test
    public void matrixVectorViewNewInstanceToRowTest() {
	Vector first = new Vector(2, 5, 3);
	IMatrix firstMatrix = first.toRowMatrix(true);
	assertEquals(Matrix.parseSimple("0 0 0 0"),
		firstMatrix.newInstance(1, 4));
    }

    @Test
    public void matrixVectorViewCopyTest() {
	Vector first = new Vector(2, 5, 3);
	IMatrix firstMatrix = first.toColumnMatrix(true);
	assertEquals(firstMatrix, firstMatrix.copy());
    }
}

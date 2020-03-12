package hr.fer.zemris.linearna.demo;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.Matrix;

/**
 * Program for example 1.2.2. calculates solution to system of equations.
 * 
 * @author Ivan Pavić
 * 
 */
public class Prog3 {
    /**
     * Main method for calculating solution to system of equations.
     * 
     * @param args
     *            none taken
     */
    public static void main(String[] args) {
	IMatrix a = Matrix.parseSimple("3 5 | 2 10");
	IMatrix r = Matrix.parseSimple("2 | 8");
	IMatrix v = a.nInvert().nMultiply(r);
	System.out.println("Rjesenje sustava je: ");
	System.out.println(v);
    }
}

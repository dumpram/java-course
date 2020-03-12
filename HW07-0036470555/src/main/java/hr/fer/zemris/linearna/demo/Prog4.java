/**
 * 
 */
package hr.fer.zemris.linearna.demo;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.Matrix;

/**
 * Program for example 1.2.3. calculates baricentrical coordinates on other way.
 * 
 * @author Ivan Pavić
 * 
 */
public class Prog4 {

    /**
     * @param args
     *            none taken
     */
    public static void main(String[] args) {
	IMatrix a = Matrix.parseSimple("1 5 3 | 0 0 8 | 1 1 1");
	IMatrix r = Matrix.parseSimple("3 | 4 | 1");
	IMatrix v = a.nInvert().nMultiply(r);
	System.out
		.println("Matrica s baricentricnim koordinatama t1, t2, t3: ");
	System.out.println(v);

    }

}

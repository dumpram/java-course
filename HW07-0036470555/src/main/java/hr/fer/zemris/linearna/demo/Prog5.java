package hr.fer.zemris.linearna.demo;

import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.Vector;

import java.util.Scanner;

/**
 * Program 5 from list of examples. Demonstration of reflected vectors.
 * 
 * @author Ivan Pavić
 * 
 */
public class Prog5 {

    /**
     * @param args
     *            none taken
     */
    public static void main(String[] args) {

	Scanner scanner = new Scanner(System.in, "UTF-8");

	System.out.println("Unesite prvi vektor(koji se reflektira): ");

	IVector m = Vector.parseSimple(scanner.nextLine());

	if (m.getDimension() > 3) {
	    scanner.close();
	    throw new IllegalArgumentException("2D or 3D vectors are accepted.");
	}
	m = m.copyPart(3);
	System.out.println("Unesite drugi vektor(prema kojem se reflektira): ");

	IVector n = Vector.parseSimple(scanner.nextLine());

	if (n.getDimension() > 3) {
	    scanner.close();
	    throw new IllegalArgumentException("2D or 3D vectors are accepted.");
	}
	n = n.copyPart(3);
	IVector p = n.scalarMultiply(m.norm() / n.norm() * m.cosine(n)).nSub(m);
	IVector result = m.nAdd(p.scalarMultiply(2));
	System.out.println("Reflektirani vektor je: ");
	System.out.println(result);

	scanner.close();
    }
}

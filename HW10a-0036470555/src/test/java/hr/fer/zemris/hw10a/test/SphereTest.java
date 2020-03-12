package hr.fer.zemris.hw10a.test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import hr.fer.zemris.java.tecaj_06.rays.Point3D;
import hr.fer.zemris.java.tecaj_06.rays.Ray;
import hr.fer.zemris.java.tecaj_06.rays.RayIntersection;
import hr.fer.zemris.java.tecaj_06.rays.Sphere;

import org.junit.Test;

public class SphereTest {

	private static final double DELTA = 1e-10;

	@Test
	public void sphereRayIntesectionTest() {
		Sphere sphere = new Sphere(new Point3D(-2, -2, 2), 5, 1, 1, 1, 0.5,
				0.5, 0.5, 10);
		RayIntersection intersection = sphere
				.findClosestRayIntersection(new Ray(new Point3D(10, 10, 10),
						new Point3D(10, 10, 10).sub(-2, -2, 2).normalize()
								.negate()));
		assertNotNull(intersection);
	}

	@Test
	public void sphereRayNoIntersectionTest() {
		Sphere sphere = new Sphere(new Point3D(-2, -2, 2), 5, 1, 1, 1, 0.5,
				0.5, 0.5, 10);
		RayIntersection intersection = sphere
				.findClosestRayIntersection(new Ray(new Point3D(10, 10, 10),
						new Point3D(10, 10, 10).sub(20, 20, 50).normalize()));
		assertNull(intersection);
	}

	@Test
	public void rayIntersectionGetterTest() {
		Sphere sphere = new Sphere(new Point3D(-2, -2, 2), 5, 1, 1, 1, 0.5,
				0.5, 0.5, 10);
		RayIntersection intersection = sphere
				.findClosestRayIntersection(new Ray(new Point3D(10, 10, 10),
						new Point3D(10, 10, 10).sub(-2, -2, 2).normalize()
								.negate()));
		assertEquals(1, intersection.getKdr(), DELTA);
		assertEquals(1, intersection.getKdg(), DELTA);
		assertEquals(1, intersection.getKdb(), DELTA);
		assertEquals(0.5, intersection.getKrr(), DELTA);
		assertEquals(0.5, intersection.getKrg(), DELTA);
		assertEquals(0.5, intersection.getKrb(), DELTA);
		assertEquals(10, intersection.getKrn(), DELTA);
	}
}

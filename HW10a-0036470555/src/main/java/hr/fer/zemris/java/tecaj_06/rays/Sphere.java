package hr.fer.zemris.java.tecaj_06.rays;
/**
 * Sphere class represents {@link GraphicalObject} which is determined by its
 * center and radius. Other parameters are common for all implementations of
 * {@link GraphicalObject}.
 * 
 * @author Ivan Pavić
 * 
 */
public class Sphere extends GraphicalObject {
	/**
	 * Center of sphere.
	 */
	private final Point3D center;
	/*
	 * Radius of sphere.
	 */
	private final double radius;
	/**
	 * Diffuse component coefficient of red color.
	 */
	private final double kdr;
	/**
	 * Diffuse component coefficient of green color.
	 */
	private final double kdg;
	/**
	 * Diffuse component coefficient of blue color.
	 */
	private final double kdb;
	/**
	 * Reflective component coefficient of red color.
	 */
	private final double krr;
	/**
	 * Reflective component coefficient of green color.
	 */
	private final double krg;
	/**
	 * Reflective component coefficient of blue color.
	 */
	private final double krb;
	/**
	 * Coefficient for surface.
	 */
	private final double krn;
	/**
	 * Sphere constructors takes as argument all parameters as described in
	 * sphere class documentation.
	 * 
	 * @param center
	 *            center of sphere
	 * @param radius
	 *            radius of sphere
	 * @param kdr
	 *            diffuse component coefficient of red color
	 * @param kdg
	 *            diffuse component coefficient of green color
	 * @param kdb
	 *            diffuse component coefficient of blue color
	 * @param krr
	 *            reflective component coefficient of red color
	 * @param krg
	 *            reflective component coefficient of green color
	 * @param krb
	 *            reflective component coefficient of blue color
	 * @param krn
	 *            coefficient for surface
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg,
			double kdb, double krr, double krg, double krb, double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		Point3D start = ray.start;

		Point3D direction = ray.direction;

		double lambda = calcLambda(start, direction);

		if (checkInvalid(lambda)) {
			return null;
		}

		Point3D intersectionPoint = start.add(direction.scalarMultiply(lambda));

		RayIntersection forExport = new RayIntersection(intersectionPoint,
				lambda, start.norm() > radius) {

			@Override
			public Point3D getNormal() {
				return getPoint().sub(center).normalize();
			}

			@Override
			public double getKrr() {
				return krr;
			}

			@Override
			public double getKrn() {
				return krn;
			}

			@Override
			public double getKrg() {
				return krg;
			}

			@Override
			public double getKrb() {
				return krb;
			}

			@Override
			public double getKdr() {
				return kdr;
			}

			@Override
			public double getKdg() {
				return kdg;
			}

			@Override
			public double getKdb() {
				return kdb;
			}
		};
		return forExport;
	}
	/**
	 * Calculates parameter lambda for equation of line in direction of
	 * intersection. As parameters you must provide start of ray and vector od
	 * direction of ray.
	 * 
	 * @param start
	 *            start of ray
	 * @param direction
	 *            direction of ray
	 * @return double value of lambda
	 */
	private double calcLambda(Point3D start, Point3D direction) {
		double a = direction.scalarProduct(direction);
		Point3D diff = start.sub(center);
		double b = diff.scalarMultiply(2).scalarProduct(direction);
		double c = diff.scalarProduct(diff) - radius * radius;

		double lambda1 = (-b + Math.sqrt(b * b - 4 * a * c)) / (2 * a);
		double lambda2 = (-b - Math.sqrt(b * b - 4 * a * c)) / (2 * a);

		Point3D intersectionPoint1 = start.add(direction
				.scalarMultiply(lambda1));
		Point3D intersectionPoint2 = start.add(direction
				.scalarMultiply(lambda2));

		double distance1 = start.sub(intersectionPoint1).norm();
		double distance2 = start.sub(intersectionPoint2).norm();

		if (checkInvalid(lambda1)) {
			return lambda2;
		}
		if (checkInvalid(lambda2)) {
			return lambda1;
		}
		if (Math.abs(distance1 - distance2) < 10e-12) {
			return lambda1;
		} else {
			return lambda2;
		}
	}

	/**
	 * Check if parameter lambda is invalid. Invalid state is: NaN, Infinite or
	 * negative.
	 * 
	 * @param lambda
	 *            given parameter.
	 */
	private boolean checkInvalid(double lambda) {
		if (Double.isNaN(lambda)) {
			return true;
		}
		if (Double.isInfinite(lambda)) {
			return true;
		}
		if (Double.compare(lambda, 0) < 0) {
			return true;
		}
		return false;
	}
}

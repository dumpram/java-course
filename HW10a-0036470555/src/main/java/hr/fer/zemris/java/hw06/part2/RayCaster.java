package hr.fer.zemris.java.hw06.part2;

import hr.fer.zemris.java.tecaj_06.rays.GraphicalObject;
import hr.fer.zemris.java.tecaj_06.rays.IRayTracerProducer;
import hr.fer.zemris.java.tecaj_06.rays.IRayTracerResultObserver;
import hr.fer.zemris.java.tecaj_06.rays.LightSource;
import hr.fer.zemris.java.tecaj_06.rays.Point3D;
import hr.fer.zemris.java.tecaj_06.rays.Ray;
import hr.fer.zemris.java.tecaj_06.rays.RayIntersection;
import hr.fer.zemris.java.tecaj_06.rays.RayTracerViewer;
import hr.fer.zemris.java.tecaj_06.rays.Scene;

import java.util.ArrayList;
import java.util.List;
/**
 * Class is designed for sequential ray-casting. Job of showing picture is
 * delegated to {@link RayTracerViewer}. Class can create IRayTracerProducer
 * which delegates job of calculating to method trace.
 * 
 * @author Ivan Pavić
 * 
 */
public class RayCaster {
	/**
	 * For double comparison.
	 */
	private static final double DELTA = 1e-6;
	/**
	 * Main method.
	 * 
	 * @param args
	 *            non required.
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0),
				new Point3D(0, 0, 0), new Point3D(0, 0, 10), 20, 20);
	}
	/**
	 * Method returns new IRayTracerProducer which calculates elements of
	 * picture.
	 * 
	 * @return new IRayTracerProducer
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {

			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
					double horizontal, double vertical, int width, int height,
					long requestNo, IRayTracerResultObserver observer) {
				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];
				Point3D yAxis = calcYAxis(eye, view, viewUp);
				Point3D xAxis = yAxis.vectorProduct(view.sub(eye).normalize())
						.normalize().scalarMultiply(-1);
				// Point3D zAxis = xAxis.vectorProduct(yAxis);
				Point3D screenCorner = view.sub(
						xAxis.scalarMultiply(horizontal / 2)).add(
						yAxis.scalarMultiply(vertical / 2));
				Scene scene = RayTracerViewer.createPredefinedScene();
				short[] rgb = new short[3];
				int offset = 0;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner.add(
								xAxis.scalarMultiply(horizontal * x
										/ (width - 1))).add(
								yAxis.scalarMultiply(-vertical * y
										/ (height - 1)));
						Ray ray = Ray.fromPoints(eye, screenPoint);
						tracer(scene, ray, rgb);
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				}
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
			/**
			 * Tracer is method designed for ray-tracing. You should provide
			 * scene ray from viewer(eye) and object, reference to RGB array
			 * where colors for given pixel are determined.
			 * 
			 * @param scene
			 *            given scene
			 * @param ray
			 *            given ray
			 * @param rgb
			 *            given array of colors
			 */
			private void tracer(Scene scene, Ray ray, short[] rgb) {
				List<GraphicalObject> objects = new ArrayList<>(
						scene.getObjects());
				double min = Double.MAX_VALUE;
				int minIndex = -1;
				for (GraphicalObject i : objects) {
					RayIntersection current = i.findClosestRayIntersection(ray);
					if (current != null) {
						if (min - current.getDistance() > DELTA) {
							min = current.getDistance();
							minIndex = objects.indexOf(i);
						}
					}
				}
				if (minIndex == -1) {
					rgb[0] = 0;
					rgb[1] = 0;
					rgb[2] = 0;
					return;
				}
				rgb[0] = 15;
				rgb[1] = 15;
				rgb[2] = 15;
				GraphicalObject object = objects.get(minIndex);
				RayIntersection intersection = object
						.findClosestRayIntersection(ray);
				objects.remove(minIndex);
				List<LightSource> sources = scene.getLights();

				for (LightSource i : sources) {
					double distance = i.getPoint().sub(intersection.getPoint())
							.norm();
					Ray ray1 = Ray.fromPoints(i.getPoint(),
							intersection.getPoint());
					double minDistanceBetweenOtherObjectAndSource = calcMinDistanceFromSourceToOtherObject(
							objects, ray1);
					if (distance - minDistanceBetweenOtherObjectAndSource > DELTA) {
						continue;
					}
					determineColor(rgb, i, intersection, ray.start);
				}
			}
			/**
			 * Method determines color of given object-ray intersection by
			 * calculating relation of position of intersection and light
			 * source. Therefore many vectors are calculated and finally color
			 * is determined by Phong's model.
			 * 
			 * @param rgb
			 *            given RGB array containing initial values of pixel
			 *            color
			 * @param i
			 *            given light source
			 * @param intersection
			 *            of object and rye
			 * @param eye
			 *            position of viewer
			 */
			private void determineColor(short[] rgb, LightSource i,
					RayIntersection intersection, Point3D eye) {
				Point3D n = intersection.getNormal().normalize();
				Point3D l = i.getPoint().sub(intersection.getPoint())
						.normalize();
				Point3D r = reflectedPoint(l, n).normalize();
				Point3D v = eye.sub(intersection.getPoint()).normalize();

				double ins2 = Math.pow(Math.max(0, r.scalarProduct(v)),
						intersection.getKrn());
				double ins = Math.max(0, l.scalarProduct(n));

				rgb[0] += i.getR() * intersection.getKdr() * ins;

				rgb[1] += i.getG() * intersection.getKdg() * ins;

				rgb[2] += i.getB() * intersection.getKdb() * ins;

				rgb[0] += i.getR() * intersection.getKrr() * ins2;

				rgb[1] += i.getG() * intersection.getKrg() * ins2;

				rgb[2] += i.getB() * intersection.getKrb() * ins2;
			}
			/**
			 * Method calculates minimal distance between source and object.
			 * 
			 * @param i
			 *            given source
			 * @param objects
			 *            list of objects
			 * @param ray1
			 *            ray between object and source
			 * @return minimal distance
			 */
			private double calcMinDistanceFromSourceToOtherObject(
					List<GraphicalObject> objects, Ray ray1) {
				double min = Double.MAX_VALUE;
				for (GraphicalObject j : objects) {
					RayIntersection intersection = j
							.findClosestRayIntersection(ray1);
					if (intersection != null) {
						double distance = intersection.getDistance();
						if (min - distance > DELTA) {
							min = distance;
						}
					}
				}
				return min;
			}
			/**
			 * Method calculates yAxis vector.
			 * 
			 * @param eye
			 *            position of viewer
			 * @param view
			 *            view position
			 * @param viewUp
			 *            viewUp vector
			 * @return vector in direction of yAxis.
			 */
			private Point3D calcYAxis(Point3D eye, Point3D view, Point3D viewUp) {
				Point3D viewUpNorm = viewUp.normalize();
				Point3D eyeToView = view.sub(eye).normalize();
				double temp = eyeToView.scalarProduct(viewUpNorm);
				return viewUpNorm.sub(eyeToView.scalarMultiply(temp))
						.normalize();
			}
			/**
			 * Calculates reflected vector. Must provide vector toReflect and
			 * vector which is reference for reflection.
			 * 
			 * @param toReflect
			 *            vector which method reflects
			 * @param toWhatReflect
			 *            reference of reflection
			 * @return new Point3D representing reflected vector.
			 */
			private Point3D reflectedPoint(Point3D toReflect,
					Point3D toWhatReflect) {
				Point3D temp = toWhatReflect.scalarMultiply(toReflect
						.scalarProduct(toWhatReflect));
				temp = temp.sub(toReflect);
				temp = temp.scalarMultiply(2);
				return toReflect.add(temp).normalize();
			}

		};
	}
}

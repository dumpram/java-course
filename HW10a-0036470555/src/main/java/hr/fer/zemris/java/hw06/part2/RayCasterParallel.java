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
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * RayCasterParallel is version of {@link RayCaster} with parallelization of
 * calculating picture elements. It is implemented using ForkJoinPool class.
 * 
 * @author Ivan Pavić
 */
public class RayCasterParallel {
	/**
	 * For double comparison.
	 */
	private static final double DELTA = 1e-6;
	/**
	 * Constant threshold.
	 */
	private static final int THRESHOLD = 16;
	/**
	 * Main method for ray caster parallel.
	 * 
	 * @param args
	 *            none required.
	 */
	public static void main(final String[] args) {
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
			public void produce(final Point3D eye, final Point3D view,
					final Point3D viewUp, final double horizontal,
					final double vertical, final int width, final int height,
					final long requestNo,
					final IRayTracerResultObserver observer) {
				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];
				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new RayCastJob(width, height, 0, height - 1,
						horizontal, vertical, eye, view, viewUp, requestNo,
						red, green, blue));
				pool.shutdown();

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}
	/**
	 * Class represents single ray casting job.
	 * 
	 * @author Ivan Pavić
	 * 
	 */
	public static class RayCastJob extends RecursiveAction {
		/**
		 * Serial version.
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * Width of screen.
		 */
		private final int width;
		/**
		 * Height of screen.
		 */
		private final int height;
		/**
		 * Minimum height in recursion.
		 */
		private final int yMin;
		/**
		 * Maximum height in recursion.
		 */
		private final int yMax;
		/**
		 * Horizontal component.
		 */
		private final double horizontal;
		/**
		 * Vertical component.
		 */
		private final double vertical;
		/**
		 * Position of viewer.
		 */
		private final Point3D eye;
		/**
		 * Center of screen.
		 */
		private final Point3D view;
		/**
		 * ViewUp vector.
		 */
		private final Point3D viewUp;
		/**
		 * RequestNo parameter for observer.
		 */
		private final long requestNo;
		/**
		 * Red color component.
		 */
		private final short[] red;
		/**
		 * Green color component.
		 */
		private final short[] green;
		/**
		 * Blue color component.
		 */
		private final short[] blue;

		/**
		 * Constructor for RayCastJob. It is generated from fields. Accepts all
		 * parameters from above.
		 * 
		 * @param width
		 *            given width
		 * @param height
		 *            given height
		 * @param yMin
		 *            given yMin
		 * @param yMax
		 *            given yMax
		 * @param horizontal
		 *            given horizontal component
		 * @param vertical
		 *            given vertical component
		 * @param eye
		 *            given eye point
		 * @param view
		 *            given view point
		 * @param viewUp
		 *            given viewUp point
		 * @param requestNo
		 *            given requestNo number of request
		 * @param red
		 *            given reference to red array
		 * @param green
		 *            given reference to green array
		 * @param blue
		 *            given reference to blue array
		 */
		public RayCastJob(final int width, final int height, final int yMin,
				final int yMax, final double horizontal, final double vertical,
				final Point3D eye, final Point3D view, final Point3D viewUp,
				final long requestNo, final short[] red, final short[] green,
				final short[] blue) {
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.eye = eye;
			this.view = view;
			this.viewUp = viewUp;
			this.requestNo = requestNo;
			this.red = red;
			this.green = green;
			this.blue = blue;
		}

		@Override
		protected final void compute() {
			if (yMax - yMin + 1 <= THRESHOLD) {
				computeDirect();
				return;
			}
			invokeAll(new RayCastJob(width, height, yMin, yMin + (yMax - yMin)
					/ 2, horizontal, vertical, eye, view, viewUp, requestNo,
					red, green, blue), new RayCastJob(width, height, yMin
					+ (yMax - yMin) / 2 + 1, yMax, horizontal, vertical, eye,
					view, viewUp, requestNo, red, green, blue));
		}
		/**
		 * Calculates pixel colors from yMin to yMax.
		 */
		private void computeDirect() {
			Point3D yAxis = calcYAxis(eye, view, viewUp);
			Point3D xAxis = yAxis.vectorProduct(view.sub(eye).normalize())
					.normalize().scalarMultiply(-1);
			// Point3D zAxis = xAxis.vectorProduct(yAxis);
			Point3D screenCorner = view.sub(
					xAxis.scalarMultiply(horizontal / 2)).add(
					yAxis.scalarMultiply(vertical / 2));
			Scene scene = RayTracerViewer.createPredefinedScene();
			short[] rgb = new short[3];
			int offset = yMin * width;
			for (int y = yMin; y <= yMax; y++) {
				for (int x = 0; x < width; x++) {
					Point3D screenPoint = screenCorner.add(
							xAxis.scalarMultiply(horizontal * x / (width - 1)))
							.add(yAxis.scalarMultiply(-vertical * y
									/ (height - 1)));
					Ray ray = Ray.fromPoints(eye, screenPoint);
					tracer(scene, ray, rgb);
					red[offset] = rgb[0] > 255 ? 255 : rgb[0];
					green[offset] = rgb[1] > 255 ? 255 : rgb[1];
					blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
					offset++;
				}
			}
		}
		/**
		 * Tracer is method designed for ray-tracing. You should provide scene
		 * ray from viewer(eye) and object, reference to RGB array where colors
		 * for given pixel are determined.
		 * 
		 * @param scene
		 *            given scene
		 * @param ray
		 *            given ray
		 * @param rgb
		 *            given array of colors
		 */
		private void tracer(final Scene scene, final Ray ray, final short[] rgb) {
			List<GraphicalObject> objects = new ArrayList<>(scene.getObjects());
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
				Ray ray1 = Ray
						.fromPoints(i.getPoint(), intersection.getPoint());
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
		 * calculating relation of position of intersection and light source.
		 * Therefore many vectors are calculated and finally color is determined
		 * by Phong's model.
		 * 
		 * @param rgb
		 *            given RGB array containing initial values of pixel color
		 * @param i
		 *            given light source
		 * @param intersection
		 *            of object and rye
		 * @param eye
		 *            position of viewer
		 */
		private void determineColor(final short[] rgb, final LightSource i,
				final RayIntersection intersection, final Point3D eye) {
			Point3D n = intersection.getNormal().normalize();
			Point3D l = i.getPoint().sub(intersection.getPoint()).normalize();
			Point3D r = reflectedPoint(l, n).normalize();
			Point3D v = eye.sub(intersection.getPoint()).normalize();

			double ins2 = Math.pow(Math.max(0, r.scalarProduct(v)),
					intersection.getKrn());
			double ins = Math.max(0, l.scalarProduct(n));
			/*
			 * Diffuse component.
			 */
			rgb[0] += i.getR() * intersection.getKdr() * ins;

			rgb[1] += i.getG() * intersection.getKdg() * ins;

			rgb[2] += i.getB() * intersection.getKdb() * ins;
			/*
			 * Reflective component.
			 */
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
				final List<GraphicalObject> objects, final Ray ray1) {
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
		private Point3D calcYAxis(final Point3D eye, final Point3D view,
				final Point3D viewUp) {
			Point3D viewUpNorm = viewUp.normalize();
			Point3D eyeToView = view.sub(eye).normalize();
			double temp = eyeToView.scalarProduct(viewUpNorm);
			return viewUpNorm.sub(eyeToView.scalarMultiply(temp)).normalize();
		}
		/**
		 * Calculates reflected vector. Must provide vector toReflect and vector
		 * which is reference for reflection.
		 * 
		 * @param toReflect
		 *            vector which method reflects
		 * @param toWhatReflect
		 *            reference of reflection
		 * @return new Point3D representing reflected vector.
		 */
		private Point3D reflectedPoint(final Point3D toReflect,
				final Point3D toWhatReflect) {
			Point3D temp = toWhatReflect.scalarMultiply(toReflect
					.scalarProduct(toWhatReflect));
			temp = temp.sub(toReflect);
			temp = temp.scalarMultiply(2);
			return toReflect.add(temp).normalize();
		}

	};
}

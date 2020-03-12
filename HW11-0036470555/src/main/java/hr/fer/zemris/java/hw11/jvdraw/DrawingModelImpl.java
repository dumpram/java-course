package hr.fer.zemris.java.hw11.jvdraw;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class DrawingModelImpl implements DrawingModel {

	private final List<GeometricalObject> objects = new ArrayList<>();

	private final List<DrawingModelListener> listeners = new ArrayList<>();

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		objects.add(object);
		for (DrawingModelListener i : listeners) {
			i.objectsAdded(this, getSize() - 1, getSize() - 1);
		}
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}

	@Override
	public void removeObject(int index) {
		objects.remove(index);
		for (DrawingModelListener i : listeners) {
			i.objectsRemoved(this, index, index);
		}
	}

	@Override
	public void clear() {
		objects.clear();
		for (DrawingModelListener i : listeners) {
			i.objectsChanged(this, 0, getSize());
		}
	}

	@Override
	public void update() {
		for (DrawingModelListener i : listeners) {
			i.objectsChanged(this, 0, getSize());
		}
	}
	/**
	 * Getter for BoundingBox of current DrawingModel.
	 * 
	 * @param canvas
	 *            given canvas rectangle
	 * @return minimal BoundingBox with all elements.
	 */
	public Rectangle getBoundingBox(Rectangle canvas) {

		int minX = (int) canvas.getMinX();
		int minY = (int) canvas.getMinY();
		int maxX = (int) canvas.getMaxX();
		int maxY = (int) canvas.getMaxY();

		for (int i = 0; i < objects.size(); i++) {
			Rectangle temp = objects.get(i).getBounds();
			minX = (int) Math.min(minX, temp.getMinX());
			minY = (int) Math.min(minY, temp.getMinX());
			maxX = (int) Math.max(maxX, temp.getMaxX());
			maxY = (int) Math.max(maxY, temp.getMaxY());
		}
		return new Rectangle(minX, minY, maxX - minX, maxY - minY);
	}
}

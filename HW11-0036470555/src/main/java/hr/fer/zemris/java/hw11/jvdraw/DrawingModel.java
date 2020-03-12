package hr.fer.zemris.java.hw11.jvdraw;
/**
 * Interface defines drawing model for JVDraw application.
 * 
 * @author Ivan Pavić
 * 
 */
public interface DrawingModel {
	/**
	 * Returns number of drawn elements.
	 * 
	 * @return number of drawn elements
	 */
	int getSize();
	/**
	 * Returns object with given index.
	 * 
	 * @param index
	 *            given index
	 * @return {@link GeometricalObject} with given index
	 */
	GeometricalObject getObject(int index);
	/**
	 * Adds {@link GeometricalObject} to model.
	 * 
	 * @param object
	 *            given {@link GeometricalObject}
	 */
	void add(GeometricalObject object);
	/**
	 * Adds listener of DrawingModel to list of listeners.
	 * 
	 * @param l
	 *            given {@link DrawingModelListener}
	 */
	void addDrawingModelListener(DrawingModelListener l);
	/**
	 * Removes given {@link DrawingModelListener} from list of listeners.
	 * 
	 * @param l
	 *            given {@link DrawingModelListener}
	 */
	void removeDrawingModelListener(DrawingModelListener l);
	/**
	 * Removes object from list of drawn objects. Notifies all listeners.
	 * 
	 * @param index
	 *            index of {@link GeometricalObject}
	 */
	void removeObject(int index);
	/**
	 * Removes all {@link GeometricalObject}.
	 */
	void clear();
	/**
	 * Notifes all listeners beacause change occured.
	 */
	void update();
}

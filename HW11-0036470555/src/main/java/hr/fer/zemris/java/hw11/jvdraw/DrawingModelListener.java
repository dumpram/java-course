package hr.fer.zemris.java.hw11.jvdraw;
/**
 * Interface defines drawing model listener. Changes are invoked after adding
 * removing or just changing the objects.
 * 
 * @author Ivan Pavić
 * 
 */
public interface DrawingModelListener {
	/**
	 * Invoked when objects are added to source drawing model.
	 * 
	 * @param source
	 *            source model
	 * @param index0
	 *            starting index of change
	 * @param index1
	 *            ending index of change
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);
	/**
	 * Invoked when objects are removed to source drawing model.
	 * 
	 * @param source
	 *            source model
	 * @param index0
	 *            starting index of change
	 * @param index1
	 *            ending index of change
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);
	/**
	 * Invoked when objects are changed in source drawing model.
	 * 
	 * @param source
	 *            source model
	 * @param index0
	 *            starting index of change
	 * @param index1
	 *            ending index of change
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);

}

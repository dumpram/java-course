package hr.fer.zemris.java.hw11.jvdraw;
/**
 * {@link ImageChangedListener} is in face {@link DrawingModelListener} which
 * contains information about state of picture. Initially is true because state
 * is changed and document can be saved. Saving document changes isChanged flag
 * to false.
 * 
 * @author Ivan Pavić
 * 
 */
public class ImageChangedListener implements DrawingModelListener {
	/**
	 * Flag which indicates state of document.
	 */
	boolean isChanged = true;
	/**
	 * Constructor accepts {@link DrawingModel}.
	 * 
	 * @param model
	 *            given {@link DrawingModel} which is listened
	 */
	public ImageChangedListener(DrawingModel model) {
		model.addDrawingModelListener(this);
	}
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		isChanged = true;
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		isChanged = true;
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		isChanged = true;
	}

	/**
	 * Getter for image state.
	 * 
	 * @return the isChanged
	 */
	public boolean isChanged() {
		return isChanged;
	}
	/**
	 * Setter for image state.
	 * 
	 * @param isChanged
	 *            the isChanged to set
	 */
	public void setChanged(boolean isChanged) {
		this.isChanged = isChanged;
	}

}

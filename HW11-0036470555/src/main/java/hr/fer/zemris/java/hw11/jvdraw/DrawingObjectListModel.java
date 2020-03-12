package hr.fer.zemris.java.hw11.jvdraw;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
/**
 * Represents model of list for {@link DrawingModel}. It is in fact
 * {@link DrawingModelListener} which has private instance of drawing model and
 * when objects are changed in list it fires notification to all listeners via
 * {@link DrawingModel}.
 * 
 * @author Ivan Pavić
 * 
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Private instance of drawingModel.
	 */
	private final DrawingModel drawingModel;
	/**
	 * Private list of listeners.
	 */
	private List<ListDataListener> listListeners = new ArrayList<>();
	/**
	 * Constructor accepts {@link DrawingModel} as argument.
	 * 
	 * @param drawingModel
	 */
	public DrawingObjectListModel(DrawingModel drawingModel) {
		this.drawingModel = drawingModel;
	}

	@Override
	public int getSize() {
		return drawingModel.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return drawingModel.getObject(index);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		ListDataEvent listDataEvent = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index0, index1);
		for (ListDataListener l : listListeners) {
			l.intervalAdded(listDataEvent);
		}
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		ListDataEvent listDataEvent = new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, index0, index1);
		for (ListDataListener l : listListeners) {
			l.intervalRemoved(listDataEvent);
		}
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		ListDataEvent listDataEvent = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, index0, index1);
		for (ListDataListener l : listListeners) {
			l.contentsChanged(listDataEvent);
		}
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		if (!listListeners.contains(l)) {
			listListeners = new ArrayList<>(listListeners);
			listListeners.add(l);
		}
	}
}

package hr.fer.zemris.java.hw11.jvdraw;

import java.awt.Color;

/**
 * Listener for color change.
 * 
 * @author Ivan Pavić
 * 
 */
public interface ColorChangeListener {
	/**
	 * Invoked after new color is selected.
	 * 
	 * @param source
	 *            of change
	 * @param oldColor
	 *            old color
	 * @param newColor
	 *            new color
	 */
	void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}

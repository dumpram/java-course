package hr.fer.zemris.java.hw11.jvdraw;

import java.awt.Color;

import javax.swing.JLabel;
/**
 * Shows currently selected colors background and foreground. Shows all 3
 * components of color Red, Green, and blue.
 * 
 * @author Ivan Pavić
 * 
 */
public class JColorPreview extends JLabel implements ColorChangeListener {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Foreground provider.
	 */
	private final IColorProvider foreground;
	/**
	 * Background color provider.
	 */
	private final IColorProvider background;
	/**
	 * Constructor accepts two {@link IColorProvider} instances.
	 * 
	 * @param foreground
	 *            foreground color provider
	 * @param background
	 *            background color provider
	 */
	public JColorPreview(IColorProvider foreground, IColorProvider background) {
		this.foreground = foreground;
		this.background = background;
		String preview = getPreview(foreground.getCurrentColor(), background.getCurrentColor());
		setText(preview);
	}
	/**
	 * Getter for preview of colors.
	 * 
	 * @param foreground
	 *            foreground color
	 * @param background
	 *            background color
	 * @return String of preview
	 */
	private String getPreview(Color foreground, Color background) {
		String preview = "Foreground: " + getStringColorComponents(foreground);
		preview += ", background: " + getStringColorComponents(background);
		return preview;
	}
	/**
	 * Gets color components as string.
	 * 
	 * @param currentColor
	 *            given color;
	 * @return string representing color componetnts
	 */
	private String getStringColorComponents(Color currentColor) {
		return "(" + currentColor.getRed() + ", " + currentColor.getGreen() + ", " + currentColor.getBlue() + ")";
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		setText(getPreview(foreground.getCurrentColor(), background.getCurrentColor()));
	}

}

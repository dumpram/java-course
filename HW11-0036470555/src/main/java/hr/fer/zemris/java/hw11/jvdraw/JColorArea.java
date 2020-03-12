package hr.fer.zemris.java.hw11.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;
/**
 * Represents tool which enables changing colors in JVDraw.
 * 
 * @author Ivan Pavić
 * 
 */
public class JColorArea extends JComponent implements IColorProvider {
	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Private list of listeners.
	 */
	private final List<ColorChangeListener> listeners = new ArrayList<>();
	/**
	 * Selected color for JColorArea.
	 */
	private Color selectedColor;
	/**
	 * Constructor accepts one argument. It is initial color.
	 * 
	 * @param initialColor
	 *            given initial color
	 */
	public JColorArea(Color initialColor) {
		selectedColor = initialColor;
		setBackground(selectedColor);
		listeners.add(new ColorChangeListener() {

			@Override
			public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
				setBackground(source.getCurrentColor());
			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(JColorArea.this, "Odabir boje", selectedColor);
				if (newColor != null) {
					selectedColor = newColor;
				}
				fire();
			}
		});
	}
	/**
	 * Notifies all listeners.
	 */
	protected void fire() {

		for (ColorChangeListener i : listeners) {
			i.newColorSelected(this, selectedColor, selectedColor);
		}
	}
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}
	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}
	/**
	 * Adds given color listener to list of color listeners.
	 * 
	 * @param listener
	 *            given listener
	 */
	public void addColorChangeListener(ColorChangeListener listener) {
		listeners.add(listener);
	}
	/**
	 * Removes color change listener from list of listeners.
	 * 
	 * @param listener
	 *            given color change listener
	 */
	public void removeColorChangeListener(ColorChangeListener listener) {
		listeners.remove(listener);
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(selectedColor);
		g.fillRect(0, 0, getPreferredSize().width, getPreferredSize().height);
	}

}

package hr.fer.zemris.java.hw11.jvdraw;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JFrame;
/**
 * Class represents abstract geometric object.
 * 
 * @author Ivan Pavić
 * 
 */
public abstract class GeometricalObject extends JComponent {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected abstract void paintComponent(Graphics g);
	/**
	 * Creates dialog in given {@link JFrame} with properties which can be
	 * changed.
	 * 
	 * @param frame
	 *            given frame
	 */
	public abstract void showChangeOptions(JFrame frame);

}

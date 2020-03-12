package hr.fer.zemris.java.hw11.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * Class represents line which has starting and ending point. Extends
 * {@link GeometricalObject}.
 * 
 * @author Ivan Pavić
 * 
 */
public class Line extends GeometricalObject {
	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Starting x coordinate of line.
	 */
	private int startX;
	/**
	 * Starting y coordinate of line.
	 */
	private int startY;
	/**
	 * Ending x coordinate of line.
	 */
	private int endX;
	/**
	 * Ending y coordinate of line.
	 */
	private int endY;
	/**
	 * Constructor accepts 4 arguments.
	 * 
	 * @param startX
	 *            starting x coordinate
	 * @param startY
	 *            starting y coordinate
	 * @param endX
	 *            ending x coordinate
	 * @param endY
	 *            ending y coordinate
	 */
	public Line(int startX, int startY, int endX, int endY) {
		super();
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(getForeground());
		g.drawLine(startX, startY, endX, endY);
	}

	/**
	 * Getter for starting X coordinate.
	 * 
	 * @return the startX
	 */
	public int getStartX() {
		return startX;
	}

	/**
	 * Setter for starting X coordinate.
	 * 
	 * @param startX
	 *            the startX to set
	 */
	public void setStartX(int startX) {
		this.startX = startX;
	}

	/**
	 * Getter for starting Y coordinate.
	 * 
	 * @return the startY
	 */
	public int getStartY() {
		return startY;
	}

	/**
	 * Setter for starting Y coordinate.
	 * 
	 * @param startY
	 *            the startY to set
	 */
	public void setStartY(int startY) {
		this.startY = startY;
	}

	/**
	 * Getter for ending X coordinate.
	 * 
	 * @return the endX
	 */
	public int getEndX() {
		return endX;
	}

	/**
	 * Setter for ending X coordinate.
	 * 
	 * @param endX
	 *            the endX to set
	 */
	public void setEndX(int endX) {
		this.endX = endX;
	}

	/**
	 * Getter for ending Y coordinate.
	 * 
	 * @return the endY
	 */
	public int getEndY() {
		return endY;
	}

	/**
	 * Setter for ending Y coordinate.
	 * 
	 * @param endY
	 *            the endY to set
	 */
	public void setEndY(int endY) {
		this.endY = endY;
	}

	@Override
	public String toString() {
		Color color = getForeground();
		return "LINE " + startX + " " + startY + " " + endX + " " + endY + " " + color.getRed() + " "
				+ color.getGreen() + " " + color.getBlue();
	}
	@Override
	public void showChangeOptions(JFrame frame) {
		JPanel panel = new JPanel(new GridLayout(3, 3));
		panel.setPreferredSize(new Dimension(200, 100));
		int colorHex = getForeground().getRGB();
		JTextField sX = new JTextField(Integer.toString(startX));
		JTextField sY = new JTextField(Integer.toString(startY));
		JTextField eX = new JTextField(Integer.toString(endX));
		JTextField eY = new JTextField(Integer.toString(endY));
		JTextField color = new JTextField(Integer.toHexString(colorHex).toUpperCase());
		panel.add(new JLabel("Start point"));
		panel.add(sX);
		panel.add(sY);
		panel.add(new JLabel("End point"));
		panel.add(eX);
		panel.add(eY);
		panel.add(new JLabel("Color"));
		panel.add(color);
		int rez = JOptionPane
				.showConfirmDialog(frame, panel, "Promijeni postavke linije", JOptionPane.OK_CANCEL_OPTION);
		if (rez == JOptionPane.OK_OPTION) {
			try {
				startX = Integer.parseInt(sX.getText());
			} catch (Exception wronginputignorable) {
			}
			try {
				startY = Integer.parseInt(sY.getText());
			} catch (Exception wronginputignorable) {
			}
			try {
				endX = Integer.parseInt(eX.getText());
			} catch (Exception wronginputignorable) {
			}
			try {
				endY = Integer.parseInt(eY.getText());
			} catch (Exception wronginputignorable) {
			}
			try {
				colorHex = Integer.parseInt(color.getText(), 16);
			} catch (Exception wronginputignorable) {
			}
			setForeground(new Color(colorHex));
		}
	}
}

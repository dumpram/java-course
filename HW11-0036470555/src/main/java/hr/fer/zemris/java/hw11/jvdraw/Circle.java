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
 * Circle class represents circle {@link GeometricalObject}.
 * 
 * @author Ivan Pavić
 * 
 */
public class Circle extends GeometricalObject {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * X coordinate of center.
	 */
	protected int centerX;
	/**
	 * Y coordinate of center.
	 */
	protected int centerY;
	/**
	 * Radius of circle.
	 */
	protected double radius;

	/**
	 * Constructor accepts 3 arguments.
	 * 
	 * @param centerX
	 *            x coordinate of center
	 * @param centerY
	 *            y coordinate of center
	 * @param d
	 *            radius of circle
	 */
	public Circle(int centerX, int centerY, double d) {
		super();
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = d;
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(getForeground());

		g.drawOval((int) (centerX - radius), (int) (centerY - radius), (int) (2 * radius), (int) (2 * radius));
	}

	@Override
	public String toString() {
		Color foreground = getForeground();
		return "CIRCLE" + " " + centerX + " " + centerY + " " + radius + " " + foreground.getRed() + " "
				+ foreground.getGreen() + " " + foreground.getBlue();
	}

	@Override
	public void showChangeOptions(JFrame frame) {
		JPanel panel = new JPanel(new GridLayout(3, 3));
		panel.setPreferredSize(new Dimension(200, 100));
		int colorHex = getForeground().getRGB();
		JTextField sX = new JTextField(Integer.toString(centerX));
		JTextField sY = new JTextField(Integer.toString(centerY));
		JTextField r = new JTextField(Double.toString(radius));
		JTextField color = new JTextField(Integer.toHexString(colorHex).toUpperCase());
		panel.add(new JLabel("Center"));
		panel.add(sX);
		panel.add(sY);
		panel.add(new JLabel("Radius"));
		panel.add(r);
		panel.add(new JLabel());
		panel.add(new JLabel("Color"));
		panel.add(color);
		int rez = JOptionPane.showConfirmDialog(frame, panel, "Promijeni postavke kruga", JOptionPane.OK_CANCEL_OPTION);
		if (rez == JOptionPane.OK_OPTION) {
			try {
				centerX = Integer.parseInt(sX.getText());
			} catch (Exception wronginputignorable) {
			}
			try {
				centerY = Integer.parseInt(sY.getText());
			} catch (Exception wronginputignorable) {
			}
			try {
				radius = Double.parseDouble(r.getText());
			} catch (Exception wronginputignorable) {
			}
			try {
				colorHex = Integer.parseInt(color.getText(), 16);
			} catch (Exception wronginputignorable) {
			}
			setForeground(new Color(colorHex));
		}
	}

	/**
	 * Getter for X coordinate of center.
	 * 
	 * @return the centerX
	 */
	public int getCenterX() {
		return centerX;
	}

	/**
	 * Setter for X coordinate of center.
	 * 
	 * @param centerX
	 *            the centerX to set
	 */
	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	/**
	 * Getter for Y coordinate of center.
	 * 
	 * @return the centerY
	 */
	public int getCenterY() {
		return centerY;
	}

	/**
	 * Setter for Y coordinate of center.
	 * 
	 * @param centerY
	 *            the centerY to set
	 */
	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	/**
	 * Getter for radius.
	 * 
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Setter for radius.
	 * 
	 * @param radius
	 *            the radius to set
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}
}

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

public class FilledCircle extends Circle {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Creates filled circle.
	 * 
	 * @param centerX
	 *            x coordinate of center of circle
	 * @param centerY
	 *            y coordinate of center of circle
	 * @param d
	 *            radius of circle
	 */
	public FilledCircle(int centerX, int centerY, double d) {
		super(centerX, centerY, d);
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillOval((int) (centerX - radius), (int) (centerY - radius), (int) (2 * radius), (int) (2 * radius));
		g.setColor(getForeground());
		g.drawOval((int) (centerX - radius), (int) (centerY - radius), (int) (2 * radius), (int) (2 * radius));
	}

	@Override
	public String toString() {
		Color background = getBackground();
		Color foreground = getForeground();

		return "FCIRCLE" + " " + centerX + " " + centerY + " " + radius + " " + foreground.getRed() + " "
				+ foreground.getGreen() + " " + foreground.getBlue() + " " + background.getRed() + " "
				+ background.getGreen() + " " + background.getBlue();
	}

	@Override
	public void showChangeOptions(JFrame frame) {
		JPanel panel = new JPanel(new GridLayout(4, 3));
		panel.setPreferredSize(new Dimension(250, 100));
		int colorForeHex = getForeground().getRGB();
		int colorBackHex = getBackground().getRGB();
		JTextField sX = new JTextField(Integer.toString(centerX));
		JTextField sY = new JTextField(Integer.toString(centerY));
		JTextField r = new JTextField(Double.toString(radius));
		JTextField colorF = new JTextField(Integer.toHexString(colorForeHex).toUpperCase());
		JTextField colorB = new JTextField(Integer.toHexString(colorBackHex).toUpperCase());
		panel.add(new JLabel("Center"));
		panel.add(sX);
		panel.add(sY);
		panel.add(new JLabel("Radius"));
		panel.add(r);
		panel.add(new JLabel());
		panel.add(new JLabel("Foreground"));
		panel.add(colorF);
		panel.add(new JLabel());
		panel.add(new JLabel("Background"));
		panel.add(colorB);
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
				colorForeHex = Integer.parseInt(colorF.getText(), 16);
			} catch (Exception wronginputignorable) {
			}
			try {
				colorBackHex = Integer.parseInt(colorB.getText(), 16);
			} catch (Exception wronginputignorable) {
			}
			setForeground(new Color(colorForeHex));
			setBackground(new Color(colorBackHex));
		}
	}
}

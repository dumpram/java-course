import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UserPanel extends JPanel {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 1L;
	private final Image image;

	public UserPanel(Image image) {
		this.image = image;
		JLabel label = new JLabel("Jabuke su zdrave (ili tako barem tvrde)");
		add(label);
		label.setBorder(BorderFactory.createLineBorder(Color.red));
	}

	@Override
	protected void paintComponent(Graphics g) {
		int height = image.getHeight(null);
		int width = image.getWidth(null);
		g.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), null);

		for (int x = 0; x < getParent().getWidth(); x += height) {
			for (int y = 0; y < getParent().getWidth(); y += width) {
				g.drawImage(image, x, y, image.getHeight(null), image.getWidth(null), null);
			}

		}
	}
}

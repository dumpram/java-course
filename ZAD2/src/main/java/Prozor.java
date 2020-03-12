import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Prozor extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Prozor() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initGUI();
	}

	private void initGUI() {
		Image image = null;
		try {
			image = ImageIO.read(new File("jabuke.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JPanel panel = new UserPanel(image);
		add(panel);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new Prozor().setVisible(true);
			}
		});
	}
}

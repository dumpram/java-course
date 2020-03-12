package servlets;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/slika")
public class SlikaServlet extends HttpServlet {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");

		int size = Integer.parseInt(req.getParameter("size"));

		String poruka = req.getParameter("msg");

		String dim = req.getParameter("dim");

		String[] parts = dim.split("x");

		int width = Integer.parseInt(parts[0]);

		int height = Integer.parseInt(parts[1]);

		BufferedImage bim = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = bim.createGraphics();
		// g.setColor(Color.red);
		// g.fillRect(0, 0, 300, 300);
		// g.setColor(Color.BLACK);
		// g.fillOval(0, 0, 300, 300);
		// g.setColor(Color.white);
		if (poruka != null && !poruka.isEmpty()) {
			g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, size));
			int textSize = g.getFontMetrics().getFont().getSize();
			g.drawString(poruka, width - textSize * poruka.length() / 2, height / 2);
		}
		g.dispose();
		ImageIO.write(bim, "png", resp.getOutputStream());
	}
}

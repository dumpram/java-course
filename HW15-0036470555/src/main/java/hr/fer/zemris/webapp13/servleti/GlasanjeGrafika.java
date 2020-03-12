package hr.fer.zemris.webapp13.servleti;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.Poll;
import hr.fer.zemris.java.tecaj_13.model.PollOption;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
/**
 * Servlet is used for creating graphic charts of results in voting. It can
 * create whole bunch of graphics and it is very optimized.
 * 
 * @author Ivan Pavić
 * 
 */
@WebServlet(name = "GlasanjeGrafika", urlPatterns = {"/glasanje-grafika"})
public class GlasanjeGrafika extends HttpServlet {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Poll poll = (Poll) req.getSession().getAttribute("poll");
		long pollID = poll.getId();
		List<PollOption> list = DAOProvider.getDao().dohvatiUnose(pollID);
		// This will create the dataset
		PieDataset dataset = createDataset(list);
		// based on the dataset we create the chart
		JFreeChart chart = createChart(dataset, "Rezultati glasanja");
		// we put the chart into a panel
		resp.setContentType("image/png");
		BufferedImage bim = chart.createBufferedImage(300, 300);
		Graphics2D g = bim.createGraphics();
		g.dispose();
		ImageIO.write(bim, "png", resp.getOutputStream());
	}

	/**
	 * Creates a chart from given data set.
	 * 
	 * @param dataset
	 *            given pie dataset
	 * @param title
	 *            title for chart
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createPieChart3D(title, // chart title
				dataset, // data
				true, // include legend
				true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;
	}

	/**
	 * Creates a sample data set.
	 * 
	 * @param defFileName
	 *            definition fileName
	 * @param rezFileName
	 *            results fileName
	 * @throws IOException
	 *             if error while reading occurs
	 */
	private PieDataset createDataset(List<PollOption> list) throws IOException {
		DefaultPieDataset result = new DefaultPieDataset();
		for (PollOption i : list) {
			result.setValue(i.getOptionTitle(), i.getVotesCount());
		}
		return result;
	}
}

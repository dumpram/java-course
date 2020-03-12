package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/**
 * Servlet creates excel document with results of voting. Initiates downloading
 * of excell file when invoked.
 * 
 * @author Ivan Pavić
 * 
 */
@WebServlet(name = "GlasanjeXLS", urlPatterns = {"/glasanje-xls"})
public class GlasanjeXLSServlet extends HttpServlet {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("application/vnd.ms-excel");

		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet();

		String rezFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		String defFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");

		List<BandInfo> bandList = Glasanje.ucitajIzDatoteke(true, defFileName, rezFileName);

		HSSFRow firstRow = sheet.createRow(0);
		firstRow.createCell(0).setCellValue("Bendovi");
		firstRow.createCell(1).setCellValue("Glasovi");
		int rowCounter = 1;

		for (BandInfo i : bandList) {
			HSSFRow row = sheet.createRow(rowCounter);
			row.createCell(0).setCellValue(i.getBandName());
			row.createCell(1).setCellValue(i.getVoteCount());
			rowCounter++;
		}
		hwb.write(resp.getOutputStream());
	}

}

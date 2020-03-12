package hr.fer.zemris.webapp13.servleti;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.Poll;
import hr.fer.zemris.java.tecaj_13.model.PollOption;

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

		Poll poll = (Poll) req.getSession().getAttribute("poll");
		long pollID = poll.getId();
		List<PollOption> list = DAOProvider.getDao().dohvatiUnose(pollID);

		HSSFRow firstRow = sheet.createRow(0);
		firstRow.createCell(0).setCellValue("Bendovi");
		firstRow.createCell(1).setCellValue("Glasovi");
		int rowCounter = 1;

		for (PollOption i : list) {
			HSSFRow row = sheet.createRow(rowCounter);
			row.createCell(0).setCellValue(i.getOptionTitle());
			row.createCell(1).setCellValue(i.getVotesCount());
			rowCounter++;
		}
		hwb.write(resp.getOutputStream());
	}

}

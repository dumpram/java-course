package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/**
 * Powers servlet creates data for excel file and initiates downloading when
 * invoked.
 * 
 * @author Ivan Pavić
 * 
 */
public class Powers extends HttpServlet {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a = req.getParameter("a");
		String b = req.getParameter("b");
		String n = req.getParameter("n");
		resp.setContentType("application/vnd.ms-excel");
		if (invalidArgument(a, -100, 100)) {
			req.getSession().setAttribute("error", "Invalid argument a. Expected interval: [-100, 100]");
			req.getRequestDispatcher("WEB-INF/pages/invalidArgument.jsp").forward(req, resp);
			return;
		}
		if (invalidArgument(b, -100, 100)) {
			req.getSession().setAttribute("error", "Invalid argument b. Expected interval: [-100, 100]");
			req.getRequestDispatcher("WEB-INF/pages/invalidArgument.jsp").forward(req, resp);
			return;
		}
		if (invalidArgument(n, 1, 5)) {
			req.getSession().setAttribute("error", "Invalid argument n. Expected interval: [1, 5]");
			req.getRequestDispatcher("WEB-INF/pages/invalidArgument.jsp").forward(req, resp);
			return;
		}
		int first = Integer.parseInt(a);
		int second = Integer.parseInt(b);
		int num = Integer.parseInt(n);

		HSSFWorkbook hwb = new HSSFWorkbook();
		for (int i = 0; i < num; i++) {
			HSSFSheet sheet = hwb.createSheet((i + 1) + ". sheet");
			for (int j = first; j < second; j++) {
				HSSFRow row = sheet.createRow(j - first);
				row.createCell(0).setCellValue(j);
				row.createCell(1).setCellValue(Math.pow(j, i + 1));
			}
		}
		hwb.write(resp.getOutputStream());
	}
	/**
	 * Checks if given argument is invalid. Argument is considered invalid if
	 * parameter is out of proportions i and j.
	 * 
	 * @param n
	 *            given argument
	 * @param i
	 *            given lower bound
	 * @param j
	 *            given higher bound
	 * @return true if is invalid, false otherwise
	 */
	private boolean invalidArgument(String n, int i, int j) {
		int param;
		try {
			param = Integer.parseInt(n);
		} catch (NumberFormatException e) {
			return true;
		}
		if (param < i || param > j) {
			return true;
		}
		return false;
	}
}

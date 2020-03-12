package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Squares creates data for table of squares from given parameters a and b.
 * Dispatches request to appropriate Java Server Page.
 * 
 * @author Ivan Pavić
 * 
 */
public class Squares extends HttpServlet {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int a = 0;

		int b = 20;

		String aTemp = req.getParameter("a");
		String bTemp = req.getParameter("b");

		if (aTemp != null && !aTemp.isEmpty()) {
			a = Integer.parseInt(aTemp);
		}
		if (bTemp != null && !aTemp.isEmpty()) {
			b = Integer.parseInt(bTemp);
		}
		if (a > b) {
			int t = a;
			a = b;
			b = t;
		}
		if (b > a + 20) {
			b = a + 20;
		}
		Map<Integer, Integer> kvadrati = new LinkedHashMap<>();
		for (int i = a; i < b; i++) {
			kvadrati.put(i, i * i);
		}
		req.setAttribute("kvadrati", kvadrati);
		req.getRequestDispatcher("/WEB-INF/pages/ispis.jsp").forward(req, resp);
	}

}

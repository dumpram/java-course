package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet for setting color. Dispatches request to appropriate Java Server
 * Page. Available colors are: cyan, white, red, green.
 * 
 * @author Ivan Pavić
 * 
 */
public class SetColor extends HttpServlet {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Map of colors.
	 */
	private static Map<String, String> colors = new HashMap<>();

	static {
		colors.put("cyan", "CYAN");
		colors.put("white", "WHITE");
		colors.put("red", "RED");
		colors.put("green", "GREEN");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String col = req.getParameter("col");
		req.getSession().setAttribute("pickedBgCol", colors.get(col));
		req.getRequestDispatcher("colors.jsp").forward(req, resp);
	}
}

package servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Listener for application start. Sets attribute "app-info" in servlet context.
 * 
 * @author Ivan Pavić
 * 
 */
@WebServlet("/info")
public class AppInfo extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String timeStamp = new SimpleDateFormat("(yyyy MM dd) HH : mm : ss").format(Calendar.getInstance().getTime());
		String[] parts = timeStamp.split(" ");
		int seconds = Integer.parseInt(parts[parts.length - 1]);
		if (seconds % 2 == 1) {
			req.setAttribute("color", "red");
		} else {
			req.setAttribute("color", "blue");
		}
		req.setAttribute("time", timeStamp);
		req.getRequestDispatcher("/WEB-INF/pages/app-info.jsp").forward(req, resp);
	}

}

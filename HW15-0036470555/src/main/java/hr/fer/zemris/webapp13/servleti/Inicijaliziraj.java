package hr.fer.zemris.webapp13.servleti;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.dao.sql.SQLConnectionProvider;
import hr.fer.zemris.java.tecaj_13.model.Poll;
import hr.fer.zemris.java.tecaj_13.model.PollOption;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet initializes database.
 * 
 * @author Ivan Pavić
 * 
 */
@WebServlet("/init")
public class Inicijaliziraj extends HttpServlet {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String optionsFileName = req.getServletContext().getRealPath("/WEB-INF/polls-options.txt");
		String pollsFileName = req.getServletContext().getRealPath("/WEB-INF/polls.txt");
		List<PollOption> pollOptions = null;
		try {
			pollOptions = Glasanje.ucitajOpcijeIzDatoteke(optionsFileName);
		} catch (Exception e) {
			req.getSession().setAttribute("error", "Pogreška prilikom čitanja inicijalizacijske datoteke!");
			req.getRequestDispatcher("WEB-INF/pages/invalidArgument.jsp").forward(req, resp);
			return;
		}
		List<Poll> polls = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			polls = Glasanje.ucitajAnketeIzDatoteke(pollsFileName);
		} catch (Exception e) {
			req.getSession().setAttribute("error", "Pogreška prilikom čitanja inicijalizacijske datoteke!");
			req.getRequestDispatcher("WEB-INF/pages/invalidArgument.jsp").forward(req, resp);
			return;
		}
		for (Poll i : polls) {
			Poll poll = DAOProvider.getDao().dohvatiAnketu(i.getPollTitle());
			if (poll == null) {
				try {
					pst = con.prepareStatement("INSERT INTO Polls (title, message) values (?, ?)",
							Statement.RETURN_GENERATED_KEYS);
					pst.setString(1, i.getPollTitle());
					pst.setString(2, i.getPollMessage());
					pst.executeUpdate();
					ResultSet generatedKeys = pst.getGeneratedKeys();
					generatedKeys.next();
					long pollID = 1;
					try {
						pollID = generatedKeys.getLong(1);
					} catch (Exception e) {

					}
					for (PollOption o : pollOptions) {
						if (i.getId() == o.getPollID()) {
							pst = con
									.prepareStatement(
											"INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) values (?, ?, ?, ?)",
											Statement.RETURN_GENERATED_KEYS);
							pst.setString(1, o.getOptionTitle());
							pst.setString(2, o.getOptionLink());
							pst.setLong(3, pollID);
							pst.setLong(4, o.getVotesCount());
							pst.executeUpdate();
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		try {
			if (pst != null) {
				pst.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		resp.sendRedirect(req.getContextPath() + "/index.html");
	}
}

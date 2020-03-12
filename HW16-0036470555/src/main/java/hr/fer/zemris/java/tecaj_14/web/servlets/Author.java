package hr.fer.zemris.java.tecaj_14.web.servlets;

import hr.fer.zemris.java.tecaj_14.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_14.model.BlogComment;
import hr.fer.zemris.java.tecaj_14.model.BlogEntry;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;
import hr.fer.zemris.java.tecaj_14.web.forms.CommentForm;
import hr.fer.zemris.java.tecaj_14.web.forms.EntryForm;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/servleti/author/*")
public class Author extends HttpServlet {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo().substring(1);
		String[] pathParts = pathInfo.split("/");
		if (pathParts.length == 0) {
			req.getRequestDispatcher(getServletContext().getContextPath());
			return;
		}
		BlogUser author = DAOProvider.getDAO().getBlogUser(pathParts[0]);
		BlogUser user = (BlogUser) req.getSession().getAttribute("current.user");
		if (author == null) {
			req.setAttribute("error", "User doesn't exist!");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);;
			return;
		}
		req.setAttribute("author", author);
		if (pathParts.length == 1) {
			List<BlogEntry> entries = author.getEntries();
			req.setAttribute("entries", entries);
			req.getRequestDispatcher("/WEB-INF/pages/entries.jsp").forward(req, resp);;
			return;
		}
		if (pathParts.length == 2) {
			if (pathParts[1].equals("new")) {
				if (!hasAuthority(user, author)) {
					req.setAttribute("error", "Authorization failure!");
					req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);;
					return;
				}
				req.getRequestDispatcher("/WEB-INF/pages/newEntry.jsp").forward(req, resp);
				return;
			}
		}
		Long id = getLong(pathParts[1]);
		BlogEntry entry = author.getEntry(id);
		if (entry == null) {
			req.setAttribute("error", "Entry doesn't exist!");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);;
			return;
		}
		if (pathParts.length == 2) {
			req.setAttribute("entry", entry);
			req.getRequestDispatcher("/WEB-INF/pages/viewEntry.jsp").forward(req, resp);
			return;
		}
		EntryForm form = new EntryForm();
		form.fromBlogEntry(entry);
		req.setAttribute("form", form);
		if (pathParts.length == 3) {
			if (pathParts[2].equals("edit")) {
				if (!hasAuthority(user, author)) {
					req.setAttribute("error", "Authorization failure!");
					req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);;
					return;
				}
				req.getRequestDispatcher("/WEB-INF/pages/editEntry.jsp").forward(req, resp);
				return;
			}
			if (pathParts[2].equals("add_comment")) {
				if (!hasAuthority2(user)) {
					req.setAttribute("error", "Authorization failure!");
					req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);;
					return;
				}
				req.getRequestDispatcher("/WEB-INF/pages/addComment.jsp").forward(req, resp);
				return;
			}
		}

	}
	private boolean hasAuthority2(BlogUser user) {
		return user != null;
	}
	private boolean hasAuthority(BlogUser user, BlogUser author) {
		return author.equals(user);
	}

	private Long getLong(String string) {
		Long id = null;
		try {
			id = Long.parseLong(string);
		} catch (NumberFormatException e) {
		}
		return id;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String metoda = req.getParameter("metoda");

		if ("Cancel".equals(metoda)) {
			resp.sendRedirect(req.getServletContext().getContextPath());
			return;
		}
		if ("Add".equals(metoda)) {
			newEntry(req, resp);
		}
		if ("Edit".equals(metoda)) {
			editEntry(req, resp);
		}
		if ("Comment".equals(metoda)) {
			addComment(req, resp);
		}
	}
	private void addComment(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		CommentForm form = new CommentForm();
		form.fromHttpRequest(req);
		if (form.hasErrors()) {
			req.setAttribute("form", form.getEntry());
			req.setAttribute("comment", form);
			req.getRequestDispatcher("/WEB-INF/pages/addComment.jsp").forward(req, resp);;
			return;
		}
		BlogComment comment = new BlogComment();
		form.fillComment(comment);
		DAOProvider.getDAO().updateBlogComment(comment);
		resp.sendRedirect(req.getServletContext().getContextPath());
	}
	private void editEntry(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		EntryForm form = new EntryForm();
		form.fromHttpRequest(req);
		if (form.hasErrors()) {
			req.setAttribute("form", form);
			req.getRequestDispatcher("/WEB-INF/pages/editEntry.jsp").forward(req, resp);;
			return;
		}
		BlogEntry entry = new BlogEntry();
		form.fillEntry(entry);
		DAOProvider.getDAO().updateBlogEntry(entry);
		resp.sendRedirect(req.getServletContext().getContextPath());
	}
	/**
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void newEntry(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		EntryForm form = new EntryForm();
		form.fromHttpRequest(req);

		if (form.hasErrors()) {
			req.setAttribute("form", form);
			req.getRequestDispatcher("/WEB-INF/pages/newEntry.jsp").forward(req, resp);;
			return;
		}
		BlogEntry entry = new BlogEntry();
		form.fillEntry(entry);
		DAOProvider.getDAO().saveBlogEntry(entry);
		resp.sendRedirect(getServletContext().getContextPath());
	}
}

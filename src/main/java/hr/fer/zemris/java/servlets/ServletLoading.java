package hr.fer.zemris.java.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTML;

import hr.fer.zemris.java.dao.DAOProvider;

/**
 * Class implements {@link HttpServlet} for poll informations loading from
 * database
 * 
 * @author Mihael
 *
 */
public class ServletLoading extends HttpServlet {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Method loads polls names with IDs and creates list in form of {@link HTML}
	 * which contains links to every poll
	 * 
	 * @param req
	 *            - server request
	 * @param resp
	 *            - server response
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("text/html; charset=utf-8");
		resp.setStatus(HttpServletResponse.SC_OK);
		req.setAttribute("allItems", DAOProvider.getDao().getPolls());
		req.getRequestDispatcher("/WEB-INF/pages/polls.jsp").forward(req, resp);
	}
}

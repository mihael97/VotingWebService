package hr.fer.zemris.java.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.dao.DAOProvider;

/**
 * Class represents data loader and creator of voting form
 * 
 * @author MIhael
 *
 */
public class GlasanjeServlet extends HttpServlet {
	/**
	 * serialVersonUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Method loads bends informations form database and then creates HTML
	 * 
	 * @param req
	 *            - request
	 * @param resp
	 *            - response
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType("text/html; charset=utf-8");

		int id = Integer.parseInt(req.getParameter("pollID"));
		req.setAttribute("pollID", id);
		req.setAttribute("poll", DAOProvider.getDao().getPollByID(id));
		req.setAttribute("allItems", DAOProvider.getDao().loadItems(id));

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}

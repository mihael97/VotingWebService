package hr.fer.zemris.java.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.dao.DAOProvider;

/**
 * Class represents servlet which analyzes our vote
 * 
 * @author Mihael
 *
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Method catches band id and increment number of votes for that band in file
	 * form disc
	 * 
	 * @param req
	 *            - request
	 * @param resp
	 *            - response
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8");
		resp.setStatus(HttpServletResponse.SC_OK);

		Integer idVote = Integer.parseInt((String) req.getParameter("id"));

		DAOProvider.getDao().incrementVote((int) req.getServletContext().getAttribute("pollID"), idVote);

		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati");
	}
}

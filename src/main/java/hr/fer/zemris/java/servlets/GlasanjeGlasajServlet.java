package hr.fer.zemris.java.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
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

		Integer idVote = Integer.parseInt(req.getParameter("id"));
		Integer pollID= Integer.parseInt(req.getParameter("pollID"));
		DAOProvider.getDao().incrementVote(idVote,pollID);

		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID="
				+pollID);
	}
}

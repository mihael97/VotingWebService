package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.strcutures.PollOptionsStructure;

/**
 * CLass prepares parameters for image rendering
 * 
 * @author Mihael
 *
 */
@WebListener("/servleti/glasanje-grafika")
public class ServletGraphic extends HttpServlet {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Method puts all names with votes in valid form and then redirect informations
	 * to graph generator
	 * 
	 * @param req
	 *            - request
	 * @param resp
	 *            - response
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		StringBuilder builder = new StringBuilder();
		List<PollOptionsStructure> list = (List<PollOptionsStructure>) req.getSession().getAttribute("allItems");

		for (PollOptionsStructure struc : list) {
			builder.append("&").append(struc.getOptionTitle()).append("=").append(struc.getVotes());
		}

		resp.sendRedirect(req.getContextPath() + "/reportImage?" + builder.toString().substring(1));
	}
}

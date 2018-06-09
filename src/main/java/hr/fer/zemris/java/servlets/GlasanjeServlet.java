package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.strcutures.PollOptionsStructure;
import hr.fer.zemris.java.strcutures.PollsStructure;

/**
 * Method represents data loader and creator of voting form
 * 
 * @author MIhael
 *
 */
@WebServlet("/servleti/glasanje")
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
		resp.setStatus(HttpServletResponse.SC_ACCEPTED);
		resp.setContentType("html/text; charset=utf-8");

		int id = (int) req.getServletContext().getAttribute("pollID");

		StringBuilder html = new StringBuilder();
		html.append("<!DOCTYPE><html><body><h2>Please vote for one of given items by clicking on name</h2>\n");

		PollsStructure poll = DAOProvider.getDao().getPolls().stream().filter(e -> e.getId() == id)
				.collect(Collectors.toList()).get(0);
		html.append("<h3>").append(poll.getTitle()).append("</h3>");
		html.append("<h4>").append(poll.getMessage()).append("</h4>");

		html.append("<ol>");

		for (PollOptionsStructure struc : DAOProvider.getDao().loadItems(id)) {
			html.append("<li>").append("<a href=")
					.append(req.getServletPath() + "/servleti/glasanje-glasaj?id=" + struc.getId());
			html.append("</a></li>");
		}

		html.append("</ol>");
		html.append("</body></html>");

		resp.getOutputStream().write(html.toString().getBytes());
		resp.getOutputStream().flush();
	}
}

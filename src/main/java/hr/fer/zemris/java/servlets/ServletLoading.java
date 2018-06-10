package hr.fer.zemris.java.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTML;

import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.strcutures.PollsStructure;

/**
 * Class implements {@link HttpServlet} for poll informations loading from
 * database
 * 
 * @author Mihael
 *
 */
@WebServlet("/servleti/index.html")
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
		StringBuilder htmlBuilder = new StringBuilder();
		htmlBuilder.append("<html>\n<body>\n<h2>Please select one of supported polls</h2><ol>\n");
		for (PollsStructure list : DAOProvider.getDao().getPolls()) {
			htmlBuilder.append("<li><a href=" + req.getContextPath() + "/servleti/glasanje?pollID=" + list.getId() + ">"
					+ list.getTitle() + "</a></li>");
		}

		htmlBuilder.append("</ol></body>\n</html>\n");

		resp.getWriter().write(htmlBuilder.toString());
		resp.getOutputStream().flush();
	}
}

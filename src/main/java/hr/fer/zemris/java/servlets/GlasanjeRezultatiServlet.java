package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.strcutures.PollOptionsStructure;

/**
 * Class represents voting analyzer
 * 
 * @author Mihael
 *
 */
@WebServlet("/servleti/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Method loads document from disc and analyzes results
	 * 
	 * @param req
	 *            - request
	 * @param resp
	 *            - response
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<PollOptionsStructure> bands = DAOProvider.getDao()
				.loadItems((int) req.getServletContext().getAttribute("pollID"));

		getResults(bands, req);

		req.getSession().setAttribute("allItems", bands);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

	/**
	 * Method analyzes current voting results
	 * 
	 * @param list
	 *            - list of all bands with results
	 * @param req
	 *            - request
	 */
	private void getResults(List<PollOptionsStructure> list, HttpServletRequest req) {
		Collections.sort(list, new Comparator<PollOptionsStructure>() {

			@Override
			public int compare(PollOptionsStructure arg0, PollOptionsStructure arg1) {
				return arg1.getVotes().compareTo(arg0.getVotes());
			}
		});

		final int max2 = list.get(0).getVotes();
		req.getSession().setAttribute("best",
				list.stream().filter(e -> e.getVotes() == max2).collect(Collectors.toList()));
	}
}

package hr.fer.zemris.java.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.strcutures.PollOptionsStructure;

/**
 * Class renders image with <code>poll options</code> values of current active
 * poll
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
		List<PollOptionsStructure> list = DAOProvider.getDao()
				.loadItems((int) req.getServletContext().getAttribute("pollID"));

		// specification setting
		resp.setContentType("png; charset=utf-8");

		PieDataset dataset = createDataset(list);
		JFreeChart chart = createChart(dataset, "");

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(chart.createBufferedImage(500, 500), "png", bos);
			resp.getOutputStream().write(bos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method creates data set
	 * 
	 * @param list
	 *            - list of <code>poll items</code>
	 * 
	 * @return {@link PieDataset} - structure with informations prepared like image
	 *         rendering source
	 */
	private PieDataset createDataset(List<PollOptionsStructure> list) {
		DefaultPieDataset result = new DefaultPieDataset();

		if (list != null) {
			for (PollOptionsStructure struc : list) {
				result.setValue(struc.getOptionTitle() + "(" + struc.getVotes() + ")", struc.getVotes());
			}
		} else {
			result.setValue("Java", 99.5);
			result.setValue("Python", 0.25);
			result.setValue("C#", 0.25);
		}

		return result;
	}

	/**
	 * Method creates chart
	 * 
	 * @param dataset
	 *            - data set
	 * @param title
	 *            title
	 * @return created chart
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;

	}
}

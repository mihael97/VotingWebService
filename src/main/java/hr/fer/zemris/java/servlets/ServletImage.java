package hr.fer.zemris.java.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 * Class represents servlet for chart drawing
 * 
 * @author Mihael
 *
 */
public class ServletImage extends HttpServlet {

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Method creates data set and chart and after pushes context to output stream
	 * 
	 * @param req
	 *            - request
	 * @param resp
	 *            - response
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// specification setting
		resp.setContentType("png; charset=utf-8");

		PieDataset dataset = createDataset(req);
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
	 * @param req
	 * 
	 * @return dataset
	 */
	private PieDataset createDataset(HttpServletRequest req) {
		DefaultPieDataset result = new DefaultPieDataset();

		Enumeration<String> enumAttribute = req.getParameterNames();

		if (enumAttribute.hasMoreElements()) {
			while (enumAttribute.hasMoreElements()) {
				String strig = enumAttribute.nextElement();
				result.setValue(strig, Integer.parseInt(req.getParameter(strig)));
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

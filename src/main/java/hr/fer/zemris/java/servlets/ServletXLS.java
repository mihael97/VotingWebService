package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.strcutures.PollOptionsStructure;

/**
 * Class represents XLS document generator
 * 
 * @author Mihael
 *
 */
public class ServletXLS extends HttpServlet {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Method catches all bands with informations from memory and generates document
	 * with it
	 * 
	 * @param req
	 *            - request
	 * @param resp
	 *            - response
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/vnd.ms-excel; charsets=utf-8");
		resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");
		resp.setStatus(HttpServletResponse.SC_OK);

		HSSFWorkbook file = generateDocument(
				DAOProvider.getDao().loadItems(Integer.parseInt(req.getParameter("pollID"))));

		file.write(resp.getOutputStream());
		resp.getOutputStream().flush();
		resp.getOutputStream().close();
	}

	/**
	 * Method generates document with voting informations
	 * 
	 * @param map
	 *            - list of stored bands with votes
	 * @param list
	 * @return {@link HSSFWorkbook}
	 */
	private HSSFWorkbook generateDocument(List<PollOptionsStructure> list) {
		HSSFWorkbook file = new HSSFWorkbook();
		HSSFSheet sheet = file.createSheet("Voting results");
		int i = 1;

		HSSFRow row = sheet.createRow(0);

		row.createCell(0).setCellValue("Item:");
		row.createCell(1).setCellValue("Votes:");

		for (PollOptionsStructure structure : list) {
			row = sheet.createRow(i);
			row.createCell(0).setCellValue(structure.getOptionTitle());
			row.createCell(1).setCellValue(structure.getVotes());
			i++;
		}

		return file;
	}
}

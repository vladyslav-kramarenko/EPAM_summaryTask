package ua.nure.kramarenko.SummaryTask4.web.command;

import ua.nure.kramarenko.SummaryTask4.db.Path;
import ua.nure.kramarenko.SummaryTask4.db.derby.ManufacturerDb;
import ua.nure.kramarenko.SummaryTask4.db.entity.Manufacturer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CompleteManufacturerCommand extends Command {

	/**
	 * @author Vlad Kramarenko
	 */
	private static final long serialVersionUID = -1219684162891924648L;

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String action = request.getParameter("action");
		String targetId = request.getParameter("id");
		StringBuffer sb = new StringBuffer();

		if (targetId != null) {
			targetId = targetId.trim().toLowerCase();
		} else {
			return Path.PAGE_ERROR_PAGE;
		}

		boolean namesAdded = false;
		if (action.equals("complete")) {

			// check if user sent empty string
			if (!targetId.equals("")) {
				ManufacturerDb manufacturerDb = new ManufacturerDb();

				List<Manufacturer> manufacturers = manufacturerDb
						.getAllManufacturers();
				for (Manufacturer m : manufacturers) {

					if ( // targetId matches first name
					m.getName().toLowerCase().startsWith(targetId)) {

						sb.append("<manufacturer>");
						sb.append("<id>" + m.getId() + "</id>");
						sb.append("<name>" + m.getName() + "</name>");
						sb.append("</manufacturer>");
						namesAdded = true;
					}
				}
			}

			if (namesAdded) {
				System.out.println(sb.toString());
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write(
						"<manufacturers>" + sb.toString() + "</manufacturers>");
			} else {
				// nothing to show
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			}
		}
		if (action.equals("lookup")) {
			response.sendRedirect("controller?command=productInfo&product_id="
					+ targetId);
			return null;
		}

		return null;
	}

}

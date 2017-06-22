package ua.nure.kramarenko.SummaryTask4.web.command.admin;

import org.apache.log4j.Logger;
import ua.nure.kramarenko.SummaryTask4.db.Path;
import ua.nure.kramarenko.SummaryTask4.db.derby.ManufacturerDb;
import ua.nure.kramarenko.SummaryTask4.db.entity.Manufacturer;
import ua.nure.kramarenko.SummaryTask4.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ManufacturersCommand extends Command {

	private static final long serialVersionUID = 1863978254689586513L;

	private static final Logger LOG = Logger
			.getLogger(ManufacturersCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Commands starts");

		HttpSession session = request.getSession();

		ManufacturerDb manufacturerDb = new ManufacturerDb();
		String action = request.getParameter("action");
		LOG.debug("Aciton = " + action);
		String name = request.getParameter("manufacturerName");
		Manufacturer manufacturer = null;
		if (action != null) {

			if (action.equals("create")) {
				manufacturer = new Manufacturer();
				manufacturer.setName(name);
				manufacturerDb.addManufacturer(manufacturer);
			} else {
				int manufacturerId = Integer.parseInt(request
						.getParameter("manufacturerId"));
				manufacturer = manufacturerDb.getManufacturer(manufacturerId);
				if (action.equals("delete")) {
					manufacturerDb.deleteManufacturer(manufacturer);
				}
				if (action.equals("edit")) {
					request.setAttribute("manufacturer", manufacturer);
					request.setAttribute("action", "edit");
				}
				if (action.equals("update")) {

					manufacturer.setName(name);
					manufacturerDb.updateManufacturer(manufacturer);
				}
			}
		}

		List<Manufacturer> manufacturersList = manufacturerDb
				.getAllManufacturers();
		request.setAttribute("manufacturersList", manufacturersList);
		session.setAttribute("page", "manufacturers");
		return Path.PAGE_MANUFACTURERS;
	}

}
package ua.nure.kramarenko.SummaryTask4.web.command;

import org.apache.log4j.Logger;
import ua.nure.kramarenko.SummaryTask4.db.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;

public class UpdateLocaleCommand extends Command {

	private static final long serialVersionUID = 7732286214029478505L;

	private static final Logger LOG = Logger
			.getLogger(UpdateLocaleCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		LOG.debug("Command starts");

		String localeToSet = request.getParameter("localeToSet");
		if (localeToSet != null && !localeToSet.isEmpty()) {
			HttpSession session = request.getSession();
			Config.set(session, "javax.servlet.jsp.jstl.fmt.locale",
					localeToSet);
			session.setAttribute("defaultLocale", localeToSet);
		}

		LOG.debug("Command finished");
		HttpSession session = request.getSession();
		String lastPage = (String) session.getAttribute("page");
		if (lastPage != null) {
			if (lastPage.equals("product")) {
				response.sendRedirect(Path.COMMAND_LIST_PRODUCTS);
				return null;
			}
			if (lastPage.equals("cart")) {
				response.sendRedirect(Path.COMMAND_VIEW_CART);
				return null;
			}
			if (lastPage.equals("allOrders")) {
				response.sendRedirect(Path.COMMAND_ALL_ORDERS);
				return null;
			}
			if (lastPage.equals("orders")) {
				response.sendRedirect(Path.COMMAND_LIST_ORDERS);
				return null;
			}

			if (lastPage.equals("users")) {
				response.sendRedirect(Path.COMMAND_ALL_USERS);
				return null;
			}
			if (lastPage.equals("manufacturers")) {
				response.sendRedirect(Path.COMMAND_MANUFACTURERS);
				return null;
			}
			if (lastPage.equals("charactiristics")) {
				response.sendRedirect(Path.COMMAND_CHARACTIRISTICS);
				return null;
			}
		}

		return Path.COMMAND_LIST_PRODUCTS;
	}

}
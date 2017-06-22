package ua.nure.kramarenko.SummaryTask4.web.command;

import org.apache.log4j.Logger;
import ua.nure.kramarenko.SummaryTask4.db.Path;
import ua.nure.kramarenko.SummaryTask4.db.derby.UserDb;
import ua.nure.kramarenko.SummaryTask4.db.entity.User;
import ua.nure.kramarenko.SummaryTask4.db.enums.Role;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegistrationCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger LOG = Logger
			.getLogger(RegistrationCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		LOG.debug("Command starts");

		HttpSession session = request.getSession();
		String forward = Path.PAGE_ERROR_PAGE;
		String login = request.getParameter("login");
		if (login != null) {
			LOG.trace("Request parameter: registration --> " + login);

			String password = request.getParameter("password");
			String email = request.getParameter("email");
			String name = request.getParameter("name");
			String phone = request.getParameter("phone");
			String surname = request.getParameter("surname");
			String city = request.getParameter("city");
			String address = request.getParameter("address");

			// error handler
			String errorMessage = null;

			if (password == null || login.isEmpty() || password.isEmpty()) {
				errorMessage = "Login/password cannot be empty" + "<br>";
				LOG.error("errorMessage --> " + errorMessage);
				forward = Path.PAGE_REGISTRATION;
			}

			UserDb userDb = new UserDb();

			User user = userDb.findUserByLogin(login);

			if (user != null) {
				LOG.trace("Found in DB: user --> " + user);
				errorMessage = "Such login is forbidden" + "<br>";
				request.setAttribute("errorMessage", errorMessage);
				LOG.error("errorMessage --> " + errorMessage);

			} else {
				user = new User();
				user.setFirstName(name);
				user.setLastName(surname);
				user.setEmail(email);
				user.setPhone(phone);
				user.setCity(city);
				user.setAddress(address);
				user.setLogin(login);
				user.setPassword(password);
				user.setRoleId(1);
				userDb.addUser(user);

				session.setAttribute("user", user);
				LOG.trace("Set the session attribute: user --> " + user);

				session.setAttribute("userRole", Role.CLIENT);
				LOG.trace("Set the session attribute: userRole --> "
						+ Role.CLIENT);

				LOG.info("User " + user + " logged as "
						+ Role.CLIENT.toString().toLowerCase());

				String lastPage = LastPage.getPage((String) session
						.getAttribute("page"));
				if (lastPage != null) {
					response.sendRedirect(lastPage);
					return null;
				} else {
					return Path.COMMAND_LIST_PRODUCTS;
				}
			}

			request.setAttribute("errorMessage", errorMessage);
			request.setAttribute("login", login);
			request.setAttribute("email", email);
			request.setAttribute("phone", phone);
			request.setAttribute("lastName", surname);
			request.setAttribute("city", city);
			request.setAttribute("address", address);

		} else {
			forward = Path.PAGE_REGISTRATION;
		}
		LOG.debug("Command finished");
		return forward;
	}

}
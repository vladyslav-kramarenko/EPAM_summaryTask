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

/**
 * Login command.
 * 
 * @author Vlad Kramarenko
 */
public class LoginCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger LOG = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		LOG.debug("Command starts");

		HttpSession session = request.getSession();

		// obtain login and password from the request
		String login = request.getParameter("login");
		String forward = Path.PAGE_ERROR_PAGE;
		if (login != null) {
			LOG.trace("Request parameter: loging --> " + login);

			String password = request.getParameter("password");

			// error handler
			String errorMessage = null;

			if (password == null || login.isEmpty() || password.isEmpty()) {
				errorMessage = "Login/password cannot be empty";
				request.setAttribute("errorMessage", errorMessage);
				LOG.error("errorMessage --> " + errorMessage);
				return forward;
			}

			UserDb userDb = new UserDb();

			User user = userDb.findUserByLogin(login);
			LOG.trace("Found in DB: user --> " + user);

			if (user == null || !password.equals(user.getPassword())) {
				errorMessage = "Cannot find user with such login/password";
				request.setAttribute("errorMessage", errorMessage);
				LOG.error("errorMessage --> " + errorMessage);
				return forward;
			} else {
				Role userRole = Role.getRole(user);
				LOG.trace("userRole --> " + userRole);

				session.setAttribute("user", user);
				LOG.trace("Set the session attribute: user --> " + user);

				session.setAttribute("userRole", userRole);
				LOG.trace("Set the session attribute: userRole --> " + userRole);

				LOG.info("User " + user + " logged as "
						+ userRole.toString().toLowerCase());

				String lastPage = LastPage.getPage((String) session
						.getAttribute("page"));
				if (lastPage == null) {
					lastPage = Path.COMMAND_LIST_PRODUCTS;
				}
				response.sendRedirect(lastPage);
				return null;
			}

		} else {
			forward = Path.PAGE_LOGIN;
		}

		LOG.debug("Command finished");
		return forward;
	}
}
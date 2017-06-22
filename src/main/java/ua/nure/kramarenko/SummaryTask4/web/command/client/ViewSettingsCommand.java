package ua.nure.kramarenko.SummaryTask4.web.command.client;

import org.apache.log4j.Logger;
import ua.nure.kramarenko.SummaryTask4.db.Path;
import ua.nure.kramarenko.SummaryTask4.db.derby.UserDb;
import ua.nure.kramarenko.SummaryTask4.db.entity.User;
import ua.nure.kramarenko.SummaryTask4.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * View settings command.
 * 
 * @author Vlad Kramarenko
 * 
 */
public class ViewSettingsCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger LOG = Logger
			.getLogger(ViewSettingsCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Command starts");
		HttpSession session = request.getSession();
		if (request.getMethod().equals("post")) {
			String save = request.getParameter("save");
			if (save.equals("true")) {
				User user = (User) session.getAttribute("user");
				String currentPassword = request
						.getParameter("currentPassword");
				String password = request.getParameter("password");
				String firstName = request.getParameter("first_name");
				String lastName = request.getParameter("last_name");
				String phone = request.getParameter("phone");
				String email = request.getParameter("email");
				String city = request.getParameter("city");
				String address = request.getParameter("address");

				user.setAddress(address);
				user.setCity(city);
				user.setEmail(email);
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setPhone(phone);

				request.setAttribute("user", user);
				if (currentPassword.equals(user.getPassword())) {
					if (password != null && password.length() > 0) {
						user.setPassword(password);
					}
					UserDb userDb = new UserDb();
					userDb.updateUser(user);
					session.setAttribute("user", user);
				}
			}
			return null;
		}

		LOG.debug("Command finished");
		return Path.PAGE_SETTINGS;
	}
}
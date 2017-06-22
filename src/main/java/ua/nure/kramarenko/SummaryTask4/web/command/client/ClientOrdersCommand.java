package ua.nure.kramarenko.SummaryTask4.web.command.client;

import org.apache.log4j.Logger;
import ua.nure.kramarenko.SummaryTask4.db.Path;
import ua.nure.kramarenko.SummaryTask4.db.bean.UserOrderBean;
import ua.nure.kramarenko.SummaryTask4.db.derby.OrderDb;
import ua.nure.kramarenko.SummaryTask4.db.entity.User;
import ua.nure.kramarenko.SummaryTask4.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Lists orders.
 * 
 * @author Vlad Kramarenko
 */
public class ClientOrdersCommand extends Command {

	private static final long serialVersionUID = 1863978254689586513L;

	private static final Logger LOG = Logger
			.getLogger(ClientOrdersCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Commands starts");

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		OrderDb orderDb = new OrderDb();
		List<UserOrderBean> userOrderBeanList = orderDb
				.getAllUserOrdersBean(user.getId());
		LOG.trace("Found in DB: userOrderBeanList --> " + userOrderBeanList);

		request.setAttribute("userOrderBeanList", userOrderBeanList);
		session.setAttribute("page", "orders");
		LOG.trace("Set the request attribute: userOrderBeanList --> "
				+ userOrderBeanList);

		LOG.debug("Commands finished");
		return Path.PAGE_CLIENT_ORDERS;
	}

}
package ua.nure.kramarenko.SummaryTask4.web.command.admin;

import org.apache.log4j.Logger;
import ua.nure.kramarenko.SummaryTask4.db.Path;
import ua.nure.kramarenko.SummaryTask4.db.bean.UserOrderBean;
import ua.nure.kramarenko.SummaryTask4.db.derby.OrderDb;
import ua.nure.kramarenko.SummaryTask4.db.enums.Status;
import ua.nure.kramarenko.SummaryTask4.web.command.Command;
import ua.nure.kramarenko.SummaryTask4.web.mail.MailCreator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Lists orders.
 * 
 * @author Vlad Kramarenko
 */
public class AllOrdersCommand extends Command {

	private static final long serialVersionUID = 1863978254689586513L;

	private static final Logger LOG = Logger.getLogger(AllOrdersCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		LOG.debug("Commands starts");

		HttpSession session = request.getSession();

		if (request.getParameter("command").equals("changeOrderStatus")) {
			int orderId = Integer.parseInt(request.getParameter("order_id"));
			Status orderStatus = Status.valueOf(request.getParameter("status"));
			System.out.println("status=" + orderStatus);

			OrderDb orderDb = new OrderDb();
			orderDb.updateOrderStatus(orderId, orderStatus.ordinal());
			LOG.trace("Changed status of order #" + orderId + " on "
					+ orderStatus.toString());
			MailCreator.sentMail(orderId);

		}
		String filter = null;
		if (request.getParameter("command").equals("allOrdersFilter")) {
			filter = request.getParameter("filter");
			session.setAttribute("all_orders_filter", filter);
		}
		Status status = null;
		String statusName = request.getParameter("status");
		if (statusName != null && !statusName.equals("")) {
			status = Status.valueOf(statusName);
			request.setAttribute("statusFilter", statusName);
		}

		OrderDb orderDb = new OrderDb();
		List<UserOrderBean> userOrderBeanList = orderDb
				.getAllOrdersBean(status);

		LOG.trace("Found in DB: userOrderBeanList --> " + userOrderBeanList);

		request.setAttribute("userOrderBeanList", userOrderBeanList);
		LOG.trace("Set the request attribute: userOrderBeanList --> "
				+ userOrderBeanList);

		// statusTypes
		ArrayList<String> statusTypes = new ArrayList<String>();
		for (Status s : Status.values()) {
			statusTypes.add(s.toString());
		}
		session.setAttribute("statusTypes", statusTypes);
		session.setAttribute("page", "allOrders");
		LOG.debug("Commands finished");
		return Path.PAGE_ALL_ORDERS;
	}
}
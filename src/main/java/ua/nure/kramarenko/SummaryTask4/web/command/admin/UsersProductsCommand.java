package ua.nure.kramarenko.SummaryTask4.web.command.admin;

import org.apache.log4j.Logger;
import ua.nure.kramarenko.SummaryTask4.db.Path;
import ua.nure.kramarenko.SummaryTask4.db.bean.UserProductsBean;
import ua.nure.kramarenko.SummaryTask4.db.derby.OrdersProductsDb;
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
public class UsersProductsCommand extends Command {

	private static final long serialVersionUID = 1863978254689586513L;

	private static final Logger LOG = Logger
			.getLogger(UsersProductsCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		LOG.debug("Commands starts");

		HttpSession session = request.getSession();

		OrdersProductsDb ordersProductDb = new OrdersProductsDb();
		List<UserProductsBean> usersProducts = ordersProductDb
				.getAllUserProducts();

		request.setAttribute("userProductsList", usersProducts);

		session.setAttribute("page", "userProducts");
		LOG.debug("Commands finished");
		return Path.PAGE_ALL_USER_PRODUCTS;
	}
}
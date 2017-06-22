package ua.nure.kramarenko.SummaryTask4.web.command.client;

import ua.nure.kramarenko.SummaryTask4.db.Path;
import ua.nure.kramarenko.SummaryTask4.db.bean.ManufacturerBean;
import ua.nure.kramarenko.SummaryTask4.db.bean.cart.ShoppingCart;
import ua.nure.kramarenko.SummaryTask4.db.derby.OrderDb;
import ua.nure.kramarenko.SummaryTask4.db.derby.OrdersProductsDb;
import ua.nure.kramarenko.SummaryTask4.db.derby.UserDb;
import ua.nure.kramarenko.SummaryTask4.db.entity.Order;
import ua.nure.kramarenko.SummaryTask4.db.entity.User;
import ua.nure.kramarenko.SummaryTask4.web.command.Command;
import ua.nure.kramarenko.SummaryTask4.web.mail.MailCreator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class CartCommand extends Command {

	private static final long serialVersionUID = 7732286214029478505L;
	private static final String cartAttribute="cart";

	public CartCommand() {

	}

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		session.setAttribute("page", cartAttribute);
		String commandName = request.getParameter("command");
		if (commandName.equals("viewCart")) {
			return Path.PAGE_CART;
		}
		if (commandName.equals("confirmOrder")) {
			ShoppingCart cart = (ShoppingCart) session.getAttribute(cartAttribute);
			if (request.getMethod().equals("POST")) {
				if (cart != null && cart.getTotal() != 0) {

					String email = request.getParameter("email");
					String name = request.getParameter("name");
					String phone = request.getParameter("phone");
					String surname = request.getParameter("last_name");
					String city = request.getParameter("city");
					String address = request.getParameter("address");

					User user = (User) session.getAttribute("user");

					user.setFirstName(name);
					user.setLastName(surname);
					user.setEmail(email);
					user.setPhone(phone);
					user.setCity(city);
					user.setAddress(address);

					session.setAttribute("user", user);
					System.out.println(user);

					UserDb userDb = new UserDb();
					userDb.updateUser(user);

					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Calendar cal = Calendar.getInstance();
					String date = dateFormat.format(cal.getTime());

					Order order = new Order();
					order.setUserId(user.getId());
					order.setBill((int) cart.getTotal());
					order.setCity(city);
					order.setAddress(address);
					order.setPhone(phone);
					order.setEmail(email);
					order.setDate(date);
					order.setStatusId(0);

					OrderDb orderDb = new OrderDb();
					order=orderDb.addOrder(order);

					OrdersProductsDb ordersProducts = new OrdersProductsDb();
					ordersProducts.setOrdersProducts(order.getId(),
							cart.getItems());

					MailCreator.sentMail(user, order);

					session.setAttribute("order_id", order.getId());
					session.setAttribute("order", cart);
					session.setAttribute(cartAttribute, new ShoppingCart());

					response.sendRedirect("controller?command=confirmOrder");
					return null;
				}
			}
			if (request.getMethod().equals("GET")) {
				return Path.PAGE_ORDER_CONFIRMATION;
			}
		}

		if (commandName.equals("orderCheckout")) {
			return Path.PAGE_ORDER_CHECKOUT;
		}
		return Path.PAGE_ERROR_PAGE;
	}

	public void updateProductsList(HttpSession session,
			HttpServletRequest request,
			List<ManufacturerBean> choosenManufacturers) {
	}
}
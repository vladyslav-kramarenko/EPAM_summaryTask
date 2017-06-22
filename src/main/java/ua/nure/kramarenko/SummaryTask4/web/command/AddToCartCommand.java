package ua.nure.kramarenko.SummaryTask4.web.command;

import org.apache.log4j.Logger;
import ua.nure.kramarenko.SummaryTask4.db.Path;
import ua.nure.kramarenko.SummaryTask4.db.bean.cart.ShoppingCart;
import ua.nure.kramarenko.SummaryTask4.db.derby.ProductDb;
import ua.nure.kramarenko.SummaryTask4.db.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddToCartCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger LOG = Logger.getLogger(AddToCartCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		LOG.debug("Command starts");

		HttpSession session = request.getSession();
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		if (cart == null) {
			cart = new ShoppingCart();
			session.setAttribute("cart", cart);
		}

		String productId = request.getParameter("product_id");

		String forward = Path.PAGE_ERROR_PAGE;

		String commandName = request.getParameter("command");
		if (commandName.equals("addToCart")) {
			forward = addToCart(cart, response, session, productId);
		}

		if (commandName.equals("deleteFromCart")) {
			forward = deleteFromCart(cart, session, productId);
		}

		return forward;
	}

	public String addToCart(ShoppingCart userCart,
			HttpServletResponse response, HttpSession session, String productId)
			throws IOException {
		ShoppingCart cart;
		if (userCart == null) {
			cart = new ShoppingCart();
			session.setAttribute("cart", cart);
		} else {
			cart = userCart;
		}

		ProductDb productDb = new ProductDb();

		if (!productId.isEmpty()) {
			Product product = productDb.getProductByID(Integer
					.parseInt(productId));
			cart.addItem(product);
		}
		session.setAttribute("cart", cart);

		response.sendRedirect(Path.COMMAND_LIST_PRODUCTS);
		return null;
	}

	public String deleteFromCart(ShoppingCart cart, HttpSession session,
			String productId) {

		if (!productId.isEmpty()) {
			ProductDb productDb = new ProductDb();
			Product product = productDb.getProductByID(Integer
					.parseInt(productId));
			cart.deleteItem(product);
		}

		return Path.COMMAND_VIEW_CART;
	}
}
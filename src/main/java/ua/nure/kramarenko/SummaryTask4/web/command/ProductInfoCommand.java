package ua.nure.kramarenko.SummaryTask4.web.command;

import org.apache.log4j.Logger;
import ua.nure.kramarenko.SummaryTask4.db.Path;
import ua.nure.kramarenko.SummaryTask4.db.bean.product.ProductCharacteristicBean;
import ua.nure.kramarenko.SummaryTask4.db.bean.product.ProductListItem;
import ua.nure.kramarenko.SummaryTask4.db.derby.ProductCharacteristicDb;
import ua.nure.kramarenko.SummaryTask4.db.derby.ProductListBeanDb;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ProductInfoCommand extends Command {

	private static final long serialVersionUID = 7732286214029478505L;

	private static final Logger LOG = Logger
			.getLogger(ProductInfoCommand.class);

	public ProductInfoCommand() {

	}

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		LOG.debug("Command starts");

		HttpSession session = request.getSession();

		ProductListBeanDb productItemDb = new ProductListBeanDb();

		ProductCharacteristicDb productCharacteristicDb = new ProductCharacteristicDb();

		int productId = Integer.parseInt(request.getParameter("product_id"));

		List<ProductCharacteristicBean> productChars = productCharacteristicDb
				.getAllProductCharactiristics(productId);
		ProductListItem product = productItemDb.getProductItem(productId);
		request.setAttribute("productChars", productChars);
		request.setAttribute("product", product);
		session.setAttribute("page", "product");

		return Path.PAGE_PRODUCT_INFO;
	}
}
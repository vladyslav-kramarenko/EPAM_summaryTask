package ua.nure.kramarenko.SummaryTask4.web.command.admin;

import org.apache.log4j.Logger;
import ua.nure.kramarenko.SummaryTask4.db.Path;
import ua.nure.kramarenko.SummaryTask4.db.bean.product.ProductCharacteristicBean;
import ua.nure.kramarenko.SummaryTask4.db.bean.product.ProductListItem;
import ua.nure.kramarenko.SummaryTask4.db.derby.CharacteristicsDb;
import ua.nure.kramarenko.SummaryTask4.db.derby.ProductCharacteristicDb;
import ua.nure.kramarenko.SummaryTask4.db.derby.ProductDb;
import ua.nure.kramarenko.SummaryTask4.db.derby.ProductListBeanDb;
import ua.nure.kramarenko.SummaryTask4.db.entity.Characteristic;
import ua.nure.kramarenko.SummaryTask4.db.entity.Product;
import ua.nure.kramarenko.SummaryTask4.db.enums.Availability;
import ua.nure.kramarenko.SummaryTask4.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductEditCommand extends Command {

	private static final long serialVersionUID = 7732286214029478505L;

	private static final Logger LOG = Logger
			.getLogger(ProductEditCommand.class);

	public ProductEditCommand() {

	}

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		LOG.debug("Command starts");

		HttpSession session = request.getSession();

		List<ProductCharacteristicBean> productChars = null;
		ProductListItem product = null;

		String productIdValue = request.getParameter("product_id");

		if (productIdValue == null) {
			product = new ProductListItem();

		} else {

			int productId = Integer.parseInt(productIdValue);

			String delete = request.getParameter("delete");

			if (delete != null) {
				deleteproduct(productId, response);
				return null;
			}

			ProductListBeanDb productItemDb = new ProductListBeanDb();

			ProductCharacteristicDb productCharacteristicDb = new ProductCharacteristicDb();

			productChars = productCharacteristicDb
					.getAllProductCharactiristics(productId);

			product = productItemDb.getProductItem(productId);
		}
		ArrayList<String> availabilityList = new ArrayList<String>();
		for (Availability availability : Availability.values()) {
			availabilityList.add(availability.toString());
		}

		session.setAttribute("availabilityList", availabilityList);
		request.setAttribute("characteristicsList", productChars);
		request.setAttribute("product", product);
		session.setAttribute("page", "product_edit");

		chars(productIdValue, request);

		return Path.PAGE_PRODUCT_EDIT;
	}

	public void chars(String productIdValue, HttpServletRequest request) {
		ProductCharacteristicDb characteristicsDb = new ProductCharacteristicDb();
		String action = request.getParameter("action");
		LOG.debug("Aciton = " + action);
		String idValue = request.getParameter("id");
		String characteristicIdValue = request.getParameter("characteristicId");
		String value = request.getParameter("characteristicValue");
		ProductCharacteristicBean productCharacteristic = null;
		int productId = 0;
		if (productIdValue != null) {
			productId = Integer.parseInt(productIdValue);
		}
		int id = 0;
		int characteristicId = 0;
		if (action != null) {

			if (idValue != null) {
				id = Integer.parseInt(idValue);
			}
			if (characteristicIdValue != null) {
				characteristicId = Integer.parseInt(characteristicIdValue);
			}

			if (action.equals("create")) {
				productCharacteristic = new ProductCharacteristicBean();
				productCharacteristic.setId(id);
				productCharacteristic.setValue(value);
				productCharacteristic.setProductId(productId);
				productCharacteristic.setCharacteristicId(characteristicId);
				characteristicsDb
						.addProductCharactiristic(productCharacteristic);
			} else {
				productCharacteristic = characteristicsDb
						.getProductCharactiristic(id);
				if (action.equals("delete")) {
					characteristicsDb
							.deleteProductCharacteristic(productCharacteristic);
				}
				if (action.equals("edit")) {
					request.setAttribute("characteristic",
							productCharacteristic);
					request.setAttribute("action", "edit");
				}
				if (action.equals("update")) {
					productCharacteristic.setValue(value);
					characteristicsDb
							.updateProductCharachteristicValue(productCharacteristic);
				}
			}
		} else {
			request.setAttribute("characteristic",
					new ProductCharacteristicBean());
		}
		List<ProductCharacteristicBean> characteristicsList = characteristicsDb
				.getAllProductCharactiristics(productId);
		request.setAttribute("characteristicsList", characteristicsList);

		CharacteristicsDb charDb = new CharacteristicsDb();

		List<Characteristic> characteristicsItems = charDb
				.getAllCharacteristics();
		request.setAttribute("characteristicsItems", characteristicsItems);
	}

	public void deleteproduct(int productId, HttpServletResponse response)
			throws IOException {
		ProductDb productDb = new ProductDb();
		Product product = productDb.getProductByID(productId);
		boolean answer = productDb.deleteProduct(product);
		if (answer) {
			response.sendRedirect(Path.COMMAND_LIST_PRODUCTS);
		} else {
			response.sendRedirect(Path.PAGE_ERROR_PAGE);
		}
	}
}
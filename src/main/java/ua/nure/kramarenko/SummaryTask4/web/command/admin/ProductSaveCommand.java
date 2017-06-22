package ua.nure.kramarenko.SummaryTask4.web.command.admin;

import org.apache.log4j.Logger;
import ua.nure.kramarenko.SummaryTask4.db.Path;
import ua.nure.kramarenko.SummaryTask4.db.derby.ManufacturerDb;
import ua.nure.kramarenko.SummaryTask4.db.derby.ProductDb;
import ua.nure.kramarenko.SummaryTask4.db.entity.Product;
import ua.nure.kramarenko.SummaryTask4.db.enums.Availability;
import ua.nure.kramarenko.SummaryTask4.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductSaveCommand extends Command {

	private static final long serialVersionUID = 7732286214029478505L;

	private static final Logger LOG = Logger
			.getLogger(ProductSaveCommand.class);

	public ProductSaveCommand() {

	}

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		LOG.debug("Command starts");

		String name = request.getParameter("name");
		String manufacturer = request.getParameter("manufacturer");
		ManufacturerDb manufacturerDb = new ManufacturerDb();

		int manufacturerId = manufacturerDb.getManufacturer(manufacturer)
				.getId();
		int productId = Integer.parseInt(request.getParameter("product_id"));
		int categoryId = Integer.parseInt(request.getParameter("category"));
		double price = Double.parseDouble(request.getParameter("price"));
		String img = request.getParameter("img");
		String availability = request.getParameter("availability");
		int availabilityId = Availability.valueOf(availability).ordinal();
		String description = request.getParameter("description");

		Product product = new Product();
		product.setCategoryId(categoryId);
		product.setDescription(description);
		product.setId(productId);
		product.setManufacturerId(manufacturerId);
		product.setName(name);
		product.setPrice(price);
		product.setImg(img);
		product.setAvailabilityId(availabilityId);
		ProductDb productDb = new ProductDb();
		product = productDb.updateProduct(product);

		return Path.COMMAND_PRODUCT_EDIT + "&product_id=" + product.getId();
	}
}
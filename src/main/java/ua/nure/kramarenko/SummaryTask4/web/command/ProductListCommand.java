package ua.nure.kramarenko.SummaryTask4.web.command;

import org.apache.log4j.Logger;
import ua.nure.kramarenko.SummaryTask4.db.Path;
import ua.nure.kramarenko.SummaryTask4.db.bean.ManufacturerBean;
import ua.nure.kramarenko.SummaryTask4.db.bean.product.ProductListBean;
import ua.nure.kramarenko.SummaryTask4.db.derby.ManufacturerDb;
import ua.nure.kramarenko.SummaryTask4.db.derby.ProductListBeanDb;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ProductListCommand extends Command {

	private static final long serialVersionUID = 7732286214029478505L;

	private static final Logger LOG = Logger
			.getLogger(ProductListCommand.class);

	public ProductListCommand() {

	}

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		LOG.debug("Command starts");

		HttpSession session = request.getSession();

		String category = request.getParameter("category");
		if (category != null) {
			updateSelectedCategory(request, session, category);

		} else {
			updateManufacturersList(request, session,
					request.getParameterValues("manufacturer_id"), 0);
		}
		String sort = request.getParameter("sort");
		if (sort != null) {
			session.setAttribute("sort", sort);
		}

		updatePrices(request, session);

		updateProductsList(session, request, null);
		session.setAttribute("page", "productList");

		LOG.debug("Command finished");

		return Path.PAGE_LIST_PRODUCTS;
	}

	public void updatePrices(HttpServletRequest request, HttpSession session) {
		double maxValue = 1000;
		String max = request.getParameter("max");
		if (max != null && !max.equals("")) {
			try {
				maxValue = Double.parseDouble(max.substring(1, max.length()));
				request.setAttribute("maxValue", String.valueOf(maxValue));
			} catch (Exception e) {
				LOG.trace(e);
			}
		}

		double minValue = 1;

		String min = request.getParameter("min");
		if (min != null && !min.equals("")) {
			try {
				minValue = Double.parseDouble(min.substring(1, min.length()));
				request.setAttribute("minValue", String.valueOf(minValue));
			} catch (Exception e) {
				LOG.trace(e);
			}
		}

	}

	public void updateManufacturersList(HttpServletRequest request,
			HttpSession session, String[] chosenManufacturers,
			int selectedCategory) {
		System.out.println("update manList");
		ManufacturerDb manufacturerDb = new ManufacturerDb();
		List<ManufacturerBean> manufacturersList = null;

		if (manufacturersList == null) {
			manufacturersList = manufacturerDb
					.getCategoryManufacturers(selectedCategory);
			LOG.trace("Found in DB: manufacturersList --> " + manufacturersList);
		}
		for (int i = 0; i < manufacturersList.size(); i++) {
			manufacturersList.get(i).setSelected(false);
		}
		if (chosenManufacturers != null) {
			for (int i = 0; i < manufacturersList.size(); i++) {
				for (String cm : chosenManufacturers) {
					if (manufacturersList.get(i).getId() == Integer.parseInt(cm
							.trim())) {
						manufacturersList.get(i).setSelected(true);
					}
				}
			}
		}

		session.setAttribute("manufacturersList", manufacturersList);
	}

	public int getSelectedCategoryId(HttpSession session) {
		int selectedCategory = 0;
		if (session.getAttribute("selectedCategory") != null) {
			selectedCategory = (int) session.getAttribute("selectedCategory");
		}
		LOG.trace("Selected category id = " + selectedCategory);
		return selectedCategory;
	}

	@SuppressWarnings("unchecked")
	public List<ManufacturerBean> getManufacturersFilter(HttpSession session,
			List<ManufacturerBean> choosenManufacturers) {
		List<ManufacturerBean> manufacturersFilter = null;
		if (choosenManufacturers == null) {
			manufacturersFilter = (List<ManufacturerBean>) session
					.getAttribute("manufacturersList");
		} else {
			manufacturersFilter = choosenManufacturers;
		}
		return manufacturersFilter;
	}

	public void updateProductsList(HttpSession session,
			HttpServletRequest request,
			List<ManufacturerBean> choosenManufacturers) {

		List<ManufacturerBean> manufacturersFilter = getManufacturersFilter(
				session, choosenManufacturers);

		String minPrice = (String) request.getAttribute("minValue");
		String maxPrice = (String) request.getAttribute("maxValue");

		LOG.trace("Chosen manufacturers --> " + manufacturersFilter);

		ProductListBeanDb productListBeanDb = new ProductListBeanDb();

		int selectedCategory = getSelectedCategoryId(session);

		ProductListBean productItems = productListBeanDb
				.getCategoryProductsByManufacturers(selectedCategory, minPrice,
						maxPrice, manufacturersFilter);

		LOG.trace("Found in DB: productItemsList --> " + productItems);

		String sort = (String) session.getAttribute("sort");
		productItems.sort(sort);

		request.setAttribute("productItems", productItems.getItems());
		LOG.trace("Set the request attribute: productItems --> " + productItems);
	}

	public void updateSelectedCategory(HttpServletRequest request,
			HttpSession session, String category) {

		int categoryId = Integer.parseInt(category);

		session.setAttribute("selectedCategory", categoryId);

		updateManufacturersList(request, session, null, categoryId);

	}
}
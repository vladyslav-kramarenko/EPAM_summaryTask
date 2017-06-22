package ua.nure.kramarenko.SummaryTask4.db.derby;

import org.apache.log4j.Logger;
import ua.nure.kramarenko.SummaryTask4.db.Fields;
import ua.nure.kramarenko.SummaryTask4.db.bean.ManufacturerBean;
import ua.nure.kramarenko.SummaryTask4.db.bean.product.CompleteCommandProduct;
import ua.nure.kramarenko.SummaryTask4.db.bean.product.ProductListBean;
import ua.nure.kramarenko.SummaryTask4.db.bean.product.ProductListItem;
import ua.nure.kramarenko.SummaryTask4.db.enums.Availability;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Work with product, manufacturer and category entities in dataBase
 * 
 * @author Vlad Kramarenko
 *
 */
public class ProductListBeanDb {
	private static final String SQL_GET_PRODUCT_LIST_BEAN = "SELECT p.id, p.price, p.name, "
			+ "m.name as manufacturer, c.name as category, p.description ,"
			+ "p.availability_id as availability, p.img"
			+ " FROM products p"
			+ " join manufacturers m on p.manufacturer_id=m.id"
			+ " join categories c on p.category_id=c.id";

	private static final String SQL_GET_COMPLETE_COMMAND_PRODUCTS = "SELECT p.id, p.name, "
			+ "m.name as manufacturer"
			+ " FROM products p"
			+ " join manufacturers m on p.manufacturer_id=m.id";

	private static final String SQL_GET_PRODUCT_LIST_ITEM = "SELECT p.id, p.price, p.name, m.name as manufacturer, c.name as category, p.description, p.availability_id as availability, p.img"
			+ " FROM products p"
			+ " join manufacturers m on p.manufacturer_id=m.id"
			+ " join categories c on p.category_id=c.id" + " WHERE p.id=";
	private static final String SQL_CATEGORY_FILTER = " c.id=";
	private static final String SQL_MIN_PRICE_FILTER = " p.price > ";
	private static final String SQL_MAX_PRICE_FILTER = " p.price < ";
	private static final String SQL_MANUFACTURERS_FILTER = " m.id in (";

	private static final Logger LOG = Logger.getLogger(ProductListBeanDb.class);

	/**
	 * Select all products for virtual table
	 * 
	 * @return productListbean
	 */
	public ProductListBean getProductListBeans() {
		ProductListBean productsList = new ProductListBean();
		DBManager dbManager = DBManager.getInstance();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = dbManager.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_GET_PRODUCT_LIST_BEAN);
			while (rs.next()) {
				productsList.add(extractProductListBean(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			dbManager.rollback(con);
			LOG.error(ex);
		} finally {
			dbManager.close(rs);
			dbManager.close(stmt);
			dbManager.close(con);
		}
		return productsList;
	}

	public List<CompleteCommandProduct> getCompleteCommandProdcuts() {
		List<CompleteCommandProduct> productsList = new ArrayList<CompleteCommandProduct>();
		DBManager dbManager = DBManager.getInstance();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = dbManager.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_GET_COMPLETE_COMMAND_PRODUCTS);
			while (rs.next()) {
				productsList.add(extractCompletCommandProduct(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			dbManager.rollback(con);
			LOG.error(ex);
		} finally {
			dbManager.close(rs);
			dbManager.close(stmt);
			dbManager.close(con);
		}
		return productsList;
	}

	/**
	 * Extracts a CompleteCommandProductsList from the result set.
	 * 
	 * @param rs
	 *            Result set from which a CompleteCommandProductList will be
	 *            extracted.
	 * @return CompleteCommandProduct object
	 */

	private CompleteCommandProduct extractCompletCommandProduct(ResultSet rs)
			throws SQLException {
		CompleteCommandProduct bean = new CompleteCommandProduct();
		bean.setId(rs.getInt(Fields.ENTITY_ID));
		bean.setManufacturer(rs.getString(Fields.PRODUCT_ITEM_MANUFACTURER));
		bean.setName(rs.getString(Fields.PRODUCT_ITEM_NAME));
		return bean;
	}

	/**
	 * Search for product for virtual table by product id
	 * 
	 * @param productId
	 *            product id
	 * @return ProductListItem
	 */
	public ProductListItem getProductItem(int productId) {
		ProductListItem product = new ProductListItem();
		DBManager dbManager = DBManager.getInstance();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = dbManager.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_GET_PRODUCT_LIST_ITEM + productId);
			System.out.println("get product : " + SQL_GET_PRODUCT_LIST_ITEM
					+ productId);
			rs.next();
			product = extractProductListBean(rs);
			con.commit();
		} catch (SQLException ex) {
			dbManager.rollback(con);
			LOG.error(ex);
		} finally {
			dbManager.close(rs);
			dbManager.close(stmt);
			dbManager.close(con);
		}
		return product;
	}

	/**
	 * Extracts a user order bean from the result set.
	 * 
	 * @param rs
	 *            Result set from which a user order bean will be extracted.
	 * @return UserOrderBean object
	 */

	private ProductListItem extractProductListBean(ResultSet rs)
			throws SQLException {
		ProductListItem bean = new ProductListItem();
		bean.setId(rs.getInt(Fields.ENTITY_ID));
		bean.setPrice(rs.getInt(Fields.PRODUCT_ITEM_PRICE));
		bean.setManufacturer(rs.getString(Fields.PRODUCT_ITEM_MANUFACTURER));
		bean.setCategory(rs.getString(Fields.PRODUCT_ITEM_CATEGORY));
		bean.setName(rs.getString(Fields.PRODUCT_ITEM_NAME));
		bean.setAvailability(Availability.values()[rs
				.getInt(Fields.PRODUCT_ITEM_AVAILABILITY)].toString());
		bean.setImg(rs.getString(Fields.PRODUCT_ITEM_IMG));
		return bean;
	}

	/**
	 * Search for product by parameters
	 * 
	 * @param categoryId
	 *            category id
	 * @param minPrice
	 *            minimum price range
	 * @param maxPrice
	 *            maximum price range
	 * @param manufacturers
	 *            list with manufacturers
	 * @return ProductListBean
	 */
	public ProductListBean getCategoryProductsByManufacturers(int categoryId,
			String minPrice, String maxPrice,
			List<ManufacturerBean> manufacturers) {
		ProductListBean productsList = new ProductListBean();
		DBManager dbManager = DBManager.getInstance();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = dbManager.getConnection();

			StringBuilder manufacturersString = new StringBuilder(
					SQL_GET_PRODUCT_LIST_BEAN);
			boolean isFirstFilter = true;
			if (categoryId != 0) {
				isFirstFilter = false;
				manufacturersString.append(" WHERE ");
				manufacturersString.append(SQL_CATEGORY_FILTER);
				manufacturersString.append(categoryId);
			}
			if (hasSelectedManufacturers(manufacturers)) {
				manufacturersString.append(getWord(isFirstFilter));
				isFirstFilter = false;
				manufacturersString.append(SQL_MANUFACTURERS_FILTER);
				for (int i = 0; i < manufacturers.size(); i++) {
					if (manufacturers.get(i).isSelected()) {
						manufacturersString
								.append(manufacturers.get(i).getId()).append(
										",");
					}
				}
				manufacturersString.delete(manufacturersString.length() - 1,
						manufacturersString.length());
				manufacturersString.append(")");
			}
			if (minPrice != null && !minPrice.equals("")) {
				manufacturersString.append(getWord(isFirstFilter));
				isFirstFilter = false;
				manufacturersString.append(SQL_MIN_PRICE_FILTER);
				manufacturersString.append(minPrice);
			}
			if (maxPrice != null && !maxPrice.equals("")) {
				manufacturersString.append(getWord(isFirstFilter));
				isFirstFilter = false;
				manufacturersString.append(SQL_MAX_PRICE_FILTER);
				manufacturersString.append(maxPrice);
			}
			LOG.trace("SQL --> " + manufacturersString.toString());

			pstmt = con.prepareStatement(manufacturersString.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				productsList.add(extractProductListBean(rs));
			}
			con.commit();
			LOG.trace("SQL --> Found" + productsList.size() + " elements");
		} catch (SQLException ex) {
			dbManager.rollback(con);
			LOG.error(ex);
		} finally {
			dbManager.close(rs);
			dbManager.close(pstmt);
			dbManager.close(con);
		}
		return productsList;
	}

	/**
	 * Get word for query
	 * 
	 * @param isFirst
	 *            is this a wirst filter in wuery
	 * @return String word
	 */
	public String getWord(boolean isFirst) {
		if (isFirst) {
			return " WHERE ";
		} else {
			return " AND ";
		}
	}

	/**
	 * Search for selected manufacturers in list
	 * 
	 * @param manufacturers
	 *            list with manufacturers
	 * @return true if in the list some manufacturers are selected and false if
	 *         no one selected
	 */
	public boolean hasSelectedManufacturers(List<ManufacturerBean> manufacturers) {
		if (manufacturers == null || manufacturers.size() == 0) {
			return false;
		}
		for (ManufacturerBean mb : manufacturers) {
			if (mb.isSelected()) {
				return true;
			}
		}

		return false;
	}
}

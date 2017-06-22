package ua.nure.kramarenko.SummaryTask4.db.derby;

import org.apache.log4j.Logger;
import ua.nure.kramarenko.SummaryTask4.db.Fields;
import ua.nure.kramarenko.SummaryTask4.db.bean.UserProductsBean;
import ua.nure.kramarenko.SummaryTask4.db.bean.cart.ShoppingCartItem;
import ua.nure.kramarenko.SummaryTask4.db.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Work with ordersProducts entity in dataBase
 * 
 * @author Vlad Kramarenko
 *
 */
public class OrdersProductsDb {

	private static final Logger LOG = Logger.getLogger(OrderDb.class);

	private static final String SQL_SELECT_ALL_ORDER_PRODUCTS = "SELECT p.id, p.manufacturer_id, p.name, p.description, p.price, p.category_id, op.quantity"
			+ " FROM orders_products op join orders o on op.order_id=o.id join products p on op.product_id=p.id WHERE op.order_id=?";

	private static final String SQL_INSERT_INTO_ORDERS_PRODUCTS = "INSERT INTO orders_products (order_id,product_id,quantity) \n"
			+ "VALUES (?, ?, ?)";

	private static final String SQL_SELECT_PRODUCTS_BY_USER = "SELECT DISTINCT op.product_id "
			+ "as productId, p.name as productName, u.first_name as userFirstName,u.last_name "
			+ "as userLastName, u.id as id"
			+ " FROM orders o "
			+ "join orders_products op on o.id=op.order_id "
			+ "join users u on o.user_id=u.id "
			+ "join products p on op.product_id=p.id";

	public List<UserProductsBean> getAllUserProducts() {
		List<UserProductsBean> productItemList = null;
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_SELECT_PRODUCTS_BY_USER);
			productItemList = parseUSerProductsResultSet(rs);
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error("Cannot obtain user products", ex);
		} finally {
			DBManager.getInstance().close(rs);
			DBManager.getInstance().close(stmt);
			DBManager.getInstance().close(con);
		}
		return productItemList;
	}

	protected List<UserProductsBean> parseUSerProductsResultSet(ResultSet rs)
			throws SQLException {
		List<UserProductsBean> result = new ArrayList<UserProductsBean>();
		UserProductsBean userProductsBean = new UserProductsBean();
		List<Product> products = new ArrayList<Product>();
		Product product = null;
		int lastId = -1;
		int counter = 0;
		while (rs.next()) {
			System.out.println();
			int userId = (rs.getInt(Fields.ENTITY_ID));
			if (lastId != userId) {
				lastId = userId;

				if (counter != 0) {
					userProductsBean.setProducts(products);
					result.add(userProductsBean);
				}

				userProductsBean = new UserProductsBean();
				userProductsBean.setId(userId);
				userProductsBean
						.setUserFirstName(rs.getString("userFirstName"));
				userProductsBean.setUserLastName(rs.getString("userLastName"));
				products = new LinkedList<Product>();
				counter++;
			}
			product = new Product();
			product.setId(rs.getInt("productId"));
			product.setName(rs.getString("productName"));
			products.add(product);

		}
		userProductsBean.setProducts(products);
		result.add(userProductsBean);
		return result;
	}

	/**
	 * Search for all orders product by order id
	 * 
	 * @param orderId
	 *            order id
	 * @return list of ShoppingCartItem
	 */
	public List<ShoppingCartItem> getAllOrderProducts(int orderId) {
		List<ShoppingCartItem> productItemList = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_SELECT_ALL_ORDER_PRODUCTS);
			pstmt.setInt(1, orderId);
			rs = pstmt.executeQuery();
			productItemList = parseResultSet(rs);
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error("Cannot obtain orders products", ex);
		} finally {
			DBManager.getInstance().close(rs);
			DBManager.getInstance().close(pstmt);
			DBManager.getInstance().close(con);
		}
		return productItemList;
	}

	/**
	 * Parse resultSet for select query
	 * 
	 * @param rs
	 *            ResultSet
	 * @return list of shoppingCartItem
	 * @throws SQLException
	 */
	protected List<ShoppingCartItem> parseResultSet(ResultSet rs)
			throws SQLException {
		LinkedList<ShoppingCartItem> result = new LinkedList<ShoppingCartItem>();
		ShoppingCartItem shoppingCartItem = null;
		while (rs.next()) {
			Product product = new Product();
			product.setId(rs.getInt(Fields.ENTITY_ID));
			product.setManufacturerId(rs
					.getInt(Fields.PRODUCT_ITEM_MANUFACTURER_ID));
			product.setName(rs.getString(Fields.PRODUCT_ITEM_NAME));
			product.setDescription(rs
					.getString(Fields.PRODUCT_ITEM_DESCRIPTION));
			product.setPrice(rs.getDouble(Fields.PRODUCT_ITEM_PRICE));
			product.setCategoryId(rs.getInt(Fields.PRODUCT_ITEM_CATEGORY_ID));
			System.out.println(product);
			shoppingCartItem = new ShoppingCartItem(product);
			shoppingCartItem.setQuantity(rs
					.getShort(Fields.ORDERSPRODUCT_QUANTITY));
			result.add(shoppingCartItem);
		}
		return result;
	}

	/**
	 * SetProducts in order
	 * 
	 * @param orderId
	 *            order id
	 * @param cartItems
	 *            products to insert
	 */
	public void setOrdersProducts(int orderId, List<ShoppingCartItem> cartItems) {
		DBManager dbManager = DBManager.getInstance();
		Connection con = null;
		try {
			con = dbManager.getConnection();
			addOrdersProducts(con, orderId, cartItems);
			con.commit();
		} catch (SQLException ex) {
			dbManager.rollback(con);
			LOG.error("Cannot create a user", ex);
		} finally {
			dbManager.close(con);
		}
	}

	/**
	 * Insert products in orders
	 * 
	 * @param con
	 *            database connection
	 * @param orderId
	 *            order id
	 * @param cartItems
	 *            products to insert
	 * @throws SQLException
	 */
	public void addOrdersProducts(Connection con, int orderId,
			List<ShoppingCartItem> cartItems) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			for (ShoppingCartItem sci : cartItems) {
				pstmt = con.prepareStatement(SQL_INSERT_INTO_ORDERS_PRODUCTS);
				int k = 1;
				pstmt.setInt(k++, orderId);
				pstmt.setInt(k++, sci.getProduct().getId());
				pstmt.setShort(k, sci.getQuantity());
				pstmt.executeUpdate();
			}
		} finally {
			DBManager.getInstance().close(pstmt);
		}
	}

}

package ua.nure.kramarenko.SummaryTask4.db.derby;

import org.apache.log4j.Logger;
import ua.nure.kramarenko.SummaryTask4.db.Fields;
import ua.nure.kramarenko.SummaryTask4.db.entity.Product;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Work with product entity in dataBase
 * 
 * @author Vlad Kramarenko
 *
 */
public class ProductDb {

	private static final Logger LOG = Logger.getLogger(ProductDb.class);

	private static final String SQL_SELECT_ALL_PRODUCTS = "SELECT * FROM Products";

	private static final String SQL_INSERT_INTO_PRODUCTS = "INSERT INTO products (manufacturer_id, name, description,availability_id, price, category_id,img) \n"
			+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_SELECT_PRODUCT_BY_ID = "SELECT * FROM products"
			+ " where id = ?";

	private static final String SQL_SELECT_MAX_PRICE = "SELECT MAX(price)as price FROM products";

	private static final String SQL_UPDATE_PRODUCT = "UPDATE products SET manufacturer_id=?, name=?, description=?,availability_id=?, price=?, category_id=?, img=?"
			+ "	WHERE id=?";

	private static final String SQL_DELETE_PRODUCT = "DELETE FROM products WHERE id= ?";

	/**
	 * Search for all products
	 * 
	 * @return list with products
	 */
	public List<Product> getAllProducts() {

		Connection con = null;
		DBManager dbManager = DBManager.getInstance();
		List<Product> productList = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = dbManager.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_SELECT_ALL_PRODUCTS);
			productList = parseResultSet(rs);
			con.commit();
		} catch (SQLException ex) {
			dbManager.rollback(con);
			LOG.error("Cannot obtain products", ex);
		} finally {
			dbManager.close(rs);
			dbManager.close(stmt);
			dbManager.close(con);
		}
		return productList;
	}

	/**
	 * Search product by id
	 * 
	 * @param productItemId
	 *            product id
	 * @return product
	 */
	public Product getProductByID(int productItemId) {
		return getProducts(productItemId, SQL_SELECT_PRODUCT_BY_ID).get(0);
	}

	/**
	 * Search for max price in all product
	 * 
	 * @return max product price
	 */
	public double getMaxPrice() {
		DBManager dbManager = DBManager.getInstance();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		double maxPrice = 0;
		try {

			con = dbManager.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_SELECT_MAX_PRICE);
			while (rs.next()) {
				maxPrice = (rs.getDouble(Fields.PRODUCT_ITEM_PRICE));
			}
			con.commit();
		} catch (SQLException ex) {
			dbManager.rollback(con);
			LOG.error("Cannot obtain max product price", ex);
		} finally {
			dbManager.close(rs);
			dbManager.close(stmt);
			dbManager.close(con);
		}
		return maxPrice;
	}

	/**
	 * Search for products by parameters
	 * 
	 * @param id
	 *            parameter id
	 * @param query
	 *            query
	 * @return list of products
	 */
	public List<Product> getProducts(int id, String query) {
		DBManager dbManager = DBManager.getInstance();
		List<Product> productItemList = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {

			con = dbManager.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			productItemList = parseResultSet(rs);
			con.commit();
		} catch (SQLException ex) {
			dbManager.rollback(con);
			LOG.error("Cannot obtain product", ex);
		} finally {
			dbManager.close(rs);
			dbManager.close(pstmt);
			dbManager.close(con);
		}
		return productItemList;
	}

	/**
	 * Update product
	 * 
	 * @param product
	 *            product to update
	 * @return updated product
	 */
	public Product updateProduct(Product product) {
		DBManager dbManager = DBManager.getInstance();
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			updateProduct(con, product);
			con.commit();
		} catch (SQLException ex) {
			dbManager.rollback(con);
			LOG.error("Cannot update a product", ex);
		} finally {
			dbManager.close(con);
		}
		return product;
	}

	/**
	 * Update product
	 * 
	 * @param con
	 *            database connection
	 * @param product
	 *            product item to update
	 * @return updated product
	 * @throws SQLException
	 */
	public Product updateProduct(Connection con, Product product)
			throws SQLException {
		PreparedStatement pstmt = null;
		try {
			if (product.getId() != 0) {
				pstmt = con.prepareStatement(SQL_UPDATE_PRODUCT);
				LOG.trace("update product with id=" + product.getId());
			} else {
				LOG.trace("add new product");
				pstmt = con.prepareStatement(SQL_INSERT_INTO_PRODUCTS,
						Statement.RETURN_GENERATED_KEYS);
			}
			int k = 1;
			pstmt.setInt(k++, product.getManufacturerId());
			pstmt.setString(k++, product.getName());
			pstmt.setString(k++, product.getDescription());
			pstmt.setInt(k++, product.getAvailabilityId());
			pstmt.setDouble(k++, product.getPrice());
			pstmt.setInt(k++, product.getCategoryId());
			pstmt.setString(k++, product.getImg());

			if (product.getId() != 0) {
				pstmt.setInt(k, product.getId());
				pstmt.executeUpdate();
			} else {
				pstmt.executeUpdate();
				ResultSet keys = pstmt.getGeneratedKeys();
				keys.next();
				product.setId(keys.getInt(1));
				LOG.trace("Create product with id=" + product.getId());
			}

		} finally {
			DBManager.getInstance().close(pstmt);
		}
		return product;
	}

	/**
	 * Delete product
	 * 
	 * @param product
	 *            product to delete
	 * @return boolean true - if delete successful and false if not
	 */
	public boolean deleteProduct(Product product) {

		LOG.trace("Try to delete product with id " + product.getId());
		DBManager dbManager = DBManager.getInstance();
		PreparedStatement pstmt = null;
		Connection con = null;
		try {
			con = dbManager.getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_PRODUCT);
			pstmt.setInt(1, product.getId());
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			dbManager.rollback(con);
			LOG.error("Cannot delete a product with id " + product.getId(), ex);
			return false;
		} finally {
			dbManager.close(pstmt);
			dbManager.close(con);
		}
		return true;
	}

	/**
	 * Parse resultSet for select query
	 * 
	 * @param rs
	 *            resultSet
	 * @return list of products
	 * @throws SQLException
	 */
	protected List<Product> parseResultSet(ResultSet rs) throws SQLException {
		LinkedList<Product> result = new LinkedList<Product>();
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
			product.setImg(rs.getString(Fields.PRODUCT_ITEM_IMG));
			result.add(product);
		}
		return result;
	}

	/**
	 * add new product
	 * 
	 * @param product
	 *            product to add
	 */
	public void addProduct(Product product) {
		updateProduct(product);
	}
}

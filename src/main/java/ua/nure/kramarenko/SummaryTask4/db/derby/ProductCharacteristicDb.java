package ua.nure.kramarenko.SummaryTask4.db.derby;

import org.apache.log4j.Logger;
import ua.nure.kramarenko.SummaryTask4.db.Fields;
import ua.nure.kramarenko.SummaryTask4.db.bean.product.ProductCharacteristicBean;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Work with ProductCharacteristic entity in dataBase
 * 
 * @author Vlad Kramarenko
 *
 */
public class ProductCharacteristicDb {

	private static final Logger LOG = Logger
			.getLogger(ProductCharacteristicDb.class);

	private static final String SQL_UPDATE_PRODUCT_CHARACTIRISTIC_VALUE = "UPDATE productcharacteristic SET value=?"
			+ "	WHERE id=?";

	private static final String SQL_SELECT_ALL_PRODUCT_CHARACTIRISTICS = "SELECT c.name,pc.value,c.description,pc.product_id, pc.id, pc.characteristic_id"
			+ " FROM characteristics c join productcharacteristic pc on c.id=pc.characteristic_id"
			+ " where pc.product_id =";

	private static final String SQL_SELECT_PRODUCT_CHARACTIRISTIC = "SELECT c.name,pc.value,c.description,pc.product_id,pc.id,pc.characteristic_id"
			+ " FROM characteristics c join productcharacteristic pc on c.id=pc.characteristic_id "
			+ " where pc.id=?";

	private static final String SQL_INSERT_INTO_PRODUCT_CHARACTIRISTIC = "INSERT INTO productcharacteristic (characteristic_id, product_id, value) \n"
			+ "VALUES (?, ?, ?)";

	private static final String SQL_DELETE_PRODUCT_CHARACTERISTIC = "DELETE FROM productcharacteristic WHERE id= ?";

	/**
	 * Seacrh for all product Characteristics by product id
	 * 
	 * @param productId
	 *            product id
	 * @return list of ProductCharacteristicBean
	 */
	public List<ProductCharacteristicBean> getAllProductCharactiristics(
			int productId) {
		DBManager dbManager = DBManager.getInstance();

		List<ProductCharacteristicBean> charactiristicList = null;
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = dbManager.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_SELECT_ALL_PRODUCT_CHARACTIRISTICS
					+ productId);
			charactiristicList = parseBeanResultSet(rs);
			con.commit();
		} catch (SQLException ex) {
			dbManager.rollback(con);
			LOG.error("Cannot obtain product characteristics", ex);
		} finally {
			dbManager.close(rs);
			dbManager.close(stmt);
			dbManager.close(con);
		}
		return charactiristicList;
	}

	/**
	 * Update value of product Characteristic
	 * 
	 * @param pcBean
	 *            ProductCharacteristicBean
	 */
	public void updateProductCharachteristicValue(
			ProductCharacteristicBean pcBean) {
		DBManager dbManager = DBManager.getInstance();
		Connection con = null;
		try {
			con = dbManager.getConnection();
			updateProductCharactiristicValue(con, pcBean);
			con.commit();
		} catch (SQLException ex) {
			dbManager.rollback(con);
			LOG.error(
					"Cannot update a charactiristic with id " + pcBean.getId()
							+ " for product with id " + pcBean.getProductId(),
					ex);
		} finally {
			dbManager.close(con);
		}
	}

	/**
	 * Update product characteristic value
	 * 
	 * @param con
	 *            database connection
	 * @param pcBean
	 *            ProductCharacteristicBean
	 * @throws SQLException
	 */
	public void updateProductCharactiristicValue(Connection con,
			ProductCharacteristicBean pcBean) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con
					.prepareStatement(SQL_UPDATE_PRODUCT_CHARACTIRISTIC_VALUE);
			int k = 1;
			pstmt.setString(k++, pcBean.getValue());
			pstmt.setInt(k, pcBean.getId());
			pstmt.executeUpdate();
		} finally {
			DBManager.getInstance().close(pstmt);
		}
	}

	/**
	 * Delete product characteristic
	 * 
	 * @param characteristic
	 *            characteristic to delete
	 */
	public void deleteProductCharacteristic(
			ProductCharacteristicBean characteristic) {
		PreparedStatement pstmt = null;
		DBManager dbManager = DBManager.getInstance();
		Connection con = null;
		try {
			con = dbManager.getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_PRODUCT_CHARACTERISTIC);
			pstmt.setInt(1, characteristic.getId());
			pstmt.executeUpdate();
			LOG.trace("Delete product characteristic with id="
					+ characteristic.getId());
		} catch (SQLException ex) {
			dbManager.rollback(con);
			LOG.error(
					"Cannot delete characteristic with id "
							+ characteristic.getId(), ex);
		} finally {
			dbManager.close(pstmt);
			dbManager.close(con);
		}
	}

	/**
	 * Parse resultSet for Select query
	 * 
	 * @param rs
	 *            ResultSet
	 * @return list of ProductCharacteristicbean
	 * @throws SQLException
	 */
	protected List<ProductCharacteristicBean> parseBeanResultSet(ResultSet rs)
			throws SQLException {
		LinkedList<ProductCharacteristicBean> result = new LinkedList<ProductCharacteristicBean>();
		while (rs.next()) {
			ProductCharacteristicBean characteristic = new ProductCharacteristicBean();
			characteristic.setId(rs.getInt(Fields.ENTITY_ID));
			characteristic.setProductId(rs
					.getInt(Fields.CHARACTERISTIC_PRODUCT_ID));
			characteristic.setCharacteristicId(rs
					.getInt(Fields.PRODUCT_CHARACTERISTIC_ID));
			characteristic.setName(rs.getString(Fields.CHARACTERISTIC_NAME));
			characteristic.setValue(rs.getString(Fields.CHARACTERISTIC_VALUE));
			characteristic.setDescription(rs
					.getString(Fields.CHARACTERISTIC_DESCRIPTION));
			result.add(characteristic);
		}
		return result;
	}

	/**
	 * Add new product characteristic
	 * 
	 * @param pcBean
	 *            productCharacteristicBean to add
	 */
	public void addProductCharactiristic(ProductCharacteristicBean pcBean) {
		DBManager dbManager = DBManager.getInstance();

		Connection con = null;
		try {
			con = dbManager.getConnection();
			addProductCharactiristic(con, pcBean);
			con.commit();
		} catch (SQLException ex) {
			dbManager.rollback(con);
			LOG.error("Cannot add product charactiristic", ex);
		} finally {
			dbManager.close(con);
		}
	}

	/**
	 * Add product characteristic
	 * 
	 * @param con
	 *            database connection
	 * @param pcBean
	 *            productCharacteristicBean to add
	 * @throws SQLException
	 */
	public void addProductCharactiristic(Connection con,
			ProductCharacteristicBean pcBean) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con
					.prepareStatement(SQL_INSERT_INTO_PRODUCT_CHARACTIRISTIC);
			int k = 1;
			pstmt.setInt(k++, pcBean.getCharacteristicId());
			pstmt.setInt(k++, pcBean.getProductId());
			pstmt.setString(k, pcBean.getValue());
			pstmt.executeUpdate();
		} finally {
			DBManager.getInstance().close(pstmt);
		}
	}

	/**
	 * Search product characteristic by id
	 * 
	 * @param id
	 *            product characteristic id
	 * @return productCharacteristicBean
	 */
	public ProductCharacteristicBean getProductCharactiristic(int id) {
		DBManager dbManager = DBManager.getInstance();

		ProductCharacteristicBean characteristic = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = dbManager.getConnection();
			pstmt = con.prepareStatement(SQL_SELECT_PRODUCT_CHARACTIRISTIC);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			characteristic = parseBeanResultSet(rs).get(0);
			con.commit();
		} catch (SQLException ex) {
			dbManager.rollback(con);
			LOG.error("Cannot obtain product characteristic", ex);
		} finally {
			dbManager.close(rs);
			dbManager.close(pstmt);
			dbManager.close(con);
		}
		return characteristic;
	}
}

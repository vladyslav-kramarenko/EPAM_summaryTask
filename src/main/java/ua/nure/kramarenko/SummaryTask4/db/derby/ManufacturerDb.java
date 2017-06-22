package ua.nure.kramarenko.SummaryTask4.db.derby;

import org.apache.log4j.Logger;
import ua.nure.kramarenko.SummaryTask4.db.Fields;
import ua.nure.kramarenko.SummaryTask4.db.bean.ManufacturerBean;
import ua.nure.kramarenko.SummaryTask4.db.entity.Manufacturer;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Work with manufacturer entity in dataBase
 * 
 * @author Vlad Kramarenko
 *
 */
public class ManufacturerDb {

	private static final Logger LOG = Logger.getLogger(DBManager.class);

	private static final String SQL_SELECT_ALL_MANUFACTURERS = "SELECT * FROM manufacturers";

	private static final String SQL_SELECT_CATEGORY_MANUFACTURERS = "SELECT DISTINCT p.manufacturer_id as id, m.name"
			+ " FROM products p JOIN manufacturers m on p.manufacturer_id = m.id JOIN categories c on p.category_id=c.id";

	private static final String SQL_INSERT_INTO_MANUFACTURERS = "INSERT INTO manufacturers (id, name) \n"
			+ "VALUES (?, ?)";

	private static final String SQL_SELECT_MANUFACTURER_BY_ID = "SELECT * FROM manufacturers where id = ?";

	private static final String SQL_SELECT_MANUFACTURER_BY_NAME = "SELECT * FROM manufacturers where name = ?";

	private static final String SQL_UPDATE_MANUFACTURER = "UPDATE manufacturers SET name=?"
			+ "	WHERE id=?";

	private static final String SQL_DELETE_MANUFACTURER = "DELETE FROM manufacturers WHERE id= ?";

	/**
	 * Search for all manufacturers
	 * 
	 * @return list of manufacturers
	 */
	public List<Manufacturer> getAllManufacturers() {
		DBManager dbManager = DBManager.getInstance();

		List<Manufacturer> manufacturerList = null;
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = dbManager.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_SELECT_ALL_MANUFACTURERS);
			manufacturerList = parseResultSet(rs);
			con.commit();
		} catch (SQLException ex) {
			dbManager.rollback(con);
			LOG.error("Cannot obtain categories", ex);
		} finally {
			dbManager.close(rs);
			dbManager.close(stmt);
			dbManager.close(con);
		}
		return manufacturerList;
	}

	/**
	 * Search Manufacturers by category
	 * 
	 * @param categoryId
	 *            Searched category id
	 * @return category
	 */
	public List<ManufacturerBean> getCategoryManufacturers(int categoryId) {
		DBManager dbManager = DBManager.getInstance();

		List<ManufacturerBean> manufacturerList = null;
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = dbManager.getConnection();
			stmt = con.createStatement();
			if (categoryId != 0) {
				rs = stmt.executeQuery(SQL_SELECT_CATEGORY_MANUFACTURERS
						+ " WHERE c.id=" + categoryId);
			} else {
				rs = stmt.executeQuery(SQL_SELECT_CATEGORY_MANUFACTURERS);
			}
			manufacturerList = parseBeanResultSet(rs);
			con.commit();
		} catch (SQLException ex) {
			dbManager.rollback(con);
			LOG.error("Cannot obtain categories", ex);
		} finally {
			dbManager.close(rs);
			dbManager.close(stmt);
			dbManager.close(con);
		}
		return manufacturerList;
	}

	/**
	 * Search manufacturer by id
	 * 
	 * @param manufacturerId
	 * @return manufacturer item
	 */
	public Manufacturer getManufacturer(int manufacturerId) {
		DBManager dbManager = DBManager.getInstance();
		Manufacturer manufacturer = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_SELECT_MANUFACTURER_BY_ID);
			pstmt.setInt(1, manufacturerId);
			rs = pstmt.executeQuery();
			manufacturer = parseResultSet(rs).get(0);
			con.commit();
			LOG.trace("Obtain manufacturer with id " + manufacturerId);
		} catch (SQLException ex) {
			dbManager.rollback(con);
			LOG.error("Cannot obtain manufacturer by id " + manufacturerId, ex);
		} finally {
			dbManager.close(rs);
			dbManager.close(pstmt);
			dbManager.close(con);
		}
		return manufacturer;
	}

	/**
	 * Search manufacturer by name
	 * 
	 * @param name
	 *            manufacturer name
	 * @return manufacturer item
	 */
	public Manufacturer getManufacturer(String name) {
		DBManager dbManager = DBManager.getInstance();
		Manufacturer manufacturer = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_SELECT_MANUFACTURER_BY_NAME);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			manufacturer = parseResultSet(rs).get(0);
			con.commit();
		} catch (Exception ex) {
			dbManager.rollback(con);
			LOG.error("Cannot obtain manufacturer by name " + name, ex);
		} finally {

			dbManager.close(rs);
			dbManager.close(pstmt);
			dbManager.close(con);
		}
		return manufacturer;
	}

	/**
	 * Update manufacturer item
	 * 
	 * @param manufacturer
	 *            manufacturer to update
	 */
	public void updateManufacturer(Manufacturer manufacturer) {
		DBManager dbManager = DBManager.getInstance();
		Connection con = null;
		try {
			con = dbManager.getConnection();
			updateManufacturer(con, manufacturer);
			con.commit();
		} catch (SQLException ex) {
			dbManager.rollback(con);
			LOG.error(
					"Cannot update a manufacturer with id "
							+ manufacturer.getId(), ex);
		} finally {
			dbManager.close(con);
		}
	}

	/**
	 * Update manufacturer
	 * 
	 * @param con
	 *            database connection
	 * @param manufacturer
	 *            manufacturer to update
	 * @throws SQLException
	 */
	public void updateManufacturer(Connection con, Manufacturer manufacturer)
			throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL_UPDATE_MANUFACTURER);
			int k = 1;
			pstmt.setString(k++, manufacturer.getName());
			pstmt.setInt(k, manufacturer.getId());
			pstmt.executeUpdate();
		} finally {
			DBManager.getInstance().close(pstmt);
		}
	}

	/**
	 * Delete manufacturer
	 * 
	 * @param manufacturer
	 *            manufacturer to delete
	 */
	public void deleteManufacturer(Manufacturer manufacturer) {
		PreparedStatement pstmt = null;
		DBManager dbManager = DBManager.getInstance();
		Connection con = null;
		try {
			con = dbManager.getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_MANUFACTURER);
			pstmt.setInt(1, manufacturer.getId());
			pstmt.executeUpdate();
			con.commit();
			LOG.trace("Delete manufacturer with id " + manufacturer.getId());
		} catch (SQLException ex) {
			dbManager.rollback(con);
			LOG.error(
					"Cannot delete a manufacturer with id "
							+ manufacturer.getId(), ex);
		} finally {
			dbManager.close(pstmt);
			dbManager.close(con);
		}
	}

	/**
	 * Parse ResultSet for Select Manufacturer Query
	 * 
	 * @param rs
	 *            ResultSet
	 * @return list of manufacturer
	 * @throws SQLException
	 */
	protected List<Manufacturer> parseResultSet(ResultSet rs)
			throws SQLException {
		LinkedList<Manufacturer> result = new LinkedList<Manufacturer>();
		while (rs.next()) {
			Manufacturer manufacturer = new Manufacturer();
			manufacturer.setId(rs.getInt(Fields.ENTITY_ID));
			manufacturer.setName(rs.getString(Fields.MANUFACTURER_NAME));
			result.add(manufacturer);
		}
		return result;
	}

	/**
	 * Parse ResultSet for select ManufacturerBean Query
	 * 
	 * @param rs
	 *            ResultSet
	 * @return return list of manufacturerBean
	 * @throws SQLException
	 */
	protected List<ManufacturerBean> parseBeanResultSet(ResultSet rs)
			throws SQLException {
		LinkedList<ManufacturerBean> result = new LinkedList<ManufacturerBean>();
		while (rs.next()) {
			ManufacturerBean manufacturer = new ManufacturerBean();
			manufacturer.setId(rs.getInt(Fields.ENTITY_ID));
			manufacturer.setName(rs.getString(Fields.MANUFACTURER_NAME));
			result.add(manufacturer);
		}
		return result;
	}

	/**
	 * Add new manufacturer
	 * 
	 * @param manufacturer
	 *            manufacturer to add
	 */
	public void addManufacturer(Manufacturer manufacturer) {
		DBManager dbManager = DBManager.getInstance();
		Connection con = null;
		try {
			con = dbManager.getConnection();
			int newManufacturerId = getMaxId(con) + 1;
			manufacturer.setId(newManufacturerId);
			addManufacturer(con, manufacturer);
			con.commit();
		} catch (SQLException ex) {
			dbManager.rollback(con);
			LOG.error("Cannot create a user", ex);
		} finally {
			dbManager.close(con);
		}
	}

	/**
	 * Add new manufacturer
	 * 
	 * @param con
	 *            database connection
	 * @param manufacturer
	 *            manufacturer to add
	 * @throws SQLException
	 */
	public void addManufacturer(Connection con, Manufacturer manufacturer)
			throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL_INSERT_INTO_MANUFACTURERS);
			int k = 1;
			pstmt.setInt(k++, manufacturer.getId());
			pstmt.setString(k, manufacturer.getName());
			pstmt.executeUpdate();
		} finally {
			DBManager.getInstance().close(pstmt);
		}
	}

	/**
	 * Return max manufacturer id
	 * 
	 * @param con
	 *            database connection
	 * @return max identificator
	 * @throws SQLException
	 */
	public int getMaxId(Connection con) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		int maxId = -1;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT MAX(id) as id FROM manufacturers");
			rs.next();
			maxId = rs.getInt("id");
			con.commit();
		} finally {
			DBManager.getInstance().close(rs);
			DBManager.getInstance().close(stmt);
		}
		return maxId;
	}

}

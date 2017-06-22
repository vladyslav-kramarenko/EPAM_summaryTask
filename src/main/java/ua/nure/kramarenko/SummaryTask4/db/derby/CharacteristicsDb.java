package ua.nure.kramarenko.SummaryTask4.db.derby;

import org.apache.log4j.Logger;
import ua.nure.kramarenko.SummaryTask4.db.Fields;
import ua.nure.kramarenko.SummaryTask4.db.entity.Characteristic;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Work with characteristic entity in dataBase
 * 
 * @author Vlad Kramarenko
 *
 */
public class CharacteristicsDb {

	private static final Logger LOG = Logger.getLogger(CharacteristicsDb.class);

	private static final String SQL_SELECT_CHARACTERISTIC_BY_ID = "SELECT * FROM characteristics WHERE id=?";

	private static final String SQL_SELECT_ALL_CHARACTERISTICS = "SELECT * FROM characteristics";

	private static final String SQL_INSERT_INTO_CHARACTERISTICS = "INSERT INTO characteristics (id, name, description) values(?,?,?)";

	private static final String SQL_UPDATE_CHARACTIRISTIC = "UPDATE characteristics SET name=?, description=? "
			+ "	WHERE id=?";

	private static final String SQL_DELETE_CHARACTERISTIC = "DELETE FROM characteristics WHERE id=";

	/**
	 * Update characteristic item
	 * 
	 * @param characteristic
	 *            characteristic to update
	 */
	public void updateCharacteristic(Characteristic characteristic) {
		DBManager dbManager = DBManager.getInstance();
		Connection con = null;
		try {
			con = dbManager.getConnection();
			updateCharacteristic(con, characteristic);
			con.commit();
		} catch (SQLException ex) {
			dbManager.rollback(con);
			LOG.error("Cannot update a characteristic with id "
					+ characteristic.getId(), ex);
		} finally {
			dbManager.close(con);
		}
	}

	/**
	 * Update characteristic item
	 * 
	 * @param con
	 *            database connection
	 * @param characteristic
	 *            characteristic to update
	 * @throws SQLException
	 */
	public void updateCharacteristic(Connection con,
			Characteristic characteristic) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL_UPDATE_CHARACTIRISTIC);
			int k = 1;
			pstmt.setString(k++, characteristic.getName());
			pstmt.setString(k++, characteristic.getDescription());
			pstmt.setInt(k, characteristic.getId());
			pstmt.executeUpdate();
		} finally {
			DBManager.getInstance().close(pstmt);
		}
	}

	/**
	 * Add new characteristic
	 * 
	 * @param characteristic
	 *            characteristic to add
	 */
	public void addCharacteristic(Characteristic characteristic) {
		DBManager dbManager = DBManager.getInstance();
		Connection con = null;
		try {
			con = dbManager.getConnection();
			String sql = "SELECT MAX(id) as id FROM characteristics";
			int newCharacteristicId = dbManager.getMaxId(con, sql) + 1;
			characteristic.setId(newCharacteristicId);
			addCharacteristic(con, characteristic);
			con.commit();
		} catch (SQLException ex) {
			LOG.error("Cannot add characteristic", ex);
			dbManager.rollback(con);
		} finally {
			dbManager.close(con);
		}
	}

	/**
	 * Add new characteristic
	 * 
	 * @param con
	 *            database connection
	 * @param characteristic
	 *            characteristic to add
	 * @throws SQLException
	 */
	public void addCharacteristic(Connection con, Characteristic characteristic)
			throws SQLException {
		PreparedStatement pstmt = null;
		try {
			LOG.trace(SQL_INSERT_INTO_CHARACTERISTICS);
			pstmt = con.prepareStatement(SQL_INSERT_INTO_CHARACTERISTICS);
			int k = 1;
			pstmt.setInt(k++, characteristic.getId());
			pstmt.setString(k++, characteristic.getName());
			pstmt.setString(k, characteristic.getDescription());
			pstmt.executeUpdate();
		} finally {
			DBManager.getInstance().close(pstmt);
		}
	}

	/**
	 * Search characteristic by id
	 * 
	 * @param characteristicId
	 *            searched characteristic id
	 * @return characteristic
	 */
	public Characteristic getCharacteristic(int characteristicId) {
		DBManager dbManager = DBManager.getInstance();
		Characteristic characteristic = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = dbManager.getConnection();
			pstmt = con.prepareStatement(SQL_SELECT_CHARACTERISTIC_BY_ID);
			pstmt.setInt(1, characteristicId);
			rs = pstmt.executeQuery();
			characteristic = parseResultSet(rs).get(0);
			con.commit();
		} catch (SQLException ex) {
			dbManager.rollback(con);
			LOG.error("Cannot obtain characteristic by id " + characteristicId,
					ex);
		} finally {
			dbManager.close(rs);
			dbManager.close(pstmt);
			dbManager.close(con);
		}
		return characteristic;
	}

	/**
	 * Search for all characteristic
	 * 
	 * @return list with all characteristics
	 */
	public List<Characteristic> getAllCharacteristics() {
		DBManager dbManager = DBManager.getInstance();

		List<Characteristic> characteristicsList = null;
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {

			con = dbManager.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_SELECT_ALL_CHARACTERISTICS);
			characteristicsList = parseResultSet(rs);
			con.commit();
		} catch (SQLException ex) {
			dbManager.rollback(con);
			LOG.error("Cannot obtain characteristics", ex);
		} finally {
			dbManager.close(rs);
			dbManager.close(stmt);
			dbManager.close(con);
		}
		return characteristicsList;
	}

	/**
	 * Parse ResultSet for Select Quesry
	 * 
	 * @param rs
	 *            ResultSet
	 * @return list with characteristics
	 * @throws SQLException
	 */
	protected List<Characteristic> parseResultSet(ResultSet rs)
			throws SQLException {
		LinkedList<Characteristic> result = new LinkedList<Characteristic>();
		while (rs.next()) {
			Characteristic characteristic = new Characteristic();
			characteristic.setId(rs.getInt(Fields.ENTITY_ID));
			characteristic.setName(rs.getString(Fields.CHARACTERISTIC_NAME));
			characteristic.setDescription(rs
					.getString(Fields.CHARACTERISTIC_DESCRIPTION));
			result.add(characteristic);
		}
		return result;
	}

	/**
	 * Delete characteristic
	 * 
	 * @param characteristic
	 *            characteristic to delete
	 */
	public void deleteCharacteristic(Characteristic characteristic) {
		PreparedStatement pstmt = null;
		DBManager dbManager = DBManager.getInstance();
		Connection con = null;
		try {
			con = dbManager.getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_CHARACTERISTIC);
			pstmt.setInt(1, characteristic.getId());
			pstmt.executeUpdate();
			con.commit();
			LOG.trace("Delete characteristic with id " + characteristic.getId());
		} catch (SQLException ex) {
			dbManager.rollback(con);
			LOG.error("Cannot delete a characteristic with id "
					+ characteristic.getId(), ex);
		} finally {
			dbManager.close(pstmt);
			dbManager.close(con);
		}
	}
}

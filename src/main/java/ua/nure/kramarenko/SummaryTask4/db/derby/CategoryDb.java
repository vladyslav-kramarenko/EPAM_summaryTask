package ua.nure.kramarenko.SummaryTask4.db.derby;

import org.apache.log4j.Logger;
import ua.nure.kramarenko.SummaryTask4.db.Fields;
import ua.nure.kramarenko.SummaryTask4.db.entity.Category;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Work with category entity in dataBase
 * 
 * @author Vlad Kramarenko
 *
 */
public class CategoryDb {

	private static final Logger LOG = Logger.getLogger(DBManager.class);

	private static final String SQL_SELECT_ALL_CATEGORIES = "SELECT * FROM Categories";

	private static final String SQL_SELECT_CATEGORY_BY_ID = "Select * From Categories where id = (?)";

	/**
	 * Return list with all categories
	 * 
	 * @return all categories
	 */
	public List<Category> getAllCategories() {
		DBManager dbManager = DBManager.getInstance();

		List<Category> categoryList = null;
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {

			con = dbManager.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_SELECT_ALL_CATEGORIES);
			categoryList = parseResultSet(rs);
			con.commit();
		} catch (SQLException ex) {
			dbManager.rollback(con);
			LOG.error("Cannot obtain categories", ex);
		} finally {
			dbManager.close(rs);
			dbManager.close(stmt);
			dbManager.close(con);
		}
		return categoryList;
	}

	/**
	 * Search category by id
	 * 
	 * @param categoryId
	 *            Searched category id
	 * @return category
	 */
	public Category getCategory(int categoryId) {
		DBManager dbManager = DBManager.getInstance();
		Category category = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = dbManager.getConnection();
			pstmt = con.prepareStatement(SQL_SELECT_CATEGORY_BY_ID);
			pstmt.setInt(1, categoryId);
			rs = pstmt.executeQuery();
			category = parseResultSet(rs).get(0);
			con.commit();
		} catch (SQLException ex) {
			dbManager.rollback(con);
			LOG.error("Cannot obtain category by id " + categoryId, ex);
		} finally {
			dbManager.close(rs);
			dbManager.close(pstmt);
			dbManager.close(con);
		}
		return category;
	}

	/**
	 * Parse resultSer for select Query
	 * 
	 * @param rs ResultSet
	 * @return list of category
	 * @throws SQLException
	 */
	protected List<Category> parseResultSet(ResultSet rs) throws SQLException {
		LinkedList<Category> result = new LinkedList<Category>();
		while (rs.next()) {
			Category category = new Category();
			category.setId(rs.getInt(Fields.ENTITY_ID));
			category.setName(rs.getString(Fields.CATEGORY_NAME));
			result.add(category);
		}
		return result;
	}

}

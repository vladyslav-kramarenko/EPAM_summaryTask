package ua.nure.kramarenko.SummaryTask4.db.derby;

import org.apache.log4j.Logger;
import ua.nure.kramarenko.SummaryTask4.db.Fields;
import ua.nure.kramarenko.SummaryTask4.db.entity.User;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Work with user entity in dataBase
 * 
 * @author Vlad Kramarenko
 *
 */
public class UserDb {

	private static final Logger LOG = Logger.getLogger(UserDb.class);

	private static final String SQL_SELECT_ALL_USERS = "SELECT * FROM users";

	private static final String SQL_INSERT_INTO_USERS = "INSERT INTO users (login, password, first_name, last_name, role_id) \n"
			+ "VALUES (?, ?, ?, ?, ?)";

	private static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM users where id = ?";

	private static final String SQL_UPDATE_USER = "UPDATE users SET login=?, password=?, first_name=?, last_name=?,"
			+ " role_id=?, phone=?, email=?, city=?, address=?" + "	WHERE id=?";

	private static final String SQL_UPDATE_USER_ROLE = "UPDATE users SET role_id=?"
			+ "	WHERE id=?";

	private static final String SQL_DELETE_USER = "DELETE FROM users WHERE id= ?";

	private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";

	/**
	 * Search for all users
	 * 
	 * @return list of users
	 */
	public List<User> getAllUsers() {
		List<User> usersList = null;
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_SELECT_ALL_USERS);
			usersList = parseResultSet(rs);
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error("Cannot obtain users", ex);
		} finally {
			DBManager.getInstance().close(rs);
			DBManager.getInstance().close(stmt);
			DBManager.getInstance().close(con);
		}
		return usersList;
	}

	/**
	 * Search user by id
	 * 
	 * @param userId
	 *            user id
	 * @return user
	 */
	public User getUser(int userId) {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_SELECT_USER_BY_ID);
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();
			user = parseResultSet(rs).get(0);
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error("Cannot obtain user by id", ex);
		} finally {
			DBManager.getInstance().close(rs);
			DBManager.getInstance().close(pstmt);
			DBManager.getInstance().close(con);
		}
		return user;
	}

	/**
	 * Update user
	 * 
	 * @param user
	 *            user to update
	 */
	public void updateUser(User user) {
		Connection con = null;
		DBManager dbManager = DBManager.getInstance();
		try {
			con = dbManager.getConnection();
			updateUser(con, user);
			con.commit();
		} catch (SQLException ex) {
			dbManager.rollback(con);
			LOG.error("Cannot update a user", ex);
		} finally {
			dbManager.close(con);
		}
	}

	/**
	 * Update user role
	 * 
	 * @param userId
	 *            id of user to update
	 * @param roleId
	 *            new role id
	 */
	public void updateUserRole(int userId, int roleId) {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			PreparedStatement pstmt = null;
			try {
				pstmt = con.prepareStatement(SQL_UPDATE_USER_ROLE);
				int k = 1;
				pstmt.setInt(k++, roleId);
				pstmt.setInt(k, userId);
				pstmt.executeUpdate();
			} finally {
				DBManager.getInstance().close(pstmt);
			}
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error("Cannot update a user role", ex);
		} finally {
			DBManager.getInstance().close(con);
		}
	}

	/**
	 * Update user
	 * 
	 * @param con
	 *            database connection
	 * @param user
	 *            user to update
	 * @throws SQLException
	 */
	public void updateUser(Connection con, User user) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL_UPDATE_USER);
			int k = 1;
			pstmt.setString(k++, user.getLogin());
			pstmt.setString(k++, user.getPassword());
			pstmt.setString(k++, user.getFirstName());
			pstmt.setString(k++, user.getLastName());
			pstmt.setInt(k++, user.getRoleId());
			pstmt.setString(k++, user.getPhone());
			pstmt.setString(k++, user.getEmail());
			pstmt.setString(k++, user.getCity());
			pstmt.setString(k++, user.getAddress());
			pstmt.setInt(k, user.getId());
			pstmt.executeUpdate();
		} finally {
			DBManager.getInstance().close(pstmt);
		}
	}

	/**
	 * Delete user
	 * 
	 * @param user
	 *            user to delete
	 */
	public void deleteUser(User user) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_USER);
			pstmt.setInt(1, user.getId());
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error("Cannot delete a user", ex);
		} finally {
			DBManager.getInstance().close(rs);
			DBManager.getInstance().close(pstmt);
			DBManager.getInstance().close(con);
		}
	}

	/**
	 * Parse resultSet for select query
	 * 
	 * @param rs
	 *            ResultSEt
	 * @return list of users
	 * @throws SQLException
	 */
	protected List<User> parseResultSet(ResultSet rs) throws SQLException {
		LinkedList<User> result = new LinkedList<User>();
		while (rs.next()) {
			User user = new User();
			user.setId(rs.getInt(Fields.ENTITY_ID));
			user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
			user.setLastName(rs.getString(Fields.USER_LAST_NAME));
			user.setLogin(rs.getString(Fields.USER_LOGIN));
			user.setPassword(rs.getString(Fields.USER_PASSWORD));
			user.setPhone(rs.getString(Fields.USER_PHONE));
			user.setEmail(rs.getString(Fields.USER_EMAIL));
			user.setCity(rs.getString(Fields.USER_CITY));
			user.setAddress(rs.getString(Fields.USER_ADDRESS));
			user.setRoleId(rs.getInt(Fields.USER_ROLE_ID));
			result.add(user);
			System.out.println(user);
		}
		return result;
	}

	/**
	 * add new user
	 * 
	 * @param user
	 *            user to add
	 */
	public void addUser(User user) {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			addUser(con, user);
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error("Cannot create a user", ex);
		} finally {
			DBManager.getInstance().close(con);
		}
	}

	/**
	 * Add new user
	 * 
	 * @param con
	 *            database connection
	 * @param user
	 *            user to add
	 * @throws SQLException
	 */
	public void addUser(Connection con, User user) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL_INSERT_INTO_USERS,
					Statement.RETURN_GENERATED_KEYS);
			int k = 1;
			pstmt.setString(k++, user.getLogin());
			pstmt.setString(k++, user.getPassword());
			pstmt.setString(k++, user.getFirstName());
			pstmt.setString(k++, user.getLastName());
			pstmt.setInt(k++, user.getRoleId());
			pstmt.executeUpdate();

			ResultSet keys = pstmt.getGeneratedKeys();
			keys.next();
			user.setId(keys.getInt(1));
		} finally {
			DBManager.getInstance().close(pstmt);
		}
	}

	/**
	 * Returns a user with the given login.
	 * 
	 * @param login
	 *            User login.
	 * @return User entity.
	 */
	public User findUserByLogin(String login) {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
			System.out.println(SQL_FIND_USER_BY_LOGIN + login);
			pstmt.setString(1, login);
			rs = pstmt.executeQuery();
			List<User> users = parseResultSet(rs);
			if (users.size() > 0) {
				user = users.get(0);
			}
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error("Cannot obtain a user by its login", ex);
		} finally {
			DBManager.getInstance().close(rs);
			DBManager.getInstance().close(pstmt);
			DBManager.getInstance().close(con);
		}
		return user;
	}

}

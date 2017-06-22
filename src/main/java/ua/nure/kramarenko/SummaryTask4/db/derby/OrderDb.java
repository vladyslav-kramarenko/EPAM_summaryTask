package ua.nure.kramarenko.SummaryTask4.db.derby;

import org.apache.log4j.Logger;
import ua.nure.kramarenko.SummaryTask4.db.Fields;
import ua.nure.kramarenko.SummaryTask4.db.bean.UserOrderBean;
import ua.nure.kramarenko.SummaryTask4.db.entity.Order;
import ua.nure.kramarenko.SummaryTask4.db.enums.Status;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Work with Order entity in dataBase
 * 
 * @author Vlad Kramarenko
 *
 */
public class OrderDb {

	private static final Logger LOG = Logger.getLogger(OrderDb.class);

	private static final String SQL_SELECT_ALL_ORDERS_BEAN = "SELECT o.date, o.id, o.bill, o.status_id,u."
			+ Fields.USER_FIRST_NAME
			+ ",u."
			+ Fields.USER_LAST_NAME
			+ ", o.city,o.address,o.email,o.phone "
			+ " FROM orders o join users u on o.user_id=u.id";

	private static final String SQL_SELECT_ALL_USER_ORDERS_BEAN = "SELECT o.date, o.id, o.bill, o.status_id,u."
			+ Fields.USER_FIRST_NAME
			+ ",u."
			+ Fields.USER_LAST_NAME
			+ ", o.city,o.address,o.email,o.phone "
			+ " FROM orders o join users u on o.user_id=u.id WHERE u.id=";

	private static final String SQL_SELECT_ALL_ORDERS = "SELECT * FROM orders";

	private static final String SQL_INSERT_INTO_ORDERS = "INSERT INTO orders (date,bill, user_id, status_id, phone, email, city, address) \n"
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_SELECT_ORDER_BY_ID = "SELECT * FROM orders WHERE id = ?";

	private static final String SQL_SELECT_ORDER_BY_STATUS_ID = "SELECT * FROM orders WHERE status_id = ?";

	private static final String SQL_SELECT_ORDER_BY_USER_ID = "SELECT * FROM orders WHERE user_id = ?";

	private static final String SQL_UPDATE_ORDER = "UPDATE orders SET date=? ,bill=?, user_id=?, status_id=?, phone=?,email=?,city=?, address=?"
			+ " WHERE id=?";

	private static final String SQL_UPDATE_ORDER_STATUS = "UPDATE orders SET status_id=?"
			+ " WHERE id=?";

	private static final String SQL_DELETE_ORDER = "DELETE FROM orders WHERE id= ?";

	/**
	 * Search all orders with defined status, or if it is null search for all
	 * orders
	 * 
	 * @param orderStatusFilter
	 *            Order status to filter
	 * @return list with OrderBeans
	 */
	public List<UserOrderBean> getAllOrdersBean(Status orderStatusFilter) {
		List<UserOrderBean> orderList = null;
		StringBuffer sb = new StringBuffer(SQL_SELECT_ALL_ORDERS_BEAN);

		if (orderStatusFilter != null) {
			sb.append(" WHERE o.status_id=");
			sb.append(orderStatusFilter.ordinal());
		}
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sb.toString());
			orderList = parseBeanResultSet(rs);
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error("Cannot obtain bean orders", ex);
		} finally {
			DBManager.getInstance().close(rs);
			DBManager.getInstance().close(stmt);
			DBManager.getInstance().close(con);
		}
		return orderList;
	}

	/**
	 * Search for all user's orders
	 * 
	 * @param userId
	 *            user id
	 * @return list of OrderBean
	 */
	public List<UserOrderBean> getAllUserOrdersBean(int userId) {
		List<UserOrderBean> orderList = null;
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_SELECT_ALL_USER_ORDERS_BEAN + userId);
			orderList = parseBeanResultSet(rs);
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error("Cannot obtain bean orders", ex);
		} finally {
			DBManager.getInstance().close(rs);
			DBManager.getInstance().close(stmt);
			DBManager.getInstance().close(con);
		}
		return orderList;
	}

	/**
	 * Search for all Orders
	 * 
	 * @return list of ordrs
	 */
	public List<Order> getAllOrders() {
		List<Order> orderList = null;
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_SELECT_ALL_ORDERS);
			orderList = parseResultSet(rs);
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error("Cannot obtain orders", ex);
		} finally {
			DBManager.getInstance().close(rs);
			DBManager.getInstance().close(stmt);
			DBManager.getInstance().close(con);
		}
		return orderList;
	}

	/**
	 * Search order by id
	 * 
	 * @param orderId
	 *            order id
	 * @return order
	 */
	public Order getOrderByID(int orderId) {
		return getOrder(orderId, SQL_SELECT_ORDER_BY_ID).get(0);
	}

	/**
	 * Search orders with determined parameter
	 * 
	 * @param id
	 *            parameter id
	 * @param sql
	 *            query
	 * @return list with orders
	 */
	public List<Order> getOrder(int id, String sql) {
		List<Order> orderList = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			orderList = parseResultSet(rs);
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error("Cannot obtain an order ", ex);
		} finally {
			DBManager.getInstance().close(rs);
			DBManager.getInstance().close(pstmt);
			DBManager.getInstance().close(con);
		}
		return orderList;
	}

	/**
	 * Update order
	 * 
	 * @param order
	 *            order to update
	 */
	public void updateOrder(Order order) {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			updateOrder(con, order);
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error("Cannot update an order", ex);
		} finally {
			DBManager.getInstance().close(con);
		}
	}

	/**
	 * Update order's status
	 * 
	 * @param orderId
	 *            order to update
	 * @param statusId
	 *            new status id
	 */
	public void updateOrderStatus(int orderId, int statusId) {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			PreparedStatement pstmt = null;
			try {
				pstmt = con.prepareStatement(SQL_UPDATE_ORDER_STATUS);
				int k = 1;
				pstmt.setInt(k++, statusId);
				pstmt.setInt(k, orderId);
				pstmt.executeUpdate();
			} finally {
				DBManager.getInstance().close(pstmt);
			}
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error("Cannot update an order status", ex);
		} finally {
			DBManager.getInstance().close(con);
		}
	}

	/**
	 * Update order
	 * 
	 * @param con
	 *            database connection
	 * @param order
	 *            order to update
	 * @throws SQLException
	 */
	public void updateOrder(Connection con, Order order) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL_UPDATE_ORDER);
			int k = 1;
			pstmt.setString(k++, order.getDate());
			pstmt.setDouble(k++, order.getBill());
			pstmt.setInt(k++, order.getUserId());
			pstmt.setInt(k++, order.getStatusId());
			pstmt.setString(k++, order.getPhone());
			pstmt.setString(k++, order.getEmail());
			pstmt.setString(k++, order.getCity());
			pstmt.setString(k++, order.getAddress());
			pstmt.setInt(k, order.getId());
			pstmt.executeUpdate();
		} finally {
			DBManager.getInstance().close(pstmt);
		}
	}

	/**
	 * Delete order
	 * 
	 * @param order
	 *            order to delete
	 */
	public void deleteOrder(Order order) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_ORDER);
			pstmt.setInt(1, order.getId());
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error("Cannot delete an order", ex);
		} finally {
			DBManager.getInstance().close(rs);
			DBManager.getInstance().close(pstmt);
			DBManager.getInstance().close(con);
		}
	}

	/**
	 * Search orders by status
	 * 
	 * @param status
	 *            status for filter
	 * @return list with orders
	 */
	public List<Order> getOrderByStatus(int status) {
		return getOrder(status, SQL_SELECT_ORDER_BY_STATUS_ID);
	}

	/**
	 * Search orders by user id
	 * 
	 * @param userId
	 *            user id
	 * @return list with orders
	 */
	public List<Order> getOrderByUser(int userId) {
		return getOrder(userId, SQL_SELECT_ORDER_BY_USER_ID);
	}

	/**
	 * Parse resultSet for Select Query
	 * 
	 * @param rs
	 *            ResultSet
	 * @return list with orders
	 * @throws SQLException
	 */
	protected List<Order> parseResultSet(ResultSet rs) throws SQLException {
		LinkedList<Order> result = new LinkedList<Order>();
		while (rs.next()) {
			Order order = new Order();
			order.setDate(rs.getString(Fields.ORDER_DATE));
			order.setId(rs.getInt(Fields.ENTITY_ID));
			order.setBill(rs.getInt(Fields.ORDER_BILL));
			order.setUserId(rs.getInt(Fields.ORDER_USER_ID));
			order.setPhone(rs.getString(Fields.ORDER_PHONE));
			order.setEmail(rs.getString(Fields.ORDER_EMAIL));
			order.setCity(rs.getString(Fields.ORDER_CITY));
			order.setStatusId(rs.getInt(Fields.ORDER_STATUS_ID));
			order.setAddress(rs.getString(Fields.ORDER_ADDRESS));
			result.add(order);
		}
		return result;
	}

	/**
	 * Parse Bean resultSet for Select Query
	 * 
	 * @param rs
	 *            ResultSet
	 * @return list of userorderBean
	 * @throws SQLException
	 */
	protected List<UserOrderBean> parseBeanResultSet(ResultSet rs)
			throws SQLException {
		LinkedList<UserOrderBean> result = new LinkedList<UserOrderBean>();
		while (rs.next()) {
			UserOrderBean uob = new UserOrderBean();
			uob.setDate(rs.getDate(Fields.ORDER_DATE));
			uob.setId(rs.getInt(Fields.ENTITY_ID));
			uob.setOrderBill(rs.getDouble(Fields.ORDER_BILL));
			uob.setStatusName(Status.values()[rs.getInt(Fields.ORDER_STATUS_ID)]
					.toString());
			uob.setUserFirstName(rs.getString(Fields.USER_FIRST_NAME));
			uob.setUserLastName(rs.getString(Fields.USER_LAST_NAME));
			uob.setCity(rs.getString(Fields.ORDER_CITY));
			uob.setAddress(rs.getString(Fields.ORDER_ADDRESS));
			uob.setEmail(rs.getString(Fields.ORDER_EMAIL));
			uob.setPhone(rs.getString(Fields.ORDER_PHONE));
			result.add(uob);
		}
		return result;
	}

	/**
	 * Add new order
	 * 
	 * @param order
	 *            order to add
	 */
	public Order addOrder(Order order) {
		Connection con = null;
		Order newOrder = null;
		try {
			con = DBManager.getInstance().getConnection();
			newOrder = addOrder(con, order);
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error("Cannot create an order", ex);
		} finally {
			DBManager.getInstance().close(con);
		}
		return newOrder;
	}

	/**
	 * Add new order
	 * 
	 * @param con
	 *            database connection
	 * @param order
	 *            order to add
	 * @throws SQLException
	 */
	public Order addOrder(Connection con, Order order) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL_INSERT_INTO_ORDERS,
					Statement.RETURN_GENERATED_KEYS);
			int k = 1;
			pstmt.setString(k++, order.getDate());
			pstmt.setInt(k++, order.getBill());
			pstmt.setInt(k++, order.getUserId());
			pstmt.setInt(k++, order.getStatusId());
			pstmt.setString(k++, order.getPhone());
			pstmt.setString(k++, order.getEmail());
			pstmt.setString(k++, order.getCity());
			pstmt.setString(k, order.getAddress());
			pstmt.executeUpdate();

			ResultSet keys = pstmt.getGeneratedKeys();
			keys.next();
			order.setId(keys.getInt(1));
			System.out.println("orderId=" + order.getId());
		} finally {
			DBManager.getInstance().close(pstmt);
		}
		return order;
	}

}

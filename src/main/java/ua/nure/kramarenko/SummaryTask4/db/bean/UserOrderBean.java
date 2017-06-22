package ua.nure.kramarenko.SummaryTask4.db.bean;

import ua.nure.kramarenko.SummaryTask4.db.entity.Entity;

import java.sql.Date;

/**
 * Provide records for virtual order table
 * 
 * @author Vlad Kramarenko
 */
public class UserOrderBean extends Entity {

	private static final long serialVersionUID = -5654982557199337483L;

	private long orderId;

	private Date date;

	private String userFirstName;

	private String userLastName;

	private String phone;

	private String email;

	private double orderBill;

	private String city;

	private String address;

	private String statusName;

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public double getOrderBill() {
		return orderBill;
	}

	public void setOrderBill(double orderBill) {
		this.orderBill = orderBill;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	@Override
	public String toString() {
		return "OrderUserBean [orderId=" + orderId + ", userFirstName="
				+ userFirstName + ", userLastName=" + userLastName
				+ ", orderBill=" + orderBill + ", statusName=" + statusName
				+ "]";
	}
}

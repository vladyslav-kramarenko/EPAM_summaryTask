package ua.nure.kramarenko.SummaryTask4.db.bean;

import ua.nure.kramarenko.SummaryTask4.db.entity.Entity;
import ua.nure.kramarenko.SummaryTask4.db.entity.Product;

import java.util.List;

/**
 * Provide records for virtual order table
 * 
 * @author Vlad Kramarenko
 */
public class UserProductsBean extends Entity {

	private static final long serialVersionUID = -5654982557199337483L;

	private String userFirstName;

	private String userLastName;

	private List<Product> products;

	/**
	 * @return the products
	 */
	public List<Product> getProducts() {
		return products;
	}

	/**
	 * @param products the products to set
	 */
	public void setProducts(List<Product> products) {
		this.products = products;
	}

	/**
	 * @return the userFirstName
	 */
	public String getUserFirstName() {
		return userFirstName;
	}

	/**
	 * @param userFirstName
	 *            the userFirstName to set
	 */
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	/**
	 * @return the userLastName
	 */
	public String getUserLastName() {
		return userLastName;
	}

	/**
	 * @param userLastName
	 *            the userLastName to set
	 */
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

}

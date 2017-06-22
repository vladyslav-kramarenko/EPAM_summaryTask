package ua.nure.kramarenko.SummaryTask4.db.bean.cart;

import ua.nure.kramarenko.SummaryTask4.db.entity.Product;

import java.io.Serializable;

/**
 * This class describes Item for ShoppingCart
 * 
 * @author Vlad Kramarenko
 *
 */
public class ShoppingCartItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8084186600472910212L;
	private Product product;
	private short quantity;

	/**
	 * Class constructor
	 * 
	 * @param product
	 *            Product to take as item
	 */
	public ShoppingCartItem(Product product) {
		this.product = product;
		quantity = 1;
	}

	public Product getProduct() {
		return product;
	}

	public short getQuantity() {
		return quantity;
	}

	public void setQuantity(short quantity) {
		this.quantity = quantity;
	}

	/**
	 * Increase quantity of product
	 */
	public void incrementQuantity() {
		quantity++;
	}

	/**
	 * Decrease quantity of product
	 */
	public void decrementQuantity() {
		quantity--;
	}

	/**
	 * This method count cost of ProductItem
	 * 
	 * @return price * item quantity
	 */
	public double getTotal() {
		double amount = 0;
		amount = (this.getQuantity() * product.getPrice().doubleValue());
		return amount;
	}
}

package ua.nure.kramarenko.SummaryTask4.db.bean.cart;

import ua.nure.kramarenko.SummaryTask4.db.entity.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart implements Serializable {
	/**
	 * Provide records for virtual cart table
	 * 
	 * @author Vlad Kramarenko
	 */
	private static final long serialVersionUID = -2968208911383328796L;
	private List<ShoppingCartItem> items;
	private int numberOfItems;
	private double total;

	/**
	 * Empty class constructor
	 */
	public ShoppingCart() {
		items = new ArrayList<ShoppingCartItem>();
		numberOfItems = 0;
		total = 0;
	}

	/**
	 * Class constructor
	 * 
	 * @param list
	 *            list of ShoppingCartItem
	 */
	public ShoppingCart(List<ShoppingCartItem> list) {
		if (items == null) {
			this.items = list;
		} else {
			items = new ArrayList<ShoppingCartItem>();
		}
		numberOfItems = getNumberOfItems();
		total = 0;
	}

	/**
	 * Adds a <code>ShoppingCartItem</code> to the <code>ShoppingCart</code>'s
	 * <code>items</code> list. If item of the specified <code>product</code>
	 * already exists in shopping cart list, the quantity of that item is
	 * incremented.
	 *
	 * @param product
	 *            the <code>Product</code> that defines the type of shopping
	 *            cart item
	 * @see ShoppingCartItem
	 */
	public synchronized void addItem(Product product) {

		boolean newItem = true;
		if (items != null && items.size() > 0) {
			for (ShoppingCartItem scItem : items) {

				if (scItem.getProduct().getId() == product.getId()) {

					newItem = false;
					scItem.incrementQuantity();
				}
			}
		}

		if (newItem) {
			ShoppingCartItem scItem = new ShoppingCartItem(product);
			items.add(scItem);
		}
	}

	/**
	 * Delete ShoppingCartItem from ShoppingCart
	 * 
	 * @param Product
	 *            product to delete drom cart
	 */
	public synchronized void deleteItem(Product product) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getProduct().getId() == product.getId()) {
				items.remove(i);
			}
		}
	}

	/**
	 * Updates the <code>ShoppingCartItem</code> of the specified
	 * <code>product</code> to the specified quantity. If '<code>0</code>' is
	 * the given quantity, the <code>ShoppingCartItem</code> is removed from the
	 * <code>ShoppingCart</code>'s <code>items</code> list.
	 *
	 * @param product
	 *            the <code>Product</code> that defines the type of shopping
	 *            cart item
	 * @param quantity
	 *            the number which the <code>ShoppingCartItem</code> is updated
	 *            to
	 * @see ShoppingCartItem
	 */
	public synchronized void update(Product product, String quantity) {

		short qty = -1;

		// cast quantity as short
		qty = Short.parseShort(quantity);

		if (qty >= 0) {

			ShoppingCartItem item = null;

			for (ShoppingCartItem scItem : items) {

				if (scItem.getProduct().getId() == product.getId()) {

					if (qty != 0) {
						// set item quantity to new value
						scItem.setQuantity(qty);
					} else {
						// if quantity equals 0, save item and break
						item = scItem;
						break;
					}
				}
			}

			if (item != null) {
				// remove from cart
				items.remove(item);
			}
		}
	}

	/**
	 * Returns the list of <code>ShoppingCartItems</code>.
	 *
	 * @return the <code>items</code> list
	 * @see ShoppingCartItem
	 */
	public synchronized List<ShoppingCartItem> getItems() {
		if (items != null) {
			return items;
		} else {
			return new ArrayList<ShoppingCartItem>();
		}
	}

	/**
	 * Returns the sum of quantities for all items maintained in shopping cart
	 * <code>items</code> list.
	 *
	 * @return the number of items in shopping cart
	 * @see ShoppingCartItem
	 */
	public synchronized int getNumberOfItems() {

		numberOfItems = 0;
		if (items != null && items.size() > 0) {
			for (ShoppingCartItem scItem : items) {

				numberOfItems += scItem.getQuantity();
			}
		}

		return numberOfItems;
	}

	/**
	 * Calculates the total cost of the order. This method calculate the sum of
	 * the product price multiplied by the quantity for all items in shopping
	 * cart list and sets the <code>total</code> instance variable with the
	 * result.
	 *
	 * @see ShoppingCartItem
	 */
	public synchronized void calculateTotal() {
		double amount = 0;

		for (ShoppingCartItem scItem : items) {

			Product product = (Product) scItem.getProduct();
			amount += (scItem.getQuantity() * product.getPrice().doubleValue());
		}

		total = amount;
	}

	/**
	 * Returns the total cost of the order for the given
	 * <code>ShoppingCart</code> instance.
	 *
	 * @return the cost of all items times their quantities plus surcharge
	 */
	public synchronized double getTotal() {
		calculateTotal();
		return total;
	}

	/**
	 * Empties the shopping cart. All items are removed from the shopping cart
	 * <code>items</code> list, <code>numberOfItems</code> and
	 * <code>total</code> are reset to '<code>0</code>'.
	 *
	 * @see ShoppingCartItem
	 */
	public synchronized void clear() {
		items.clear();
		numberOfItems = 0;
		total = 0;
	}

}
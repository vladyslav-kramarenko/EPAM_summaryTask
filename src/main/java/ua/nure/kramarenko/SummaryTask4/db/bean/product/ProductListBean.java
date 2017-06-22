package ua.nure.kramarenko.SummaryTask4.db.bean.product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Provide records for virtual table with product's
 * 
 * @author Vlad Kramarenko
 *
 */
public class ProductListBean {

	private List<ProductListItem> items;

	/**
	 * @return the items
	 */
	public List<ProductListItem> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(List<ProductListItem> items) {
		this.items = items;
	}

	public ProductListBean() {
		items = new ArrayList<ProductListItem>();
	}

	/**
	 * return quantity of products in the list
	 * 
	 * @return products quantity
	 */
	public int size() {
		return items.size();
	}

	/**
	 * Add rpoduct to the list
	 * 
	 * @param product
	 *            Product to add
	 */
	public void add(ProductListItem product) {
		items.add(product);
	}

	/**
	 * Sorts list of products
	 * 
	 * @param sort
	 *            String Sort type
	 */
	public void sort(String sort) {
		
		String sortType = "";
		if (sort != null) {
			sortType = sort;
		}

		switch (sortType) {
		case "price":
			sortByPrice(getItems(), 1);
			break;
		case "price_desc":
			sortByPrice(getItems(), -1);
			break;
		default:
			sortByPrice(getItems(), 1);
			break;
		}
	}

	/**
	 * Sort items by price
	 * 
	 * @param productListBean
	 *            list with products
	 * @param desc
	 *            type of sort
	 */
	public void sortByPrice(List<ProductListItem> productListBean,
			final int desc) {
		Collections.sort(productListBean, new Comparator<ProductListItem>() {
			public int compare(ProductListItem o1, ProductListItem o2) {
				return (int) desc * (o1.getPrice() - o2.getPrice());
			}
		});
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (ProductListItem product : items) {
			sb.append(product.getName()).append(" ");
		}
		return sb.toString();

	}
}

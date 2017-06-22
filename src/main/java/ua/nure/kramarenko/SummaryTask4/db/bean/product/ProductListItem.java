package ua.nure.kramarenko.SummaryTask4.db.bean.product;

import ua.nure.kramarenko.SummaryTask4.db.entity.Entity;

/**
 * Item for ProductListBean virtual table
 * 
 * @author Vlad Kramarenko
 *
 */
public class ProductListItem extends Entity {

	private static final long serialVersionUID = -4814110645324013002L;

	private int id;
	private int price;
	private String availability;
	private String name;
	private String manufacturer;
	private String category;
	private String description;
	private String img;

	/**
	 * @return the img
	 */
	public String getImg() {
		return img;
	}

	/**
	 * @param img
	 *            the img to set
	 */
	public void setImg(String img) {
		this.img = img;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the availability
	 */
	public String getAvailability() {
		return availability;
	}

	/**
	 * @param availability_id
	 *            the availability to set
	 */
	public void setAvailability(String availability) {
		this.availability = availability;
	}

}
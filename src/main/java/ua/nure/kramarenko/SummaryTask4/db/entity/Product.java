package ua.nure.kramarenko.SummaryTask4.db.entity;

/**
 * product entity.
 * 
 * @author Vlad Kramarenko
 */
public class Product extends Entity {

	private static final long serialVersionUID = 4716395168539434663L;

	private String name;

	private String img;

	private Integer manufacturerId;

	private int availabilityId;

	private String description;

	private Double price;

	private Integer categoryId;

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

	/**
	 * @return the availability_id
	 */
	public int getAvailabilityId() {
		return availabilityId;
	}

	/**
	 * @param availabilityId
	 *            the availability_id to set
	 */
	public void setAvailabilityId(int availabilityId) {
		this.availabilityId = availabilityId;
	}

	/**
	 * @return the manufacturer_id
	 */
	public Integer getManufacturerId() {
		return manufacturerId;
	}

	/**
	 * @param manufacturerId
	 *            the manufacturer_id to set
	 */
	public void setManufacturerId(Integer manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return "ProductItem [name=" + name + ", price=" + price
				+ ", categoryId=" + categoryId + ", availabilityId="
				+ availabilityId + ", getId()=" + getId() + "]";
	}

}
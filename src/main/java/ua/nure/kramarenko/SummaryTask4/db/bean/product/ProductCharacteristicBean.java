package ua.nure.kramarenko.SummaryTask4.db.bean.product;

import ua.nure.kramarenko.SummaryTask4.db.entity.Entity;

/**
 * Provide records for virtual table with product's characteristics
 * 
 * @author Vlad Kramarenko
 *
 */
public class ProductCharacteristicBean extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 100432515260624709L;

	private int productId;
	private int characteristicId;
	private String name;
	private String value;
	private String description;

	/**
	 * @return the characteristicId
	 */
	public int getCharacteristicId() {
		return characteristicId;
	}

	/**
	 * @param characteristicId
	 *            the characteristicId to set
	 */
	public void setCharacteristicId(int characteristicId) {
		this.characteristicId = characteristicId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
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

	/**
	 * @return the product_id
	 */
	public int getProductId() {
		return productId;
	}

	/**
	 * @param productId
	 *            the product_id to set
	 */
	public void setProductId(int productId) {
		this.productId = productId;
	}

}

package ua.nure.kramarenko.SummaryTask4.db.bean.product;

import ua.nure.kramarenko.SummaryTask4.db.entity.Entity;

/**
 * Item for ProductListBean virtual table
 * 
 * @author Vlad Kramarenko
 *
 */
public class CompleteCommandProduct extends Entity {

	private static final long serialVersionUID = -4814110645324013002L;

	private int id;
	private String name;
	private String manufacturer;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

}
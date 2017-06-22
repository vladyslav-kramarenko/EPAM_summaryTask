package ua.nure.kramarenko.SummaryTask4.db.enums;


public enum SortType {

	PRICE, PRICE_DESC;
	
	public String getName() {
		return name().toLowerCase();
	}
	
}
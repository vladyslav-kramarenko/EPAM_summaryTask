package ua.nure.kramarenko.SummaryTask4.db.enums;

import ua.nure.kramarenko.SummaryTask4.db.entity.User;

public enum Role {
	ADMIN, CLIENT, BANNED;
	
	public static Role getRole(User user) {
		int roleId = user.getRoleId();
		return Role.values()[roleId];
	}
	
	public String getName() {
		return name().toLowerCase();
	}
	
}

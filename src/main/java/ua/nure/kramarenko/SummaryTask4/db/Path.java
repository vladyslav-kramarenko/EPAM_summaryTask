package ua.nure.kramarenko.SummaryTask4.db;

/**
 * Path holder (jsp pages, controller commands).
 * 
 * @author Vlad Kramarenko
 */
public final class Path {
	
	// pages
	public static final String PAGE_ALL_USER_PRODUCTS	= "/WEB-INF/jsp/admin/ordersProducts.jsp";
	
	public static final String PAGE_LOGIN 				= "/WEB-INF/jsp/login.jsp";
	public static final String PAGE_REGISTRATION 		= "/WEB-INF/jsp/registration.jsp";
	public static final String PAGE_ERROR_PAGE		    = "/WEB-INF/jsp/error_page.jsp";
	public static final String PAGE_LIST_PRODUCTS		= "/WEB-INF/jsp/productList.jsp";
	public static final String PAGE_PRODUCT_INFO		= "/WEB-INF/jsp/product_info.jsp";
	public static final String PAGE_CART 				= "/WEB-INF/jsp/cart.jsp";
	public static final String PAGE_CLIENT_ORDERS 		= "/WEB-INF/jsp/client/client_orders.jsp";
	public static final String PAGE_SETTINGS 			= "/WEB-INF/jsp/client/settings.jsp";
	public static final String PAGE_ORDER_CONFIRMATION  = "/WEB-INF/jsp/client/confirmation.jsp";
	public static final String PAGE_ORDER_CHECKOUT		= "/WEB-INF/jsp/client/checkout.jsp";
	public static final String PAGE_CHARACTERISTICS		= "/WEB-INF/jsp/admin/characteristics.jsp";
	public static final String PAGE_MANUFACTURERS		= "/WEB-INF/jsp/admin/manufacturers.jsp";
	public static final String PAGE_ALL_USERS 			= "/WEB-INF/jsp/admin/all_users.jsp";
	public static final String PAGE_ALL_ORDERS 			= "/WEB-INF/jsp/admin/all_orders.jsp";
	public static final String PAGE_PRODUCT_EDIT		= "/WEB-INF/jsp/admin/product_edit.jsp";

	// commands
	public static final String COMMAND_USER_PRODUCTS	= "controller?command=ordersProducts";
	
	public static final String COMMAND_MANUFACTURERS	= "controller?command=manufacturers";
	public static final String COMMAND_CHARACTIRISTICS	= "controller?command=charactiristics";
	public static final String COMMAND_LOGIN 			= "controller?command=login";
	public static final String COMMAND_REGISTRATION 	= "controller?command=registration";
	public static final String COMMAND_LIST_ORDERS 		= "controller?command=listOrders";
	public static final String COMMAND_ALL_ORDERS 		= "controller?command=allOrders";
	public static final String COMMAND_ALL_USERS 		= "controller?command=allOrders";
	public static final String COMMAND_LIST_PRODUCTS	= "controller?command=productList";
	public static final String COMMAND_VIEW_CART		= "controller?command=viewCart";
	public static final String COMMAND_PRODUCT_EDIT		= "controller?command=editProduct";

}





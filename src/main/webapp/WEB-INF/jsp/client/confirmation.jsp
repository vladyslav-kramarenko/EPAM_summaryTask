<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<div style="width: 100%" id="singleColumn">

	<p id="confirmationText" class="rounded">
		<strong><fmt:message key="confirmOrder_jsp.successMessage" /></strong>
		<br> <br>
		<fmt:message key="confirmOrder_jsp.confirmationNumberMessage" />
		<strong>[${order_id}]</strong> <br>
		<fmt:message key="confirmOrder_jsp.contactMessage" />
		<br> <br>
		<fmt:message key="confirmOrder_jsp.thankYouMessage" />
	</p>

	<div style="width: 100%" class="summaryColumn">

		<table style="width: 100%" id="orderSummaryTable" class="detailsTable">
			<tr class="header">
				<th colspan="3"><fmt:message
						key="confirmOrder_jsp.orderSummary" /></th>
			</tr>

			<tr class="tableHeading">
				<td><fmt:message key="confirmOrder_jsp.table.header.name" /></td>
				<td><fmt:message key="confirmOrder_jsp.table.header.quantity" /></td>
				<td><fmt:message key="confirmOrder_jsp.table.header.price" /></td>
			</tr>

			<c:forEach var="item" items="${order.items}" varStatus="iter">

				<tr class="${((iter.index % 2) != 0) ? 'lightBlue' : 'white'}">
					<td>${item.product.name}"</td>
					<td class="quantityColumn">${item.quantity}</td>
					<td class="confirmationPriceColumn"><fmt:formatNumber
							type="currency" currencySymbol="&dollar; "
							value="${item.product.price * item.quantity}" /></td>
				</tr>

			</c:forEach>

			<tr class="lightBlue">
				<td colspan="3" style="padding: 0 20px"><hr></td>
			</tr>


			<tr>
				<td class="total"><fmt:message key="confirmOrder_jsp.total" />:</td>
				<td class="total checkoutPriceColumn">${order.numberOfItems}</td>
				<td class="total checkoutPriceColumn"><fmt:formatNumber
						type="currency" currencySymbol="&dollar; " value="${order.total}" /></td>
			</tr>

			<tr class="lightBlue">
				<td colspan="3" style="padding: 0 20px"><hr></td>
			</tr>
		</table>

	</div>

	<div style="width: 100%" class="summaryColumn">

		<table style="width: 100%" id="deliveryAddressTable"
			class="detailsTable">
			<tr class="header">
				<th colspan="3"><fmt:message key="user.address" /></th>
			</tr>

			<tr>
				<td colspan="3" class="lightBlue">${user.firstName}
					${user.lastName} <br> ${user.city} <br> ${user.address} <br>
					<hr> <strong><fmt:message key="user.email" />:</strong>
					${user.email} <br> <strong><fmt:message
							key="user.phone" />:</strong> ${user.phone}
				</td>
			</tr>
		</table>
	</div>
</div>
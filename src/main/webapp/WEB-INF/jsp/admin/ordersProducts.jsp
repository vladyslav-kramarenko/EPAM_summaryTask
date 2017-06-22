<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="List orders" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>

	<%@ include file="/WEB-INF/jspf/header.jspf"%>

	<%-- CONTENT --%>
	<c:choose>
		<c:when test="${fn:length(userProductsList) == 0}">No such orders</c:when>

		<c:otherwise>

			<table style="width: 100%" id="list_order_table">
				<thead>
					<tr>
						<td align="center"><fmt:message
								key="list_orders_jsp.table.header.client" /></td>
						<td align="center"><fmt:message
								key="list_orders_jsp.table.header.products" /></td>
					</tr>
				</thead>
				<tr>
					<td colspan="10"><hr></td>
				</tr>
				<c:forEach var="bean" items="${userProductsList}">

					<tr>
						<td>${bean.userFirstName}${bean.userLastName}</td>
						<td align="center"><c:forEach var="p" items="${bean.products}">
									${p.name}; 
									</c:forEach></td>
					</tr>

				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
	<%-- CONTENT --%>
</body>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</html>
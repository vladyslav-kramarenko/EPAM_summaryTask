<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="List orders" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>

	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<table style="width: 100%" id="main-container">


		<tr>
			<td class="content" style="width: 100%">
				<%-- CONTENT --%> <c:choose>
					<c:when test="${fn:length(userOrderBeanList) == 0}">No such orders</c:when>

					<c:otherwise>
						<table style="width: 100%" id="client_order_table">
							<thead>
								<tr>
									<td>№</td>
									<td><fmt:message
											key="client_orders_jsp.table.header.client" /></td>
									<td><fmt:message key="client_orders_jsp.table.header.city" /></td>
									<td><fmt:message
											key="client_orders_jsp.table.header.address" /></td>
									<td><fmt:message key="client_orders_jsp.table.header.bill" /></td>
									<td><fmt:message key="client_orders_jsp.table.header.date" /></td>
									<td><fmt:message
											key="client_orders_jsp.table.header.status" /></td>
								</tr>
							</thead>
							<tr>
								<td colspan="10"><hr></td>
							</tr>
							<c:forEach var="bean" items="${userOrderBeanList}">

								<tr>
									<td>${bean.id}</td>
									<td>${bean.userFirstName}${bean.userLastName}</td>
									<td>${bean.city}</td>
									<td>${bean.address}</td>
									<td>${bean.orderBill}</td>
									<td>${bean.date}</td>
									<td>${bean.statusName}</td>
								</tr>

							</c:forEach>
						</table>
					</c:otherwise>
				</c:choose> <%-- CONTENT --%>
			</td>
		</tr>
	</table>
</body>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</html>
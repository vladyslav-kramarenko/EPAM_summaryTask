<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="List orders" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>

	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<script>
		$("#orderStatus").selectmenu({
			change : function(event, ui) {
				$("#userForm").submit();
			}
		});
	</script>
	<form id="filterForm" action="controller" method="get">
		<input type="hidden" name="command" value="allOrders" /> <label
			for="sort"><fmt:message key='list_orders_jsp.label.filter' />:</label>
		<select name="status" id="status" onChange="this.form.submit();">
			<c:forEach var="status" items="${statusTypes}">
				<c:choose>
					<c:when test="${status != statusFilter}">
						<option value="${status}">
							<fmt:message key='order.status.${status}' />
						</option>
					</c:when>
					<c:otherwise>
						<option selected value="${status}">
							<fmt:message key='order.status.${status}' />
						</option>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:choose>
				<c:when test="${statusFilter==null}">
					<option selected value="">
						<fmt:message key='order.status.all' />
					</option>
				</c:when>
				<c:otherwise>
					<option value="">
						<fmt:message key='order.status.all' />
					</option>
				</c:otherwise>
			</c:choose>
		</select>
	</form>

	<table id="main-container">
		<tr>
			<td class="content">
				<%-- CONTENT --%> <c:choose>
					<c:when test="${fn:length(userOrderBeanList) == 0}">No such orders</c:when>

					<c:otherwise>

						<table id="list_order_table">
							<thead>
								<tr>
									<td align="center">№</td>
									<td align="center"><fmt:message
											key="list_orders_jsp.table.header.client" /></td>
									<td align="center"><fmt:message
											key="list_orders_jsp.table.header.bill" /></td>
									<td align="center"><fmt:message
											key="list_orders_jsp.table.header.phone" /></td>
									<td align="center"><fmt:message
											key="list_orders_jsp.table.header.email" /></td>
									<td align="center"><fmt:message
											key="list_orders_jsp.table.header.city" /></td>
									<td align="center"><fmt:message
											key="list_orders_jsp.table.header.address" /></td>
									<td align="center"><fmt:message
											key="list_orders_jsp.table.header.date" /></td>
									<td align="center"><fmt:message
											key="list_orders_jsp.table.header.status" /></td>
								</tr>
							</thead>
							<tr>
								<td colspan="10"><hr></td>
							</tr>
							<c:forEach var="bean" items="${userOrderBeanList}">

								<tr>
									<td>${bean.id}</td>
									<td>${bean.userFirstName}${bean.userLastName}</td>
									<td>${bean.orderBill}</td>
									<td>${bean.phone}</td>
									<td>${bean.email}</td>
									<td>${bean.city}</td>
									<td>${bean.address}</td>
									<td>${bean.date}</td>
									<td>
										<form id="userForm" action="controller">
											<input type="hidden" name="command" value="changeOrderStatus" />
											<input type="hidden" name="order_id" value="${bean.id}" /> <select
												name="status" id="orderStatus"
												onChange="this.form.submit();">
												<c:forEach var="status" items="${statusTypes}">
													<c:choose>
														<c:when test="${status != bean.statusName}">
															<option value="${status}">
																<fmt:message key='order.status.${status}' />
															</option>
														</c:when>
														<c:otherwise>
															<option selected value="${status}"><fmt:message
																	key='order.status.${status}' />
															</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select>
										</form>
								</tr>

							</c:forEach>
						</table>
					</c:otherwise>
				</c:choose> <%-- CONTENT --%>
			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>
</html>
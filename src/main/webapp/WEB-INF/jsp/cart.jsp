<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Menu" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<script>
		$(function() {
			$("input[type=submit]").button({
				text : true,
				icons : {
					primary : "ui-icon-cart"
				}
			});
			$("a").button({});
			$("#category").buttonset();
			$("#delete").button({
				text : true,
				icons : {
					primary : "ui-icon-person"
				}
			});

		});
	</script>

	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<c:choose>
		<c:when test="${cart.numberOfItems > 4}">
			<p>
				<fmt:message key="cart_jsp.yourCartContains" />
				${cart.numberOfItems}
				<fmt:message key="cart_jsp.items" />
				.
			</p>
		</c:when>
		<c:when test="${cart.numberOfItems > 1 && cart.numberOfItems < 5}">
			<p>
				<fmt:message key="cart_jsp.yourCartContains" />
				${cart.numberOfItems}
				<fmt:message key="cart_jsp.items15" />
				.
			</p>
		</c:when>
		<c:when test="${cart.numberOfItems == 1}">
			<p>
				<fmt:message key="cart_jsp.yourCartContains" />
				${cart.numberOfItems}
				<fmt:message key="cart_jsp.item" />
				.
			</p>
		</c:when>
		<c:otherwise>
			<p>
				<fmt:message key="cart_jsp.yourCartEmpty" />
			</p>
		</c:otherwise>
	</c:choose>

	<c:if test="${cart.numberOfItems>0}">

		<table style="width: 100%">
			<thead>
				<tr>
					<td width="100"><fmt:message key="cart_jsp.table.header.name" /></td>
					<td width="100"><fmt:message
							key="cart_jsp.table.header.quantity" /></td>
					<td width="100"><fmt:message key="cart_jsp.table.header.price" />,
						$</td>
					<td width="100"><fmt:message key="cart_jsp.table.header.total" />,
						$</td>
					<td width="100"><fmt:message
							key="cart_jsp.table.header.delete" /></td>
				</tr>
			</thead>
		</table>

		<c:set var="k" value="0" />
		<c:forEach var="item" items="${cart.items}">
			<hr>
			<table style="width: 100%">
				<tr>
					<td width="100">${item.product.name}</td>
					<td width="100">${item.quantity}</td>
					<td width="100">${item.product.price}</td>
					<td width="100">${item.total}</td>
					<td width="100">
						<form action="controller" method="post">
							<input type="hidden" name="command" value="deleteFromCart" /> <input
								type="hidden" name="product_id" value="${item.product.id }" />
							<input type="submit" class="delete" name="submit"
								value="<fmt:message key='cart_jsp.button.deleteFromCart'/>">
						</form>

					</td>
				</tr>
			</table>

		</c:forEach>
		
	Total: ${cart.total} $
	</c:if>
	<c:choose>
		<c:when test="${userRole.name == 'client'}">
			<c:if test="${cart.numberOfItems>0}">
				<a href="controller?command=orderCheckout"> <fmt:message
						key='cart_jsp.button.confirmOrder' />
				</a>
			</c:if>
		</c:when>

		<c:when test="${userRole.name == 'banned' }">
			<br>
			<fmt:message key='cart_jsp.banned' />
		</c:when>

		<c:otherwise>
			<a href="controller?command=login"> <fmt:message
					key="header_jspf.anchor.login" />
			</a>
			<a href="controller?command=registration"> <fmt:message
					key="header_jspf.anchor.registration" />
			</a>
		</c:otherwise>


	</c:choose>


	<br>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
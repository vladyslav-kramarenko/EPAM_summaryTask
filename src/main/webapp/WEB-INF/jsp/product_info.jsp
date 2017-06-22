<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Menu" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>

	<script>
		$(function() {
			$("#add").button();
			$("#edit").button();
		});
	</script>

	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<table style="width: 100%">
		<tr>
			<td rowspan="5" width="20%"><img id="productImage" height="200"
				src="<fmt:message key='imageDir'/>${product.img}"
				alt="image of ${product.name}"></td>
			<td>Product name:</td>
			<td align="left">${product.name}</td>
		</tr>


		<tr>
			<td>Manufacturer:</td>
			<td align="left">${product.manufacturer}</td>
		</tr>
		<tr>
			<td>Availability:</td>
			<td align="left">${product.availability}</td>
		</tr>
		<tr>
			<td>Price:</td>
			<td align="left">${product.price}$</td>
		</tr>
		<tr>
			<td></td>
			<td align="left" colspan="1">
				<form>
					<c:choose>
						<c:when test="${userRole.name == 'admin' }">
							<input type="hidden" name="command" value="editProduct" />
							<input type="hidden" name="product_id" value="${product.id}" />
							<input id="edit" type="submit" name="submit"
								value="<fmt:message key='product_edit_jsp.button.editProduct'/>" />
						</c:when>
						<c:otherwise>
							<input type="hidden" name="command" value="addToCart" />
							<input type="hidden" name="product_id" value="${product.id}" />
							<input id="add" type="submit" name="submit"
								value="<fmt:message key='products_jsp.button.addtocart'/>" />
						</c:otherwise>
					</c:choose>
				</form>
			</td>
		</tr>
	</table>
	<div>${product.description}</div>

	<c:if test="${productChars!=null}">
		<div>
			<table style="width: 100%" id="Product chars">
				<thead>
					<tr>
						<td colspan="10">
							<hr>
						</td>
					</tr>
					<tr>
						<td><fmt:message key="product_info_jsp.table.header.charName" /></td>
						<td><fmt:message
								key="product_info_jsp.table.header.charValue" /></td>
						<td><fmt:message
								key="product_info_jsp.table.header.charDescription" /></td>
					</tr>
				</thead>
				<c:forEach var="item" items="${productChars}">
					<tr>
						<td>${item.name}</td>
						<td>${item.value}</td>
						<td>${item.description}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</c:if>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	<!--  <script>
		function updateCart(total) {
			document.getElementById("total").innerHTML = total
		}
	</script> -->
</body>
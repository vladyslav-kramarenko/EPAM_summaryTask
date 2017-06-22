<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Menu" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body onload="init()">
	<script>
		$(function() {
			$("#productSave").button();
			$("#productDelete").button();
			$("#btns").buttonset();
			$("#addBtn").button();
			$("#saveBtn").button();
			$("#cancelBtn").button();
		});
	</script>

	<script src="js/javascript.js" type="text/javascript"></script>
	<table style="width: 100%" id="main-container">
		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<c:if test="${userRole.name == 'admin' }">
			<tr>
				<td align="center">
					<form id="productForm" action="controller" method="post">
						<table style="width: 100%">
							<tr>
								<td rowspan="8" width="20%"><img id="productImage"
									height="200" src="<fmt:message key='imageDir'/>${product.img}"
									alt="image of ${product.name}">
								<td>Product name:</td>
								<td><input style="width: 240px" type="text" name="name"
									value="${product.name} " required /></td>
							</tr>


							<tr>
								<td>Manufacturer:</td>
								<td><input style="width: 240px" type="text"
									name="manufacturer" id="complete-field"
									value="${product.manufacturer}"
									onkeyup="doManufacturerCompletion();" required /></td>
							</tr>
							<tr>
								<td></td>
								<td colspan="1"><table style="width: 100%;">
										<tr>
											<td id="auto-row" colspan="1">
												<table id="complete-table" class="popupBox"></table>
											</td>
										</tr>
									</table></td>
							</tr>
							<tr>
								<td>Category:</td>
								<td><select style="width: 240px" name="category">

										<c:forEach var="category" items="${categoryItems}">
											<c:choose>
												<c:when test="${category.name != product.category}">
													<option value="${category.id}">
														<fmt:message
															key='products_jsp.button.category.${category.name}' />
													</option>
												</c:when>
												<c:otherwise>
													<option selected value="${category.id}"><fmt:message
															key='products_jsp.button.category.${category.name}' /></option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td>Image name:</td>
								<td><input style="width: 240px" type="text" name="img"
									value="${product.img}" /></td>
							</tr>
							<tr>
								<td>Availability:</td>
								<td><select style="width: 240px" name="availability">
										<c:forEach var="availability" items="${availabilityList}">
											<c:choose>
												<c:when test="${availability != product.availability}">
													<option value="${availability}">
														<fmt:message
															key='products_jsp.availability.${availability}' />
													</option>
												</c:when>
												<c:otherwise>
													<option selected value="${availability}"><fmt:message
															key='products_jsp.availability.${availability}' /></option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td>Price, $:</td>
								<td><input style="width: 240px" type="number" name="price"
									value="${product.price}" required /></td>
							</tr>
							<tr>
								<td></td>
								<td align="left" colspan="1"><input type="hidden"
									name="command" value="saveProduct" /> <input type="hidden"
									name="product_id" value="${product.id}" /> <span id="btns">
										<input type="submit" style="width: 120px" id="productSave"
										name="submit"
										value="<fmt:message key='product_edit_jsp.button.save'/>" />
										<input type="submit" style="width: 120px" id="productDelete"
										name="delete" class="button"
										value="<fmt:message key='product_edit_jsp.button.deleteProduct'/>">
								</span></td>
							</tr>
							<tr>
								<td width=100% colspan="3"><textarea style="width: 100%"
										name="description" cols="80" rows="5">${product.description}</textarea></td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
			<tr>
				<td>
					<form id="editForm" action="controller" method="get">
						<input type="hidden" name="command" value="editProduct" /> <input
							type="hidden" name="product_id" value="${product.id }" />
						<table style="width: 100%">
							<tr>
								<td><fmt:message key="label.name" />:</td>
								<td><select style="width: 173px" name="characteristicId">

										<c:forEach var="item" items="${characteristicsItems}">
											<c:choose>
												<c:when test="${item.name != characteristic.name}">
													<option value="${item.id}">
														<fmt:message key='characteristic.${item.name}' />
													</option>
												</c:when>
												<c:otherwise>
													<option selected value="${item.id}"><fmt:message
															key='characteristic.${item.name}' /></option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
								</select></td>
								<c:choose>
									<c:when test="${action=='edit'}">
										<td><input type="hidden" name="action" value="update" /></td>
										<td><input type="hidden" name="characteristicId"
											value="${characteristic.id }" /></td>
										<td><input type="hidden" name="id"
											value="${characteristic.characteristicId }" /></td>

										<td><fmt:message key="label.value" />:</td>
										<td><input type="text" name="characteristicValue"
											value="${characteristic.value }" required /></td>

										<td><input type="submit" id="saveBtn"
											value="<fmt:message key="button.saveCharacteristic" />"></td>

									</c:when>
									<c:otherwise>
										<td><input type="hidden" name="action" value="create" /></td>

										<td><fmt:message key="label.value" />:</td>
										<td><input type="text" name="characteristicValue"
											required /></td>

										<td><input type="submit" id="addBtn"
											value="<fmt:message key="button.addCharacteristic" />"></td>
									</c:otherwise>
								</c:choose>
								<td><a id="cancelBtn" href="controller?command=editProduct"><fmt:message
											key="button.cancel" /></a></td>
							</tr>
							<tr>
								<td colspan="10">
									<hr>
								</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>

			<tr>
				<td>
					<table style="width: 100%">
						<thead>
							<tr>
								<td width="100" align="left"><fmt:message
										key="product.characteristic.name" /></td>
								<td width="100" align="center"><fmt:message
										key="product.characteristic.value" /></td>
								<td width="300" align="left"><fmt:message
										key="product.characteristic.description" /></td>
								<td width="100" align="center"><fmt:message
										key="table.header.edit" /></td>
								<td width="100" align="center"><fmt:message
										key="table.header.delete" /></td>
							</tr>
						</thead>
						<c:forEach var="bean" items="${characteristicsList}">

							<tr height="20">
								<td width="300" align="left">${bean.name}</td>
								<td width="300" align="center">${bean.value}</td>
								<td width="300" align="left">${bean.description}</td>
								<td width="100" align="center"><a
									href="controller?command=editProduct&product_id=${product.id}&action=edit&id=${bean.id}"><fmt:message
											key="button.edit" /></a></td>
								<td align="center"><a
									href="controller?command=editProduct&product_id=${product.id}&action=delete&id=${bean.id}"><fmt:message
											key="button.delete" /></a></td>

							</tr>

						</c:forEach>
					</table>
				</td>
			</tr>
		</c:if>
	</table>
	<script>
		$("#productForm").validate();
	</script>

</body><%@ include file="/WEB-INF/jspf/footer.jspf"%>
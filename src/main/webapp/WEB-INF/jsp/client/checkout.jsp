<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<c:set var="view" value="/checkout" scope="session" />
<body>
	<script>
		$(function() {
			$("#confirmOrder").button({});
		});
	</script>


	<script>
		$(document).ready(function() {
			$("#checkoutForm").validate({
				rules : {
					name : "required",
					lastName : "required",
					email : {
						required : true,
						email : true
					},
					phone : {
						required : true,
						number : true,
						minlength : 9,
						maxlength : 9
					},
					address : {
						required : true
					},
					city : {
						required : true
					}
				}
			});
		});
	</script>
	<%@ include file="/WEB-INF/jspf/head.jspf"%>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>

	<%-- HTML markup starts below --%>
	<div style="width: 100%" id="singleColumn">

		<h2>
			<fmt:message key="chekout_jsp.checkout" />
		</h2>

		<p>
			<fmt:message key="chekout_jsp.checkoutText" />
		</p>

		<c:if test="${!empty orderFailureFlag}">
			<p class="error">
				<fmt:message key="chekout_jsp.orderFailureError" />
			</p>
		</c:if>

		<form id="checkoutForm" action="controller" method="post">
			<table id="checkoutTable" class="rounded">
				<c:if test="${!empty validationErrorFlag}">
					<tr>
						<td colspan="2" style="text-align: left"><span
							class="error smallText"><fmt:message
									key="chekout_jsp.validationErrorMessage" /> <c:if
									test="${!empty nameError}">
									<br>
									<span class="indent"><fmt:message
											key="chekout_jsp.nameError" /></span>
								</c:if> <c:if test="${!empty lastNameError}">
									<br>
									<span class="indent"><fmt:message
											key="chekout_jsp.last_nameError" /></span>
								</c:if> <c:if test="${!empty emailError}">
									<br>
									<span class="indent"><fmt:message
											key="chekout_jsp.emailError" /></span>
								</c:if> <c:if test="${!empty phoneError}">
									<br>
									<span class="indent"><fmt:message
											key="chekout_jsp.phoneError" /></span>
								</c:if> <c:if test="${!empty addressError}">
									<br>
									<span class="indent"><fmt:message
											key="chekout_jsp.addressError" /></span>
								</c:if> <c:if test="${!empty cityRegionError}">
									<br>
									<span class="indent"><fmt:message
											key="chekout_jsp.cityError" /></span>
								</c:if> </span></td>
					</tr>
				</c:if>
				<tr>
					<td align="right"><label for="name"><fmt:message
								key="user.first_name" />:</label></td>
					<td align="left"><input type="text" size="31" maxlength="45"
						id="name" name="name" value="${user.firstName}" required></td>
				</tr>
				<tr>
					<td align="right"><label for="lastName"><fmt:message
								key="user.last_name" />:</label></td>
					<td align="left" class="inputField"><input type="text"
						size="31" maxlength="45" id="last_name" name="last_name"
						value="${user.lastName}" required></td>
				</tr>
				<tr>
					<td align="right"><label for="email"><fmt:message
								key="user.email" />:</label></td>
					<td align="left" class="inputField"><input type="text"
						size="31" maxlength="45" id="email" name="email"
						value="${user.email}" required></td>
				</tr>
				<tr>
					<td align="right"><label for="phone"><fmt:message
								key="user.phone" />:</label></td>
					<td align="left" style="width: 100%" class="inputField"><input
						type="number" size="31" maxlength="12" id="phone" name="phone"
						value="${user.phone}" required></td>
				</tr>
				<tr>
					<td align="right"><label for="city"><fmt:message
								key="user.city" />:</label></td>
					<td align="left" class="inputField"><input type="text"
						size="31" maxlength="45" id="city" name="city"
						value="${user.city}" required></td>
				</tr>
				<tr>
					<td align="right"><label for="address"><fmt:message
								key="user.address" />:</label></td>
					<td align="left" class="inputField"><input type="text"
						size="31" maxlength="45" id="address" name="address"
						value="${user.address}" required></td>
				</tr>
				<tr>
					<td align="center" colspan="2"><input type="hidden"
						name="command" value="confirmOrder" /> <input type="submit"
						id="confirmOrder"
						value="<fmt:message key='checkout_jsp.button.submit'/>"></td>
				</tr>
			</table>
		</form>
	</div>
</body>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Manufacturers list" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<script>
		$("#editForm").validate();
	</script>

	<Script>
		$(function() {
			$("#addBtn").button();
			$("#saveBtn").button();
			$("#cancelBtn").button();
		});
	</Script>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<form id="editForm" action="controller" method="get">
		<input type="hidden" name="command" value="manufacturers" />
		<table style="width: 100%">
			<tr>
				<td><fmt:message key="label.manufacturersName" />:</td>
				<c:choose>
					<c:when test="${action=='edit'}">
						<td><input type="hidden" name="action" value="update" /></td>
						<td><input type="hidden" name="manufacturerId"
							value="${manufacturer.id }" /></td>
						<td><input type="text" name="manufacturerName"
							value="${manufacturer.name}" required /></td>
						<td><input type="submit" id="saveBtn"
							value="<fmt:message
						key="button.saveManufacturer" />"></td>

					</c:when>
					<c:otherwise>
						<td><input type="hidden" name="action" value="create" /></td>
						<td><input type="text" name="manufacturerName" /></td>
						<td><input type="submit" id="addBtn"
							value="<fmt:message
						key="button.addManufacturer" />"
							required /></td>
					</c:otherwise>
				</c:choose>
				<td><a id="cancelBtn" href="controller?command=manufacturers"><fmt:message
							key="button.cancel" /></a></td>
			</tr>
		</table>
	</form>
	<table style="width: 100%">
		<thead>
			<tr>
				<td width="50">№</td>
				<td width="300" align="center"><fmt:message
						key="product.manufacturer.name" /></td>
				<td width="100" align="center"><fmt:message
						key="table.header.edit" /></td>
				<td width="100" align="center"><fmt:message
						key="table.header.delete" /></td>
			</tr>
		</thead>
		<c:forEach var="bean" items="${manufacturersList}">

			<tr height="20">
				<td align="left" width="50">${bean.id}</td>
				<td align="center" width="300">${bean.name}</td>
				<td align="center" width="100"><a
					href="controller?command=manufacturers&action=edit&manufacturerId=${bean.id}"><fmt:message
							key="button.edit" /></a></td>
				<td align="center"><a
					href="controller?command=manufacturers&action=delete&manufacturerId=${bean.id}"><fmt:message
							key="button.delete" /></a></td>
			</tr>

		</c:forEach>
	</table>
</body>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</html>
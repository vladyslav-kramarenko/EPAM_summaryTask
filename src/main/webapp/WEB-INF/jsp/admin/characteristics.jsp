<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Characteristics list" scope="page" />
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
		<input type="hidden" name="command" value="characteristics" />
		<table style="width: 100%">
			<tr>
				<td><fmt:message key="label.name" />:</td>
				<c:choose>
					<c:when test="${action=='edit'}">
						<td><input type="hidden" name="action" value="update" /></td>
						<td><input type="hidden" name="characteristicId"
							value="${characteristic.id }" /></td>
						<td><input type="text" name="characteristicName"
							value="${characteristic.name}" required /></td>
						<td><fmt:message key="label.description" />:</td>
						<td><input type="text" name="characteristicDescription"
							value="${characteristic.description}" required /></td>
						<td><input type="submit" id="saveBtn"
							value="<fmt:message
						key="button.saveCharacteristic" />"></td>

					</c:when>
					<c:otherwise>
						<td><input type="hidden" name="action" value="create" /></td>
						<td><input type="text" name="characteristicName" required /></td>
						<td><fmt:message key="label.description" />:</td>
						<td><input type="text" name="characteristicDescription"
							required /></td>
						<td><input type="submit" id="addBtn"
							value="<fmt:message
						key="button.addCharacteristic" />"></td>
					</c:otherwise>
				</c:choose>
				<td><a id="cancelBtn" href="controller?command=characteristics"><fmt:message
							key="button.cancel" /></a></td>
			</tr>
		</table>
	</form>
	<table style="width: 100%">
		<thead>
			<tr>
				<td width="50">№</td>
				<td width="100" align="center"><fmt:message
						key="product.characteristic.name" /></td>
				<td width="300" align="center"><fmt:message
						key="product.characteristic.description" /></td>
				<td width="100" align="center"><fmt:message
						key="table.header.edit" /></td>
				<td width="100" align="center"><fmt:message
						key="table.header.delete" /></td>
			</tr>
		</thead>
		<c:forEach var="bean" items="${characteristicsList}">

			<tr height="20">
				<td width="50" align="left">${bean.id}</td>
				<td width="300" align="center"><fmt:message
						key="characteristic.${bean.name}" /></td>
				<td width="300" align="center">${bean.description}</td>
				<td width="100" align="center"><a
					href="controller?command=characteristics&action=edit&characteristicId=${bean.id}"><fmt:message
											key="button.edit" /></a></td>
				<td align="center"><a
					href="controller?command=characteristics&action=delete&characteristicId=${bean.id}"><fmt:message
											key="button.delete" /></a></td>

			</tr>

		</c:forEach>
	</table>
</body>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</html>
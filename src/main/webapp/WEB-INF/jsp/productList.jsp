<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<style>
    .button {
        width: 170px;
        text-align: center;
        height: 35px;
    }

    .select {
        width: 200px;
        margin-top: 0px;
        text-align: center;
        font-size: 5px;
        height: 25px;
        font-size: 5px;
    }
</style>
<c:set var="title" value="Poduct catalog" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body onload="init()">

<script src="js/javascript.js" type="text/javascript"></script>
<script>
    $(function () {
        $("#newProduct").button();
        $("input[type=submit], button").button();
        $("#sort").selectmenu({
            change: function (event, ui) {
                $("#filterForm").submit();
            }
        });
        $("#slider-range-max").slider({
            range: "max",
            value: "${(maxValue == null) ? maxPrice :maxValue}",
            min: 1,
            max: "${maxPrice}",
            slide: function (event, ui) {
                $("#max").val("$" + ui.value);
            },
            change: function (event, ui) {
                $("#filterForm").submit();
            }
        });
        $("#max").val("$" + $("#slider-range-max").slider("value"));

        $("#slider-range-min").slider({
            range: "min",
            value: "${minValue}",
            min: 1,
            max: "${maxPrice}",
            slide: function (event, ui) {
                $("#min").val("$" + ui.value);
            },
            change: function (event, ui) {
                $("#filterForm").submit();
            }
        });
        $("#min").val("$" + $("#slider-range-min").slider("value"));
    });
</script>
<div id="main">

    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <table id="main-container" style="width: 100%;">

        <tr>
            <td align="center">
                <table style="width: 60%;">
                    <tr>
                        <td><fmt:message key='label.search'/>:</td>
                        <td style="width: 100%;"><input type="text"
                                                        style="width: 100%;" id="complete-field"
                                                        onkeyup="doCompletion();"></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td id="auto-row" colspan="1">
                            <table id="complete-table" class="popupBox"></table>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td>
                <table>
                    <tr>
                        <td width="200" style="vertical-align: top;">
                            <table>
                                <c:if test="${userRole.name == 'admin' }">
                                    <tr>
                                        <td>
                                            <form action="controller" method="get">
                                                <input type="hidden" name="command" value="editProduct"/>
                                                <input style="width: 200" type="submit" name="newProduct"
                                                       value="<fmt:message
																		key='products_jsp.button.addProduct' />"
                                                       id="newProduct">
                                            </form>
                                        </td>
                                    </tr>
                                </c:if>
                            </table>
                            <form id="filterForm" action="controller" method="get">
                                <table style="width: 100%">

                                    <tr>
                                        <td><input type="hidden" name="command"
                                                   value="productList"/> <label for="sort"><fmt:message
                                                key='label.sort'/>:</label></td>
                                    </tr>
                                    <tr>
                                        <td><select name="sort" id="sort" class="select"
                                                    onChange="this.form.submit();">
                                            <c:forEach var="sortType" items="${sortTypes}">

                                                <c:choose>
                                                    <c:when test="${sortType.name != sort}">
                                                        <option value="${sortType.name}">
                                                            <fmt:message
                                                                    key='products_jsp.button.sort.${sortType.name}'/>
                                                        </option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option selected value="${sort}"><fmt:message
                                                                key='products_jsp.button.sort.${sort}'/></option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select></td>
                                    </tr>
                                    <tr>
                                        <td><label for="min"><fmt:message
                                                key='label.minPrice'/>:</label> <input
                                                onChange="this.form.submit();" type="text" readonly
                                                name="min" id="min"
                                                style="width: 100%; border: 0; color: #555; font-weight: bold;">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <div id="slider-range-min"></div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><label for="max"><fmt:message
                                                key='label.maxPrice'/>:</label> <input
                                                onChange="this.form.submit();" type="text" readonly
                                                name="max" id="max"
                                                style="width: 100%; border: 0; color: #555; font-weight: bold;">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>

                                            <div id="slider-range-max"></div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table style="width: 100%" class="manufacturers">
                                                <c:forEach var="manufacturer" items="${manufacturersList}">
                                                    <tr>
                                                        <td>${manufacturer.name}</td>
                                                        <td><c:choose>
                                                            <c:when test="${manufacturer.selected}">
                                                                <input type="checkbox" name="manufacturer_id"
                                                                       value="${manufacturer.id} " checked
                                                                       onChange="this.form.submit();"/>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="checkbox" name="manufacturer_id"
                                                                       value="${manufacturer.id} "
                                                                       onChange="this.form.submit();"/>
                                                            </c:otherwise>
                                                        </c:choose></td>
                                                    </tr>
                                                </c:forEach>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </td>

                        <td id="productList" rowspan="2" style="vertical-align: top;">
                            <c:forEach
                                    var="item" items="${productItems}">
                                <hr>
                                <table style="width: 100%;">
                                    <tr>
                                        <td width="150" rowspan="2">
                                            <img id="productImage" height="100" width="100"
                                                 <%--src="<fmt:message key='imageDir'/>${item.img}"--%>
                                                 <%--alt="image of ${item.name}"/>--%>
                                            src="<c:url value="/image/${item.img}"/>"
                                            alt="image of ${item.name}"/>
                                        </td>

                                        <td width="*">
                                            <a href="controller?command=productInfo&product_id=${item.id}">
                                                    ${item.manufacturer} ${item.name} </a>
                                        </td>
                                        <td align="center" width="200">${item.price}$(<fmt:message
                                                key='products_jsp.availability.${item.availability}'/>)
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><fmt:message key='product.category.${item.category}'/></td>
                                        <td align="center"><c:choose>
                                            <c:when test="${userRole.name == 'admin' }">

                                                <form action="controller" method="get">
                                                    <input type="hidden" name="command" value="editProduct"/>
                                                    <input type="hidden" name="product_id" value="${item.id}">
                                                    <span id="adminEdit${loop}"> <input
                                                            style="text-align: center; width: 110px" type="submit"
                                                            name="edit" class="button"
                                                            value="<fmt:message key='product_edit_jsp.button.editProduct'/>">
																<input style="text-align: center; width: 110px"
                                                                       type="submit" name="delete" class="button"
                                                                       value="<fmt:message key='product_edit_jsp.button.deleteProduct'/>">
															</span>
                                                </form>
                                            </c:when>
                                            <c:otherwise>
                                                <form action="controller" method="post">
                                                    <input type="hidden" name="command" value="addToCart"/>
                                                    <input type="hidden" name="product_id" value="${item.id}">
                                                    <input type="submit" name="submit" class="button"
                                                           value="<fmt:message key='products_jsp.button.addtocart'/>">
                                                </form>
                                            </c:otherwise>
                                        </c:choose></td>
                                    </tr>
                                </table>
                            </c:forEach></td>
                    </tr>

                </table>
            </td>
        </tr>

    </table>
</div>

<script type="text/javascript">
    $("#date").datepicker();
</script>
</body>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
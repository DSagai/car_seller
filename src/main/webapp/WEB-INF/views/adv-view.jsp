<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="static/css/w3.css?v=<?=time();?"/>
    <script type="text/javascript" src="static/scripts/updatePageScript.js?v=<?=time();?"></script>
</head>
<body onload="iterateNextImage(0)">

<div class="w3-content w3-display-container">
    <c:forEach var="item" items="${albumItems}">
        <img src="${pageContext.request.contextPath}/albumItem.jpg?id=${item}"
             class="albumItem" style="display: none">
    </c:forEach>


    <button class="w3-button w3-black w3-display-left" onclick="iterateNextImage(-1)">&#10094;</button>
    <button class="w3-button w3-black w3-display-right" onclick="iterateNextImage(1)">&#10095;</button>
</div>

<br>

<table>
    <tr>
        <td>
            Manufacturer:
        </td>
        <td>
            ${advertisement.manufacturer.name}
        </td>
    </tr>

    <tr>
        <td>Model:</td>
        <td>
            ${advertisement.modelName}
        </td>
    </tr>
    <tr>
        <td>Year of production:</td>
        <td>
            ${advertisement.yearOfProduction}
        </td>
    </tr>
    <tr>
        <td>
            Car body:
        </td>
        <td>
            ${advertisement.carBodyType.name}
        </td>
    </tr>
    <tr>
        <td>
            Transmission type:
        </td>
        <td>
            ${advertisement.transmission.name}
        </td>
    </tr>
    <tr>
        <td>
            Engine type:
        </td>
        <td>
            ${advertisement.engineType.name}
        </td>
    </tr>
    <tr>
        <td>Engine volume, cc:</td>
        <td>
            ${advertisement.engineVolume}
        </td>
    </tr>
    <tr>
        <td>Engine power, hp:</td>
        <td>
            ${advertisement.horsePowers}
        </td>
    </tr>
    <tr>
        <td>Odometer, km:</td>
        <td>
            ${advertisement.odometer}
        </td>
    </tr>
    <tr>
        <td>Description:</td>
        <td>
            ${advertisement.description}
        </td>
    </tr>
    <tr>
        <td>Price:</td>
        <td>
            ${advertisement.sellPrice}
        </td>
    </tr>
</table>

<br>

<div style="background: yellow">
    <sec:authorize access="isAuthenticated()">
        <sec:authentication var="user" property="principal" />
        <c:if test="${user.username == advertisement.owner.username}">
            <c:url var="link" value="update-advertisement-form">
                <c:param name="advId" value="${advertisement.id}"/>
            </c:url>
            <a href=${link}>Edit</a>
        </c:if>
    </sec:authorize>
</div>

</body>
</html>

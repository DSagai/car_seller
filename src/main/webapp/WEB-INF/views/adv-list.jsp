<%--
  Created by IntelliJ IDEA.
  User: A
  Date: 11.05.2017
  Time: 21:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="static/css/w3.css?v=<?=time();?"/>
    <script type="text/javascript" src="static/scripts/advListPageScript.js?v=<?=time();?"></script>
</head>
<body onload="init()">
<h1>Advertisement list</h1>

<form action="${pageContext.request.contextPath}/logout" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="logout">
</form>


<table>
    <tr>
        <td>Manufacturer:</td>
        <td>
            <select name="manufacturer" class="searchParam" restriction="EQ">
                <option value=""/>
                <c:forEach var="item" items="${manufacturerList}">
                    <option value="${item.id}">${item.name}</option>
                </c:forEach>
            </select>
        </td>
    </tr>
    <tr>
        <td>Model:</td>
        <td>
            <input name="modelName" class="searchParam" restriction="LIKE"
                   prefix="%" suffix="%"
                   type="text" placeholder="Enter model name here.">
        </td>
    </tr>
    <tr>
        <td>Car body:</td>
        <td>
            <select name="carBodyType" class="searchParam" restriction="EQ">
                <option value=""/>
                <c:forEach var="item" items="${carBodyType}">
                    <option value="${item.id}">${item.name}</option>
                </c:forEach>
            </select>
        </td>
    </tr>
    <tr>
        <td>Transmission:</td>
        <td>
            <select name="transmission" class="searchParam" restriction="EQ">
                <option value=""/>
                <c:forEach var="item" items="${transmission}">
                    <option value="${item.id}">${item.name}</option>
                </c:forEach>
            </select>
        </td>
    </tr>
    <tr>
        <td>Engine type:</td>
        <td>
            <select name="engineType" class="searchParam" restriction="EQ">
                <option value=""/>
                <c:forEach var="item" items="${engineType}">
                    <option value="${item.id}">${item.name}</option>
                </c:forEach>
            </select>
        </td>
    </tr>
    <tr>
        <td>Engine volume:</td>
        <td>
            From
            <input name="engineVolume" class="searchParam" restriction="GE" type="text">
        </td>
        <td>
            To
            <input name="engineVolume" class="searchParam" restriction="LE" type="text">
        </td>
    </tr>
    <tr>
        <td>Engine power:</td>
        <td>
            From
            <input name="horsePowers" class="searchParam" restriction="GE" type="text"/>
        </td>
        <td>
            To
            <input name="horsePowers" class="searchParam" restriction="LE" type="text"/>
        </td>
    </tr>
    <tr>
        <td>Odometer:</td>
        <td>
            From
            <input name="odometer" class="searchParam" restriction="GE" type="text"/>
        </td>
        <td>
            To
            <input name="odometer" class="searchParam" restriction="LE" type="text"/>
        </td>
    </tr>
    <tr>
        <td>Price:</td>
        <td>
            From
            <input name="sellPrice" class="searchParam" restriction="GE" type="text"/>
        </td>
        <td>
            To
            <input name="sellPrice" class="searchParam" restriction="LE" type="text"/>
        </td>
    </tr>
    <tr>
        <td>Year of production:</td>
        <td>
            From
            <input name="yearOfProduction" class="searchParam" restriction="GE" type="text"/>
        </td>
        <td>
            To
            <input name="yearOfProduction" class="searchParam" restriction="LE" type="text"/>
        </td>
    </tr>
    <tr>
        <td>
            <button onclick="filterButtonClick()">Apply filter</button>
        </td>
    </tr>
</table>


<div id="result">

</div>

</body>
</html>

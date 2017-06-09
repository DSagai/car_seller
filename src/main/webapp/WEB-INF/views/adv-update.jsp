<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="static/css/main.css?v=<?=time();?"/>
    <link rel="stylesheet" type="text/css" href="static/css/w3.css?v=<?=time();?"/>
    <script type="text/javascript" src="static/scripts/slideShow.js?v=<?=time();?"></script>

</head>
<body onload="init()">

<img src="${pageContext.request.contextPath}/sketchPhoto.jpg?id=${advertisement.id}"/>

<br>
<div class="w3-content w3-display-container">
    <c:forEach items="${images}" var="item">
        <img src="${pageContext.request.contextPath}/albumPhoto.jpg?id=${item.id}" class="albumItem">
    </c:forEach>
    <button onclick="putNextImage(-1)" class="w3-button w3-black w3-display-left">&#10094;</button>
    <button onclick="putNextImage(1)" class="w3-button w3-black w3-display-right">&#10095;</button>
</div>


<br>
<div id="container">
    <sf:form id="adv-add-form" action="${pageContext.request.contextPath}/UPDATE-ADVERTISEMENT"
             commandName="advertisement" method="post">

        <table>
            <tr>
                <td colspan="2">Enter car details</td>
            </tr>
            <tr>
                <td>
                    Manufacturer:
                </td>
                <td>
                    <sf:select path="manufacturer.id">
                        <c:forEach var="item" items="${manufacturerList}">
                            <sf:option value="${item.id}">${item.name}</sf:option>
                        </c:forEach>
                    </sf:select>
                </td>
            </tr>

            <tr>
                <td>Model:</td>
                <td>
                    <sf:input path="modelName" type="text" placeholder="Enter model here."/>
                    <br>
                    <sf:errors path="modelName" cssClass="error"/>
                </td>
            </tr>
            <tr>
                <td>Year of production:</td>
                <td>
                    <sf:input path="yearOfProduction" type="text"/>
                    <sf:errors path="yearOfProduction" cssClass="error"/>
                </td>
            </tr>
            <tr>
                <td>
                    Car body:
                </td>
                <td>
                    <sf:select path="carBodyType.id">
                        <c:forEach var="item" items="${carBodyType}">
                            <sf:option value="${item.id}">${item.name}</sf:option>
                        </c:forEach>
                    </sf:select>
                </td>
            </tr>
            <tr>
                <td>
                    Transmission type:
                </td>
                <td>
                    <sf:select path="transmission.id">
                        <c:forEach var="item" items="${transmission}">
                            <sf:option value="${item.id}">${item.name}</sf:option>
                        </c:forEach>
                    </sf:select>
                </td>
            </tr>
            <tr>
                <td>
                    Engine type:
                </td>
                <td>
                    <sf:select path="engineType.id">
                        <c:forEach var="item" items="${engineType}">
                            <sf:option value="${item.id}">${item.name}</sf:option>
                        </c:forEach>
                    </sf:select>
                </td>
            </tr>
            <tr>
                <td>Engine volume, cc:</td>
                <td>
                    <sf:input path="engineVolume" type="text"
                              placeholder="Enter engine volume in cc. For example 1600."/>
                    <br>
                    <sf:errors path="engineVolume" cssClass="error"/>
                </td>
            </tr>
            <tr>
                <td>Engine power, hp:</td>
                <td>
                    <sf:input path="horsePowers" type="text" placeholder="Enter number of hp."/>
                    <br>
                    <sf:errors path="horsePowers" cssClass="error"/>
                </td>
            </tr>
            <tr>
                <td>Odometer, km:</td>
                <td>
                    <sf:input path="odometer" type="text" placeholder="Enter number of kilometers."/>
                    <br>
                    <sf:errors path="odometer" cssClass="error"/>
                </td>
            </tr>
            <tr>
                <td>Description:</td>
                <td>
                    <sf:input path="description" type="text" placeholder="up to 1000 characters."/>
                </td>
            </tr>
            <tr>
                <td>Price:</td>
                <td>
                    <sf:input path="sellPrice" type="text"/>
                    <sf:errors path="sellPrice" cssClass="error"/>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit">
                </td>
            </tr>
        </table>
    </sf:form>
</div>
</body>
</html>

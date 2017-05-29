<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!DOCTYPE html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="static/css/main.css?v=<?=time();?"/>
    <script type="text/javascript" src="static/scripts/password-validation.js?v=<?=time();?"></script>
</head>
<body>

<div id = "container">
    <h3>Add new user</h3>

    <sf:form id = "user_form" action="${pageContext.request.contextPath}/ADD_USER"
    method="post" commandName="user">
        <table>
            <tbody>
            <tr>
                <td>Logins:</td>
                <td>
                    <sf:input path="username" type="text" name="username" placeholder="enter Login here" />
                    <br>
                    <sf:errors path="username" cssClass="error" />
                </td>
            </tr>
            <tr>
                <td>Password:</td>
                <td>
                    <input type="password" name="password"
                           id="password" placeholder="enter password" required onkeyup="comparePasswords()"/>
                    <br>
                    <div id="pass-valid"></div>
                </td>
            </tr>
            <tr>
                <td>Confirm password:</td>
                <td>
                    <input type="password" name="confirmpass"
                           id="confirmPass" placeholder="enter password here" required onkeyup="comparePasswords()"/>
                    <br>
                    <div id="matchPass"></div>
                </td>
            </tr>
            <tr>
                <td>First name:</td>
                <td>
                    <sf:input path="firstName" type="text" name="firstName"
                              id="firstName" placeholder="enter first name here"/>
                    <br>
                    <sf:errors path="firstName" cssClass="error"/>
                </td>
            </tr>
            <tr>
                <td>Last name:</td>
                <td>
                    <sf:input path="lastName" type="text" name="lastName"
                              id="lastName" placeholder="enter last name here"/>
                    <br>
                    <sf:errors path="lastName" cssClass="error"/>
                </td>
            </tr>
            <tr>
                <td>Email:</td>
                <td>
                    <sf:input path="email" type="text" name="email" placeholder="enter email here"/>
                    <br>
                    <sf:errors path="email" cssClass="error"/>
                </td>
            </tr>
            <tr>
                <td>Phone number:</td>
                <td>
                    <sf:input path="phoneNumber" type="text" name="phoneNumber"
                              id = "phoneNumber" placeholder="enter phone number here"/>
                    <br>
                    <sf:errors path="phoneNumber" cssClass="error"/>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="button" value="Save" onclick="onSubmit()"/>
                </td>
            </tr>

            </tbody>
        </table>
    </sf:form>

    <div style="clear: both"></div>
    <p>
        <a href="${pageContext.request.contextPath}/login">Back to login
            page</a>
    </p>

</div>

</body>

</tml>

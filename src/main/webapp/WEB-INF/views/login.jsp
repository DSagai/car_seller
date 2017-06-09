<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<head>
    <title>Login</title>

</head>
<body>

<c:if test="${param.error != null }">
    <p class="error">Login failed. Check your login and password.</p>
</c:if>

<div id="container">
    <form name='f'
          action='${pageContext.request.contextPath}/j_spring_security_check'
          method='POST'>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <table>
            <tr>
                <td>Login:</td>
                <td>
                    <input type="text" name="j_username" value="">
                </td>
            </tr>
            <tr>
                <td>Password:</td>
                <td>
                    <input type="password" name="j_password">
                </td>
            </tr>
            <tr>
                <td>Remember me:</td>
                <td>
                    <input type="checkbox" class="checkbox" name="_spring_security_remember_me" checked="checked">
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" name="submit" value="Login">
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>

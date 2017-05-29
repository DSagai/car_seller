<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    User <b>${USERNAME}</b> has been created.
    <br>
    Activation letter was sent to the registered email.
    <br>
    <a href="${pageContext.request.contextPath}/login">Return to login page</a>

</div>

</body>
</html>

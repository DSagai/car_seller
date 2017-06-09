
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="static/css/main.css?v=<?=time();?"/>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/upload-file"
      enctype="multipart/form-data"
>
    <sec:csrfInput/>
    <input type="file" name="picture">
    <input type="submit">
</form>
</body>
</html>

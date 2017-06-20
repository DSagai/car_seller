<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="static/css/main.css?v=<?=time();?"/>
    <script>
        function preview() {
            var files = document.querySelectorAll("input[type=file]");
            files[0].addEventListener("change", function() {
                if(this.files && this.files[0]) {
                    var reader = new FileReader();
                    reader.onload = function(e) { document.getElementById("preview").setAttribute("src", e.target.result); }
                    reader.readAsDataURL(this.files[0]);
                }
            });
        }
    </script>
</head>
<body onload="preview()">
<form method="post" action="${pageContext.request.contextPath}/upload-file"
      enctype="multipart/form-data"
>
    <sec:csrfInput/>
    <input type="hidden" name="id" value="2">
    <input type="file" name="picture" accept="image/*">
    <input type="submit">
</form>
<br>
<img src="" id="preview">
</body>
</html>

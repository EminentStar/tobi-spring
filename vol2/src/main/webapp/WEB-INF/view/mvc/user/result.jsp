<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>result</title>
</head>
<body>
<div class="reuslt">
  <c:if test="${not empty form}">
    id: ${form.id}<br>
    name: ${form.name}<br>
    nickname: ${form.nickname}<br>
  </c:if>
</div>

<div class="error">
  errorCount: ${errorCount}<br>
  errorMessage: ${errorMessage}<br>
</div>
</body>
</html>

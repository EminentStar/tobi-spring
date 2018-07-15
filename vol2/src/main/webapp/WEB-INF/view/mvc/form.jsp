<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- TODO: isELIgnored를 default로 false하는 법을 찾아야할 듯 -->

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>form</title>
</head>
<body>
<div class="msg">
  message: ${message} <!-- ELIgnored 재현용 -->
</div>
<div>
  <form name="form" action="form" method="post">
    <input type="text" name="name"/>
    <button type="submit">submit</button>
  </form>
</div>

<div class="result">
  <c:if test="${not empty name}">
    name: ${name}
  </c:if>
</div>
</body>
</html>

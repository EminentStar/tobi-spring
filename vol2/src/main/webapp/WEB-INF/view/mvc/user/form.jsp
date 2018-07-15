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
<div>
  <form name="form" action="form" method="post">
    id: <input type="text" name="id" id="id"/>
    name: <input type="text" name="name" id="name"/>
    nickname: <input type="text" name="nickname" id="nicname"/>
    <button type="submit">submit</button>
  </form>
  <br>
  form model<br>
  id: ${form.id}<br>
  name: ${form.name}<br>
  nickname: ${form.nickname}<br>
</div>
</body>
</html>

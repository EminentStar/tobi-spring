<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
  "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.springframework.context.ApplicationContext" %>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@page import="org.eminentstar.temp.HelloSpring" %>
<html>
<head>
  <title>Test</title>
</head>
<body>
<!-- http://localhost:8080/hellospring.jsp 로 접근 -->
<%
ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(
  request.getSession().getServletContext());
HelloSpring helloSpring = context.getBean(HelloSpring.class);

out.println(helloSpring.sayHello("Root Context"));
%>

</body>
</html>

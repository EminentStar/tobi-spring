<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>index</title>
  <%--local file 부르는게 왜 안되지?--%>
  <%--<script src="/js/library/jquery-3.3.1.min.js"></script>--%>
  <script
    src="https://code.jquery.com/jquery-3.3.1.min.js"
    integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
    crossorigin="anonymous"></script>
</head>
<body>
<form id="user">
  <fieldset>
    <label>로그인 아이디 : </label>
    <input id="loginid" name="loginid" type="text"/><br/>
    <label>비밀번호 : </label>
    <input id="password" name="password" type="password"/><br/>
    <label>이름 : </label>
    <input id="name" name="name" type="text"/><br/>
    <input type="submit" value="등록"/>
  </fieldset>
</form>
</body>
<content tag="jsfooter">
  <script type="text/javascript">
    $(document).ready(function () {
      $('#user').submit(function (e) {
        e.preventDefault();
        console.log('submit clicked!');
        var user = $(this).serialize();   // 폼의 모든 입력 필드를 JSON 포맷 메시지로 만듬.

        <!-- TODO: form 전송이 안됨... 나중에 보자. -->
        $.ajax({
          url: 'register',
          type: 'POST',
          data: {
            id: user.id,
            password: user.password,
            name: user.name
          },
          success: function(data, status, xhr) {
            alert('등록완료.');
            console.log(user);
          },
          error: function(xhr, status, error) {
            console.log(error);
          }
        });

        return false;

      });
    });
  </script>
</content>
</html>

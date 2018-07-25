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
<label>Login ID: </label>
<input id="loginid" name="loginid" type="text"/>
<input id="loginidcheck" type="button" value="아이디 중복검사"/>
<br>
<a href="/ajax/edit">로그인하기</a>
</body>
<content tag="jsfooter">
  <script type="text/javascript">
    $(document).ready(function() {
      $('#loginidcheck').click(function() {
        // 이렇게 하니깐 id의 dot(.)이 포함되질 않음.
        $.getJSON('checkloginid/' + $('#loginid').val(), function(result) {
          if (result.duplicated == true) {
            alert('이미 등록된 로그인 ID입니다. ' + result.availableId + '는 사용 할 수 있습니다.');
          } else {
            alert('사용할 수 있는 로그인 ID 입니다.');
          }
        });
      })
    });
  </script>
</content>
</html>

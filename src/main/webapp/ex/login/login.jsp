<%@ page import="java.awt.geom.Area" %><%--
  Created by IntelliJ IDEA.
  User: kitri
  Date: 2024-01-16
  Time: 오전 9:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>로그인 폼</title>
</head>
<body>
    <%
//        String isLoginFailed = (String) request.getAttribute("isLoginFailed");
        String id = (String) session.getAttribute("id"); //세션의 id의 값을 가져옴
        if (id != null)
        {
            //로그인 성공했을 때 나오는 브라우저%>
    <h2>안녕하세요! <%=id%>님 접속중입니다.</h2>
    <a href="/ex/logout">로그아웃</a>
    <%--로그인이 성공되면 로그인 성공 멘트가 나오고 만약 로그인에 실패하게 되면 재로그인 브라우저 창을 띄운다.--%>

    <%}else {%>

    <form method="post" action="/ex/login">
        <label for="id"> 아이디 : </label>
        <input type="text" name="id" id="id"><br>
        <label for="pw"> 비밀번호 : </label>
        <input type="password" name="pw" id="pw"><br>
        <input type="submit" value="로그인">

    </form>

    <div class="notification">${requestScope.loginFailed ? "로그인이 실패하였습니다. 아이디와 비밀번호를 정확하게 입력해주세요." : "" }</div>
    <%}%>

</body>
<script>
    <%--자바스크립트--%>
    setTimeout(() => {
        document.querySelector(".notification").hidden = true;
    }, 2000);
</script>
</html>

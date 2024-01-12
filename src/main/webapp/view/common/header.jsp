<%--
  Created by IntelliJ IDEA.
  User: kitri
  Date: 2024-01-09
  Time: 오후 4:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header>
    <a class="logo" href="/board/list"><span class="material-symbols-outlined">emoticon</span></a>
    <nav>
        <ul class="nav-items">
            <li><a href="/board/list">게시글목록</a></li>
            <li><a href="/board/createForm">게시글등록</a></li>
            <li><a href="/board/updateForm">게시글수정</a></li>
            <li><a href="/view/member/join.jsp">회원가입</a></li>
            <li><a href="/view/member/registration.jsp">회원정보수정</a></li>
            <li><a href="/view/member/login.jsp">로그인</a></li>
        </ul>


        <form class="d-flex" role="search" action="/board/list?" method="post">

            <%--작성자 / 제목이 전달됨--%>
            <select class="SelectSearch" name="search">
                <option value="writer">작성자</option>
                <option value="title">제목</option>
            </select>

            <%--키워드가 전달됨--%>
            <input type="text" name="keyword">
            <button class="btn btn-outline-success" type="submit">Search</button>
        </form>
    </nav>
</header>


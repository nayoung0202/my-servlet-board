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
<%--    <div class="d-flex">--%>
<%--    <nav>--%>
<%--        <ul class="nav-items">--%>
<%--            <li><a href="/board/list">게시글목록</a></li>--%>
<%--            <li><a href="/board/createForm">게시글등록</a></li>--%>
<%--            <li><a href="/board/updateForm">게시글수정</a></li>--%>
<%--            <li><a href="/view/member/join.jsp">회원가입</a></li>--%>
<%--            <li><a href="/view/member/registration.jsp">회원정보수정</a></li>--%>
<%--            <li><a href="/view/member/login.jsp">로그인</a></li>--%>

<%--            <form class="d-flex" role="search" action="/board/list?" method="post">--%>


<%--                &lt;%&ndash;날짜 기간으로 검색 드롭다운&ndash;%&gt;--%>
<%--                <select class="DateSearch" name="dateTime">--%>
<%--                    <option value="100 YEAR" ${param.dateTime == "100 YEAR" ? "selected" : ""}>전체 기간</option>--%>
<%--                    <option value="2 day" ${param.dateTime == "2 day" ? "selected" : ""}>1일</option>--%>
<%--                    <option value="7 day" ${param.dateTime == "7 day" ? "selected" : ""}>1주일</option>--%>
<%--                    <option value="1 month" ${param.dateTime == "1 month" ? "selected" : ""}>1개월</option>--%>
<%--                    <option value="6 MONTH" ${param.dateTime == "6 MONTH" ? "selected" : ""}>6개월</option>--%>
<%--                    <option value="1 YEAR" ${param.dateTime == "1 YEAR" ? "selected" : ""}>1년</option>--%>
<%--                </select>--%>

<%--                &lt;%&ndash;작성자 / 제목이 전달됨&ndash;%&gt;--%>
<%--                <select class="SelectSearch" name="search">--%>
<%--                    &lt;%&ndash;param으로 search를 가져와서 title 또는 writer이면 selected를 실행해서 드롭다운에 제목 또는 작성자가 나오게 만든다.&ndash;%&gt;--%>
<%--                    <option value="title" ${param.search == "title" ? "selected" : ""}>제목</option>--%>
<%--                    <option value="writer" ${param.search == "writer" ? "selected" : ""}>작성자</option>--%>
<%--                </select>--%>

<%--                &lt;%&ndash;키워드가 전달됨&ndash;%&gt;--%>
<%--                &lt;%&ndash;param으로 keyword의 정보를 받아서 input창에 작성한 키워드의 값이 유지된다.&ndash;%&gt;--%>
<%--                <input type="text" name="keyword" value="${param.keyword}">--%>
<%--                <button class="btn btn-outline-success" type="submit">Search</button>--%>
<%--            </form>--%>
<%--        </ul>--%>

<%--    </nav>--%>
    <nav class="navbar bg-body-tertiary">
        <div class="container-fluid">

            <ul class="nav-items">
                            <li><a href="/board/list">게시글목록</a></li>
                            <li><a href="/board/createForm">게시글등록</a></li>
<%--                            <li><a href="/board/updateForm">게시글수정</a></li>--%>
<%--                            <li><a href="/view/member/join.jsp">회원가입</a></li>--%>
                            <li><a href="/view/member/registration.jsp">회원정보수정</a></li>
                            <li><a href="/view/member/login.jsp">로그인</a></li>
                <form class="d-flex" role="search"  action="/board/list?" method="post">

<%--                <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">--%>
                <select class="DateSearch" name="dateTime">--%>
                    <option value="100 YEAR" ${param.dateTime == "100 YEAR" ? "selected" : ""}>전체 기간</option>
                    <option value="2 day" ${param.dateTime == "2 day" ? "selected" : ""}>1일</option>
                    <option value="7 day" ${param.dateTime == "7 day" ? "selected" : ""}>1주일</option>
                    <option value="1 month" ${param.dateTime == "1 month" ? "selected" : ""}>1개월</option>
                    <option value="6 MONTH" ${param.dateTime == "6 MONTH" ? "selected" : ""}>6개월</option>
                    <option value="1 YEAR" ${param.dateTime == "1 YEAR" ? "selected" : ""}>1년</option>
                </select>

                <%--작성자 / 제목이 전달됨--%>
                <select class="SelectSearch" name="search">
                    <%--param으로 search를 가져와서 title 또는 writer이면 selected를 실행해서 드롭다운에 제목 또는 작성자가 나오게 만든다.--%>
                    <option value="title" ${param.search == "title" ? "selected" : ""}>제목</option>
                    <option value="writer" ${param.search == "writer" ? "selected" : ""}>작성자</option>
                </select>

                <%--키워드가 전달됨--%>
                <%--param으로 keyword의 정보를 받아서 input창에 작성한 키워드의 값이 유지된다.--%>
                <input type="text" name="keyword" value="${param.keyword}">
                <button class="btn btn-outline-success" type="submit">Search</button>
            </form>
            </ul>
        </div>
    </nav>
<%--</div>--%>
</header>




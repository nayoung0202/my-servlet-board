<%--
  Created by IntelliJ IDEA.
  User: kitri
  Date: 2024-01-09
  Time: 오후 4:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language= "java" %>
<header class="d-flex flex-row">
    <a class="logo" href="/board/list"><span class="material-symbols-outlined">emoticon</span></a>
    <nav class="flex-fill ">
        <ul class="nav-items d-flex flex-row">
            <li><a href="/board/list">게시글목록</a></li>
            <%--            <li><a href="/board/createForm">게시글등록</a></li>--%>
            <%--            <li><a href="/board/updateForm">게시글수정</a></li>--%>
            <li><a href="/view/member/join.jsp">회원가입</a></li>
            <li><a href="/view/member/registration.jsp">회원정보수정</a></li>
            <li><a href="/view/member/login.jsp">로그인</a></li>

            <div class="flex-fill"></div>
            <form class="d-flex pt-2 pb-3" role="search" action="/board/list?" method="post">

                <%--날짜 기간으로 검색 드롭다운--%>
                <select class="me-2 DateSearch" name="dateTime">
                    <option value="100 YEAR" ${param.dateTime == "100 YEAR" ? "selected" : ""}>전체 기간</option>
                    <option value="2 day" ${param.dateTime == "2 day" ? "selected" : ""}>1일</option>
                    <option value="7 day" ${param.dateTime == "7 day" ? "selected" : ""}>1주일</option>
                    <option value="1 month" ${param.dateTime == "1 month" ? "selected" : ""}>1개월</option>
                    <option value="6 MONTH" ${param.dateTime == "6 MONTH" ? "selected" : ""}>6개월</option>
                    <option value="1 YEAR" ${param.dateTime == "1 YEAR" ? "selected" : ""}>1년</option>
                </select>

                <%--작성자 / 제목이 전달됨--%>
                <select class="me-1 SelectSearch" name="search">
                    <%--param으로 search를 가져와서 title 또는 writer이면 selected를 실행해서 드롭다운에 제목 또는 작성자가 나오게 만든다.--%>
                    <option value="title" ${param.search == "title" ? "selected" : ""}>제목</option>
                    <option value="writer" ${param.search == "writer" ? "selected" : ""}>작성자</option>
                </select>

                <%--키워드가 전달됨--%>
                <%--param으로 keyword의 정보를 받아서 input창에 작성한 키워드의 값이 유지된다.--%>
                <input style="padding-left:10px" type="text" name="keyword" value="${param.keyword}">
                <button class="btn btn-outline-success" type="submit">Search</button>

            </form>
        </ul>


    </nav>

    <div class="first-dropdown">


    </div>
</header>





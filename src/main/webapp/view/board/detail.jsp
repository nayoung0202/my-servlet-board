<%@ page import="com.kitri.myservletboard.data.Member" %>
<%@ page import="com.kitri.myservletboard.data.Board" %>
<%@ page import="com.kitri.myservletboard.data.Comment" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<jsp:include page="/view/common/head.jsp">
    <jsp:param name="title" value="게시판 상세"/>
</jsp:include>

<body class="sb-nav-fixed">
<jsp:include page="/view/common/header.jsp"/>
<% Board board = (Board) request.getAttribute("board");
    Member memberLogin = (Member) session.getAttribute("memberLogin");
    Member member = (Member) request.getAttribute("member");
    ArrayList<Comment> comment = (ArrayList<Comment>) request.getAttribute("comment");
%>

<main class="mt-5 pt-5">
    <div class="container-fluid px-4 ">

        <div class="card mb-4 w-50 mx-auto">
            <div>
                <h2 class="mt-3" style="text-align: center;"><b>게시판 상세</b></h2>
                <hr class="mb-0">
            </div>
            <div class="d-flex flex-column" style="height: 500px;">
                <div class="p-2 border-bottom border-dark-subtle d-flex flex-row-reverse">
                    <div class="pd opacity-75 bg-info-subtle border border-dark rounded-pill"><small>조회수 : ${board.getViewCount()}</small></div>
                    &nbsp
                    <div class="pd opacity-75 bg-success-subtle border border-dark rounded-pill"><small>댓글수 : ${board.getCommentCount()}</small></div>
                    <div class="d-flex flex-row flex-fill">
                        <div class="pd opacity-75 border border-dark rounded-pill">#${board.getId()}</div>
                    </div>
                </div>
                <div class="p-2 border-bottom">
                    <b>${board.getTitle()}</b>
                </div>
                <div class="p-2 border-bottom">
                    <b>저자 :</b> ${board.getWriter()}
                </div>
                <div class="p-2 border-bottom">
                    <b>등록일시 :</b> ${board.getCreatedAt()}
                </div>
                <div class="m-3 h-75">
                    <textarea rows="5" class="h-100 form-control bg-white" id="content" name="content"
                              disabled>${board.getContent()}</textarea>
                </div>
                <div class="d-flex flex-row-reverse mb-3 mr-3">
                    <%
                        if (memberLogin != null) {
                            if (memberLogin.getId().equals(board.getMember_id())) {
                    %>
                    &nbsp
                    &nbsp
                    <a href="/board/delete?id=${board.getId()}" class="btn btn-secondary btn-sm" onclick="return confirm('삭제하시겠습니까?')"><small>삭제하기</small></a>
                    &nbsp
                    <a href="/board/updateForm?id=${board.getId()}" class="btn btn-secondary btn-sm"><small>수정하기</small></a>
                    <%      }
                        }
                    %>
                    &nbsp
                    <a href="/board/list" class="btn btn-secondary btn-sm"><small>목록으로</small></a>
                    &nbsp
                </div >
                <form action="/comment/detail" method="post">
                    <label class="form-label">댓글 달기</label>
                    <div class="m-3 h-20">
                    <input type="text" class="h-20 form-control bg-white" id="comment" name="comment" value="">
                    <input type="text" name="board_id" value="${board.getId()}" hidden>
                    <input type="text" name="member_id" value="${memberLogin.getId()}" hidden>
                    </div>
                    <div class="d-flex flex-row-reverse mb-3 mr-3">
                        <%
                            if (memberLogin != null) {
                                if (memberLogin.getId().equals(board.getMember_id())) {
                        %>
<%--                        <a href="/comment/delete?id=${comment.getId()}" class="btn btn-secondary btn-sm" onclick="return confirm('삭제하시겠습니까?')"><small>댓글 삭제하기</small></a>--%>
<%--                        &nbsp--%>
<%--                        <a href="/comment/updateForm?id=${comment.getId()}" class="btn btn-secondary btn-sm"><small>댓글 수정하기</small></a>--%>
                        <%      }
                        }
                        %>
                        &nbsp
                        <button type="submit" class="btn btn-secondary btn-sm"><small>확인</small></button>
                        &nbsp
                    </div >
                </form>

                    <table class="table">
                        <thead>
                        <tr>
                            <th scope="col">번호</th>
                            <th scope="col">작성자</th>
                            <th scope="col">댓글 내용</th>
                            <th scope="col">날짜</th>
                        </tr>

                        </thead>

                        <tbody>

                        <%for (int i = 0; i < comment.size(); i++) { %>
                        <tr>
                            <th scope ="row"> <%= comment.get(i).getId()%> </th>
                            <td> <%= board.getWriter()%> </td>
                            <td> <%= comment.get(i).getContent() %> </td>
                            <td> <%= comment.get(i).getCreated_at().format(DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm")) %> </td>
                            <td><form action="/comment/delete" method="post"><button class="btn btn-secondary btn-sm" onclick="return confirm('삭제하시겠습니까?')"><small>댓글 삭제하기</small></button>
                            <input type="text" name="delete_id" value="${comment.get(i).getId()}" hidden>
                                <input type="text" name="board_id" value="${board.getId()}" hidden></form></td>

                        </tr>
                        <% } %>
                        </tbody>
                    </table>
            </div>
        </div>
    </div>
</main>
</body>
<style>
    .pd {
        padding-left: 5px;
        padding-right: 5px;
    }
</style>
</html>
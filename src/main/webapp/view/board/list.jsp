<%@ page import="java.util.ArrayList" %>
<%@ page import="com.kitri.myservletboard.data.Board" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="com.kitri.myservletboard.data.Pagination" %>
<%@ page import="com.kitri.myservletboard.data.Member" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<%
  Pagination pagination = (Pagination) request.getAttribute("pagination");
  String search = (String) request.getAttribute("search");
  String keyword = (String) request.getAttribute("keyword");
  String dateTime = (String) request.getAttribute("dateTime");
  String firstdata = (String) request.getAttribute("firstdata");

  //조건 : keyword가 null이 아니면 keyword가 null이면 공백의 값을 보내서 다음 페이지로 갔을 때 search와 keyword의 값이 전달되지 않게 한다.
//  String searchparms = "";
  String params = "";
  if (keyword != null) {
    params += "&search=" + search + "&keyword=" + keyword + "&dateTime=" + dateTime + "&firstdata=" +firstdata + "&number=" + pagination.getMaxRecordsPerPage();
  } else {
    keyword = "";
  }
%>


<jsp:include page="/view/common/head.jsp">
  <jsp:param name="title" value="게시판 목록"/>
</jsp:include>


<body>
<%-- 동적 jsp 리팩토링 --%>
<jsp:include page="/view/common/header.jsp">
  <jsp:param name="search" value="<%=search%>"/>
  <jsp:param name="keyword" value="<%=keyword%>"/>
  <jsp:param name="dateTime" value="<%=dateTime%>"/>
  <jsp:param name="firstdata" value="<%=firstdata%>"/>
  <jsp:param name="number" value="<%=pagination.getMaxRecordsPerPage()%>"/>
</jsp:include>

  <div class="d-flex pt-5 mt-5">
    <div class="flex-fill w-25"></div>
    <h2 class = "flex-fill w-50" style="text-align: center;"><b>게시판 목록</b></h2>

    <%--form에서 action을 통해 이후 갈 경로를 넣지 않아도 현재 있는 경로에서 데이터를 보낸다.--%>
    <form class="flex-fill w-25 pr-5 mr-5">
      <input hidden = "hidden" name="search" value="<%=search%>">
      <input hidden = "hidden" name="keyword" value="<%=keyword%>">
      <input hidden = "hidden" name="dateTime" value="<%=dateTime%>">

      <select class="firstdata" onchange="this.form.submit()" name="firstdata">
        <option value="createdAtdesc" <%if (firstdata.equals("createdAtdesc")) {%>selected<%}%>>최신순</option>
        <option value="viewCountdesc" <%if (firstdata.equals("viewCountdesc")) {%>selected<%}%>>조회순</option>
        <option value="accuracy" <%if (firstdata.equals("accuracy")) {%>selected<%}%>>정확도순</option>
      </select>

        <select class="numberdata" name="number" onchange="this.form.submit()">
          <option value="5" <%if(pagination.getMaxRecordsPerPage() == 5 ){%>selected<%}%>>5개씩 보기</option>
          <option value="10" <%if(pagination.getMaxRecordsPerPage() == 10){%>selected<%}%>>10개씩 보기</option>
          <option value="15" <%if(pagination.getMaxRecordsPerPage() == 15){%>selected<%}%>>15개씩 보기</option>
          <option value="20" <%if(pagination.getMaxRecordsPerPage() == 20){%>selected<%}%>>20개씩 보기</option>
          <option value="30" <%if(pagination.getMaxRecordsPerPage() == 30){%>selected<%}%>>30개씩 보기</option>
          <option value="40" <%if(pagination.getMaxRecordsPerPage() == 40){%>selected<%}%>>40개씩 보기</option>
          <option value="50" <%if(pagination.getMaxRecordsPerPage() == 50){%>selected<%}%>>50개씩 보기</option>
        </select>
    </form>
  </div>


  <div class="container class=d-flex justify-content-center">
    <div class="p-2 border-primary mb-3">
      <table class="table align-middle table-hover">
        <thead class="table-dark">
          <tr>
            <th scope="col">번호</th>
            <th scope="col">제목</th>
            <th scope="col">작성자</th>
            <th scope="col">날짜</th>
            <th scope="col">조회수</th>
            <th scope="col">댓글수</th>
          </tr>
        </thead>
        <tbody class="table-group-divider">
        <% ArrayList<Board> boards = (ArrayList<Board>) request.getAttribute("boards");
          for (int i = 0; i < boards.size(); i++) { %>

        <tr>
            <th scope ="row"> <%= boards.get(i).getId() %> </th>
          <td> <a href="/board/detail?id=<%= boards.get(i).getId() %>"><%= boards.get(i).getTitle() %></a></td>
            <td> <%= boards.get(i).getWriter() %> </td>
            <td> <%= boards.get(i).getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm")) %> </td>
          <td> <%= boards.get(i).getViewCount() %> </td>
            <td> <%= boards.get(i).getCommentCount() %> </td>
          </tr>
        <% } %>

        </tbody>
      </table>
      <div>
        <%
          Member memberLogin = (Member) session.getAttribute("memberLogin");
          if (memberLogin != null){
            if (memberLogin.getUserId() != null)
            {%>
          <a href="/board/createForm" role="button" class="btn btn-outline-dark">글쓰기</a>
          <%}}%>

      </div>
      <div class="d-flex justify-content-center">
      <nav aria-label="Page navigation example">
        <ul class="pagination pagination-sm">
          <%--위에서 객체를 선언해주면 아래에서도 가져다가 쓸 수 있다.--%>

          <%--pagination에서 hasNext, hasPrev를 getter를 만들어 준다.--%>
          <%-- pagination은 이미 선언되어 있어서 뒤에서 또 쓸 수 있다.--%>

            <%
              if (pagination.isHasPrev()){
            %>

            <li class="page-item">
              <%--첫 번째 페이지에서 -1을 하면 이전 페이지로 이동--%>
              <a class="page-link" href="/board/list?page=<%=pagination.getStartPageOnScreen()-1%><%=params%>" tabindex="-1" aria-disabled="true">Previous</a>
            </li>
          <%} else {%>
            <li class="page-item disabled">
            <a class="page-link" href="/board/list?page=<%=pagination.getStartPageOnScreen()-1%><%=params%>" tabindex="-1" aria-disabled="true">Previous</a>
        <%}%>


        <%
          for (int i = pagination.getStartPageOnScreen(); i <= pagination.getEndPageOnScreen(); i++){
            if (pagination.getPage() == i){
        %>
          <li class="page-item"><a class="page-link active" href="/board/list?page=<%=i%><%=params%>"><%=i%></a></li>

          <%} else {%>
          <li class="page-item"><a class="page-link" href="/board/list?page=<%=i%><%=params%>"><%=i%></a></li>
          <%}}%>

        <%
          if (pagination.isHasNext()){
        %>

          <li class="page-item">
            <a class="page-link" href="/board/list?page=<%=pagination.getEndPageOnScreen() + 1%><%=params%>">Next</a>
          </li>
<%--          <li class="page-item">--%>
<%--            <a class="page-link" href="/board/list?page=<%=pagination.getEndPageOnScreen()+1%>">Next</a>--%>
<%--          </li>--%>
        <%} else {%>
          <li class="page-item disabled">
            <a class="page-link" href="/board/list?page=<%=pagination.getEndPageOnScreen()+1%><%=params%>">Next</a>
          </li>
        <%}%>

        </ul>
      </nav>
    </div>
    </div>
  </div>

  <div class="p-2">
    <div class="container d-flex justify-content-center">
      <footer>
        <span class="text-muted">&copy; Company's Bootstrap-board</span>
      </footer>
    </div>
  </div>


</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
  integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>

</html>
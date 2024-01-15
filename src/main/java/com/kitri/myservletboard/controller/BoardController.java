package com.kitri.myservletboard.controller;

import com.kitri.myservletboard.data.Board;
import com.kitri.myservletboard.data.Pagination;
import com.kitri.myservletboard.service.BoardService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;


//Controller : 요청받고 적절한 페이지를 응답하는 것 -> 데이터를 해당 jsp 에 보내는 것이 request.setAttribute인 것
@WebServlet("/board/*")
public class BoardController extends HttpServlet {

    BoardService boardService = BoardService.getInstance();
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        out.println("<h1>요청을 응답 받았습니다.</h1>");

        //url을 파싱해서 어떤 요청인지 파악 (분기)
        out.println(request.getRequestURI());

        request.setCharacterEncoding("UTF-8");
        String resquestURI = request.getRequestURI(); // /board/create
        String contextPath = request.getContextPath(); // /
        String command = resquestURI.substring(contextPath.length()); // /board/list
        String view = "/view/board/";
        //substring :

        out.println("command = " + command); // /board/create, command = /board/create


        if (command.equals("/board/list")) {
            //요청 : 조회 게시글 리스트 화면
            //응답 : 게시글 리스트 페이지로 응답
//            response.sendRedirect("/view/board/list.jsp");
//            response.addHeader("Refresh", "2; url = " + "/view/board/list.jsp");

            //*(2)pagination!!=> page id 가져오기 -> /board/list?page = 3 을 읽어야 됨
            String page = request.getParameter("page");
            //page의 값이 아무것도 없을 경우
            if (page == null || page == "") {
                page = "1";
            }

            String number = request.getParameter("number");
            //default값을 넣어줌
            if (number == null){
                number = "10";
            }

//            //writer 또는 title 의 정보 가져오기
            String search = request.getParameter("search");
            if (search == null || search == "") {
                search = "title";
            }
            String keyword = request.getParameter("keyword");
            if (keyword == null || keyword == "") {
                keyword = "";
            }
            String  dateTime = request.getParameter("dateTime");
            if (dateTime == null || dateTime == "") {
                dateTime = "100 YEAR";
            }
            String firstdata = request.getParameter("firstdata");
            if (firstdata == null){
                firstdata = "createdAtdesc";
            }





            // 가져와서 쓸 수 있게 paginaiton을 선언
            //pagination안에 maxRecodesPerPage의 값이 number의 값이므로
            Pagination pagination = new Pagination(Integer.parseInt(page), Integer.parseInt(number));


            // 조건문 작성 : search 와 keyword의 값이 null값일 경우

            ArrayList<Board> boards = boardService.getBoards(search, keyword, pagination, dateTime, firstdata);

            //*(3) pagination 정보를 가져와서 페이지바의 활성화 비활성화를 결정
            request.setAttribute("pagination", pagination);

//            // 전체 조회를 하는 Boards의 길이 (size)를 받아서 전체 레코드의 수 가져오기
//            pagination.setTotalRecords(boardService.getBoards(Integer.parseInt()));
            request.setAttribute("dateTime", dateTime);

            request.setAttribute("search", search);
            request.setAttribute("keyword", keyword);
            request.setAttribute("firstdata", firstdata);

            request.setAttribute("boards", boards);
            // 데이터를 가져와 저장후 jsp에 넘겨주는 역할 -> dispatcher
            //jsp에게 넘겨줘야 함 - 게시판을 동적으로 만듦



            view += "list.jsp";

        } else if (command.equals("/board/createForm")) {
            // 요청 : 게시글 등록 폼 화면
            // 응답 : 등록 폼으로 응답
//            response.sendRedirect("/view/board/createFrom.html");
            view += "createForm.jsp";

        } else if (command.equals("/board/create")) {
            // 데이터를 읽고 등록

            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String writer = request.getParameter("writer");

            // 생성자로 값을 주는 것 순서를 맞게 적어주기
            // 게시판 객체 생성
            // 정적 메소드 타입 => LocalDataTime.now() : 알아서 현재 시간을 나타냄
            Board board = new Board(null, title, content, writer, LocalDateTime.now(), 0, 0);
            boardService.addBoard(board);

            response.sendRedirect("/board/list");
            return;
            // 리다이렉트를 사용하면 return을 해줘야 한다.


        } else if (command.equals("/board/updateForm")) {
//            response.sendRedirect("/view/board/updateForm.jsp");
            // 게시판 수정하기
            Long id = Long.parseLong(request.getParameter("id"));
            //detail.jsp에서 id 받아옴
            Board board = boardService.getBoard(id);
            // request를 통해 브라우저에서 id를 받아오고 board 변수 생성하고 boardService에서 id의 값을 처리한다. ( boardService에 가서 boardDao에 갔다가 boardDao를 상속받은 BoardMemory 또는 BoardJdbcDao로 가서 getbyId 로 처리한다.


            request.setAttribute("board", board);
            //처리한 id를 board에 넣는다.
            view += "updateForm.jsp";

        } else if (command.equals("/board/update")) {
            // 수정폼에서 보낸 데이터를 읽고 수정하라는 데이터를 수정한다.

            //수정할 브라우저에 필요한 데이터를 가져온다.
            //request를 통해 사용자에게 받은 데이터를 가져오는 것
            Long id = Long.parseLong(request.getParameter("id"));
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String writer = request.getParameter("writer");


            // updateboard메소드를 통해 수정된 데이터 ( boardService로 가서 boardService에서 BoardDao에 갔다가 상속받은 BoardMemory 또는 BoardJdbcDao에서 처리한다.)를 받아서 boardService에 덮어쓰기 한다.(업데이트)
            boardService.updateBoard(new Board(id, title, content, writer, LocalDateTime.now(), 0, 0));

            response.sendRedirect("/board/list");
            // 처리가 끝나면 list.jsp로 돌아간다.
            return;


        } else if (command.equals("/board/delete")) {
            //삭제할 때 필요한 데이터는 id를 식별자로 삭제하기 때문에 id만 가져오고 다른 데이터 값은 null로 한다.
            Long id = Long.parseLong(request.getParameter("id"));
            // 브라우저에서 id를 가져온다.

            boardService.deleteBoard(new Board(id, null, null, null, LocalDateTime.now(), 0, 0));
            //boardService에 가서 BoardDao로 가고 BoardDao를 상속받은 BoardMemory 또는 BoardJdbcDao로 간다.
            // BoardMemory로 간 id는 delete를 처리한다.

            response.sendRedirect("/board/list");
            return;

        } else if (command.contains("/board/detail")) {
            // /board/detail?id=3
            // id에 해당하는 게시판 하나를 가져와야 됨

//            String quertyString = request.getQueryString();


            Long id = Long.parseLong(request.getParameter("id"));
            Board board = boardService.getBoard(id);
            //board 데이터를 detail.jsp에 전달하기 위해 어딘가에(request) 담아져서 와야한다.
            // request로 브라우저에 id 값을 가져온다.
            // boardService를 통해
            request.setAttribute("board", board);

            view += "detail.jsp";
        }

            //뷰(페이지)를 응답하는 방법
            // 리다이렉트 : 클라이언트한테 재요청할 URL을 직접 전달
            // 포워드 :
            RequestDispatcher dispatcher = request.getRequestDispatcher(view);
            dispatcher.forward(request, response);
        }

}

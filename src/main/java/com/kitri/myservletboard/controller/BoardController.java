package com.kitri.myservletboard.controller;

import com.kitri.myservletboard.data.Board;
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

            ArrayList<Board> boards = boardService.getBoards();

            request.setAttribute("boards", boards);
            // 데이터를 가져와 저장후 jsp에 넘겨주는 역할 -> dispatccher
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
            Board board = new Board(null, title, content, writer, LocalDateTime.now(), 0, 0 );
            boardService.addBoard(board);

            response.sendRedirect("/board/list");
            return;
            // 리다이렉트를 사용하면 return을 해줘야 한다.


        } else if (command.equals("/board/updateForm")) {
//            response.sendRedirect("/view/board/updateForm.jsp");
            view += "updateForm.jsp";

        } else if (command.equals("/board/update")) {
            response.sendRedirect("/view/member/join.jsp");


        } else if (command.equals("/board/delete")) {
            response.sendRedirect("/view/member/login.jsp");

        } else if (command.contains("/board/detail")) {
            // /board/detail?id=3
            // id에 해당하는 게시판 하나를 가져와야 됨
            //

//            String quertyString = request.getQueryString();
            Long id = Long.parseLong(request.getParameter("id"));
            Board board = boardService.getBoard(id);
            //board 데이터를 detail.jsp에 전달하기 위해 어딘가에(request) 담아져서 와야한다.
            request.setAttribute("board", board);

            view += "detail.jsp";

        }

        //뷰(페이지)를 응답하는 방법
            // 리다이렉트 : 클라이언트한테 재요청할 URL을 전달
            // 포워드 :
        RequestDispatcher dispatcher = request.getRequestDispatcher(view);
        dispatcher.forward(request, response);
    }
}

package com.kitri.myservletboard.controller;

import com.kitri.myservletboard.data.Board;
import com.kitri.myservletboard.data.BoardService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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

        out.println("command = " + command); // /board/create, command = /board/create

        if (command.equals("/board/list")) {
            //요청 : 조회 게시글 리스트 화면
            //응답 : 게시글 리스트 페이지로 응답
//            response.sendRedirect("/view/board/list.jsp");
//            response.addHeader("Refresh", "2; url = " + "/view/board/list.jsp");
            ArrayList<Board> boards = boardService.getBoards();
            request.setAttribute("boards", boards);

            view += "list.jsp";

        } else if (command.equals("/board/createForm")) {
            // 요청 : 게시글 등록 폼 화면
            // 응답 : 등록 폼으로 응답
//            response.sendRedirect("/view/board/createFrom.html");
            view += "createForm.jsp";

        } else if (command.equals("/board/create")) {
            response.sendRedirect("/view/member/registration.jsp");

        } else if (command.equals("/board/updateForm")) {
//            response.sendRedirect("/view/board/updateForm.jsp");
            view += "updateForm.jsp";

        } else if (command.equals("/board/update")) {
            response.sendRedirect("/view/member/join.jsp");


        } else if (command.equals("/board/delete")) {
            response.sendRedirect("/view/member/login.jsp");

        } else if (command.equals("/board/detail")) {

        }

        //뷰(페이지)를 응답하는 방법
            // 리다이렉트 : 클라이언트한테 재요청할 URL을 전달
            // 포워드
        RequestDispatcher dispatcher = request.getRequestDispatcher(view);
        dispatcher.forward(request, response);
    }
}

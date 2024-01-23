package com.kitri.myservletboard.controller;

import com.kitri.myservletboard.data.Board;
import com.kitri.myservletboard.data.Comment;
import com.kitri.myservletboard.data.Pagination;
import com.kitri.myservletboard.service.CommentService;

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

@WebServlet("/comment/*")
public class CommentController extends HttpServlet {

    CommentService commentService = CommentService.getInstance();
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        out.println("<h1>요청을 응답 받았습니다.</h1>");

        //url을 파싱해서 어떤 요청인지 파악 (분기)
        out.println(request.getRequestURI());

        request.setCharacterEncoding("UTF-8");
        String resquestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String command = resquestURI.substring(contextPath.length());
        String view = "/view/board/";

        out.println("command = " + command);


        if (command.equals("/comment/detail")) {

            Long board_id = Long.parseLong(request.getParameter("board_id"));
            Long member_id = Long.parseLong(request.getParameter("member_id"));
            String comment = request.getParameter("comment");
            request.setAttribute("comment", comment);

            commentService.save(board_id, member_id, comment);

            response.sendRedirect("/board/detail?id=" + board_id);
            return;

//            view += "detail?id=" + board_id;
            ///board/detail?id=142
            //강사님 질문...
        }else if (command.equals("/comment/delete")){

            Long delete_id = Long.parseLong(request.getParameter("delete_id")); // 잘못된 id가 옴
            Long board_id = Long.parseLong(request.getParameter("board_id"));

            commentService.delete(delete_id);

            response.sendRedirect("/board/detail?id=" + board_id);
            return;

        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(view);
        dispatcher.forward(request, response);
    }
}

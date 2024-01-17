package com.kitri.myservletboard.controller;

import com.kitri.myservletboard.data.Board;
import com.kitri.myservletboard.data.Comment;
import com.kitri.myservletboard.data.Pagination;
import com.kitri.myservletboard.service.CommentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/comment/*")
public class CommentController extends HttpServlet {

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

            String comment = request.getParameter("comment");
            String board_id = request.getParameter("board_id");
            String member_id = request.getParameter("member_id");

            Comment comment1 = new Comment(comment, board_id, member_id);
            CommentService.saveBoard(comment1);

            view += "detail.jsp";
        }
    }
}

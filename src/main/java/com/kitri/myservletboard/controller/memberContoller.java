package com.kitri.myservletboard.controller;

import com.kitri.myservletboard.data.Member;
import com.kitri.myservletboard.service.MemberService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/member/*")
public class memberContoller extends HttpServlet {
    MemberService memberService = MemberService.getInstance();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        //세션을 받는 객체를 생성하여 계속 가져와 쓰는 방법도 있다.

        PrintWriter out = response.getWriter();
        out.println("<h1>요청을 응답 받았습니다.</h1>");

        //url을 파싱해서 어떤 요청인지 파악 (분기)
        out.println(request.getRequestURI());

        request.setCharacterEncoding("UTF-8");
        String resquestURI = request.getRequestURI(); // /board/create
        String contextPath = request.getContextPath(); // /
        String command = resquestURI.substring(contextPath.length()); // /board/list
        String view = "/view/member/";

        out.println("command = " + command); // /board/create, command = /board/create



        if (command.equals("/member/login")) {

            String userId = request.getParameter("userId");
            String userPw = request.getParameter("userPw");

            request.setAttribute("userId", userId);
            request.setAttribute("userPw", userPw);

            Member members = memberService.getMember(userId);
            // 브라우저에서 받은 사용자의 정보를 memberService를 통해 DB에 있는 정보를 가져온다.

//            String id1 = members.getUserId();
//            String pw1 = members.getUserPw();

            boolean isLoginFailed = false;

            if (userId.isEmpty() || userPw.isEmpty()) {
                isLoginFailed = true;

            }

            if (members.getUserId() == null) {

                isLoginFailed = true;

            } else {
                if (!members.getUserPw().equals(userPw)) {
                    isLoginFailed = true;
                }
            }

            if (isLoginFailed) {
                //isLoginFailed가 true이면 실패, false이면 성공인 것을 알려줌
                //로그인 실패
                request.setAttribute("isLoginFailed", isLoginFailed);
                response.sendRedirect("/member/loginForm");
                return;

            } else {
                // 로그인 성공 -> 사용자가 인증된 사용자라는 의미이므로 세션에 정보를 담음
                HttpSession session = request.getSession();
                session.setAttribute("memberLogin", members); // 로그인한 사용자의 id를 세션에 담는다. 또한 이것을 통해 현재 사용자가 로그인 된 사용자인지 아닌지를 알 수 있음

                view = "/board/list";
            }

        } else if (command.equals("/member/loginForm")) {
            view += "login.jsp";

        } else if (command.equals("/member/logout")) {
            HttpSession session = request.getSession();
            session.invalidate();

            response.sendRedirect("/board/list");
            return;

        } else if (command.equals("/member/join")) {
            // id, pw, name, email을 받아서 조건문 작성 : 회원가입 실패시 회원가입 실패 폼을 내보내고 회원가입 성공시 성공 폼을 내보냄

            String id = request.getParameter("id");
            String userId = request.getParameter("userId");
            String userPw = request.getParameter("userPw");
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String userPwcheck = request.getParameter("userPwcheck");

            Member members = new Member(id, userId, userPw, name, email);
            Member membercheck = memberService.getMember(userId);

            if ( membercheck.getUserId() != null) {
                // 아이디 중복이다.
                response.sendRedirect("/view/member/joinfail.jsp");
                return;
            } else {
                // 아이디 중복이 아니다.
                if (membercheck.getUserPw().equals(membercheck.getUserPwcheck())){
                    //비밀번호 중복인 경우
                    response.sendRedirect("/view/member/pwfail.jsp");
                    return;
                }
                memberService.addMemeber(members);
                //브라우저에서 받은 members의 값을 memberService를 통해 MemberJdbc에서 INSERT쿼리로 DB에 저장한다.
                response.sendRedirect("/view/member/joinsuccess.jsp");
                return;
            }
        }else if (command.equals("/member/registration")){
            String userId = request.getParameter("userId");
            String name = request.getParameter("name");
            String userPw = request.getParameter("userPw");
            String userPwcheck = request.getParameter("userPwcheck");
            String email = request.getParameter("email");

            memberService.updateMember(userId, userPw, userPwcheck, name, email);

            view += "registrationsuccess.jsp";
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(view);
        dispatcher.forward(request, response);
    }

}

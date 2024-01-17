package ex.login;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/ex/login")
public class LoginServlet extends HttpServlet {
    static HashMap<String, String> members = new HashMap<>();
    //members라는 맵

    static {

        members.put("abc123", "12345");

        members.put("captain91", "12345");

        members.put("ilovecoding", "12345");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //로그인을 요청했을 때 사용자가 로그인 폼을 요청
        RequestDispatcher dispatcher = request.getRequestDispatcher("/ex/login/login.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //사용자가 로그인 하면 로그인 과정을 처리함
        String id = request.getParameter("id");
        String pw = request.getParameter("pw");
        //id, pw를 읽음

        boolean isLoginFailed = false; // 우선은 로그인이 된다는 것을 가정
        //isLoginFailed가 false면 로그인 성공 , true이면 로그인 실패
        if (id.isEmpty() || pw.isEmpty()){
            isLoginFailed = true;
            // 만약 아이디 또는 비밀번호가 없으면 로그인이 안되도록 함
        }

        //members로 id를 가져오면 비밀번호를 알 수 있다.
        if (members.get(id) == null) {
            //만약에 id가 없으면 (null 체크)
            // equals로 하게 되면 null인 경우 if문으로 들어가지 못 하기 때문에 equals 보단 null로 해야한다.
            isLoginFailed = true;

        }else {
            if (! members.get(id).equals(pw)){
                isLoginFailed = true;
            }
        }

        if (isLoginFailed){
            //isLoginFailed가 true이면 실패, false이면 성공인 것을 알려줌
            //로그인 실패
            request.setAttribute("isLoginFailed", isLoginFailed);
        }else {
            // 로그인 성공 -> 사용자가 인증된 사용자라는 의미이므로 세션에 정보를 담음
            HttpSession session = request.getSession();
            session.setAttribute("id", id); // 로그인한 사용자의 id를 세션에 담는다. 또한 이것을 통해 현재 사용자가 로그인 된 사용자인지 아닌지를 알 수 있음

        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/ex/login/login.jsp");
        dispatcher.forward(request, response);


    }
}

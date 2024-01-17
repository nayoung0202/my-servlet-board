package com.kitri.myservletboard.dao;

import com.kitri.myservletboard.data.Board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CommentJdbcDao implements CommentDao{
    private static final CommentJdbcDao instance = new CommentJdbcDao();

    public static CommentDao getInstance() {
        return instance;
    }


    private CommentJdbcDao() {
    }

    public Connection connectDB() {
        //DB와 연결
        Connection conn = null;

        //쿼리를 날리기 위해서는 try-catch를 사용 -> 쿼리는
        try {
            //forName : 원하는 위치의 클래스를 로드 시키는 것 => Driver를 로드 시키는 것
            Class.forName("com.mysql.cj.jdbc.Driver");

            //url, user, pwd 필요 -> 왜? : 연결하려면 기본으로 필요한 정보 (내 정보)
            //if, orcle이면 오라클에 맞는 정보를 입력해야 한다.
            String url ="jdbc:mysql://localhost:3306/my_servlet_board";
            String user = "root";
            String pwd = "1234";
            conn = DriverManager.getConnection(url, user, pwd);
            //연결하기 위해서는 드라이버매니저에 정보 3개가 꼭 들어가야한다.
        }catch (Exception e){
            //연결 오류시 잡는 것
            e.printStackTrace();
        }
        return conn;
        //Connection 객체가 담긴 드라이버 매니저를 리턴
        // 예외가 없으면 리턴되어 디비와 연결됨
    }


    @Override
    public ArrayList<Board> getAllByBoardId(Long id) {
        //댓글을 저장하는 메소드

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<Board> comment = new ArrayList<>();

        try {
            connection = connectDB();
//            String sql = "INSERT INTO board (title, content, writer, member_id) VALUES (?,?,?, ?)";
//            ps = connection.prepareStatement(sql);
//            rs = ps.executeQuery();
//
//            connection = connectDB();
//            ps.setString(1, comment.getTitle());
//            ps.setString(2, board.getContent());
//            ps.setString(3, board.getWriter());
//            ps.setLong(4, board.getMember_id());
//            ps.executeUpdate();


        }catch (Exception e){

        } finally {
            try {
                ps.close();
                connection.close();

            }catch ( Exception e){
                e.printStackTrace();
            }
        }
        return comment;

    }
}

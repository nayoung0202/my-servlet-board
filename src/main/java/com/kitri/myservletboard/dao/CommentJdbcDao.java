package com.kitri.myservletboard.dao;

import com.kitri.myservletboard.data.Board;
import com.kitri.myservletboard.data.Comment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
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
    public ArrayList<Comment> getAllByBoardId(Long id) {
        //댓글 조회

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<Comment> comment = new ArrayList<>();


        try {
            connection = connectDB();

            String sql = "SELECT * FROM comment WHERE board_id = " + id;
            //SELECT * FROM comment where board_id = 145;

            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while(rs.next()){
                Long id1 = rs.getLong("id");
                Long member_id = rs.getLong("member_id");
                String content = rs.getString("content");
                LocalDateTime created_at = rs.getTimestamp("created_at").toLocalDateTime();

                comment.add(new Comment(id1, content, member_id, created_at));
            }


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

    @Override
    public void save(Long id , Long member_id, String content) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectDB();
            String insertsql = "INSERT INTO comment (board_id, member_id, content) VALUES (?,?,?)";
            //INSERT INTO comment (board_id, member_id, content) values ("143", "6", "초콜릿에 어떤 성분이..?");
            ps = connection.prepareStatement(insertsql);


            connection = connectDB();

            ps.setLong(1, id);
            ps.setLong(2, member_id);
            ps.setString(3, content);
            ps.executeUpdate();


        }catch (Exception e){

        } finally {
            try {
                ps.close();
                connection.close();

            }catch ( Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Long delete_id) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectDB();

            String query = "DELETE FROM comment WHERE id = ?";

            ps = connection.prepareStatement(query);

            ps.setLong(1, delete_id);
            ps.executeUpdate();

        }catch (Exception e){

        } finally {
            try {
                ps.close();
                connection.close();

            }catch ( Exception e){
                e.printStackTrace();
                //예외처리하기 위한 명령어
            }
        }
    }
}

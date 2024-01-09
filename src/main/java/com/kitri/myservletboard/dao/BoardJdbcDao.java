package com.kitri.myservletboard.dao;

import com.kitri.myservletboard.data.Board;
import com.kitri.myservletboard.service.BoardService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class BoardJdbcDao implements BoardDao{
    private static final BoardJdbcDao instance = new BoardJdbcDao();

    public static BoardJdbcDao getInstance(){
        return instance;
    }

    private BoardJdbcDao() {
    }

    public Connection connectDB() {
        Connection conn = null;

        try {
            //forName : 원하는 위치의 클래스를 로드 시키는 것
            Class.forName("com.mysql.cj.jdbc.Driver");

            //url, user, pwd 필요
            String url ="jdbc:mysql://localhost:3306/my_servlet_board";
            String user = "root";
            String pwd = "1234";
            conn = DriverManager.getConnection(url, user, pwd);
        }catch (Exception e){
            //연결 오류시 잡는 것
            e.printStackTrace();
        }
        return conn;
        //Connection 객체가 담긴 객체를 리턴..?
    }

    @Override
    public ArrayList<Board> getAll() {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;


        ArrayList<Board> boards = new ArrayList<>();

        //쿼리 날릴 때는 try catch 써야 됨
        try {
            connection = connectDB();
            String sql = "SELECT * FROM board";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery(sql);

            //컬럼 단위로 읽음
            while (rs.next()){
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String writer = rs.getString("writer");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                int viewCount = rs.getInt("view_count");
                int commentCount = rs.getInt("comment_count");

                boards.add(new Board(id, title, content, writer, createdAt, viewCount, commentCount));
            }

        }catch (Exception e){

        } finally {
            //무조건 실행
            try {
                rs.close();
                ps.close();
                connection.close();

            }catch ( Exception e){
                e.printStackTrace();
            }
        }

        return boards;
    }

    @Override
    public Board getById(Long id) {
        return null;
    }

    @Override
    public void save(Board board) {

    }

    @Override
    public void update(Board board) {

    }

    @Override
    public void delete(Board board) {

    }
}

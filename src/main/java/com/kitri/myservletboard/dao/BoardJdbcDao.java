package com.kitri.myservletboard.dao;

import com.kitri.myservletboard.data.Board;
import com.kitri.myservletboard.data.Pagination;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class BoardJdbcDao implements BoardDao {
    private static final BoardJdbcDao instance = new BoardJdbcDao();
    public static BoardJdbcDao getInstance() {
        return instance;
    }
    private BoardJdbcDao() {
    }
    // 싱글톤 구현 필수 코드

    public Connection connectDB(){
        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url ="jdbc:mysql://localhost:3306/my_servlet_board";
            String user = "root";
            String pwd = "1234";
            conn = DriverManager.getConnection(url, user, pwd);

        }catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }
    @Override
    public ArrayList<Board> getAll() {
        // 게시물 전체 조회
        Connection connection= null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<Board> boards = new ArrayList<>();

        try{
            connection = connectDB();
            String sql = "SELECT * FROM board";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery(sql);

            while(rs.next()){
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String writer = rs.getString("writer");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
            //Timestamp : int / String ???? =>
                int viewCount = rs.getInt("view_count");
                int commentCount = rs.getInt("comment_count");

                boards.add(new Board(id, title, content, writer, createdAt, viewCount, commentCount));
            }

        }catch (Exception e){

        }finally {
            try {
                rs.close();
                ps.close();
                connection.close();

            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return boards;
    }

    @Override
    public Board getById(Long id) {
        // 게시물 상세 조회
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        Board board = null;

        try{
            connection = connectDB();
            String sql = "SELECT * FROM board WHERE id = ?";
            ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();

            while (rs.next()){
                id = rs.getLong("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String writer = rs.getString("writer");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                int viewCount = rs.getInt("view_count");
                int commentCount = rs.getInt("comment_count");

                board = new Board(id, title, content, writer, createdAt,viewCount, commentCount);
            }

        }catch (Exception e){
        }finally {
            try{
                rs.close();
                ps.close();
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return board;
    }

    @Override
    public void save(Board board) {
        // 게시물 등록하기
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectDB();
            String insertsql = "INSERT INTO board (title, content, writer) values (?, ?, ?)";
            //INSERT INTO board (title, content, writer) value ('열공!', 'jdbc 연동 완료하기', '나용이용') ;
            ps = connection.prepareStatement(insertsql);

            //board의 title, content, writer의 값을 가져와야 한다.
            ps.setString(1, board.getTitle());
            ps.setString(2, board.getContent());
            ps.setString(3, board.getWriter());
            ps.executeUpdate();

        }catch (Exception e){

        }finally {
            try {
                ps.close();
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public void update(Board board) {
        // 게시물 수정하기
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = connectDB();
            String sql = "UPDATE board SET title = ?, content = ? where id = ? ";
            //update board set title = '대박', content = '너무 어려워요..', writer = '익명' where id = 3;
            ps = connectDB().prepareStatement(sql);

            ps.setString(1, board.getTitle());
            ps.setString(2, board.getContent());
            ps.setLong(3, board.getId());

            //while로 게시물의 정보를 다시 가져올 필요는 없다. DB가 알아서 값을 변경하기 때문에 컨트롤러에서 변경된 값의 브라우저를 보여준다.


        }catch (Exception e){

        }finally {
            try {
                rs.close();
                ps.close();
                connection.close();

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public void delete(Board board) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectDB();
            String sql = "DELETE from board where id = ?";
            ps = connection.prepareStatement(sql);

            ps.setLong(1, board.getId());
            ps.executeUpdate();


        }catch (Exception e){

        }finally{
            try{
                ps.close();
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<Board> getAll(Pagination pagination) {
        Connection connection= null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<Board> boards = new ArrayList<>();

        try{
            connection = connectDB();
            String sql = "SELECT count(*) FROM board";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery(sql);

            while(rs.next()){
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String writer = rs.getString("writer");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                //Timestamp : int / String ???? =>
                int viewCount = rs.getInt("view_count");
                int commentCount = rs.getInt("comment_count");

                boards.add(new Board(id, title, content, writer, createdAt, viewCount, commentCount));
            }

        }catch (Exception e){

        }finally {
            try {
                rs.close();
                ps.close();
                connection.close();

            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return boards;
    }
}

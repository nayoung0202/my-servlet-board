package com.kitri.myservletboard.dao;

import com.kitri.myservletboard.data.Board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.SignStyle;
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
        // 상세 조회
        // connection
        // ps -> executeQuery();
        // rs

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;


        Board Board = new Board();
        try {
            connection = connectDB();
            //상세 페이지를 구현하기 위해서는 id가 필요하기 때문에 id =? 로 id가져오기
            // ? 나중에 값을 대입하겠다는 의미 => id가 계속 달라지기 때문에 (동적)
            // 물음표 갯수대로 대체하는 것
            String sql = "SELECT * FROM board WHERE id = ?";
            ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
//                id = rs.getLong("id");
                String title = rs.getString("title");
                String writer = rs.getString("writer");
                String content = rs.getString("content");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                int viewCount = rs.getInt("view_count");
                int commentCount = rs.getInt("comment_count");

                Board board_ = new Board(id, title, content, writer, createdAt, viewCount, commentCount);
                return  board_;
            }


        } catch (Exception e) {
        }
        finally {
            //무조건 실행
            try {
                rs.close();
                ps.close();
                connection.close();

            }catch ( Exception e){
                e.printStackTrace();
            }
        }
        return Board;
    }

    @Override
    public void save(Board board) {
        //게시물 등록하기 (글쓰기)
        Connection connection = null;
        PreparedStatement ps = null;


        //폼에서 받은 제목, 작성자, 내용만 저장하고 다른 조회수나 댓글 수는 디비가 알아서 가져온다.

        try {
            connection = connectDB();
            String insertsql = "INSERT INTO board (title, content, writer) VALUES (?,?,?)";
//            rs = ps.executeQuery(insertsql);
            ps = connection.prepareStatement(insertsql);

                connection = connectDB();

                ps.setString(1, board.getTitle());
                ps.setString(2, board.getContent());
                ps.setString(3, board.getWriter());
                ps.executeUpdate();
                // Update()문을 사용하면 입력받은 데이터를 갱신할 수 있다.


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
    public void update(Board board) {
        // 수정하기
        Connection connection = null;
        PreparedStatement ps = null;


        //폼에서 받은 제목, 작성자, 내용만 저장하고 다른 조회수나 댓글 수는 디비가 알아서 가져온다.

        try {
            connection = connectDB();
            String insertsql = "UPDATE board SET title = ?, content = ? WHERE id = ?";
//            rs = ps.executeQuery(insertsql);
            ps = connection.prepareStatement(insertsql);

            connection = connectDB();

            ps.setString(1, board.getTitle());
            ps.setString(2, board.getContent());
            ps.setLong(3, board.getId());
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
    public void delete(Board board) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectDB();

            String query = "DELETE FROM board WHERE id = ?";

            ps = connection.prepareStatement(query);

            ps.setLong(1, board.getId());
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
}

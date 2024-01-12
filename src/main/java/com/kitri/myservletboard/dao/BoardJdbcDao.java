package com.kitri.myservletboard.dao;

import com.kitri.myservletboard.data.Board;
import com.kitri.myservletboard.data.Pagination;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.SignStyle;
import java.util.ArrayList;

public class BoardJdbcDao implements BoardDao{
    private static final BoardJdbcDao instance = new BoardJdbcDao();


    public static BoardJdbcDao getInstance(){
        //boardService에서 생성된 BoardjdbcDao의 싱글톤을 가져옴
        return instance;
    }

    private BoardJdbcDao() {
    }

    public Connection connectDB() {
        //DB와 연결
        Connection conn = null;

        //쿼리를 날리기 위해서는 try-catch를 사용 -> 쿼리는
        try {
            //forName : 원하는 위치의 클래스를 로드 시키는 것
            Class.forName("com.mysql.cj.jdbc.Driver");

            //url, user, pwd 필요 -> 왜? :
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
    public ArrayList<Board> getAll(Pagination pagination) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<Board> boards = new ArrayList<>();

        //쿼리 날릴 때는 try catch 써야 됨
        try {
            connection = connectDB();
            //가고자 하는 페이지의 값을 알기 위해선 pagination에 있는 page에서 값을 가져온다.
            //sql에서 2페이지로 가려면 10~20 페이지이기 때문에 10,10으로 쓰면 된다. -> 10번부터 10개의 정보를 불러오는 것 : 앞에는 동적인 데이터 : "?"로 나타낸다.
            String sql = "SELECT * FROM board LIMIT ?,?";
            // 한 페이지에 나올 게시물 갯수를 정적에서 동적으로 변경

            ps = connection.prepareStatement(sql);
            //? 치환하기
            // 첫 번째 물음표를 대체하는 것 : startIndex

            // 페이지 번호를 받으면 쿼리를 0,10 / 10,10 / 20,10 ... 의 식으로 만들어야 하는데 page가 1부터 시작해서 2페이지를 가져오기 때문에 -1를 해야 원하는 1페이지의 데이터를 가져오게 된다.
            ps.setInt(1,(pagination.getPage() -1) * pagination.getMaxRecordsPerPage());
            // 10으로 고정했던 값을 MaxRecordsPerPage 변수로 변경
            //rs에 sql을 빼는 이유 ? : ps에 값이 담겨져 있기 때문에 sql을 넣으면 중복되어 안된다.

            ps.setInt(2, pagination.getMaxRecordsPerPage());
            rs = ps.executeQuery();

            //컬럼 단위로 읽음
            //wile문을 10번 반복후 가져오게 되는것
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
    public ArrayList<Board> getAll(String search, String keyword, Pagination pagination, String dateTime) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<Board> boards = new ArrayList<>();

        if (search == null){
            search = "title";
        }
        if (keyword == null){
            keyword = "";
        }
        if (dateTime == null){
//            return getAll(search, keyword, pagination);
            dateTime = "100 YEAR";
        }


        try {
            connection = connectDB();

            //2023-10-10, between and
            //LocalDate.now()가 현재 날짜를 나타냄
//            if (dateTime.equals("1")) {
////                LocalDate.now().minusDays("")
//            }

            String sql = "SELECT * FROM board WHERE "+ search + " LIKE " + "'%" + keyword + "%' AND created_at " + "BETWEEN DATE_ADD(NOW(), INTERVAL -" + dateTime + ") AND NOW() LIMIT ?, ?";
            //select * from board where created_at between date_add(now(), interval -1 day) and now();

            ps = connection.prepareStatement(sql);


            ps.setInt(1,(pagination.getPage() -1) * pagination.getMaxRecordsPerPage());
            ps.setInt(2, pagination.getMaxRecordsPerPage());



            rs = ps.executeQuery();
            System.out.println(rs);

            while (rs.next()) {
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String writer = rs.getString("writer");
                String content = rs.getString("content");
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
        // excutequery : 수행결과로 ResultSet 객체의 값을 반환
        // rs : ResultSet -> PreparedStatement를 통해 받아온 값을 저장 (DB 쿼리 결과값)

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;


        Board Board = new Board();
        try {
            connection = connectDB();
            //상세 페이지를 구현하기 위해서는 id가 필요하기 때문에 id = ? 로 id가져오기
            // ? 나중에 값을 대입하겠다는 의미 => id가 계속 달라지기 때문에 (동적)
            // 물음표 갯수대로 대체하는 것
            String sql = "SELECT * FROM board WHERE id = ?";
            ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();

            // ResultSet은 next()메서드를 제공한다. -> next()를 통해 브라우저에서 입력받은 데이터 값을 가져온다.
            while (rs.next()) {
                //쿼리 결과값 만큼 while문을 돌게된다.
//                id = rs.getLong("id"); -> 위에 ps.setLong(1, id); 을 통해 id를 선언했기 때문에 없어도 된다.
                String title = rs.getString("title");
                String writer = rs.getString("writer");
                String content = rs.getString("content");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                int viewCount = rs.getInt("view_count");
                int commentCount = rs.getInt("comment_count");

                // 브라우저에서 입력받은 데이터를 new Board로 생성자를 생성하여 저장한다.
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

    //count 메소드 만들기
    // SELECT count(*) FROM board;
    //실제 내보내는 쿼리에 해당하는
    public int count(){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;


        int count = 0;

        //쿼리 날릴 때는 try catch 써야 됨
        try {
            connection = connectDB();
            String sql = "SELECT count(*) FROM board";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            rs.next(); //DB에서 쿼리 실행하면 나오는 결과값 (총 게시글 갯수)

            count = rs.getInt("count(*)");

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
        return count;
    }

    @Override
    public ArrayList<Board> getAll(String search, String keyword, Pagination pagination) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<Board> boards = new ArrayList<>();


        if (search == null){
            search = "title";
        }
        if (keyword == null){
            keyword = "";
        }



        //쿼리 날릴 때는 try catch 써야 됨
        try {
            connection = connectDB();

            String sql = "SELECT * FROM board WHERE "+ search + " LIKE " + "'%" + keyword + "%'" + " LIMIT ?,?";
            //            SELECT  * from board where title like "%파리%";
            // title 또는 writer 의 정보를 search에서 받고 sql에 쿼리에 넣기 +로 작성한다.
            //띄어쓰기 주의!! -> LIKE 와 같은 쿼리를 쌍따음표 사이에 공백을 넣어줘야 한다. 원래 sql 명령문에도 공백이 있기 때문이다.
            // 한 문장으로 작성할 경우에는 작은 따음표가 붙어져서 나오기 때문에 쌍따음표로 넣어줘야한다.


            ps = connection.prepareStatement(sql);


            ps.setInt(1,(pagination.getPage() -1) * pagination.getMaxRecordsPerPage());
            ps.setInt(2, pagination.getMaxRecordsPerPage());



            rs = ps.executeQuery();
            System.out.println(rs);

            while (rs.next()) {
                //쿼리 결과값 만큼 while문을 돌게된다.
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String writer = rs.getString("writer");
                String content = rs.getString("content");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                int viewCount = rs.getInt("view_count");
                int commentCount = rs.getInt("comment_count");

                // 브라우저에서 입력받은 데이터를 new Board로 생성자를 생성하여 저장한다.
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

    public int count(String search, String keyword) {
        if (keyword == null) {
            // 전체조회
            return count();
        } else {
            Connection connection = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            int count = 0;

            try {
                connection = connectDB();
//                String sql;
//                if(search.equals("title")){
//                    sql = "SELECT count(*) FROM board WHERE title LIKE ?";
//                } else {
//                    sql = "SELECT count(*) FROM board WHERE writer LIKE ?";
//                }
//                String sql = "SELECT count(*) FROM board WHERE " + search + "%" + "" LIKE "+'%';
                String sql = "SELECT count(*) FROM board WHERE "+ search + " LIKE " + "'%" + keyword + "%'";
                ps = connection.prepareStatement(sql);
//                ps.setString(1,keyword);
                rs = ps.executeQuery();

                rs.next(); //DB에서 쿼리 실행하면 나오는 결과값 (총 게시글 갯수)

                count = rs.getInt("count(*)");

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
            return count;
        }
    };

    public int count(String search, String keyword, String dateTime) {
        if (dateTime == null || keyword == null) {
            // 전체조회
            return count();
        } else {
            Connection connection = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            int count = 0;

            try {
                connection = connectDB();


                String sql = "SELECT count(*) FROM board WHERE "+ search + " LIKE " + "'%" + keyword + "%' AND created_at " + "BETWEEN DATE_ADD(NOW(), INTERVAL -" + dateTime + ") AND NOW()";
                ps = connection.prepareStatement(sql);

                rs = ps.executeQuery();

                rs.next(); //DB에서 쿼리 실행하면 나오는 결과값 (총 게시글 갯수)

                count = rs.getInt("count(*)");

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
            return count;
        }
    };

    }


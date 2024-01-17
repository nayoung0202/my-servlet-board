package com.kitri.myservletboard.dao;


import com.kitri.myservletboard.data.Member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemberJdbcDao implements MemberDao {
    private static final MemberJdbcDao instance = new MemberJdbcDao();

    public static MemberJdbcDao getInstance() {
        return instance;
    }
    public MemberJdbcDao() {
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
    public Member getById(String userId) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        Member member = new Member();
        try {
            connection = connectDB();

            String sql = "SELECT * FROM member WHERE userId =" + "'" + userId + "'" ;
            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("id");
                String userId1 = rs.getString("userId");
                String userPw = rs.getString("userPw");
                String name = rs.getString("name");
                String email = rs.getString("email");

                Member member1 = new Member(id, userId1, userPw, name, email);
                return member1;

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
        return  member;
    }

    @Override
    public Member getMember(String userId, String userPw, String name, String email) {
        return null;
    }

    @Override
    public void addMemeber(Member member) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = connectDB();

            String sql = "INSERT INTO member (userId, userPw, name, email) VALUES (?,?,?,?)";
            ps = connection.prepareStatement(sql);

            ps.setString(1, member.getUserId());
            ps.setString(2, member.getUserPw());
            ps.setString(3, member.getName());
            ps.setString(4, member.getEmail());
            ps.executeUpdate();


        } catch (Exception e) {
        } finally {
            //무조건 실행
            try {
                rs.close();
                ps.close();
                connection.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public Member getById(String userId, String userPw, String name, String email) {
        return null;
    }



    @Override
    public void updateMember(String userId, String userPw, String userPwchek, String name, String email) {
        Connection connection = null;
        PreparedStatement ps = null;


        //폼에서 받은 제목, 작성자, 내용만 저장하고 다른 조회수나 댓글 수는 디비가 알아서 가져온다.

        try {
            connection = connectDB();
            String insertsql = "UPDATE member SET userPw = ?, name = ?, email = ? WHERE userId = ?";
            ps = connection.prepareStatement(insertsql);

            connection = connectDB();


            ps.setString(1, userPw);
            ps.setString(2, name);
            ps.setString(3, email);
            ps.setString(4, userId);
            ps.executeUpdate();

        }catch (Exception e){

        } finally {
            try {
                ps.close();
                connection.close();
                //연결하고 쿼리문 날렸으므로 쿼리문 먼저 닫고 연결 닫음

            }catch ( Exception e){
                e.printStackTrace();
            }
        }
    }
}

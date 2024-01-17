package com.kitri.myservletboard.service;


import com.kitri.myservletboard.dao.MemberDao;
import com.kitri.myservletboard.dao.MemberJdbcDao;
import com.kitri.myservletboard.data.Member;

public class MemberService {
    //싱글톤 생성
    MemberDao memberDao = MemberJdbcDao.getInstance();
    private MemberService() {};
    private static final MemberService instance = new MemberService();

    public static MemberService getInstance() { return instance;
    }

    public void updateMember(String userId, String userPw, String userPwcheck, String name, String email) {
        memberDao.updateMember(userId, userPw, userPwcheck, name, email);
    }

    public Member getMember(String userId){
        return memberDao.getById(userId);
    }
    public Member getMember(String userId, String userPw, String name, String email){
        return memberDao.getById(userId, userPw, name, email);
    }

    //아이디 중복 메소드 만듦 boolean으로 중복되면 false로 변경

    public void addMemeber(Member member){
        memberDao.addMemeber(member);
    }

}

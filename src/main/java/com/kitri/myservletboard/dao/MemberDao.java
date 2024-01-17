package com.kitri.myservletboard.dao;

import com.kitri.myservletboard.data.Member;

public interface MemberDao {
    //DB랑 통신하여 현재 로그인중인 회원의 정보 하나만 가져오기 때문에 Arraylist가 필요 없다.
    public Member getById(String userId);
    
    public Member getMember(String userId, String userPw, String name, String email);


    Member getById(String userId, String userPw, String name, String email);

    void addMemeber(Member member);


    void updateMember(String userId, String userPw, String userPwchek, String name, String email);
}

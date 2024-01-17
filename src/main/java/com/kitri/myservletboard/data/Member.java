package com.kitri.myservletboard.data;

import com.kitri.myservletboard.data.Board;

public class Member {
    private Long id;
    private String userPw;

    private String userId;
    private String name;
    private String email;
    private String userPwcheck;

    public Member(Long id,  String userId, String userPw, String name, String email) {
        this.id = id;
        this.userId = userId;
        this.userPw = userPw;
        this.name = name;
        this.email = email;
    }


    public Member(String userPw, String userId, String name, String email, String userPwcheck) {
        this.userPw = userPw;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.userPwcheck = userPwcheck;
    }

    public Member() {
    }

    public Member(String userId, String userPw) {
        this.userId = userId;
        this.userPw = userPw;
    }





    public String getUserPwcheck() {
        return userPwcheck;
    }

    public void setUserPwcheck(String userPwcheck) {
        this.userPwcheck = userPwcheck;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


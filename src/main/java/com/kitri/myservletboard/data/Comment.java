package com.kitri.myservletboard.data;

public class Comment {
    private String content;
    private String created_at;
    private String member_id;
    private String board_id;

    public Comment(String content, String created_at, String member_id, String board_id) {
        this.content = content;
        this.created_at = created_at;
        this.member_id = member_id;
        this.board_id = board_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getBoard_id() {
        return board_id;
    }

    public void setBoard_id(String board_id) {
        this.board_id = board_id;
    }
}

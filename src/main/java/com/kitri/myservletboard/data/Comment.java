package com.kitri.myservletboard.data;


import java.time.LocalDateTime;

public class Comment {
    private Long id;
    private String content;
    private LocalDateTime created_at;
    private Long member_id;
    private Long board_id;

    public Comment(String content, LocalDateTime created_at, Long member_id, Long board_id) {
        this.content = content;
        this.created_at = created_at;
        this.member_id = member_id;
        this.board_id = board_id;
    }

    public Comment(String content, Long member_id, Long  board_id) {
        this.content = content;
        this.member_id = member_id;
        this.board_id = board_id;
    }

    public Comment(Long id, String content, Long member_id, LocalDateTime created_at) {
        this.id = id;
        this.content = content;
        this.member_id = member_id;
        this.created_at = created_at;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public Long getMember_id() {
        return member_id;
    }

    public void setMember_id(Long member_id) {
        this.member_id = member_id;
    }

    public Long getBoard_id() {
        return board_id;
    }

    public void setBoard_id(Long board_id) {
        this.board_id = board_id;
    }
}

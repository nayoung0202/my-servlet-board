package com.kitri.myservletboard.service;

import com.kitri.myservletboard.dao.CommentDao;
import com.kitri.myservletboard.dao.CommentJdbcDao;
import com.kitri.myservletboard.data.Board;
import com.kitri.myservletboard.data.Comment;

import java.util.ArrayList;

public class CommentService implements CommentDao {

    CommentDao commentDao = CommentJdbcDao.getInstance();

    private CommentService() {};
    private static final CommentService instance = new CommentService();
    public static CommentService getInstance() {
        return instance;
    }

    public ArrayList<Comment> getAllByBoardId(Long id) {
        return commentDao.getAllByBoardId(id);
    }

    public void save(Long id, Long member_id, String comment) {
        commentDao.save(id, member_id, comment);
    }

    public void delete(Long delete_id) {
        commentDao.delete(delete_id);
    }



}

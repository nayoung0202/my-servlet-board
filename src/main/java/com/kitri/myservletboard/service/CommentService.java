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

    public static void saveBoard(Comment id) {
        //CommentDao.getAllByBoardId(id);
    }

    public ArrayList<Board> getAllByBoardId(Long id) {
        return commentDao.getAllByBoardId(id);
    }

}

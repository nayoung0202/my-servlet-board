package com.kitri.myservletboard.service;

import com.kitri.myservletboard.dao.BoardDao;
import com.kitri.myservletboard.dao.MemberDao;
import com.kitri.myservletboard.dao.MemberJdbcDao;
import com.kitri.myservletboard.data.Board;
import com.kitri.myservletboard.data.Comment;

public class CommentService {
    MemberDao CommentDao = MemberJdbcDao.getInstance();
    private CommentService() {};
    private static final CommentService instance = new CommentService();

    public static CommentService getInstance(){return instance;}


}

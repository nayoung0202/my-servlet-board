package com.kitri.myservletboard.dao;

import com.kitri.myservletboard.data.Board;
import com.kitri.myservletboard.data.Comment;

import java.util.ArrayList;

public interface CommentDao{


    public static Comment save(String content) {
        return null;
    }


    public ArrayList<Board> getAllByBoardId(Long id);
}

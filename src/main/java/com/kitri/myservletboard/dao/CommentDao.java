package com.kitri.myservletboard.dao;

import com.kitri.myservletboard.data.Board;
import com.kitri.myservletboard.data.Comment;

import java.util.ArrayList;

public interface CommentDao{

    //Board의 id와 Comment의 content, member_id 필요)

    


    public ArrayList<Comment> getAllByBoardId(Long id);


    public void save(Long id, Long member_id, String content);

    public void delete(Long delete_id);
    public void commentdelete(Long id);
}

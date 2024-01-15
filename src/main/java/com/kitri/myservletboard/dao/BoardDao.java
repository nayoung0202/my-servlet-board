package com.kitri.myservletboard.dao;

import com.kitri.myservletboard.data.Board;
import com.kitri.myservletboard.data.Pagination;
import com.kitri.myservletboard.service.BoardService;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface BoardDao {
    public ArrayList<Board> getAll();
    public Board getById(Long id);
    public void save (Board board);
    public void update (Board board);
    public void delete (Board board);

    //pagination 의 정보를 가져오기 위해 BoardDao에도 선언해준다.
    public ArrayList<Board> getAll(Pagination pagination);

    public ArrayList<Board> getAll(String search, String keyword, Pagination pagination, String dateTime);

    public ArrayList<Board> getAll(String search, String keyword, Pagination pagination);
}

package com.kitri.myservletboard.dao;

import com.kitri.myservletboard.data.Board;
import com.kitri.myservletboard.data.Pagination;
import com.kitri.myservletboard.service.BoardService;

import java.time.LocalDateTime;
import java.util.ArrayList;
//쿼리 관련은 Dao, 컨트롤러는 정보를 받아서 view로 안내해주는 역할,

public interface BoardDao {
    public ArrayList<Board> getAll();
    public Board getById(Long id);
    public void save (Board board);
    public void update (Board board);
    public void delete (Board board);

    //pagination 의 정보를 가져오기 위해 BoardDao에도 선언해준다.
    public ArrayList<Board> getAll(Pagination pagination);

    public ArrayList<Board> getAll(String search, String keyword, Pagination pagination, String dateTime);

    public ArrayList<Board> getAll(String search, String keyword, Pagination pagination, String dateTime, String firstdata);

    public ArrayList<Board> getAll(String search, String keyword, Pagination pagination);
}

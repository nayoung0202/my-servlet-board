package com.kitri.myservletboard.service;

import com.kitri.myservletboard.dao.BoardDao;
import com.kitri.myservletboard.dao.BoardJdbcDao;
import com.kitri.myservletboard.dao.BoardMemoryDao;
import com.kitri.myservletboard.data.Board;
import com.kitri.myservletboard.data.Pagination;

import javax.swing.plaf.IconUIResource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BoardService {

//    BoardDao boardDao = BoardMemoryDao.getInstance();
    //BoardJdbcDao의 싱글톤 생성 -> BoardJdbcDao에서 사용할 수 있는 싱글톤
    BoardDao boardDao = BoardJdbcDao.getInstance();
    private BoardService() {};
    private static final BoardService instance = new BoardService();


    //Pagination의
    public ArrayList<Board> getBoards(Pagination pagination) {
        //전체 레코드 수를 찾기 위해 카운트를 가져옴
        pagination.setTotalRecords(((BoardJdbcDao)boardDao).count()); //토탈 레코드의 값을 계산
        pagination.calcPagination(); //계산하는 메소드

        return boardDao.getAll(pagination);
    }


    public ArrayList<Board> getBoards(String search, String keyword, Pagination pagination) {

        pagination.setTotalRecords(((BoardJdbcDao)boardDao).count(search, keyword)); //토탈 레코드의 값을 계산
        pagination.calcPagination(); //계산하는 메소드

        return boardDao.getAll(search, keyword, pagination);

    }
    public ArrayList<Board> getBoards(String search, String keyword, Pagination pagination, String dateTime) {

        pagination.setTotalRecords(((BoardJdbcDao)boardDao).count(search, keyword, dateTime)); //토탈 레코드의 값을 계산
        pagination.calcPagination(); //계산하는 메소드

        return boardDao.getAll(search, keyword, pagination, dateTime);

    }
    public ArrayList<Board> getBoards(String search, String keyword, Pagination pagination, String dateTime, String firstdata) {

        pagination.setTotalRecords(((BoardJdbcDao)boardDao).count(search, keyword, dateTime)); //토탈 레코드의 값을 계산
        pagination.calcPagination(); //계산하는 메소드

        return boardDao.getAll(search, keyword, pagination, dateTime, firstdata);

    }


    public static BoardService getInstance(){return instance;}
    //게시판 리스트 가져오는 로직


    public Board getBoard(Long id){
        return boardDao.getById(id);
    }

    public ArrayList<Board> getBoards() {
        return boardDao.getAll();
    }
    public void addBoard(Board board) {
        boardDao.save(board);
    }
    public void updateBoard(Board board) {
        boardDao.update(board);
    }
    public void deleteBoard(Board board) {
        boardDao.delete(board);
    }


}

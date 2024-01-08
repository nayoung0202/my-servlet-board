package com.kitri.myservletboard.dao;

import com.kitri.myservletboard.data.Board;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class BoardMemoryDao implements BoardDao {
    private static final BoardMemoryDao instance = new BoardMemoryDao();

    public static BoardMemoryDao getInstance(){
        return instance;
    }
    ArrayList<Board> BoardMemoryDB = new ArrayList<>();

    private BoardMemoryDao() {
        BoardMemoryDB.add(new Board(1L, "첫 번째 글!!", "반갑습니다", "손흥민", LocalDateTime.now(), 10, 2));
        BoardMemoryDB.add(new Board(2L, "두 번째 글!!", "반갑습니다", "손흥민", LocalDateTime.now(), 10, 2));
        BoardMemoryDB.add(new Board(3L, "세 번째 글!!", "반갑습니다", "손흥민", LocalDateTime.now(), 10, 2));
        BoardMemoryDB.add(new Board(4L, "네 번째 글!!", "반갑습니다", "손흥민", LocalDateTime.now(), 10, 2));
        BoardMemoryDB.add(new Board(5L, "다섯 번째 글!!", "반갑습니다", "손흥민", LocalDateTime.now(), 10, 2));
        BoardMemoryDB.add(new Board(6L, "여섯 번째 글!!", "반갑습니다", "손흥민", LocalDateTime.now(), 10, 2));
        BoardMemoryDB.add(new Board(7L, "일곱 번째 글!!", "반갑습니다", "손흥민", LocalDateTime.now(), 10, 2));
        BoardMemoryDB.add(new Board(8L, "여덟 번째 글!!", "반갑습니다", "손흥민", LocalDateTime.now(), 10, 2));
        BoardMemoryDB.add(new Board(9L, "아홉 번째 글!!", "반갑습니다", "손흥민", LocalDateTime.now(), 10, 2));
        BoardMemoryDB.add(new Board(10L, "열 번째 글!!", "반갑습니다", "손흥민", LocalDateTime.now(), 10, 2));
    }

    @Override
    public ArrayList<Board> getAll() {
        return BoardMemoryDB;
    }

    @Override
    public Board getById(Long id) {
        return BoardMemoryDB.stream().filter(board -> {
            return board.getId() == id;
        }).findFirst().get();
    }

    @Override
    public void save(Board board) {
        BoardMemoryDB.add(board);
    }

    @Override
    public void update(Board board) {
        Board board_ = getById(board.getId());
        BoardMemoryDB.remove(board_);
        BoardMemoryDB.add(board);
    }

    @Override
    public void delete(Board board) {
        BoardMemoryDB.remove(board);
    }
}

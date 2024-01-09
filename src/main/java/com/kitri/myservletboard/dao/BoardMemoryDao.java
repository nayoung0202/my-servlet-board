package com.kitri.myservletboard.dao;

import com.kitri.myservletboard.data.Board;

import java.time.LocalDateTime;
import java.util.ArrayList;

//저장소를 관리하는 객체(클래스)


public class BoardMemoryDao implements BoardDao {
    private static final BoardMemoryDao instance = new BoardMemoryDao();

    public static BoardMemoryDao getInstance(){
        return instance;
    }
    ArrayList<Board> BoardMemoryDB = new ArrayList<>();
    //자바에서 사용하는 자료구조

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
        // id 자동 생성 로직 (단, id가 기존의 id와 중복되지 않게)


        Long id = 0L;
        boolean flag = false;
        while (!flag) {
            flag = true;
            id ++;
            for (Board board1 : BoardMemoryDB) {
                if (board1.getId() == id) {
                    // id 중복
                    flag = false;
                    break;
                };
            }// 1~10까지 break 되지 않고 계속 while문을 돌다가 10이 지나면 flag가 true가 되고 while문을 나오게 된다.
            // 중복이 없을 때
        }
        board.setId(id);
        BoardMemoryDB.add(board);
    }

    @Override
    public void update(Board board) {
        Board board_ = getById(board.getId());
        board_.setTitle(board.getTitle());
        board_.setContent(board.getContent());
    }

    @Override
    public void delete(Board board) {
        Board board_ = getById(board.getId());
        BoardMemoryDB.remove(board_);
    }
}

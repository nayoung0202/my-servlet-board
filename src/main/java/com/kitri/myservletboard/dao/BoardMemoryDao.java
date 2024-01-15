package com.kitri.myservletboard.dao;

import com.kitri.myservletboard.data.Board;
import com.kitri.myservletboard.data.Pagination;

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

        //id는 0부터 시작하고 flag는 false로 시작 -> flag가 false이므로 while문을 들어간다.
        // while문에 들어간 falg는 true가 되고 id가 중복되면 while문을 계속 돌고 중복되지 않으면 flag가 false가 되지않고 true인 채로 break 되어 빠져나온다.
        // while문을 빠져나온 id는 board에 저장되고 BoardMemoryDB에 추가된다.

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

    //interface에 Pagination을 추가해서 오류나지 않게 넣어만 준다.
    @Override
    public ArrayList<Board> getAll(Pagination pagination) {
        return null;
    }

    @Override
    public ArrayList<Board> getAll(String search, String keyword, Pagination pagination, String dateTime) {
        return null;
    }

    @Override
    public ArrayList<Board> getAll(String search, String keyword, Pagination pagination) {
        return null;
    }

}


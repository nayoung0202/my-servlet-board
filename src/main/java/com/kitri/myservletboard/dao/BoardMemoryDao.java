package com.kitri.myservletboard.dao;

import com.kitri.myservletboard.data.Board;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class BoardMemoryDao implements BoardDao {
    // 싱글톤 : 메모리 절약을 위해 인스턴스가 필요할 때 똑같은 인스턴스를 새로 만들지 않고 기존의 인스턴스르 가져와 활용하는 기법
    // 생성자 메서드에 privat 키워드를 붙여서 클래스를 외부에서 마구잡이로 new 생성자를 통해 인스턴스화 하는 것을 제한한다.

    private static final BoardMemoryDao instace = new BoardMemoryDao();


    public static BoardMemoryDao getInstance() {
        return instace;
    }

    ArrayList<Board> memoryBoardDB = new ArrayList<>();

    private BoardMemoryDao() {
        memoryBoardDB.add(new Board(1L, "첫 번째 글", "안녕하세요", "홍길동1", LocalDateTime.now(), 10, 1));
        memoryBoardDB.add(new Board(2L, "두 번째 글", "안녕하세요", "홍길동2", LocalDateTime.now(), 10, 1));
        memoryBoardDB.add(new Board(3L, "세 번째 글", "안녕하세요", "홍길동3", LocalDateTime.now(), 10, 1));
        memoryBoardDB.add(new Board(4L, "네 번째 글", "안녕하세요", "홍길동4", LocalDateTime.now(), 10, 1));
        memoryBoardDB.add(new Board(5L, "다섯 번째 글", "안녕하세요", "홍길동5", LocalDateTime.now(), 10, 1));
        memoryBoardDB.add(new Board(6L, "여섯 번째 글", "안녕하세요", "홍길동6", LocalDateTime.now(), 10, 1));
        memoryBoardDB.add(new Board(7L, "일곱 번째 글", "안녕하세요", "홍길동7", LocalDateTime.now(), 10, 1));
        memoryBoardDB.add(new Board(8L, "여덟 번째 글", "안녕하세요", "홍길동8", LocalDateTime.now(), 10, 1));
        memoryBoardDB.add(new Board(9L, "아홉 번째 글", "안녕하세요", "홍길동9", LocalDateTime.now(), 10, 1));
        memoryBoardDB.add(new Board(10L, "열 번째 글", "안녕하세요", "홍길동10", LocalDateTime.now(), 10, 1));
    }

    @Override
    public ArrayList<Board> getAll() {
        return memoryBoardDB;
    }

    // memoryBoardDB에 있는 id가 입력받은 id와 같은지 비교
    // findFirst().get() => 일치하는 요소들 중에 맨 처음 값을 리턴
    @Override
    public Board getById(Long id) {
        return memoryBoardDB.stream().filter(board -> {
            return board.getId() == id;
        }).findFirst().get();
    }

    @Override
    public void save(Board board) {
        // 자동 id 생성 로직 (중복  X)

        Long id = 0L;
        // id 선언

        boolean flag = false;
        // flag는 false로 시작해서 flag가 true가 되면 while문에 들어간다.
        while (!flag){
            flag = true;
            id++;
            //id 의 번호를 증가 시킴
            for (Board board1 : memoryBoardDB){
                if (board1.getId() == id){
                    flag = false;
                    break;
                };
            }

        }
        board.setId(id);
        memoryBoardDB.add(board);
    }

    @Override
    public void update(Board board) {
        Board board1 = getById(board.getId());
        board1.setTitle(board.getTitle());
        board1.setContent(board.getContent());
    }

    @Override
    public void delete(Board board) {
        Board board1 = getById(board.getId());
        memoryBoardDB.remove(board1);
    }
}

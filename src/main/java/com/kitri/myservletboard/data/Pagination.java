package com.kitri.myservletboard.data;

public class Pagination {
    // 요청하는 페이지
    private int Page;
    // 최대 몇 개까지 레코드를 보여주는지 -> 고정값
    private int maxRecordsPerPage = 10;
    // 최대 몇 개의 페이지 번호를 보여준다. -> 고정값
    private  int maxpagesOnScreen = 5;
    //조회할 게스트 레코드가 시작하는 DB의 시작점
    private int startIndex = 0;
    //DB에 저장된 모든 레코드의 갯수
    private  int totalRecords = 0;


    // Next와 Previous 의 활성화 -> false면 비활성화, true면 활성화
    private boolean hasNext = false;
    private boolean hasPrev = false;

    // 페이지바에서 시작하는 페이지
    private int startPageOnScreen = 1;
    //페이지바에서 끝인 페이지
    // 5개씩 페이지바를 구현하려고 한다면,,
    private int endPageOnScreen = this.maxpagesOnScreen;


    public void calcPagination(){
        //페이지네이션 정보를 계산하는 메소드
        // 전체 레코드 갯수를 알아야 다음 페이지가 있는 지 없는 지 알 수 있고 현재 페이지가 어디에 있는 지 알 수 있다.

        //startPage 찾는 방법!
        //알고 있는 정보 : 현재 페이지 정보 + 한 페이지에서 보여주는 게시물 = 10, 페이지바의 최대 갯수 = 5 + 총 레코드 수 = 12

        // 게시물 갯수가 101 -> 11개 , 100 -> 10개
        //**전체 페이지 수 구하기
        //자동 형변환 되므로 올림을 하려면 double로 바꿔줘야 한다.
        int totalPages = ((int)Math.ceil((double) this.totalRecords / this.maxRecordsPerPage));

        // **첫 번째 페이지 구하기
        // 자동형변환으로 double로 변경
        this.startPageOnScreen =
                ((int)Math.ceil((double) this.Page / this.maxpagesOnScreen) - 1) * this.maxpagesOnScreen + 1;

        // 마지막 페이지 구하기
        //첫 페이지 =1 , 마지막 페이지 = 5,
        // if, (1,5) 이면 1+5 -1 = 5 이고 (6,10) 이면 6+5-1 = 15 이고 (11, 20) 이면 11+5-1 = 15 이 나온다.
        //maxpageOnScreen = 5 -> 고정값
        this.endPageOnScreen = this.startPageOnScreen + this.maxpagesOnScreen -1;

        // if, 마지막 페이지가 전체 페이지보다 클 경우,
        if (this.endPageOnScreen > totalPages){
            this.endPageOnScreen = totalPages;
        }

//        계산 확인하기
//        System.out.println(this.startPageOnScreen);
//        System.out.println(this.endPageOnScreen);


        // default = false 이므로 따로 적지 않아도 된다.
        if (this.endPageOnScreen < totalPages){
            this.hasNext = true;
        }

        if (this.startPageOnScreen > this.maxpagesOnScreen){
            this.hasPrev = true;
        }

    }

    public  Pagination(){

    }

    public Pagination(int Page) {
        this.Page = Page;
    }

    public int getPage() {
        return Page;
    }

    public void setPage(int page) {
        Page = page;
    }

    public int getMaxRecordsPerPage() {
        return maxRecordsPerPage;
    }

    public void setMaxRecordsPerPage(int maxRecordsPerPage) {
        this.maxRecordsPerPage = maxRecordsPerPage;
    }

    public int getMaxpagesOnScreen() {
        return maxpagesOnScreen;
    }

    public void setMaxpagesOnScreen(int maxpagesOnScreen) {
        this.maxpagesOnScreen = maxpagesOnScreen;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isHasPrev() {
        return hasPrev;
    }

    public void setHasPrev(boolean hasPrev) {
        this.hasPrev = hasPrev;
    }

    public int getStartPageOnScreen() {
        return startPageOnScreen;
    }

    public void setStartPageOnScreen(int startPageOnScreen) {
        this.startPageOnScreen = startPageOnScreen;
    }

    public int getEndPageOnScreen() {
        return endPageOnScreen;
    }

    public void setEndPageOnScreen(int endPageOnScreen) {
        this.endPageOnScreen = endPageOnScreen;
    }
}

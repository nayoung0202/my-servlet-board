<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">

<jsp:include page="/view/common/head.jsp">
    <jsp:param name="title" value="게시물 수정"/>
</jsp:include>

<body>

<jsp:include page="/view/common/header.jsp"/>


    <div class="container">
        <div class="input-form-backgroud row">
            <div class="input-form col-md-12 mx-auto">
                <h4 class="mb-3"><b>게시물 수정</b></h4>
                <hr>
                <br>
                <form class="validation-form" novalidate action="/board/update" method="post">
                    <%--action을 통해 /board/update로 post로 입력받은 데이터 값을 보낸다.--%>
                    <%--input 태그안에 value를 지정하여 value="${board.getTitle()}"으로 값을 받아온다.--%>

                    <div class="mb-3">
                        <label for="address">제목</label>
                        <input name="title" type="text" value="${board.getTitle()}" class="form-control" id="address" placeholder="제목을 입력해주세요" required>
                        <div class="invalid-feedback">
                            제목을 입력해주세요.
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="name">작성자</label>
                            <input name="writer" type="text" class="form-control" id="name" placeholder="" value="${board.getWriter()}" required>
                            <div class="invalid-feedback">
                                작성자를 입력해주세요.
                            </div>
                        </div>
<%--                        <div class="col-md-6 mb-3">--%>
<%--                            <label for="name">비밀번호</label>--%>
<%--                            <input type="password" class="form-control" id="password" placeholder="비밀번호를 입력해주세요"--%>
<%--                                value="" required>--%>
<%--                            <div class="invalid-feedback">--%>
<%--                                비밀번호를 입력해주세요.--%>
<%--                            </div>--%>
<%--                        </div>--%>
                    </div>

<%--                 수정한 값을 다시 detail로 보내기 위해서는 id라는 식별자로 구분해야 하기 때문에 필요하다--%>
                    <input type="text" name="id" value="${board.getId()}" hidden>
                    <div class="mb-3">
                        <label for="content" class="form-label">내용</label>
                        <textarea name="content" class="form-control" cols="30" rows="5" id="content"
                            placeholder="내용을 입력해주세요">${board.getContent()}</textarea>
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <button class="btn btn-secondary btn-block" type="submit">게시물 수정하기</button>
                        </div>
                        <div class="col-md-6 mb-3">
                            <button class="btn btn-secondary btn-block" type="submit">취소</button>
                        </div>
                    </div>

            </form>
        </div>
    </div>
    <div class="p-2">
        <div class="footer">
            <footer>
                <span class="text-muted d-flex justify-content-center">Copyright &copy; 2024 Bootstrap board</span>
            </footer>
        </div>
    </div>
    </div>
    <script>
        window.addEventListener('load', () => {
            const forms = document.getElementsByClassName('validation-form');

            Array.prototype.filter.call(forms, (form) => {
                form.addEventListener('submit', function (event) {
                    if (form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }

                    form.classList.add('was-validated');
                }, false);
            });
        }, false);
    </script>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>

</html>
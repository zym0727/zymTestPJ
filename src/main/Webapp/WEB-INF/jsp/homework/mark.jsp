<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>作业管理系统</title>
    <%@include file="../common/mainHeader.jspf" %>
    <script src="${pageContext.request.contextPath}/js/homework/teacherMark.js"></script>
</head>
<body>
<div class="page-wrapper">
    <%@include file="../common/bodyHeader.jspf" %>

    <div class="main-container">
        <%@include file="../common/bodySidebar.jspf" %>
        <div class="scrollSet">
            <div class="container ">
                <div id="homeworkSubmit">
                </div>
                <div id="fileSubmit" class="form-inline" style="display: none;
                 margin-left: 430px; margin-top:150px;margin-bottom: 50px ">
                    <label class="homeworkLabel">
                        已提交作业文件：
                    </label>
                    <a style=" margin-top: 27px; margin-left: 5px; color: #949e46;"
                       href="${pageContext.request.contextPath}/file/homework/download?fileName=${homeworkScore.fileName}&courseId=${courseId}&homeworkId=${homeworkScore.homeworkId}&userId=${homeworkScore.studentId}">
                       ${homeworkScore.fileName}
                    </a>
                </div>
                <div id="score" class="form-inline">
                    <label class="homeworkLabel">
                        此作业成绩：
                    </label>
                    <input type="text" id="scoreMark" name="scoreMark" class="form-control"
                           placeholder="        0-100" value= "${homeworkScore.score}"
                           aria-describedby="basic-addon1" minlength="1"  maxlength="3"
                           required oninput="showScoreMarkInputSuggestion();">
                </div>
                <p id="scoreMarkError" class="text-danger" style="margin-left: 550px; margin-top: 10px"></p>
                <div id="commit">
                    <label class="homeworkLabel" style="margin-bottom: 10px">
                        此作业评价：
                    </label>
                    <textarea id="commitText"  class="form-control" rows="7" >${homeworkScore.evaluate}</textarea>
                </div>
                <div id="submitThis">
                    <button id="markButton" type="button" class="btn btn-success btn-lg">提交批改</button>
                </div>
                <input id="questionNumber" type="hidden" value="${questionNumber}" />
                <input id="fileName" type="hidden" value="${homeworkScore.fileName}" />
                <input id="homeworkScoreId" type="hidden" value="${homeworkScore.id}" />
            </div>
            <div id="hiddenOne" class="bottom" style="margin:8px auto 0 25px;
                    display: none;
                    text-align:center;
                    bottom:0;
                    left:0;
                    overflow:hidden;
                    padding-bottom:8px;
                    color:#0c0b2f;
                    word-spacing:3px;
                    zoom:1;
                    font-size:15px
                    ">
                <p class="text-center">
                    Copyright &copy; 2019 zym
                </p>
            </div>
        </div>
    </div>
</div>
<div id="hiddenTwo" style="display: none">
    <%@include file="../common/mainFooter.jspf" %>
</div>
<script src="${pageContext.request.contextPath}/js/homework/mark.js"></script>
</body>
</html>

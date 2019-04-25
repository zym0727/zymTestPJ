<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!DOCTYPE html>
<html>
<head>
    <title>作业管理系统</title>
    <%@include file="../common/mainHeader.jspf" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-table.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-select.min.css">
    <script src="${pageContext.request.contextPath}/js/bootstrap/bootstrap-table.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap/bootstrap-table-zh-CN.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap/bootstrap-select.min.js"></script>
</head>
<body>
<div class="page-wrapper">
    <%@include file="../common/bodyHeader.jspf" %>

    <div class="main-container">
        <%@include file="../common/bodySidebar.jspf" %>
        <div class="container">
            <div class="form-inline" style="margin-top: 20px">
                <label id="courseChoose" class="control-label">课程:</label>
                <select name="select" id="selectCourse" class="selectpicker show-tick form-control col-sm-10"
                        data-width="98%" data-first-option="false"  title="请选择" data-live-search="true">
                    <c:forEach items="${courseList}" var="course">
                        <option value="${course.id}">
                                ${course.courseName}   (上课时间：${course.classTime}  上课班级:${course.classIds})
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-inline" style="margin-top: 20px">
                <label id="homeworkChoose" class="control-label">作业:</label>
                <select name="select" id="selectHomework" class="selectpicker show-tick form-control col-sm-10"
                        data-width="98%" data-first-option="false"  title="请选择" data-live-search="true">
                    <c:forEach items="${homeworkMessageList}" var="homeworkMessage">
                        <option value="${homeworkMessage.id}">
                                ${homeworkMessage.questionName}
                                    (课程：${homeworkMessage.courseName},
                                    发布时间: <fmt:formatDate value="${homeworkMessage.assignTime}"
                                                         pattern="yyyy-MM-dd HH:mm" />
                                    截止时间：<fmt:formatDate value="${homeworkMessage.deadline}"
                                                         pattern="yyyy-MM-dd HH:mm:ss" />)
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div style="margin-top: 20px;margin-left: 357px">
                <label id="classChoose" class="control-label">班级:</label>
                <select name="select" id="selectClass" class="selectpicker show-tick form-control col-sm-3"
                        data-width="98%" data-first-option="false"  title="请选择" data-live-search="true">
                    <c:forEach items="${classList}" var="classVar">
                        <option value="${classVar.id}">
                                ${classVar.className}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div>
                <table id="homeworkScoreTable" class="table table-striped"></table>
            </div>
        </div>
    </div>
</div>
<%@include file="../common/mainFooter.jspf" %>
<script src="${pageContext.request.contextPath}/js/table/homeworkScoreTable.js"></script>
</body>
</html>
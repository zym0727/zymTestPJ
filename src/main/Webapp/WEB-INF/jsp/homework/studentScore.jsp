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
            <div class="form-inline" style="margin-top: 65px;margin-bottom: 30px">
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

            <div>
                <table id="studentScoreTable" class="table table-striped"></table>
            </div>
        </div>
    </div>
</div>
<%@include file="../common/mainFooter.jspf" %>
<script src="${pageContext.request.contextPath}/js/table/studentScoreTable.js"></script>
</body>
</html>
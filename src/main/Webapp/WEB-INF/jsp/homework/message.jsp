<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>作业管理系统</title>
    <%@include file="../common/mainHeader.jspf" %>
    <link href="${pageContext.request.contextPath}/css/other.css" rel="stylesheet">
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
            <section class="dashboard-counts no-padding-bottom" style="margin-left: 200px;margin-right: 200px">
                <div class="container-fluid">
                    <div class="row bg-white has-shadow">
                        <!-- Item -->
                        <div class="col-sm-6">
                            <div id="newReply" class="item d-flex align-items-center">
                                <div class="icon dollar1"><i class="fa fa-users"></i></div>
                                <div class="title"><span>未查看的新回复</span></div>
                                <div id="newReplyNumber" class="number"><strong>0</strong></div>
                            </div>
                        </div>
                        <!-- Item -->
                        <div class="col-sm-6">
                            <div id="haveMessage" class="item d-flex align-items-center">
                                <div class="icon dollar2"><i class="fa fa-laptop"></i></div>
                                <div class="title"><span>已有的作业留言</span></div>
                                <div id="haveMessageNumber" class="number"><strong>0</strong></div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <div class="form-inline" style="margin-top: 30px; margin-left: 40px; margin-bottom: 10px">
                <label id="courseChoose" class="control-label">课程:</label>
                <select name="select" id="selectCourse" class="selectpicker show-tick form-control col-sm-9"
                        data-width="98%" data-first-option="false"  title="请选择" data-live-search="true">
                    <c:forEach items="${courseList}" var="course">
                        <option value="${course.id}">
                                ${course.courseName}   (上课时间：${course.classTime}  上课班级:${course.classIds})
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div id="toolbar">
                <button class="btn btn-primary" type="button" onclick="addItemBank();">进行新的留言</button>
            </div>

            <div>
                <table id="messageTable" class="table table-striped"></table>
            </div>
        </div>
    </div>
</div>
<%@include file="../common/mainFooter.jspf" %>
<script src="${pageContext.request.contextPath}/js/table/messageTable.js"></script>
</body>
</html>

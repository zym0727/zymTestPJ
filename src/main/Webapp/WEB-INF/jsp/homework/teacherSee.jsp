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
            <section class="dashboard-counts no-padding-bottom">
                <div class="container-fluid">
                    <div class="row bg-white has-shadow">
                        <!-- Item -->
                        <div class="col-xl-3 col-sm-6">
                            <div id="unManual" class="item d-flex align-items-center">
                                <div class="icon bg-red"><i class="icon icon-question"></i></div>
                                <div class="title"><span>未手动批改作业</span></div>
                                <div id="unManualNumber" class="number"><strong>0</strong></div>
                            </div>
                        </div>
                        <!-- Item -->
                        <div id="haveManual" class="col-xl-3 col-sm-6">
                            <div class="item d-flex align-items-center">
                                <div class="icon bg-green"><i class="icon icon-arrow-left"></i></div>
                                <div class="title"><span>已手动批改作业</span></div>
                                <div id="haveManualNumber" class="number"><strong>0</strong></div>
                            </div>
                        </div>
                        <!-- Item -->
                        <div id="unAutomatic" class="col-xl-3 col-sm-6">
                            <div class="item d-flex align-items-center">
                                <div class="icon bg-violet"><i class="icon icon-share"></i></div>
                                <div class="title"><span>未自动批改作业</span></div>
                                <div id="unAutomaticNumber" class="number"><strong>0</strong></div>
                            </div>
                        </div>
                        <!-- Item -->
                        <div id="haveAutomatic" class="col-xl-3 col-sm-6">
                            <div class="item d-flex align-items-center">
                                <div class="icon bg-orange"><i class="icon-check"></i></div>
                                <div class="title"><span>已自动批改作业</span></div>
                                <div id="haveAutomaticNumber" class="number"><strong>0</strong></div>
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

            <div>
                <table id="teacherSeeHomeworkTable" class="table table-striped"></table>
            </div>
        </div>
    </div>
</div>
<%@include file="../common/mainFooter.jspf" %>
<script src="${pageContext.request.contextPath}/js/table/teacherSee.js"></script>
</body>
</html>

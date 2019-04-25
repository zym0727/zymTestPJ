<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>作业管理系统</title>
    <%@include file="../common/mainHeader.jspf" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-select.min.css">
    <script src="${pageContext.request.contextPath}/js/bootstrap/bootstrap-datetimepicker.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap/bootstrap-select.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/homework/assign.js"></script>
</head>
<body>
<div class="page-wrapper">
    <%@include file="../common/bodyHeader.jspf" %>

    <div class="main-container">
        <%@include file="../common/bodySidebar.jspf" %>

        <div class="container">

            <div id="assign">

                <div id="theTitle">
                    <label class="control-label">作业标题：</label>
                    <input type="text" name="title" id="homeworkTitle" class="col-md-9"
                           minlength="1"  maxlength="50">
                </div>

                <div id="courseSelect" class="form-inline">
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

                <div id="time">
                    <label class="control-label">发布时间：</label>
                    <input class="input-medium search-query col-md-3" name="createDatetime_start form-control"
                                value="" id="datetimepicker_start" readonly/>
                    <label id = "endTime" class="control-label">截止时间：</label>
                    <input class="input-medium search-query col-md-3" name="createDatetime_end form-control"
                                 value="" id="datetimepicker_end" readonly/>
                </div>

                <div id="questionMake" class="form-inline">
                    <label id="itemBankChoose" class="control-label">题库:</label>
                    <select name="select" id="itemBankSelect" class="selectpicker show-tick form-control col-sm-2"
                            data-width="98%" data-first-option="false" title="请选择" data-live-search="true">
                        <c:forEach items="${itemBankList}" var="itemBank">
                            <option value="${itemBank.id}">
                                    ${itemBank.itemName}
                            </option>
                        </c:forEach>
                    </select>

                    <label id="questionChoose" class="control-label">题目:</label>
                    <select id ="questionSelect" name="questionSelect" class="form-control selectpicker col-md-7"
                        multiple="multiple" title="请选择" data-live-search="true">
                    </select>
                </div>

                <div id="selfDefined">
                    <label id="selfDefinedDescription" class="control-label">此次作业附加备注或描述（可选）:</label>
                    <div class="form-group">
                        <textarea id="remarkText" rows="7" cols="110"></textarea>
                    </div>
                </div>

                <div id="isAutomatic" class="form-inline">
                    <div class="radio">
                        <label>
                            <input type="radio" name="marking" id="optionsRadios1" value="0">手动批改
                        </label>
                    </div>
                    <div class="radio">
                        <label>
                            <input type="radio" name="marking" id="optionsRadios2" value="1">自动批改
                        </label>
                    </div>
                </div>

                <div id="tip" class="col-sm-4">
                    <div id="tips1">
                        如果是文件上传的作业请设置手动批改
                    </div>
                </div>

                <div id="assignThis">
                    <button id="assignButton" type="button" class="btn btn-success btn-lg"
                            onclick="assignHomework(true);">发布作业</button>
                </div>
            </div>

        </div>
    </div>
</div>
<%@include file="../common/mainFooter.jspf" %>
<script src="${pageContext.request.contextPath}/js/homework/assignDate.js"></script>
</body>
</html>

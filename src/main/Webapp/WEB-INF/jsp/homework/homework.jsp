<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>作业管理系统</title>
    <%@include file="../common/mainHeader.jspf" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-table.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-select.min.css">
    <script src="${pageContext.request.contextPath}/js/bootstrap/bootstrap-table.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap/bootstrap-table-zh-CN.js"></script>
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
            <div id="courseLine" class="form-group">
                <label class="control-label">指定课程:</label>
                <select id="courseId" name="select" class="selectpicker show-tick form-control col-sm-9"
                        data-width="98%" data-first-option="false"  title="请选择" data-live-search="true">
                    <c:forEach items="${courseList}" var="course">
                        <option value="${course.id}">
                                ${course.courseName}
                                    (上课时间：${course.classTime}上课班级:${course.classIds})
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div id="toolbar">
                <button id="batchDeleteHomework" class="btn btn-danger" type="button">
                    批量删除
                </button>
            </div>

            <table id="homeworkTable" class="table table-striped"></table>

            <!-- 编辑模态框（Modal） -->
            <div class="modal fade" id="updateModal" tabindex="-1" role="dialog"
                 aria-labelledby="myModalLabel" aria-hidden="true" style="margin-left: 60px">
                <div class="modal-dialog" style="max-height:600px; max-width: 1000px; margin: 0">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="myUpdateModalLabel" style="margin-left: 325px">
                                修改发布的作业信息</h4>
                        </div>
                        <div class="modal-body">
                            <form>
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
                                    <label id="selfDefinedDescription" class="control-label">
                                        此次作业附加备注或描述（可选）:</label>
                                    <div class="form-group">
                                        <textarea id="remarkText" rows="5" cols="95"></textarea>
                                    </div>
                                </div>

                                <div id="isAutomatic" class="form-inline"  style="margin-left: 330px">
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="marking" id="optionsRadios1" value="0">
                                            手动批改
                                        </label>
                                    </div>
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="marking" id="optionsRadios2" value="1">
                                            自动批改
                                        </label>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer" style="margin-right: 320px">
                            <button type="button" class="btn btn-default" data-dismiss="modal"
                                    style="margin-right: 30px">关闭
                            </button>
                            <button type="button" class="btn btn-primary" id="updateConfirmBtn">
                                提交更改
                            </button>
                        </div>
                    </div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal -->
        </div>
    </div>
</div>
<%@include file="../common/mainFooter.jspf" %>
<script src="${pageContext.request.contextPath}/js/table/homeworkTable.js"></script>
<script src="${pageContext.request.contextPath}/js/homework/assignDate.js"></script>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

            <!-- 自定义搜索框 -->
            <div class="form-group">
                <div class="row" id="findBar">
                    <div class="font">
                        <label class="myLabel" style="text-align: center; margin-top:8px">课程编号：</label>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" name="searchString" id="courseNumber"
                               class="form-control" placeholder="请输入大致课程编号"/>
                    </div>

                    <div class="font">
                        <label class="myLabel" style="text-align: center; margin-top:8px;
                         margin-left: 30px">课程名称：</label>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" name="searchString" id="courseName"
                               class="form-control" placeholder="请输入大致课程名称"/>
                    </div>

                    <div class="font">
                        <label class="myLabel" style="text-align: center; margin-top:8px;
                         margin-left: 30px">任课教师：</label>
                    </div>

                    <select name="select" id="selectTeacher" class="selectpicker show-tick form-control
                     col-sm-1" data-width="98%" data-first-option="false"  title="请选择"
                            data-live-search="true">
                        <c:forEach items="${teacherList}" var="teacher">
                            <option value="${teacher.id}">
                                    ${teacher.userName}
                            </option>
                        </c:forEach>
                    </select>

                    <div class="font">
                        <label class="myLabel" style="text-align: center; margin-top:8px;
                         margin-left: 30px">上课班级：</label>
                    </div>

                    <select name="select" id="selectClass" class="selectpicker show-tick form-control
                     col-sm-2" data-width="98%" data-first-option="false"
                            title="请选择" data-live-search="true">
                        <c:forEach items="${classList}" var="item">
                            <option value="${item.id}">
                                    ${item.className}
                            </option>
                        </c:forEach>
                    </select>

                    <div class="font" style="margin-top: 10px">
                        <label class="myLabel" style="text-align: center; margin-top:8px;
                         margin-left: 120px">上课时间：</label>
                    </div>

                    <div class="col-sm-2" style="margin-top: 10px">
                        <input type="text" name="searchString" id="classTime"
                               class="form-control" placeholder="请输入大致上课时间"/>
                    </div>

                    <div class="font" style="margin-top: 10px">
                        <label class="myLabel" style="text-align: center; margin-top:8px;
                         margin-left: 100px">开课学期：</label>
                    </div>
                    <div class="col-sm-2" style="margin-top: 10px">
                        <input type="text" name="searchString" id="semester"
                               class="form-control" placeholder="请输入大致开课学期"/>
                    </div>

                    <div class="col-sm-1" style="margin-top: 10px; margin-left: 70px">
                        <button type="button" class="btn btn-primary btn-w-m" id="courseQueryBtn">
                            <span class="glyphicon glyphicon-search"></span> 搜索
                        </button>
                    </div>
                </div>
            </div>

            <div id="toolbar">
                <button id="addCourse" class="btn btn-primary" type="button">增加一门课程</button>
                <button id="batchAddCourse" class="btn btn-info" type="button">
                    批量上传
                </button>
                <input name="uploadFile" type="file" id="courseFile" style="display: none"/>
                <button id="batchDeleteCourse" class="btn btn-danger" type="button">
                    批量删除
                </button>
            </div>

            <table id="courseTable" class="table table-striped"></table>

            <!-- 编辑模态框（Modal） -->
            <div class="modal fade" id="updateModal" tabindex="-1" role="dialog"
                 aria-labelledby="myModalLabel" aria-hidden="true" style="margin-left: 130px">
                <div class="modal-dialog" style="max-height:700px; max-width: 800px; margin: 0">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="myUpdateModalLabel"
                                style="margin-left:320px">修改课程信息</h4>
                        </div>
                        <div class="modal-body">
                            <div style="margin-left: 200px">
                                <form>
                                    <div class="allLeft">
                                        <label>课程编号</label>
                                    </div>
                                    <div class="col-sm-8">
                                        <input type="text" name="searchString" id="updateCourseNumber"
                                               class="form-control"/>
                                    </div>

                                    <div class="allLeft">
                                        <label>课程名称</label>
                                    </div>
                                    <div class="col-sm-8">
                                        <input type="text" name="searchString" id="updateCourseName"
                                               class="form-control"/>
                                    </div>

                                    <div class="allLeft">
                                        <label>任课教师</label>
                                    </div>

                                    <select name="select" id="updateSelectTeacher"
                                            class="selectpicker show-tick form-control col-sm-8" data-width="98%"
                                            data-first-option="false"  title="请选择" data-live-search="true">
                                        <c:forEach items="${teacherList}" var="teacher">
                                            <option value="${teacher.id}">
                                                    ${teacher.userName}
                                            </option>
                                        </c:forEach>
                                    </select>

                                    <div class="allLeft">
                                        <label>上课班级</label>
                                    </div>

                                    <select name="select" id="updateSelectClass"
                                            class="selectpicker show-tick form-control col-sm-8"
                                            multiple="multiple" data-width="98%" data-first-option="false"
                                            title="请选择" data-live-search="true">
                                        <c:forEach items="${classList}" var="item">
                                            <option value="${item.id}">
                                                    ${item.className}
                                            </option>
                                        </c:forEach>
                                    </select>

                                    <div class="allLeft">
                                        <label>上课时间</label>
                                    </div>

                                    <div class="col-sm-8">
                                        <input type="text" name="searchString" id="updateClassTime"
                                               class="form-control"/>
                                    </div>

                                    <div class="allLeft">
                                        <label>开课学期</label>
                                    </div>
                                    <div class="col-sm-8">
                                        <input type="text" name="searchString" id="updateSemester"
                                               class="form-control"/>
                                    </div>
                                    <div class="allLeft">
                                        <label>学分</label>
                                    </div>
                                    <div class="col-sm-8">
                                        <input type="text" name="searchString" id="updateCredit"
                                               class="form-control"/>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="modal-footer" style="padding-right:288px">
                            <button type="button" class="btn btn-default" data-dismiss="modal"
                                    style="margin-right:20px">关闭</button>
                            <button type="button" class="btn btn-primary" id="updateConfirmBtn">
                                提交更改</button>
                        </div>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>
            <!-- /.modal -->
            <!-- 添加模态框（Modal） -->
            <div class="modal fade" id="saveModal" tabindex="-1" role="dialog"
                 aria-labelledby="myModalLabel" aria-hidden="true" style="margin-left: 130px">
                <div class="modal-dialog" style="max-height:700px; max-width: 800px; margin: 0">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="myAddModalLabel"
                                style="margin-left:320px">添加一门课程</h4>
                        </div>
                        <div class="modal-body">
                            <div style="margin-left: 200px">
                                <form>
                                    <div class="allLeft">
                                        <label>课程编号</label>
                                    </div>
                                    <div class="col-sm-8">
                                        <input type="text" name="searchString" id="saveCourseNumber"
                                               class="form-control"/>
                                    </div>

                                    <div class="allLeft">
                                        <label>课程名称</label>
                                    </div>
                                    <div class="col-sm-8">
                                        <input type="text" name="searchString" id="saveCourseName"
                                               class="form-control"/>
                                    </div>

                                    <div class="allLeft">
                                        <label>任课教师</label>
                                    </div>

                                    <select name="select" id="saveSelectTeacher"
                                            class="selectpicker show-tick form-control col-sm-8" data-width="98%"
                                            data-first-option="false"  title="请选择" data-live-search="true">
                                        <c:forEach items="${teacherList}" var="teacher">
                                            <option value="${teacher.id}">
                                                    ${teacher.userName}
                                            </option>
                                        </c:forEach>
                                    </select>

                                    <div class="allLeft">
                                        <label>上课班级</label>
                                    </div>

                                    <select name="select" id="saveSelectClass"
                                            class="selectpicker show-tick form-control col-sm-8"
                                            multiple="multiple" data-width="98%" data-first-option="false"
                                            title="请选择" data-live-search="true">
                                        <c:forEach items="${classList}" var="item">
                                            <option value="${item.id}">
                                                    ${item.className}
                                            </option>
                                        </c:forEach>
                                    </select>

                                    <div class="allLeft">
                                        <label>上课时间</label>
                                    </div>

                                    <div class="col-sm-8">
                                        <input type="text" name="searchString" id="saveClassTime"
                                               class="form-control"/>
                                    </div >

                                    <div class="allLeft">
                                        <label>开课学期</label>
                                    </div>
                                    <div class="col-sm-8">
                                        <input type="text" name="searchString" id="saveSemester"
                                               class="form-control"/>
                                    </div>
                                    <div class="allLeft">
                                        <label>学分</label>
                                    </div>
                                    <div class="col-sm-8">
                                        <input type="text" name="searchString" id="saveCredit"
                                               class="form-control"/>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="modal-footer" style="padding-right:305px">
                            <button type="button" class="btn btn-default" data-dismiss="modal"
                                    style="margin-right:20px">关闭</button>
                            <button type="button" class="btn btn-primary" id="saveConfirmBtn">添加</button>
                        </div>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>
            <!-- /.modal -->

        </div>
    </div>
</div>
<%@include file="../common/mainFooter.jspf" %>
<script src="${pageContext.request.contextPath}/js/table/courseTable.js"></script>
</body>
</html>

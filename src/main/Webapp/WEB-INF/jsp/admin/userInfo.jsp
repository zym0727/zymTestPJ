<%@ page contentType="text/html;charset=UTF-8" %>
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
                        <label class="myLabel" >班级编号：</label>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" name="searchString" id="classNumber"
                               class="form-control" placeholder="请输入大致班级编号"/>
                    </div>
                    <div class="font">
                        <label class="myLabel" style="text-align: center; margin-top:8px">班级名字：</label>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" name="searchString" id="className"
                               class="form-control" placeholder="请输入大致班级名字"/>
                    </div>

                    <div class="font">
                        <label class="myLabel" style="text-align: center; margin-top:8px">入学时间/年级：</label>
                    </div>
                    <div class="col-sm-3">
                        <input type="text" name="searchString" id="grade"
                               class="form-control" placeholder="请输入大致入学时间/年级"/>
                    </div>

                    <div class="col-sm-1">
                        <button type="button" class="btn btn-primary btn-w-m" id="classQueryBtn">
                            <span class="glyphicon glyphicon-search"></span> 搜索
                        </button>
                    </div>
                </div>
            </div>

            <div id="toolbar">
                <button id="addUser" class="btn btn-primary" type="button">增加一个用户</button>
                <button id="batchAddUser" class="btn btn-info" type="button">
                    批量上传
                </button>
                <input name="uploadFile" type="file" id="userFile" style="display: none"/>
                <button id="batchDeleteUser" class="btn btn-danger" type="button">
                    批量删除
                </button>
            </div>

            <table id="userTable" class="table table-striped"></table>

            <!-- 编辑模态框（Modal） -->
            <div class="modal fade" id="updateModal" tabindex="-1" role="dialog"
                 aria-labelledby="myModalLabel" aria-hidden="true" style="margin-left: 170px">
                <div class="modal-dialog" style="max-height:600px; max-width: 500px; margin: 0">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="myUpdateModalLabel"
                                style="margin-left:160px">修改用户信息</h4>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="form-group">
                                    <label>班级编号</label> <input type="text" class="form-control"
                                                               id="updateClassNumber">
                                </div>
                                <div class="form-group">
                                    <label>班级名字</label> <input type="text" class="form-control"
                                                               id="updateClassName">
                                </div>
                                <div class="form-group">
                                    <label>入学时间/年级</label> <input type="text" class="form-control"
                                                                  id="updateGrade">
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer" style="padding-right:168px">
                            <button type="button" class="btn btn-default" data-dismiss="modal"
                                    style="margin-right:20px">关闭</button>
                            <button type="button" class="btn btn-primary" id="updateConfirmBtn">提交更改</button>
                        </div>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>
            <!-- /.modal -->
            <!-- 添加模态框（Modal） -->
            <div class="modal fade" id="saveModal" tabindex="-1" role="dialog"
                 aria-labelledby="myModalLabel" aria-hidden="true" style="margin-left: 170px">
                <div class="modal-dialog" style="max-height:600px; max-width: 500px; margin: 0">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="myAddModalLabel"
                                style="margin-left:160px">添加一个用户</h4>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="form-group">
                                    <label>班级编号</label> <input type="text" class="form-control"
                                                               id="saveClassNumber">
                                </div>
                                <div class="form-group">
                                    <label>班级名字</label> <input type="text" class="form-control"
                                                               id="saveClassName">
                                </div>
                                <div class="form-group">
                                    <label>入学时间/年级</label> <input type="text" class="form-control"
                                                                  id="saveGrade">
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer" style="padding-right:185px">
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
<script src="${pageContext.request.contextPath}/js/table/userTable.js"></script>
</body>
</html>
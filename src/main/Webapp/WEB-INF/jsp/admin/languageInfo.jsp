<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>作业管理系统</title>
    <%@include file="../common/mainHeader.jspf" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-table.css">
    <script src="${pageContext.request.contextPath}/js/bootstrap/bootstrap-table.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap/bootstrap-table-zh-CN.js"></script>
</head>
<body>
<div class="page-wrapper">
    <%@include file="../common/bodyHeader.jspf" %>

    <div class="main-container">
        <%@include file="../common/bodySidebar.jspf" %>

        <div class="container">

            <div style="margin-top: 60px">

            </div>

            <div id="toolbar">
                <button id="addLanguage" class="btn btn-primary" type="button">增加一个语言标记</button>
                <button id="batchDeleteLanguage" class="btn btn-danger" type="button">
                    批量删除
                </button>
            </div>

            <table id="languageTable" class="table table-striped"></table>

            <!-- 编辑模态框（Modal） -->
            <div class="modal fade" id="updateModal" tabindex="-1" role="dialog"
                 aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="myUpdateModalLabel"
                                style="margin-left:160px">修改语言标记信息</h4>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="form-group">
                                    <label>语言标记</label> <input type="text" class="form-control"
                                                              id="updateLanguage">
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer" style="padding-right:155px">
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
                 aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="myAddModalLabel"
                                style="margin-left:160px">添加一个语言标记</h4>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="form-group">
                                    <label>语言标记</label> <input type="text" class="form-control"
                                                              id="saveLanguage">
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer" style="padding-right:170px">
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
<script src="${pageContext.request.contextPath}/js/table/languageTable.js"></script>
</body>
</html>
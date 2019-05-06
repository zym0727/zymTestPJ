<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>作业管理系统</title>
    <%@include file="../common/mainHeader.jspf" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-table.css">
    <script src="${pageContext.request.contextPath}/js/bootstrap/bootstrap-table.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap/bootstrap-table-zh-CN.js"></script>
    <script src="${pageContext.request.contextPath}/js/itemBank/itemBank.js"></script>
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
                        <label class="myLabel" style="text-align: center; margin-top:8px;">题库名：</label>
                    </div>
                    <div class="col-sm-3">
                        <input type="text" name="searchString" id="itemName"
                               class="form-control" placeholder="请输入大致题库名模糊搜索"/>
                    </div>
                    <div class="font">
                        <label class="myLabel" style="text-align: center; margin-top:8px">描述：</label>
                    </div>
                    <div class="col-sm-6">
                        <input type="text" name="searchString" id="itemDescription"
                               class="form-control" placeholder="请输入大致题库描述模糊搜索"/>
                    </div>
                    <div class="col-sm-1">
                        <button type="button" class="btn btn-primary btn-w-m" id="itemBankQueryBtn">
                            <span class="glyphicon glyphicon-search"></span> 搜索
                        </button>
                    </div>
                </div>
            </div>

            <div id="toolbar">
                <button class="btn btn-primary" type="button" onclick="addItemBank();">增加一个题库</button>
                <button class="btn btn-info" type="button" onclick="itemBankClick();">
                    批量上传
                </button>
                <input name="uploadFile" type="file" id="itemBankFile" style="display: none"
                       onchange="batchAddItemBank();"/>
                <button class="btn btn-danger" type="button" onclick="batchDeleteItemBank();">
                    批量删除
                </button>
            </div>

            <table id="itemTable" class="table table-striped"></table>

            <!-- 编辑模态框（Modal） -->
            <div class="modal fade" id="updateModal" tabindex="-1" role="dialog"
                 aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="myUpdateModalLabel" style="margin-left:180px">修改题库信息</h4>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="form-group">
                                    <label>题库名</label> <input type="text" class="form-control"
                                                             id="updateItemBankName">
                                </div>
                                <div class="form-group">
                                    <label>题库描述</label> <input type="text" class="form-control"
                                                             id="updateItemBankDescription">
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
                            <h4 class="modal-title" id="myAddModalLabel" style="margin-left:180px">添加一个题库</h4>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="form-group">
                                    <label>题库名</label> <input type="text" class="form-control"
                                                             id="saveItemBankName">
                                </div>
                                <div class="form-group">
                                    <label>题库描述</label> <input type="text" class="form-control"
                                                             id="saveItemBankDescription">
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
<script src="${pageContext.request.contextPath}/js/table/itemTable.js"></script>
</body>
</html>

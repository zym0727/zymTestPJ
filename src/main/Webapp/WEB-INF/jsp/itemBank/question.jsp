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
    <script src="${pageContext.request.contextPath}/js/itemBank/question.js"></script>
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
                        <label class="myLabel" style="text-align: center;
                         margin-left:30px;margin-top:8px;">所属题库：</label>
                    </div>
                    <select id="itemBankSelect" name="select"
                            class="selectpicker show-tick form-control col-sm-2"
                            data-width="98%" data-first-option="false"
                            title="请选择" data-live-search="true">
                        <c:forEach items="${itemBankList}" var="itemBank">
                            <option value="${itemBank.id}">
                                    ${itemBank.itemName}
                            </option>
                        </c:forEach>
                    </select>
                    <div class="font">
                        <label class="myLabel" style="text-align: center; margin-top:8px;
                        margin-left: 20px">题目编号：</label>
                    </div>
                    <select id="questionSelect" name="select"
                            class="selectpicker show-tick form-control col-sm-2"
                            data-width="98%" data-first-option="false"  title="请选择"
                            data-live-search="true">
                        <c:forEach items="${questionList}" var="question">
                            <option value="${question.questionNumber}">
                                    ${question.questionNumber}
                            </option>
                        </c:forEach>
                    </select>
                    <div class="font">
                        <label class="myLabel" style="text-align: center; margin-top:8px;
                        margin-left: 20px">题目名字：</label>
                    </div>
                    <div class="col-sm-3">
                        <input type="text" name="searchString" id="questionName"
                               class="form-control" placeholder="请输入大致题目名字模糊搜索"/>
                    </div>
                    <div class="col-sm-1">
                        <button type="button" class="btn btn-primary btn-w-m" id="questionQueryBtn">
                            <span class="glyphicon glyphicon-search"></span> 搜索
                        </button>
                    </div>
                </div>
            </div>

            <div id="toolbar">
                <button class="btn btn-primary" type="button" onclick="addQuestion();">增加一个题目</button>
                <button class="btn btn-info" type="button" onclick="questionClick();">
                    批量上传
                </button>
                <input name="uploadFile" type="file" id="questionFile" style="display: none"
                       onchange="batchAddQuestion();"/>
                <button class="btn btn-danger" type="button" onclick="batchDeleteQuestion();">
                    批量删除
                </button>
            </div>

            <table id="questionTable" class="table table-striped"></table>
            <!-- 编辑模态框（Modal） -->
            <div class="modal fade" id="updateModal" tabindex="-1" role="dialog"
                 aria-labelledby="myModalLabel" aria-hidden="true" style="margin-left: 80px">
                <div class="modal-dialog" style="max-height:800px; max-width: 1200px; margin: 0">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="myUpdateModalLabel"
                                style="margin-left: 360px">修改题目信息</h4>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="form-group form-inline">
                                    <label class="questionSet">题目名</label>
                                    <input type="text" class="form-control questionInput col-sm-4"
                                           style="margin-left: 47px" id="updateQuestionName">
                                </div>
                                <div class="form-group form-inline">
                                    <label class="questionSet" style="margin-right: 30px">所属题库</label>
                                    <select id="updateItemBankId" name="select"
                                            class="selectpicker show-tick form-control col-sm-2"
                                            data-width="98%" data-first-option="false"
                                            title="请选择" data-live-search="true">
                                        <c:forEach items="${itemBankList}" var="itemBank">
                                            <option value="${itemBank.id}">
                                                    ${itemBank.itemName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group form-inline">
                                    <label class="questionSet">题目编号</label>
                                    <input type="text" class="form-control questionInput col-sm-4"
                                           id="updateQuestionNumber">
                                </div>
                                <div class="form-group">
                                    <label style="margin-left: 385px">题目描述</label>
                                    <textarea id="updateQuestionDescription" rows="15" cols="85"
                                              style="margin-left: 120px"></textarea>
                                </div>
                                <div class="form-group form-inline">
                                    <label class="questionSet">标准答案</label>
                                    <input type="text" class="form-control questionInput col-sm-4"
                                           id="updateAnswer">
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal"
                                    style="margin-right: 50px">关闭
                            </button>
                            <button type="button" class="btn btn-primary" id="updateConfirmBtn"
                                    style="margin-right: 294px">提交更改</button>
                        </div>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>
            <!-- /.modal -->
            <!-- 添加模态框（Modal） -->
            <div class="modal fade" id="saveModal" tabindex="-1" role="dialog"
                 aria-labelledby="myModalLabel" aria-hidden="true" style="margin-left: 80px">
                <div class="modal-dialog" style="max-height:800px; max-width: 1200px; margin: 0">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="myAddModalLabel"
                                style="margin-left: 360px">添加一个题目</h4>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="form-group form-inline">
                                    <label class="questionSet">题目名</label>
                                    <input type="text" class="form-control questionInput col-sm-4"
                                           style="margin-left: 47px" id="saveQuestionName">
                                </div>
                                <div class="form-group form-inline">
                                    <label class="questionSet" style="margin-right: 30px">所属题库</label>
                                    <select id="saveItemBankId" name="select"
                                            class="selectpicker show-tick form-control col-sm-2"
                                            data-width="98%" data-first-option="false"
                                            title="请选择" data-live-search="true">
                                        <c:forEach items="${itemBankList}" var="itemBank">
                                            <option value="${itemBank.id}">
                                                    ${itemBank.itemName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group form-inline">
                                    <label class="questionSet">题目编号</label>
                                    <input type="text" class="form-control questionInput col-sm-4"
                                           id="saveQuestionNumber">
                                </div>
                                <div class="form-group">
                                    <label style="margin-left: 385px">题目描述</label>
                                    <textarea id="saveQuestionDescription" rows="15" cols="85"
                                              style="margin-left: 120px"></textarea>
                                </div>
                                <div class="form-group form-inline">
                                    <label class="questionSet">标准答案</label>
                                    <input type="text" class="form-control questionInput col-sm-4"
                                           id="saveAnswer">
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default"
                                    style="margin-right: 50px" data-dismiss="modal">关闭
                            </button>
                            <button type="button" class="btn btn-primary"
                                    style="margin-right: 309px" id="saveConfirmBtn">添加</button>
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
<script src="${pageContext.request.contextPath}/js/table/questionTable.js"></script>
</body>
</html>

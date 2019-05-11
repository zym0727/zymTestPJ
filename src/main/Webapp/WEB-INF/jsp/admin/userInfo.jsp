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
                        <label class="myLabel" >账号名称：</label>
                    </div>
                    <div class="col-sm-3">
                        <input type="text" name="searchString" id="account"
                               class="form-control" placeholder="请输入大致账号名称模糊搜索"/>
                    </div>
                    <div class="font">
                        <label class="myLabel" style="text-align: center; margin-top:8px">用户名称：</label>
                    </div>
                    <div class="col-sm-3">
                        <input type="text" name="searchString" id="userName"
                               class="form-control" placeholder="请输入大致账号名称模糊搜索"/>
                    </div>

                    <div class="font">
                        <label class="myLabel" style="text-align: center; margin-top:8px">用户编号：</label>
                    </div>
                    <div class="col-sm-3">
                        <input type="text" name="searchString" id="userNumber"
                               class="form-control" placeholder="请输入大致账号名称模糊搜索"/>
                    </div>

                    <div class="row col-sm-12" style="margin-top: 10px; margin-left:20px ">

                        <div class="font">
                            <label class="myLabel" style="text-align: center; margin-top:8px;">角色：</label>
                        </div>

                        <select name="select" id="selectRole" class="selectpicker show-tick form-control
                     col-sm-2" data-width="98%" data-first-option="false"  title="请选择"
                                data-live-search="true">
                            <c:forEach items="${roleList}" var="role">
                                <option value="${role.id}">
                                        ${role.remark}
                                </option>
                            </c:forEach>
                        </select>

                        <div class="font">
                            <label class="myLabel" style="text-align: center; margin-top:8px;
                         margin-left: 70px">用户班级：</label>
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

                        <div class="font">
                            <label class="myLabel" style="text-align: center; margin-top:8px;
                         margin-left: 70px">性别：</label>
                        </div>

                        <select name="select" id="selectSex" class="selectpicker show-tick form-control
                     col-sm-2" data-width="98%" data-first-option="false" title="请选择">
                            <option value="1">
                                男
                            </option>
                            <option value="0">
                                女
                            </option>
                        </select>

                        <div class="col-sm-1" style="margin-left: 40px">
                            <button type="button" class="btn btn-primary btn-w-m" id="userQueryBtn">
                                <span class="glyphicon glyphicon-search"></span> 搜索
                            </button>
                        </div>

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
                 aria-labelledby="myModalLabel" aria-hidden="true" style="margin-left: 130px">
                <div class="modal-dialog" style="max-height:700px; max-width: 800px; margin: 0">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="myUpdateModalLabel"
                                style="margin-left:320px">修改用户信息</h4>
                        </div>
                        <div class="modal-body">
                            <div style="margin-left: 200px">
                                <form>
                                    <div class="allLeft">
                                        <label>角色</label>
                                    </div>

                                    <select name="select" id="updateRole"
                                            class="selectpicker show-tick form-control col-sm-8"
                                            data-width="98%" data-first-option="false"
                                            title="请选择" data-live-search="true">
                                        <c:forEach items="${roleList}" var="role">
                                            <option value="${role.id}">
                                                    ${role.remark}
                                            </option>
                                        </c:forEach>
                                    </select>

                                    <div class="allLeft">
                                        <label>账号名称</label>
                                    </div>
                                    <div class="col-sm-8">
                                        <input type="text" name="searchString" id="updateAccount"
                                               class="form-control"/>
                                    </div>

                                    <div class="allLeft">
                                        <label>密码</label>
                                    </div>
                                    <div class="col-sm-8">
                                        <input type="text" name="searchString" id="updatePassword"
                                               class="form-control"/>
                                    </div>

                                    <div class="allLeft">
                                        <label>用户名称</label>
                                    </div>

                                    <div class="col-sm-8">
                                        <input type="text" name="searchString" id="updateUserName"
                                               class="form-control"/>
                                    </div>

                                    <div class="allLeft">
                                        <label>用户编号</label>
                                    </div>
                                    <div class="col-sm-8">
                                        <input type="text" name="searchString" id="updateUserNumber"
                                               class="form-control"/>
                                    </div>

                                    <div class="allLeft">
                                        <label>上课班级</label>
                                    </div>

                                    <select name="select" id="updateClass"
                                            class="selectpicker show-tick form-control col-sm-8"
                                            data-width="98%" data-first-option="false"
                                            title="请选择" data-live-search="true">
                                        <c:forEach items="${classList}" var="item">
                                            <option value="${item.id}">
                                                    ${item.className}
                                            </option>
                                        </c:forEach>
                                    </select>

                                    <div class="allLeft">
                                        <label>性别</label>
                                    </div>

                                    <select name="select" id="updateSex"
                                            class="selectpicker show-tick form-control col-sm-8"
                                            data-width="98%" data-first-option="false" title="请选择">
                                        <option value="1">
                                            男
                                        </option>
                                        <option value="0">
                                            女
                                        </option>
                                    </select>

                                    <div class="allLeft">
                                        <label>电话号码</label>
                                    </div>
                                    <div class="col-sm-8">
                                        <input type="text" name="searchString" id="updateTelephone"
                                               class="form-control"/>
                                    </div>

                                    <div class="allLeft">
                                        <label>是否启用</label>
                                    </div>

                                    <select name="select" id="updateEnabled"
                                            class="selectpicker show-tick form-control col-sm-8"
                                            data-width="98%" data-first-option="false" title="请选择">
                                        <option value="1">
                                            启用
                                        </option>
                                        <option value="0">
                                            不启用
                                        </option>
                                    </select>
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
                                style="margin-left:320px">添加一个用户</h4>
                        </div>
                        <div class="modal-body">
                            <div style="margin-left: 200px">
                                <form>
                                    <div class="allLeft">
                                        <label>角色</label>
                                    </div>

                                    <select name="select" id="saveRole"
                                            class="selectpicker show-tick form-control col-sm-8"
                                            data-width="98%" data-first-option="false"
                                            title="请选择" data-live-search="true">
                                        <c:forEach items="${roleList}" var="role">
                                            <option value="${role.id}">
                                                    ${role.remark}
                                            </option>
                                        </c:forEach>
                                    </select>

                                    <div class="allLeft">
                                        <label>账号名称</label>
                                    </div>
                                    <div class="col-sm-8">
                                        <input type="text" name="searchString" id="saveAccount"
                                               class="form-control"/>
                                    </div>

                                    <div class="allLeft">
                                        <label>密码</label>
                                    </div>
                                    <div class="col-sm-8">
                                        <input type="text" name="searchString" id="savePassword"
                                               class="form-control"/>
                                    </div>

                                    <div class="allLeft">
                                        <label>用户名称</label>
                                    </div>

                                    <div class="col-sm-8">
                                        <input type="text" name="searchString" id="saveUserName"
                                               class="form-control"/>
                                    </div>

                                    <div class="allLeft">
                                        <label>用户编号</label>
                                    </div>
                                    <div class="col-sm-8">
                                        <input type="text" name="searchString" id="saveUserNumber"
                                               class="form-control"/>
                                    </div>

                                    <div class="allLeft">
                                        <label>上课班级</label>
                                    </div>

                                    <select name="select" id="saveClass"
                                            class="selectpicker show-tick form-control col-sm-8"
                                            data-width="98%" data-first-option="false"
                                            title="请选择" data-live-search="true">
                                        <c:forEach items="${classList}" var="item">
                                            <option value="${item.id}">
                                                    ${item.className}
                                            </option>
                                        </c:forEach>
                                    </select>

                                    <div class="allLeft">
                                        <label>性别</label>
                                    </div>

                                    <select name="select" id="saveSex"
                                            class="selectpicker show-tick form-control col-sm-8"
                                            data-width="98%" data-first-option="false" title="请选择">
                                        <option value="1">
                                            男
                                        </option>
                                        <option value="0">
                                            女
                                        </option>
                                    </select>

                                    <div class="allLeft">
                                        <label>电话号码</label>
                                    </div>
                                    <div class="col-sm-8">
                                        <input type="text" name="searchString" id="saveTelephone"
                                               class="form-control"/>
                                    </div>

                                    <div class="allLeft">
                                        <label>是否启用</label>
                                    </div>

                                    <select name="select" id="saveEnabled"
                                            class="selectpicker show-tick form-control col-sm-8"
                                            data-width="98%" data-first-option="false" title="请选择">
                                        <option value="1">
                                            启用
                                        </option>
                                        <option value="0">
                                            不启用
                                        </option>
                                    </select>
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
<script src="${pageContext.request.contextPath}/js/table/userTable.js"></script>
</body>
</html>
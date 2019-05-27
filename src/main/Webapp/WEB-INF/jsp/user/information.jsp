<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>作业管理系统</title>
    <%@include file="../common/mainHeader.jspf" %>
</head>
<body>
<div class="page-wrapper">
    <%@include file="../common/bodyHeader.jspf" %>

    <div class="main-container">
        <%@include file="../common/bodySidebar.jspf" %>

        <div class="scrollSet">
            <div class="container">
                <div class="card card-small mb-4 col-md-12" style="margin-top: 50px">
                    <div class="card-header border-bottom">
                        <h6 style="font-size: 20px">个人信息</h6>
                    </div>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item p-3">
                            <div class="row">
                                <div class="col">
                                    <form>
                                        <div style="margin-left: 380px">
                                            <div class="form-group col-md-6">
                                                <label for="feName">姓名</label>
                                                <input type="text" class="form-control" id="feName" readonly>
                                            </div>
                                            <div class="form-group col-md-6">
                                                <label for="feAccount">账号名称</label>
                                                <input type="text" class="form-control"
                                                       placeholder="长度至少3并小于20，并是字母、数字或汉字"
                                                       id="feAccount">
                                            </div>
                                            <div class="form-group col-md-6">
                                                <security:authorize access="hasRole('ROLE_STUDENT')">
                                                    <label for="feNumber">学号</label>
                                                </security:authorize>
                                                <security:authorize access="hasRole('ROLE_TEACHER')">
                                                    <label for="feNumber">教工号</label>
                                                </security:authorize>
                                                <input type="text" class="form-control"
                                                       id="feNumber" readonly>
                                            </div>
                                            <div class="form-group col-md-6">
                                                <label for="feSex">性别</label>
                                                <input type="text" class="form-control"
                                                       id="feSex" readonly>
                                            </div>
                                            <security:authorize access="hasAuthority('ROLE_STUDENT')">
                                                <div class="form-group col-md-6">
                                                    <label for="feClass">班级</label>
                                                    <input type="text" class="form-control"
                                                           id="feClass" readonly>
                                                </div>
                                            </security:authorize>
                                            <div class="form-group col-md-6">
                                                <label for="feTelephone">电话号码</label>
                                                <input type="text" class="form-control"
                                                       id="feTelephone" readonly>
                                            </div>
                                            <div class="form-group col-md-6">
                                                <label for="fePassword">原密码</label>
                                                <input type="password" class="form-control"
                                                       placeholder="长度至少6并小于20，并是字母、数字或汉字"
                                                       id="fePassword">
                                            </div>
                                            <div class="form-group col-md-6">
                                                <label for="feNewPassword">新密码</label>
                                                <input type="password" class="form-control"
                                                       placeholder="长度至少6并小于20，并是字母、数字或汉字"
                                                       id="feNewPassword">
                                            </div>
                                            <div class="form-group col-md-6">
                                                <label for="feConfirmPassword">确认密码</label>
                                                <input type="password" class="form-control"
                                                       placeholder="长度至少6并小于20，并是字母、数字或汉字"
                                                       id="feConfirmPassword">
                                            </div>
                                        </div>
                                        <div class="alert alert-danger" role="alert"
                                             style="padding: 10px 0 0 30px;
                                                text-align: center;
                                                font-size: 15px;
                                                background-color: transparent;
                                                border-color: transparent;
                                                color: #3c763d;">
                                            账号和密码可以修改，其他信息错误请到学院办公室进行相应处理</div>
                                        <button id="updateInfo" type="button"
                                                class="btn btn btn-success btn-lg">修改账号和密码</button>
                                    </form>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="bottom" style="margin:8px auto 0 25px;
                    text-align:center;
                    bottom:0;
                    left:0;
                    overflow:hidden;
                    padding-bottom:8px;
                    color:#0c0b2f;
                    word-spacing:3px;
                    zoom:1;
                    font-size:15px
                    ">
                    <p class="text-center">
                        Copyright &copy; 2019 zym
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/user/info.js"></script>
</body>
</html>

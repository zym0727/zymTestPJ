<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html>
<html>
<head>
    <title>作业管理系统</title>
    <%@include file="common/header.jspf" %>
    <script src="${pageContext.request.contextPath}/js/user/login.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/first.css" />
</head>
<body>
<div class="login">
    <div class="container">
        <div class="page-header">
            <h1>用户登录</h1>
        </div>
        <div class="form">
            <form class="form-signin" method="POST" action="${pageContext.request.contextPath}/user/login"
                  oninput="satisfySubmit();">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="row">
                    <div class="col-md-6 col-md-offset-3">
                        <div class="input-group">
                            <span class="input-group-addon" id="basic-addon1">账号</span>
                            <input type="text" id="username" name="username" class="form-control"
                                   placeholder="长度至少3并小于20，并且是字母或者数字"
                                   aria-describedby="basic-addon1" minlength="3"  maxlength="20"
                                   required oninput="showAccountInputSuggestion();">
                        </div>
                    </div>
                    <div class="col-md-6 col-md-offset-3 ">
                        <p id="usernameError" class="text-danger"></p>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-md-6 col-md-offset-3">
                        <div class="input-group">
                            <span class="input-group-addon" id="basic-addon2">密码</span>
                            <input type="password" id="password" name="password" class="form-control"
                                   placeholder="长度至少6并小于20，并且是字母或者数字"
                                   aria-describedby="basic-addon1" minlength="6" maxlength="20"
                                   required oninput="showPasswordInputSuggestion();">
                        </div>
                    </div>
                    <div class="col-md-6 col-md-offset-3">
                        <p id="passwordError" class="text-danger"></p>
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-md-6 col-md-offset-5">
                        <input id="remember-me" name="remember-me" type="checkbox" value="true"/>一天内记住我
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-md-6 col-md-offset-4">
                        <input class="btn btn-danger" id="submit" name="submit" type="button" value="请输入账号和密码并检查格式登录" disabled
                               onclick="authorize();"/>
                    </div>
                </div>
                <br>
                <p id="success"></p>
                <div class="alert alert-danger" role="alert">用户注册、登录密码忘记可到学院办公室进行相应处理</div>
            </form>
        </div>
    </div>
</div>
<div class="footer"></div>
<%@include file="common/footer.jspf" %>
</body>
</html>

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
        <div style="background: url(${pageContext.request.contextPath}/img/bg.jpg) center no-repeat;
                width: 100%;background-size: cover;">
            <div style="background: rgba(220, 198, 198, 0.2); height: 100%; width: 100%">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12 col-md-offset-2 intro-text">
                            <h1 class="mainH1ss">欢迎，${user.account}</h1>
                            <p class="mainPara">
                                作业上传与自动批改系统将提供作业管理相关的功能，并支持程序作业的自动批改处理</p>
                            <security:authorize access="hasRole('ROLE_TEACHER')">
                                <a href="${pageContext.request.contextPath}/homework/teacher/assign/getPage"
                                   class="btn btn-custom btn-lg page-scroll">来发布作业试试</a>
                            </security:authorize>

                            <security:authorize access="hasRole('ROLE_STUDENT')">
                                <a href="${pageContext.request.contextPath}/homework/student/see"
                                   class="btn btn-custom btn-lg page-scroll">马上查看作业</a>
                            </security:authorize>

                            <security:authorize access="hasRole('ROLE_ADMIN')">
                                <a href="${pageContext.request.contextPath}/admin/userManage"
                                   class="btn btn-custom btn-lg page-scroll">进行用户管理</a>
                            </security:authorize>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../common/mainFooter.jspf" %>
<script>
    $("#homePage").attr("class","nav-link active");
</script>
</body>
</html>

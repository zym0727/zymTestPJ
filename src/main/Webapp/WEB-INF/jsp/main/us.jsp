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
                            <h1 class="mainH1ss">关于我们</h1>
                            <p class="mainPara">
                                此系统当前是1.0版本，有任何建议或者错误可以联系XXX，联系方式是XXX</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../common/mainFooter.jspf" %>
</body>
</html>


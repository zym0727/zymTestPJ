<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>作业管理系统</title>
    <%@include file="../common/mainHeader.jspf" %>
    <link href="${pageContext.request.contextPath}/css/other.css" rel="stylesheet">
</head>
<body>
<div class="page-wrapper">
    <%@include file="../common/bodyHeader.jspf" %>

    <div class="main-container">
        <%@include file="../common/bodySidebar.jspf" %>

        <div class="container">
            <section class="dashboard-counts no-padding-bottom">
                <div class="container-fluid">
                    <div class="row bg-white has-shadow">
                        <!-- Item -->
                        <div class="col-xl-3 col-sm-6">
                            <div id="unSubmit" class="item d-flex align-items-center">
                                <div class="icon bg-red"><i class="icon icon-question"></i></div>
                                <div class="title"><span>未手动批改作业</span></div>
                                <div class="number"><strong>25</strong></div>
                            </div>
                        </div>
                        <!-- Item -->
                        <div id="haveSubmitted" class="col-xl-3 col-sm-6">
                            <div class="item d-flex align-items-center">
                                <div class="icon bg-green"><i class="icon icon-arrow-left"></i></div>
                                <div class="title"><span>已手动批改作业</span></div>
                                <div class="number"><strong>70</strong></div>
                            </div>
                        </div>
                        <!-- Item -->
                        <div id="unMark" class="col-xl-3 col-sm-6">
                            <div class="item d-flex align-items-center">
                                <div class="icon bg-violet"><i class="icon icon-share"></i></div>
                                <div class="title"><span>未自动批改作业</span></div>
                                <div class="number"><strong>40</strong></div>
                            </div>
                        </div>
                        <!-- Item -->
                        <div id="haveMarked" class="col-xl-3 col-sm-6">
                            <div class="item d-flex align-items-center">
                                <div class="icon bg-orange"><i class="icon-check"></i></div>
                                <div class="title"><span>已自动批改作业</span></div>
                                <div class="number"><strong>50</strong></div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>
</div>
<%@include file="../common/mainFooter.jspf" %>
<script src="${pageContext.request.contextPath}/js/homework/teacherSee.js"></script>
</body>
</html>

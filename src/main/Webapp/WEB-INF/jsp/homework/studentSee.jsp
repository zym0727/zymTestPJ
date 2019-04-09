<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>作业管理系统</title>
    <%@include file="../common/mainHeader.jspf" %>
    <link href="${pageContext.request.contextPath}/css/other.css" rel="stylesheet">
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
            <section class="dashboard-counts no-padding-bottom">
                <div class="container-fluid">
                    <div class="row bg-white has-shadow">
                        <!-- Item -->
                        <div class="col-xl-3 col-sm-6">
                            <div id="unSubmit" class="item d-flex align-items-center">
                                <div class="icon bg-red"><i class="icon icon-question"></i></div>
                                <div class="title"><span>未提交作业</span></div>
                                <div id="unSubmitNumber" class="number"><strong>0</strong></div>
                            </div>
                        </div>
                        <!-- Item -->
                        <div id="haveSubmitted" class="col-xl-3 col-sm-6">
                            <div class="item d-flex align-items-center">
                                <div class="icon bg-green"><i class="icon icon-arrow-left"></i></div>
                                <div class="title"><span>已提交作业</span></div>
                                <div id="submittedNumber" class="number"><strong>0</strong></div>
                            </div>
                        </div>
                        <!-- Item -->
                        <div id="unMark" class="col-xl-3 col-sm-6">
                            <div class="item d-flex align-items-center">
                                <div class="icon bg-violet"><i class="icon icon-share"></i></div>
                                <div class="title"><span>未批改作业</span></div>
                                <div id="unMarkNumber" class="number"><strong>0</strong></div>
                            </div>
                        </div>
                        <!-- Item -->
                        <div id="haveMarked" class="col-xl-3 col-sm-6">
                            <div class="item d-flex align-items-center">
                                <div class="icon bg-orange"><i class="icon-check"></i></div>
                                <div class="title"><span>已批改作业</span></div>
                                <div id="markNumber" class="number"><strong>0</strong></div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <div>
                <table id="studentHomeworkTable" class="table table-striped"></table>
            </div>
        </div>
    </div>
</div>
<%@include file="../common/mainFooter.jspf" %>
<script src="${pageContext.request.contextPath}/js/table/studentHomework.js"></script>
</body>
</html>

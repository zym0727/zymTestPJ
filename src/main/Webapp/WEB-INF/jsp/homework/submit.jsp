<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>作业管理系统</title>
    <%@include file="../common/mainHeader.jspf" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/fileinput.min.css">
    <script src="${pageContext.request.contextPath}/js/bootstrap/fileinput.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap/zh.js"></script>
    <script src="${pageContext.request.contextPath}/js/file/homeworkFile.js"></script>
</head>
<body>
<div class="page-wrapper">
    <%@include file="../common/bodyHeader.jspf" %>

    <div class="main-container">
        <%@include file="../common/bodySidebar.jspf" %>
        <div class="scrollSet">
            <div class="container ">
                <label class="homeworkLabel">
                    作业的详细信息：
                </label>
                <textarea id="homeworkDetail" class="form-control"   rows="25"  readonly>${homeworkDetail}</textarea>
                <div class="form-inline">
                    <label id="assignLabel">
                        该作业的发布时间：${assignTime}
                    </label>
                    <label id="deadlineLabel">
                        该作业的截止时间：${deadline}
                    </label>
                </div>
                <label class="homeworkLabel">
                    此次作业说明：
                </label>
                <textarea  class="form-control"   rows="5"  readonly>
                    ${remark}
                </textarea>
                <p  class="text-warning homeworkLabel">提交前请注意以下几点：<br><br>
                    1.请选择文件上传或者输入信息到文本框提交作业，如果文本框内有信息将不会上传文件   <br><br>
                    2.Java语言代码请按照这种格式：输入输出流：System.In 和 System.Out，主类：Main，函数：标准 main 函数。
                    <br><br>
                    3.请在截止日期前提交，截止日期后再提交请向相应的课程老师报告<br><br>
                    4.作业的题目请用<strong>div----------div</strong>在问题之间进行隔开(两个div中间10个-)，
                    题目中禁止出现，否则自动批改可能出错
                </p>
                <label class="homeworkLabel">
                    自己的作业答案：
                </label>
                <textarea id="homeworkSubmit" class="form-control"   rows="20" >${homeworkScore.answer}</textarea>
                <div class="form-inline">
                    <label class="homeworkLabel">
                        文件上传：
                    </label>
                    <div class="col-md-7 homeworkLabel">
                        <input name="uploadFile" type="file" id="uploadFile" class="file-loading" />
                    </div>
                    <a id="downloadFile"
                       href="${pageContext.request.contextPath}/file/homework/download?fileName=${homeworkScore.fileName}&courseId=${courseId}&homeworkId=${homeworkId}&userId=${user.id}">
                        已提交文件：${homeworkScore.fileName}
                    </a>
                </div>
                <p class="help-block">支持doc、docx、xls、xlsx、ppt、pptx、txt、rar、zip格式，
                    大小不超过10M，不符合的将红色提醒</p>
                <div id="submitThis">
                    <button id="submitButton" type="button" class="btn btn-success btn-lg">提交作业</button>
                </div>
                <input id="pageContext" type="hidden" value="${pageContext.request.contextPath}" />
                <input id="courseId" type="hidden" value="${courseId}" />
                <input id="homeworkId" type="hidden" value="${homeworkId}" />
                <input id="userId" type="hidden" value="${user.id}" />
                <input id="deadline" type="hidden" value="${deadline}" />
                <input id="fileName" type="hidden" value="${homeworkScore.fileName}" />
            </div>
        </div>

    </div>
</div>
<%@include file="../common/mainFooter.jspf" %>
<script>
    $("#studentHomeworkPage").attr("class","nav-link active");
</script>
</body>
</html>

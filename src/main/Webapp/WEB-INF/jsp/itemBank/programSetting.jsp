<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>作业管理系统</title>
    <%@include file="../common/mainHeader.jspf" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-select.min.css">
    <script src="${pageContext.request.contextPath}/js/bootstrap/bootstrap-select.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/homework/assign.js"></script>
</head>
<body>
<div class="page-wrapper">
    <%@include file="../common/bodyHeader.jspf" %>

    <div class="main-container">
        <%@include file="../common/bodySidebar.jspf" %>
        <div class="container">
            <div class="programSet">
                <div>
                    <label>当前题目现有的输入输出</label>
                    <button style="margin-left: 680px" class="btn btn-danger" type="button"
                            onclick="DeleteTestData();">
                        删除当前题目输入输出
                    </button>
                </div>
                <div style="margin-top: 10px">
                    <textarea class="form-control" id="inputOutputText"
                              rows="12" cols="100" readonly>${inputOutput}</textarea>
                </div>
                <form method="POST" action="${pageContext.request.contextPath}/itemBank/testData/save"
                      onsubmit="return checkInputOutput();" style="margin-left: 100px">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div style="margin-left: 120px; margin-top: 20px">
                        <label>选择题库:</label>
                        <select id="itemBankSelect" name="itemBankId"
                                class="selectpicker show-tick form-control col-sm-2"
                                data-width="98%" data-first-option="false"
                                title="请选择" data-live-search="true">
                            <c:forEach items="${itemBankList}" var="itemBank">
                                <option value="${itemBank.id}">
                                        ${itemBank.itemName}
                                </option>
                            </c:forEach>
                        </select>
                        <label style="margin-left: 80px">选择题目:</label>
                        <select id="questionSelect" name="questionId"
                                class="selectpicker show-tick form-control col-sm-2"
                                data-width="98%" data-first-option="false"  title="请选择"
                                data-live-search="true">
                        </select>
                    </div>
                    <div class="inout">
                        <label style="margin-top: 15px">一组输入</label>
                    </div>
                    <textarea class="inout" id="oneInput" rows="4" cols="100" name="input"></textarea>
                    <div class="inout">
                        <label>一组输出</label>
                    </div>
                    <textarea class="inout" id="oneOutput" rows="4" cols="100" name="output"></textarea>
                    <div style="margin-left: 268px; margin-top: 10px">
                        <label>选择语言</label>
                        <select id="languageSelect" name="languageMarkId"
                                class="selectpicker show-tick form-control col-sm-2"
                                data-width="98%" data-first-option="false"
                                title="请选择" data-live-search="true">
                        <c:forEach items="${languageMarkList}" var="languageMark">
                            <option value="${languageMark.id}">
                                    ${languageMark.mark}
                            </option>
                        </c:forEach>
                        </select>
                    </div>
                    <div class="submitInout">
                        <button type="submit" value="提交" class="btn btn-success btn-lg">提交</button>
                    </div>
                    <input id="error" type="hidden" value="${error}" />
                    <input id="questionId" type="hidden" value="${questionId}" />
                    <input id="itemBankId" type="hidden" value="${itemBankId}" />
                    <input id="languageMarkId" type="hidden" value="${languageMarkId}" />
                </form>
            </div>
        </div>
    </div>
</div>
<%@include file="../common/mainFooter.jspf" %>
<script src="${pageContext.request.contextPath}/js/itemBank/programSet.js"></script>
</body>
</html>
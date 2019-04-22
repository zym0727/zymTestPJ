$("#programHomeworkSetting").attr("class", "nav-link active");
var errorVal = $("#error").val();
if (errorVal !== "" && errorVal === "error")
    alert("提交失败");

var itemBankIdVal = $("#itemBankId").val();
if (itemBankIdVal !== "")
    $("#itemBankSelect").selectpicker("val", itemBankIdVal);
var questionIdVal = $("#questionId").val();
if (questionIdVal !== "" && itemBankIdVal !== ""){
    $("#questionSelect").find("option").remove();
    $.ajax({
        url: "/homework/teacher/questionList/" + itemBankIdVal,
        method: "get",
        dataType: "json",
        success: function (data) {
            $.each(data, function (i, item) {
                var tempId = '<option  value="' + item.id + '">' + item.questionName + '</option>';
                $("#questionSelect").append(tempId);
            });
            var questionSelect =$("#questionSelect");
            questionSelect.selectpicker('refresh');
            questionSelect.selectpicker("val", questionIdVal);
        },
        error: function () {
            alert("题目加载失败");
        }
    });
}
$(function () {
    $("#questionSelect").change(function () {
        var questionId = $("#questionSelect").val();
        $("#questionId").val(questionId);
        $.ajax({
            url: "/itemBank/testData/getAll/" + questionId,
            method: "get",
            dataType: "json",
            success: function (data) {
                if (data !== null && data.testData !== "null")
                    $("#inputOutputText").val(data.testData);
                else
                    $("#inputOutputText").val("");
            }
        })
    })
});
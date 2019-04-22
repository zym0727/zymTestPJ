$(function () {
    if ($("#fileName").val() !== "") {
        $("#fileSubmit").show();
        $("#hiddenTwo").show();
    } else
        $("#hiddenOne").show();
    $("#markButton").click(function () {
        var score = $("#scoreMark").val();
        var commit = $("#commitText").val();
        var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");
        if (score === "") {
            alert("成绩没有输入哦");
            return;
        }
        $.ajax({
            url: "/homework/teacher/mark",
            method: "post",
            dataType: "json",
            data: {
                id: $("#homeworkScoreId").val(),
                score: score,
                evaluate: commit
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                if (data === "success") {
                    alert("提交批改成功");
                    window.history.go(-1);
                }
            },
            error: function () {
                alert("提交批改失败");
            }
        })
    })
});

function showScoreMarkInputSuggestion() {
    var scoreMarkError = document.getElementById("scoreMarkError");
    if (isNullOrEmpty("scoreMark") === true) scoreMarkError.innerHTML = "成绩为空";
    else if ($("#scoreMark").val() === "100")
        scoreMarkError.innerHTML = "";
    else if (checkInputPattern("^[0-9]{1,2}$", "scoreMark") === false)
        scoreMarkError.innerHTML = "成绩格式有误，1-3数字";
    else
        scoreMarkError.innerHTML = "";
}
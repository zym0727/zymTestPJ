$("#teacherSeeHomeworkPage").attr("class", "nav-link active");

$.ajax({
    url: "/homework/teacher/AnswerList/get/" + $("#homeworkScoreId").val(),
    method: "get",
    dataType: "json",
    success: function (data) {
        if (data.length !== 0) {
            var i = 0;
            var questionNumber = $("#questionNumber").val();
            for (i; i < questionNumber; i++)
                $("#homeworkSubmit").append("<label class=\"homeworkLabel\">" + "第" + (i + 1) + "题：" + "</label>\n" +
                    "<textarea id=\"homeworkSubmit" + i + "\" class=\"form-control\"   rows=\"10\" readonly>" + data[i] + "</textarea>"
                );
        }
    },
    error: function () {
        alert("作业加载失败");
    }
});

$(function () {
    if ($("#fileName").val() !== "") {
        $("#fileSubmit").show();
        $("#hiddenTwo").show();
    } else if ($("#answer").val() === "null"){
        $("#hiddenTwo").show();
        $("#kong").show();
    }
    else
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

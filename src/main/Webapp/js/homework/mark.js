$("#teacherSeeHomeworkPage").attr("class", "nav-link active");

$.ajax({
    url: "/homework/teacher/AnswerList/get/" + $("#homeworkScoreId").val(),
    method: "get",
    dataType: "json",
    success: function (data) {
        if (data.length !== 0 ) {
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

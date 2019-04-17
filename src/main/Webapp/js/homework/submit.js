$("#studentHomeworkPage").attr("class","nav-link active");
var questionNumber = ($("#questionNumber").val());
var i = 0;
var isSubmit = false;
var homeworkScoreId = $("#homeworkScoreId").val();
if (homeworkScoreId !== "") {
    $.ajax({
        url: "/homework/student/AnswerList/get/" + homeworkScoreId,
        method: "get",
        dataType: "json",
        success: function (data) {
            console.log(data);
            if (data !== null && isSubmit !== true) {
                for (i = 0; i < questionNumber; i++)
                    $("#homeworkSubmit").append("<label class=\"homeworkLabel\">" + "第" + (i + 1) + "题：" + "</label>\n" +
                        "<textarea id=\"homeworkSubmit" + i + "\" class=\"form-control\"   rows=\"10\" >" + data[i] + "</textarea>"
                    );
                isSubmit = true;
            }
        },
        error: function (data) {
            console.log(data);
        }
    });
} else if (questionNumber !== "" && isSubmit === false) {
    for (i = 0; i < questionNumber; i++)
        $("#homeworkSubmit").append("<label class=\"homeworkLabel\">" + "第" + (i + 1) + "题：" + "</label>\n" +
            "<textarea id=\"homeworkSubmit" + i + "\" class=\"form-control\"   rows=\"10\" >" + "</textarea>"
        );
}
$("#studentHomeworkPage").attr("class", "nav-link active");
var questionNumber = $("#questionNumber").val();
var i = 0;
var isSubmit = false;
var homeworkScoreId = $("#homeworkScoreId").val();
if (homeworkScoreId !== "") {
    $.ajax({
        url: "/homework/student/AnswerList/get/" + homeworkScoreId,
        method: "get",
        dataType: "json",
        success: function (data) {
            if (isSubmit !== true) {
                for (i = 0; i < questionNumber; i++)
                    $("#homeworkSubmit" + i).val(data[i]);
                isSubmit = true;
            }
        },
        error: function () {
            alert("作业加载失败");
        }
    });
}

$('textarea').each(function () {
    this.setAttribute('style', 'height:' + (this.scrollHeight) + 'px');
}).on('input', function () {
    this.style.height = 'auto'; this.style.height = (this.scrollHeight) + 'px';
});
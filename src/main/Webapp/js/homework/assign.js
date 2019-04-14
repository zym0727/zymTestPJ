function assignHomework() {
    var courseId = $("#selectCourse").val();
    if (courseId === "") {
        alert("请选择课程");
        return;
    }
    var assignTime = $("#datetimepicker_start").val();
    if (assignTime === "") {
        alert("请选择发布时间！");
        return;
    }
    if (assignTime < curDateTime()) {
        alert("发布时间已经小于当前时间了！");
        return;
    }
    var deadline = $("#datetimepicker_end").val();
    if (deadline === "") {
        alert("请选择截止时间！");
        return;
    }
    if (!(assignTime < deadline)) {
        alert("截止时间不能小于或等于发布时间！");
        return;
    }
    var questionIds = $("#questionSelect").val();
    if (questionIds.length === 0) {
        alert("请选择要发布的题目！");
        return;
    }
    var remark = $("#remarkText").val();
    var isAutomatic = $('input:radio[name="marking"]:checked').val();
    if (isAutomatic === undefined) {
        alert("请选择是否自动批改！");
        return;
    }
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    $.ajax({
        url: "/homework/teacher/assign",
        method: "post",
        data: {
            courseId: courseId,
            assignTime: new Date(assignTime),
            deadline: new Date(deadline),
            questionIds: questionIds.toString(),
            isAutomatic: isAutomatic,
            remark: remark
        },
        dataType: "json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if (data === "success")
                alert("发布成功！");
            else if (data === "time out")
                alert("发布时间已过了，请重新发布！")
        },
        error: function () {
            alert("发布失败，请联系管理员！")
        }
    });
}

$(function () {
    $("#itemBankSelect").change(function () {
        var id = $("#itemBankSelect").val();
        $("#questionSelect").find("option").remove();
        $.ajax({
            url: "/homework/teacher/questionList/" + id,
            method: "get",
            dataType: "json",
            success: function (data) {
                $.each(data, function (i, item) {
                    var tempId = '<option  value="' + item.id + '">' + item.questionName + '</option>';
                    $("#questionSelect").append(tempId);
                });
                $('#questionSelect').selectpicker('refresh');
            },
            error: function () {
                alert("题目加载失败");
            }
        });
    })
});
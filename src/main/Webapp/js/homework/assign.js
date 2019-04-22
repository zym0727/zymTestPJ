function assignHomework(isAssign) {
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
    if (isAssign === true)
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
    console.log(isAutomatic);
    if (isAutomatic === undefined) {
        alert("请选择是否自动批改！");
        return;
    }
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    if (isAssign === true) {
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
    } else {
        $.ajax({
            url: "/homework/teacher/update",
            method: "post",
            data: {
                id: updateHomeworkId,
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
                    alert("修改成功！");
                $("#updateModal").modal("hide");
                $('#homeworkTable').bootstrapTable("refresh");
            },
            error: function () {
                alert("修改失败，请联系管理员！")
            }
        });
    }
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

function checkInputOutput() {
    if ($("#questionSelect").val() === "") {
        alert("没有选择题目！");
        return false;
    }
    if ($("#oneInput").val() === "" && $("#oneOutput").val() === "") {
        alert("输入输出不能都为空！");
        return false;
    }
    return true;
}

function DeleteTestData() {
    var questionId = $("#questionId").val();
    if(questionId === ""){
        alert("当前并没有显示任何输入输出信息哦")
    } else {
        if(confirm("确认删除该题目下的输入输出数据？")){
            $.ajax({
                url: "/itemBank/testData/batchDelete/" + questionId,
                method: "get",
                dataType: "json",
                success: function (data) {
                    if (data === "success") {
                        alert("删除成功！");
                        $("#inputOutputText").val("");
                    } else {
                        alert("删除失败！")
                    }
                },
                error: function () {
                    alert("删除失败！");
                }
            })
        }
    }
}
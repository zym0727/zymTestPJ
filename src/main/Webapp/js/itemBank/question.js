function addQuestion() {
    $("#saveQuestionName").val("");
    $("#saveItemBankId").selectpicker('val', "");
    $("#saveQuestionNumber").val("");
    $("#saveQuestionDescription").val("");
    $("#saveAnswer").val("");
    $("#saveModal").modal("show");
}

function batchAddQuestion() {
    var fileObj = document.getElementById("questionFile").files[0]; // js 获取文件对象
    if (typeof (fileObj) == "undefined" || fileObj.size <= 0) {
        alert("请选择Excel文件");
        return;
    }
    var files = $("#questionFile").val();
    var ex = files.substring(files.indexOf('.') + 1);
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    if (ex === "xls" || ex === "xlsx") {
        var formFile = new FormData();
        formFile.append("uploadFile", fileObj); //加入文件对象
        $.ajax({
            url: "/file/teacher/question/upload",
            type: "Post",
            data: formFile,
            dataType: "json",
            cache: false,//上传文件无需缓存
            processData: false,//用于对data参数进行序列化处理 这里必须false
            contentType: false, //必须
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (result) {
                if (result === "success")
                    alert("批量导入成功！");
                else if (result === "repeat")
                    alert("有题目编号重复的数据，重复部分没有插入！");
                else if (result === "error")
                    alert("检查题库名称，语言标记，题目编号有没有写错的，这些写错的没有插入！");
                $('#questionTable').bootstrapTable("destroy");
                initTheTable();
            },
            error: function () {
                alert("批量导入失败,务必按照模板上的Excel文件来导入！")
            }
        })
    } else {
        alert("文件格式不符,后缀名为xls或xlsx！")
    }
    var obj = document.getElementById("questionFile");
    obj.outerHTML = obj.outerHTML;//删除原来文件
}

function questionClick() {
    $('#questionFile').click();
}

function batchDeleteQuestion() {
    //获取所有被选中的记录
    var rows = $("#questionTable").bootstrapTable('getSelections');
    if (rows.length === 0) {
        alert("请先选择要删除的记录!");
        return;
    }
    if (!confirm("确认批量删除吗？"))
        return;
    var ids = '';
    for (var i = 0; i < rows.length; i++) {
        ids += rows[i]['id'] + ",";
    }
    ids = ids.substring(0, ids.length - 1);
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    $.ajax({
        url: "/itemBank/question/batchDelete",
        method: "post",
        data: {ids: ids},
        dataType: "json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if (data === "success") {
                alert("批量删除成功！");
                $('#questionTable').bootstrapTable("refresh");
            }
        },
        error: function () {
            alert("批量删除失败！");
        }
    });
}
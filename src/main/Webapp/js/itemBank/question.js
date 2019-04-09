function addQuestion() {
    $("#saveQuestionName").val("");
    $("#saveQuestionDescription").val("");
    $("#saveModal").modal("show");
}

function batchAddQuestion() {

}

function batchDeleteQuestion() {
    //获取所有被选中的记录
    var rows = $("#questionTable").bootstrapTable('getSelections');
    if (rows.length === 0) {
        alert("请先选择要删除的记录!");
        return;
    }
    if(!confirm("确认批量删除吗？"))
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
        data:{ids:ids},
        dataType: "json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if (data === "success"){
                alert("批量删除成功！");
                $('#questionTable').bootstrapTable("refresh");
            }
        },
        error: function () {
            alert("批量删除失败！");
        }
    });
}
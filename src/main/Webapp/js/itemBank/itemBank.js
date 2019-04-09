function addItemBank() {
    $("#saveItemBankName").val("");
    $("#saveItemBankDescription").val("");
    $("#saveModal").modal("show");
} 

function batchAddItemBank() {
    
}

function batchDeleteItemBank() {
    //获取所有被选中的记录
    var rows = $("#itemTable").bootstrapTable('getSelections');
    if (rows.length === 0) {
        alert("请先选择要删除的记录!");
        return;
    }
    if(!confirm("确认批量删除吗，会把附属题目一起删掉！"))
        return;
    var ids = '';
    for (var i = 0; i < rows.length; i++) {
        ids += rows[i]['id'] + ",";
    }
    ids = ids.substring(0, ids.length - 1);
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    $.ajax({
        url: "/itemBank/batchDelete",
        method: "post",
        data:{ids:ids},
        dataType: "json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if (data === "success"){
                alert("批量删除成功！");
                $('#itemTable').bootstrapTable("refresh");
            }
        },
        error: function () {
            alert("批量删除失败！");
        }
    });
}
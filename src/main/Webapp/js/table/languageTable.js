$("#languageManage").attr("class","nav-link active");

$(function () {
    initTheTable();
});

var initTheTable = function () {
    var myTable = $('#languageTable');
    myTable.bootstrapTable({
        url: '/admin/languageTable/list',         //请求后台的URL（*）
        method: 'get',                      //请求方式（*）
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: false,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        onLoadError: function () {  //加载失败时执行
            return "加载失败";
        },
        queryParams: function () { // 请求服务器数据时发送的参数
            return {
                pageSize: this.pageSize,
                pageNumber: this.pageNumber
            }
        },
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 20, 30],        //可供选择的每页的行数（*）
        showColumns: true,                  //是否显示所有的列
        clickToSelect: true,                //是否启用点击选中行
        height: $(window).height() - 200,
        width: $(window).width(),
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        columns: [{
            checkbox: true
        }, {
            //field: 'id',
            title: '序号',
            align: 'center',
            width: 50,
            formatter: function (value, row, index) {
                var languageTable = $('#languageTable');
                //获取每页显示的数量
                var pageSize = languageTable.bootstrapTable('getOptions').pageSize;
                //获取当前是第几页
                var pageNumber = languageTable.bootstrapTable('getOptions').pageNumber;
                //返回序号，注意index是从0开始的，所以要加上1
                return pageSize * (pageNumber - 1) + index + 1;
            }
        }, {
            field: 'mark',
            title: '标记语言名称',
            align: 'center',
            width: 300
        }, {
            field: 'operations',
            title: '操作',
            align: 'center',
            width: 180,
            events: operateEvents,//给按钮注册事件
            formatter: addFunction//表格中增加按钮
        }]
    });
};


// 修改按钮、删除按钮
function addFunction() {
    return [
        '<button type="button" id="btn_edit" class="btn btn-success" data-toggle="modal" data-target="#ModalInfo">' +
        '修改</button>  ',
        '<button id="btn_delete" class="btn btn-warning" type="button">删除</button>'
    ].join('');
}

var updateLanguageId;

window.operateEvents = {
    // 点击修改按钮执行的方法
    'click #btn_edit': function (e, value, row, index) {
        $("#updateLanguage").val("");
        $.ajax({
            url: "/admin/languageMark/get/" + row.id,
            method: "get",
            dataType: "json",
            success: function (data) {
                $("#updateLanguage").val(data.mark);
                updateLanguageId = row.id;
                $("#updateModal").modal("show");
            },
            error: function () {
                alert("出错了,请联系管理员");
            }
        });
    },
    // 点击删除按钮执行的方法
    'click #btn_delete': function (e, value, row, index) {
        if (confirm("你确定要删除这个语言标记吗，请务必先把相关的题目关联清除哦")) {
            $.ajax({
                url: "/admin/languageMark/delete/" + row.id,
                method: "get",
                dataType: "json",
                success: function (data) {
                    if (data === "success") {
                        alert("删除成功！")
                    }
                    $('#languageTable').bootstrapTable("refresh");
                },
                error: function () {
                    alert("删除失败！");
                }
            })
        }
    }
};

$("#updateConfirmBtn").click(function () {
    var mark = $("#updateLanguage").val();
    if (checkParam(mark) === null) {
        alert("语言标记还没写哦");
        return;
    }
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    $.ajax({
        url: "/admin/languageMark/update",
        method: "post",
        data: {
            id: updateLanguageId,
            mark: mark
        },
        dataType: "json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if (data === "success") {
                alert("修改成功！");
                $('#languageTable').bootstrapTable("refresh");
            } else if (data === "repeat")
                alert("重复了，语言标记不能有重复！");
        },
        error: function () {
            alert("修改失败！");
        }
    });
    $("#updateModal").modal("hide");
});

$("#saveConfirmBtn").click(function () {
    var mark = $("#saveLanguage").val();
    if (checkParam(mark) === null) {
        alert("语言标记还没写哦");
        return;
    }
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    $.ajax({
        url: "/admin/languageMark/add",
        method: "post",
        data: {
            mark: mark
        },
        dataType: "json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if (data === "success") {
                alert("添加成功！");
                $('#languageTable').bootstrapTable("refresh");
            } else if (data === "repeat")
                alert("重复了，题库名字不能有重复！");
        },
        error: function () {
            alert("添加失败！");
        }
    });
    $("#saveModal").modal("hide");
});

$("#addLanguage").click(function () {
    $("#saveLanguage").val("");
    $("#saveModal").modal("show");
});

$("#batchDeleteLanguage").click(function () {
    //获取所有被选中的记录
    var rows = $("#languageTable").bootstrapTable('getSelections');
    if (rows.length === 0) {
        alert("请先选择要删除的记录!");
        return;
    }
    if (!confirm("确认批量删除吗,请务必先把相关的题目关联清除哦"))
        return;
    var ids = '';
    for (var i = 0; i < rows.length; i++) {
        ids += rows[i]['id'] + ",";
    }
    ids = ids.substring(0, ids.length - 1);
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    $.ajax({
        url: "/admin/languageMark/batchDelete",
        method: "post",
        data: {ids: ids},
        dataType: "json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if (data === "success") {
                alert("批量删除成功！");
                $('#languageTable').bootstrapTable("refresh");
            }
        },
        error: function () {
            alert("批量删除失败！");
        }
    });
});
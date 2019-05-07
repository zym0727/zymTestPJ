$("#questionList").attr("class", "nav-link active");

$("#itemBankSelect").val("");

$("#questionSelect").val("");

$(function () {
    initTheTable();
    $("#questionQueryBtn").bind("click", function () {
        $('#questionTable').bootstrapTable("destroy");
        initTheTable();
    });

    $("#itemBankSelect").change(function () {
        var id = $("#itemBankSelect").val();
        $("#questionSelect").find("option").remove();
        $.ajax({
            url: "/homework/teacher/questionList/" + id,
            method: "get",
            dataType: "json",
            success: function (data) {
                $.each(data, function (i, item) {
                    var tempId = '<option  value="' + item.questionNumber + '">' + item.questionNumber + '</option>';
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

var initTheTable = function () {
    var myTable = $('#questionTable');
    myTable.bootstrapTable({
        url: '/itemBank/questionTable/list',         //请求后台的URL（*）
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
        queryParams: function () { // 请求服务器数据时发送的参数，可以在这里添加额外的查询参数，返回false则终止请求
            return {
                pageSize: this.pageSize,
                pageNumber: this.pageNumber,
                itemId: checkParam($("#itemBankSelect").val()), // 所属题库
                questionNumber: checkParam($("#questionSelect").val()), // 题目编号
                questionName: checkParam($("#questionName").val()) // 题目名字
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
                var questionTable = $('#questionTable');
                //获取每页显示的数量
                var pageSize = questionTable.bootstrapTable('getOptions').pageSize;
                //获取当前是第几页
                var pageNumber = questionTable.bootstrapTable('getOptions').pageNumber;
                //返回序号，注意index是从0开始的，所以要加上1
                return pageSize * (pageNumber - 1) + index + 1;
            }
        }, {
            field: 'questionNumber',
            title: '题目编号',
            align: 'center',
            width: 180
        }, {
            field: 'questionName',
            title: '题目名字',
            align: 'center',
            width: 400
        }, {
            field: 'itemName',
            title: '所属题库',
            align: 'center',
            width: 100
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
        '查看修改</button>  ',
        '<button id="btn_delete" class="btn btn-warning" type="button">删除</button>'
    ].join('');
}

var updateQuestionId;

window.operateEvents = {
    // 点击修改按钮执行的方法
    'click #btn_edit': function (e, value, row, index) {
        $("#updateQuestionName").val("");
        $("#updateItemBankId").selectpicker('val', "");
        $("#updateQuestionNumber").val("");
        $("#updateQuestionDescription").val("");
        $("#updateAnswer").val("");
        $.ajax({
            url: "/itemBank/question/get/" + row.id,
            method: "get",
            dataType: "json",
            success: function (data) {
                $("#updateQuestionName").val(data.questionName);
                $("#updateItemBankId").selectpicker('val', data.itemId);
                $("#updateQuestionNumber").val(data.questionNumber);
                $("#updateQuestionDescription").val(data.description);
                $("#updateAnswer").val(data.answer);
                updateQuestionId = row.id;
                $("#updateModal").modal("show");
            },
            error: function () {
                alert("出错了,请联系管理员");
            }
        });
    },
    // 点击删除按钮执行的方法
    'click #btn_delete': function (e, value, row, index) {
        if (confirm("你确定要删除这个题目吗？")) {
            $.ajax({
                url: "/itemBank/question/delete/" + row.id,
                method: "get",
                dataType: "json",
                success: function (data) {
                    if (data === "success") {
                        alert("删除成功！")
                    }
                    $('#questionTable').bootstrapTable("refresh");
                },
                error: function () {
                    alert("删除失败！");
                }
            })
        }
    }
};

$("#updateConfirmBtn").click(function () {
    var questionName = $("#updateQuestionName").val();
    if (questionName === "") {
        alert("题目名字还没写哦");
        return;
    }
    var itemBankId = $("#updateItemBankId").val();
    if (itemBankId === "") {
        alert("所属题库还没选哦");
        return;
    }
    var questionNumber = $("#updateQuestionNumber").val();
    if (questionNumber === "") {
        alert("题目编号还没写哦");
        return;
    }
    var description = $("#updateQuestionDescription").val();
    if (description === "") {
        alert("题目描述还没写哦");
        return;
    }
    var answer = $("#updateAnswer").val();

    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");

    $.ajax({
        url: "/itemBank/question/update",
        method: "post",
        data: {
            id: updateQuestionId,
            questionName: questionName,
            itemId: itemBankId,
            questionNumber: questionNumber,
            description: description,
            answer: answer
        },
        dataType: "json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if (data === "success") {
                alert("修改成功！");
                $('#questionTable').bootstrapTable("refresh");
            } else if (data === "repeat")
                alert("重复了，题目编号不能重复");
        },
        error: function () {
            alert("修改失败！");
        }
    });
    $("#updateModal").modal("hide");
});

$("#saveConfirmBtn").click(function () {
    var questionName = $("#saveQuestionName").val();
    if (questionName === "") {
        alert("题目名字还没写哦");
        return;
    }
    var itemBankId = $("#saveItemBankId").val();
    if (itemBankId === "") {
        alert("所属题库还没选哦");
        return;
    }
    var questionNumber = $("#saveQuestionNumber").val();
    if (questionNumber === "") {
        alert("题目编号还没写哦");
        return;
    }
    var description = $("#saveQuestionDescription").val();
    if (description === "") {
        alert("题目描述还没写哦");
        return;
    }
    var answer = $("#saveAnswer").val();
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    $.ajax({
        url: "/itemBank/question/add",
        method: "post",
        data: {
            questionName: questionName,
            itemId: itemBankId,
            questionNumber: questionNumber,
            description: description,
            answer: answer
        },
        dataType: "json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if (data === "success") {
                alert("添加成功！");
                $('#questionTable').bootstrapTable("refresh");
            } else if (data === "repeat")
                alert("重复了，题目编号不能重复");
        },
        error: function () {
            alert("添加失败！");
        }
    });
    $("#saveModal").modal("hide");
});
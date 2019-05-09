$("#homeworkManage").attr("class", "nav-link active");

$(function () {
    initTheTable("");
    $("#courseId").change(function () {
        $('#homeworkTable').bootstrapTable("destroy");
        initTheTable($("#courseId").val());
    });
    $("#batchDeleteHomework").click(function () {
        //获取所有被选中的记录
        var rows = $("#homeworkTable").bootstrapTable('getSelections');
        if (rows.length === 0) {
            alert("请先选择要删除的记录!");
            return;
        }
        if (!confirm("确认批量删除吗，所有学生提交关联的作业也将删除!！"))
            return;
        var ids = '';
        for (var i = 0; i < rows.length; i++) {
            ids += rows[i]['id'] + ",";
        }
        ids = ids.substring(0, ids.length - 1);
        var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");
        $.ajax({
            url: "/homework/teacher/batchDelete",
            method: "post",
            data: {ids: ids},
            dataType: "json",
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                if (data === "success") {
                    alert("批量删除成功！");
                    $('#homeworkTable').bootstrapTable("refresh");
                }
            },
            error: function () {
                alert("批量删除失败！");
            }
        });
    });
    $("#updateConfirmBtn").click(function () {
        assignHomework(false);
    })

});

var initTheTable = function (courseId) {
    $('#homeworkTable').bootstrapTable({
        url: '/homework/teacher/homeworkManageList',         //请求后台的URL（*）
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
                courseId: checkParam(courseId)
            }
        },
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 20, 30],        //可供选择的每页的行数（*）
        showColumns: true,                  //是否显示所有的列
        clickToSelect: true,                //是否启用点击选中行
        height: $(window).height() - 240,
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
                var homeworkTable = $('#homeworkTable');
                //获取每页显示的数量
                var pageSize = homeworkTable.bootstrapTable('getOptions').pageSize;
                //获取当前是第几页
                var pageNumber = homeworkTable.bootstrapTable('getOptions').pageNumber;
                //返回序号，注意index是从0开始的，所以要加上1
                return pageSize * (pageNumber - 1) + index + 1;
            }
        }, {
            field: 'title',
            title: '作业标题',
            align: 'center',
            width: 350
        }, {
            field: 'courseName',
            title: '课程',
            align: 'center',
            width: 150
        }, {
            field: 'assignTime',
            title: '发布时间',
            align: 'center',
            width: 150
        }, {
            field: 'deadline',
            title: '截止时间',
            align: 'center',
            width: 150
        }, {
            field: 'operations',
            title: '操作',
            align: 'center',
            width: 140,
            events: operateEvents,//给按钮注册事件
            formatter: addFunction//表格中增加按钮
        }]
    });
};

function addFunction() {
    return [
        '<button type="button" id="btn_edit" class="btn btn-success" data-toggle="modal" data-target="#ModalInfo">' +
        '修改</button>  ',
        '<button id="btn_delete" class="btn btn-warning" type="button">删除</button>'
    ].join('');
}

var updateHomeworkId;

window.operateEvents = {
    // 点击修改按钮执行的方法
    'click #btn_edit': function (e, value, row, index) {
        $("#homeworkTitle").val("");
        $("#selectCourse").selectpicker('val',"");
        $("#datetimepicker_start").val("");
        $("#datetimepicker_end").val("");
        $("#itemBankSelect").selectpicker('val',"");
        $("#questionSelect").selectpicker('val',"");
        $("#remarkText").val("");
        $('input:radio[name="marking"]:checked').attr("checked",false);
        $.ajax({
            url: "/homework/teacher/get/" + row.id,
            method: "get",
            dataType: "json",
            success: function (data) {
                $("#homeworkTitle").val(data.title);
                $('#selectCourse').selectpicker('val',data.courseId);
                $("#datetimepicker_start").val(data.assignTime);
                $("#datetimepicker_end").val(data.deadline);
                $("#remarkText").val(data.remark);
                $("input:radio[name='marking'][value='" + data.isAutomatic + "']").prop("checked", "checked");
                $.ajax({
                    url: "/itemBank/id/get",
                    method: "get",
                    dataType: "json",
                    data: {ids: data.questionIds},
                    success: function (itemBankId) {
                        var itemBankSelect = $("#itemBankSelect");
                        itemBankSelect.selectpicker('val',itemBankId);
                        var id = itemBankSelect.val();
                        var questionSelect = $("#questionSelect");
                        questionSelect.find("option").remove();
                        $.ajax({
                            url: "/homework/teacher/questionList/" + id,
                            method: "get",
                            dataType: "json",
                            success: function (questionData) {
                                $.each(questionData, function (i, item) {
                                    var tempId = '<option  value="' + item.id + '">' + item.questionName + '</option>';
                                    questionSelect.append(tempId);
                                });
                                questionSelect.selectpicker('refresh');
                                questionSelect.selectpicker('val',data.questionIds.split(","));
                            },
                            error: function () {
                                alert("作业加载失败");
                            }
                        });
                    }
                });
                updateHomeworkId = row.id;
                $("#updateModal").modal("show");
            },
            error: function () {
                alert("出错了,请联系管理员");
            }
        });
    },
    // 点击删除按钮执行的方法
    'click #btn_delete': function (e, value, row, index) {
        if (confirm("你确定要删除这个作业吗,所有学生提交关联的作业也将删除!")) {
            $.ajax({
                url: "/homework/teacher/delete/" + row.id,
                method: "get",
                dataType: "json",
                success: function (data) {
                    if (data === "success") {
                        alert("删除成功！")
                    }
                    $('#homeworkTable').bootstrapTable("refresh");
                },
                error: function () {
                    alert("删除失败！");
                }
            })
        }
    }
};
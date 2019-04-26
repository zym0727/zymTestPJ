$("#checkResult").attr("class", "nav-link active");

$("#selectCourse").val("");

$('#selectHomework').val("");

$('#selectClass').val("");

$(function () {
    initTheTable();
    $('#selectCourse').change(function () {
        $('#homeworkScoreTable').bootstrapTable("destroy");
        initTheTable();
        $.ajax({
            url: "/homework/teacher/homeworkList/" + $('#selectCourse').val(),
            method: "get",
            dataType: "json",
            success: function (data) {
                if (data !== null) {
                    var selectHomework = $("#selectHomework");
                    selectHomework.find("option").remove();
                    $.each(data, function (i, item) {
                        var tempId = '<option  value="' + item.id + '">' + item.title + '(课程：'
                            + item.courseName + ',发布时间:' + item.assignTime + '截止时间：'
                            + item.deadline + ')' + '</option>';
                        selectHomework.append(tempId);
                    });
                    selectHomework.selectpicker('refresh');
                }
            },
            error: function () {
                alert("作业加载失败");
            }
        });
    });

    $('#selectHomework').change(function () {
        $('#homeworkScoreTable').bootstrapTable("destroy");
        initTheTable();
        $.ajax({
            url: "/homework/teacher/majorClassList/" + $('#selectHomework').val(),
            method: "get",
            dataType: "json",
            success: function (data) {
                if (data !== null) {
                    var selectClass = $("#selectClass");
                    selectClass.find("option").remove();
                    $.each(data, function (i, item) {
                        var tempId = '<option  value="' + item.id + '">' + item.className + '</option>';
                        selectClass.append(tempId);
                    });
                    selectClass.selectpicker('refresh');
                }
            },
            error: function () {
                alert("班级加载失败");
            }
        });
    });

    $('#selectClass').change(function () {
        $('#homeworkScoreTable').bootstrapTable("destroy");
        initTheTable();
    });
});

var initTheTable = function () {
    $('#homeworkScoreTable').bootstrapTable({
        url: '/homework/teacher/homeworkSeeScore',         //请求后台的URL（*）
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
                courseId : checkParam($('#selectCourse').val()),
                homeworkId : checkParam($('#selectHomework').val()),
                classId : checkParam($('#selectClass').val())
            }
        },
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 20, 30],        //可供选择的每页的行数（*）
        showColumns: true,                  //是否显示所有的列
        clickToSelect: true,                //是否启用点击选中行
        height: $(window).height() - 280,
        width: $(window).width(),
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        columns: [ {
            //field: 'id',
            title: '序号',
            align: 'center',
            width: 50,
            formatter: function (value, row, index) {
                var homeworkScoreTable = $('#homeworkScoreTable');
                //获取每页显示的数量
                var pageSize = homeworkScoreTable.bootstrapTable('getOptions').pageSize;
                //获取当前是第几页
                var pageNumber = homeworkScoreTable.bootstrapTable('getOptions').pageNumber;
                //返回序号，注意index是从0开始的，所以要加上1
                return pageSize * (pageNumber - 1) + index + 1;
            }
        }, {
            field: 'title',
            title: '作业标题',
            align: 'center',
            width: 400,
            formatter: aFormatter //添加超链接的方法
        }, {
            field: 'courseName',
            title: '课程名称',
            align: 'center',
            width: 150
        }, {
            field: 'studentName',
            title: '学生',
            align: 'center',
            width: 100
        }, {
            field: 'score',
            title: '成绩',
            align: 'center',
            width: 100
        }, {
            field: 'operations',
            title: '操作',
            align: 'center',
            width: 150,
            events: operateEvents,//给按钮注册事件
            formatter: addFunction//表格中增加按钮
        }]
    });
};

function aFormatter(value, row, index) {
    return [
        '<a href="' + "/homework/teacher/studentHomework/" + row.id + '">' + value + '</a>'
    ].join("")
}

function addFunction() {
    return [
        '<button type="button" id="btn_edit" class="btn btn-success" data-toggle="modal" data-target="#ModalInfo">' +
        '成绩修改</button>  '
    ].join('');
}

var updateHomeworkScoreId;

window.operateEvents = {
    // 点击修改按钮执行的方法
    'click #btn_edit': function (e, value, row, index) {
        $("#scoreMark").val("");
        $("#commitText").val("");
        $.ajax({
            url: "/homework/teacher/homeworkScore/get/" + row.id,
            method: "get",
            dataType: "json",
            success: function (data) {
                $("#scoreMark").val(data.score);
                $("#commitText").val(data.evaluate);
                updateHomeworkScoreId = row.id;
                $("#updateModal").modal("show");
            },
            error:function () {
                alert("出错了,请联系管理员");
            }
        });
    }
};

$("#updateConfirmBtn").click(function () {
    var score = $("#scoreMark").val();
    var commit = $("#commitText").val();
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    if (score === "") {
        alert("成绩没有输入哦");
        return;
    }
    if (checkInputPattern("^[0-9]{1,2}$", "scoreMark") === false && score !== "100") {
        alert("成绩格式有误");
        return;
    }
    $.ajax({
        url: "/homework/teacher/mark",
        method: "post",
        dataType: "json",
        data: {
            id: updateHomeworkScoreId,
            score: score,
            evaluate: commit
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if (data === "success") {
                alert("提交批改成功");
                $('#homeworkScoreTable').bootstrapTable("refresh");
            }
        },
        error: function () {
            alert("提交批改失败");
        }
    });
    $("#updateModal").modal("hide");
});
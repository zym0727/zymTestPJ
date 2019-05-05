$("#homeworkMessage").attr("class", "nav-link active");

//这儿提前设置select的值，不然有个bug多次请求会显示该select的值不同，会出现""和1两者情况，文档加载则是null和""？？？
$("#selectCourse").val("");

var theNum = 1;

$(function () {
    getHomeworkNumber();

    initTheTable(1);

    $("#newReply").click(function () {
        theNum = 1;
        getHomeworkNumber();
        $('#messageTable').bootstrapTable("destroy");
        initTheTable(1)
    });

    $("#haveMessage").click(function () {
        theNum = 2;
        getHomeworkNumber();
        $('#messageTable').bootstrapTable("destroy");
        initTheTable(0)
    });

    $("#selectCourse").change(function () {
        getHomeworkNumber();
        $('#messageTable').bootstrapTable("refresh");
    });

    $("#messageButton").click(function () {
        $("#saveModal").modal("show");
    });

    $("#selectCourseModal").change(function () {
        $.ajax({
            url: "/homework/student/message/homework",
            method: "get",
            dataType: "json",
            data: {
                courseId: checkParam($("#selectCourseModal").val())
            },
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

    $("#saveConfirmBtn").click(function () {
        var homeworkId = $("#selectHomework").val();
        var message = $("#leaveMessage").val();
        if (homeworkId === "") {
            alert("作业没有选择！");
            return;
        }
        if (message === "") {
            alert("留言信息为空！");
            return;
        }
        var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");
        $.ajax({
            url: "/homework/student/message/save",
            method: "post",
            data: {
                homeworkId: homeworkId,
                message: message
            },
            dataType: "json",
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                if (data === "success") {
                    getHomeworkNumber();
                    $('#messageTable').bootstrapTable("refresh");
                    alert("留言成功！");
                } else if (data === "repeat")
                    alert("当前作业你已经有留言了！");
                else
                    alert("留言失败！");
            },
            error: function () {
                alert("留言失败！");
            }
        });
        $("#saveModal").modal("hide");
    });

    $("#sendButton").click(function () {
        var textVal = $("#sendMessage").val();
        if (textVal === '') {
            alert("输入框为空哦！");
            return;
        }
        if (homeworkIdParam === undefined || studentIdParam === undefined) {
            alert("出错了请稍后再试！");
            return;
        }

        var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");
        $.ajax({
            url: "/homework/student/reply/save",
            method: "post",
            data: {
                homeworkId: homeworkIdParam,
                studentId: studentIdParam,
                message: textVal
            },
            dataType: "json",
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                if (data !== null) {
                    var scroll = $("#messageSee");
                    scroll.append(student(data.userName, data.messageTime, textVal));
                    $("#sendMessage").val('');
                    scroll.scrollTop(scroll.outerHeight(true) * scroll.outerHeight(true));
                } else
                    alert("留言失败！");
            },
            error: function () {
                alert("留言失败！");
            }
        })
    })
});

function student(studentName, time, message) {
    return '<div class="activity-row activity-row1 form-inline clear answer">\n' +
        '   <div class="col-xs-2 activity-desc1"></div>\n' +
        '   <div class="col-xs-7 activity-img2">\n' +
        '       <div class="time messageLeft">\n' +
        '            <span>' + time + '</span>\n' +
        '       </div>\n' +
        '       <div class="activity-desc-sub1 answer messageLeft">\n' +
        '        <p>' + message + '</p>\n' +
        '       </div>\n' +
        '   </div>\n' +
        '   <div class="col-xs-3 activity-img imgLeft">\n' +
        '       <img src="/img/1.jpg"\n' +
        '           class="img-responsive" alt=""/>\n' +
        '       <span>' + studentName + '</span></div>\n' +
        '</div>';
}

function teacher(teacherName, time, message) {
    return '<div class="activity-row activity-row1 form-inline clear">\n' +
        '   <div class="col-xs-3 activity-img imgRight">\n' +
        '       <img src="/img/2.jpg"\n' +
        '           class="img-responsive" alt=""/>\n' +
        '       <span>' + teacherName + '</span></div>\n' +
        '   <div class="col-xs-5 activity-img1">\n' +
        '       <div class="time messageRight">\n' +
        '           <span>' + time + '</span>\n' +
        '       </div>\n' +
        '       <div class="activity-desc-sub messageRight">\n' +
        '           <p>' + message + '</p>\n' +
        '       </div>\n' +
        '   </div>\n' +
        '   <div class="col-xs-4 activity-desc1"></div>\n' +
        '</div>';
}

var initTheTable = function (status) {
    var myTable = $('#messageTable');
    myTable.bootstrapTable({
        url: '/homework/student/messageTable',         //请求后台的URL（*）
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
        formatNoMatches: function formatNoMatches() {
            if (status === 1)
                return '没有新回复的留言哦';
            else if (status === 0)
                return '你没有任何留言';
        },
        queryParams: function () { // 请求服务器数据时发送的参数，返回false则终止请求
            return {
                pageSize: this.pageSize,
                pageNumber: this.pageNumber,
                isNew: status,
                courseId: checkParam($("#selectCourse").val())
            }
        },
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 20, 30],        //可供选择的每页的行数（*）
        clickToSelect: true,                //是否启用点击选中行
        height: $(window).height() - 362,
        width: $(window).width(),
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        columns: [{
            //field: 'id',
            title: '序号',
            align: 'center',
            width: 50,
            formatter: function (value, row, index) {
                var messageTable = $('#messageTable');
                //获取每页显示的数量
                var pageSize = messageTable.bootstrapTable('getOptions').pageSize;
                //获取当前是第几页
                var pageNumber = messageTable.bootstrapTable('getOptions').pageNumber;
                //返回序号，注意index是从0开始的，所以要加上1
                return pageSize * (pageNumber - 1) + index + 1;
            }
        }, {
            field: 'title',
            title: '作业标题',
            align: 'center',
            width: 400
        }, {
            field: 'studentName',
            title: '发起人',
            align: 'center',
            width: 100
        }, {
            field: 'userName',
            title: '此消息发送者',
            align: 'center',
            width: 100
        }, {
            field: 'messageTime',
            title: '消息时间',
            align: 'center',
            width: 150
        }, {
            field: 'operations',
            title: '操作',
            align: 'center',
            width: 100,
            events: operateEvents,//给按钮注册事件
            formatter: addFunction//表格中增加按钮
        }]
    });
};

function addFunction() {
    return [
        '<button type="button" id="btn_query" class="btn btn-success" data-toggle="modal"' +
        ' data-target="#ModalInfo">' +
        '详细查看</button>  '
    ].join('');
}

var studentIdParam, homeworkIdParam;

window.operateEvents = {
    'click #btn_query': function (e, value, row, index) {
        studentIdParam = row.studentId;
        homeworkIdParam = row.homeworkId;
        var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");
        if (theNum === 1) {
            $.ajax({
                url: "/homework/student/message/update",
                method: "post",
                data: {
                    id: row.id,
                    isSee: 1
                },
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (data) {
                    if (data !== "success") {
                        alert("标记已读留言回复失败！");
                    }
                },
                error: function () {
                    alert("标记已读留言回复失败！");
                }
            })
        }
        var scroll = $("#messageSee");
        $.ajax({
            url: "/homework/student/message/all",
            method: "get",
            dataType: "json",
            data: {
                studentId: studentIdParam,
                homeworkId: homeworkIdParam
            },
            success: function (data) {
                if (data === null) {
                    alert("出错了,请联系管理员");
                    return;
                }
                scroll.empty();
                data.forEach(function (t) {
                    if (t.studentName === t.userName)
                        scroll.append(student(t.studentName, t.messageTime, t.message));
                    else
                        scroll.append(teacher(t.userName, t.messageTime, t.message));
                });
            },
            error: function () {
                alert("出错了,请联系管理员");
            }
        });
        $("#messageModal").modal("show");
    }
};

function getHomeworkNumber() {
    $.ajax({
        url: "/homework/student/messageReply/number",
        method: "get",
        dataType: "json",
        data: {
            courseId: checkParam($("#selectCourse").val())
        },
        success: function (data) {
            $("#newReplyNumber").text(data.paramOne);
            $("#haveMessageNumber").text(data.paramTwo);
        },
        error: function () {
            alert("出错了,请联系管理员");
        }
    });
    onNumberRemind('student', 'studentRemind');
}
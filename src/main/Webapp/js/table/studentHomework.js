$("#studentHomeworkPage").attr("class","nav-link active");

//这儿提前设置select的值，不然有个bug多次请求会显示该select的值不同，会出现""和1两者情况，文档加载则是null和""？？？
$("#selectCourse").val("");

$(function () {
    getHomeworkNumber();

    initTheTable(1);

    $("#unSubmit").click(function () {
        $('#studentHomeworkTable').bootstrapTable("destroy");
        initTheTable(1)
    });

    $("#haveSubmitted").click(function () {
        $('#studentHomeworkTable').bootstrapTable("destroy");
        initTheTable(2)
    });

    $("#unMark").click(function () {
        $('#studentHomeworkTable').bootstrapTable("destroy");
        initTheTable(3)
    });

    $("#haveMarked").click(function () {
        $('#studentHomeworkTable').bootstrapTable("destroy");
        initTheTable(4)
    });

    $("#selectCourse").change(function () {
        getHomeworkNumber();
        $('#studentHomeworkTable').bootstrapTable("refresh");
    })
});


var initTheTable = function (status) {
    var myTable = $('#studentHomeworkTable');
    myTable.bootstrapTable({
        url: '/homework/student/homeworkList',         //请求后台的URL（*）
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
                return '你已经没有未提交作业了！';
            else if (status === 2)
                return '你没有提交过作业！';
            else if (status === 3)
                return '你已经没有未被批改的作业了！';
            else if (status === 4)
                return '你没有被批改过作业！';
        },
        queryParams: function () { // 请求服务器数据时发送的参数，可以在这里添加额外的查询参数，返回false则终止请求
            return {
                pageSize: this.pageSize,
                pageNumber: this.pageNumber,
                status: status,
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
                var studentHomeworkTable = $('#studentHomeworkTable');
                //获取每页显示的数量
                var pageSize = studentHomeworkTable.bootstrapTable('getOptions').pageSize;
                //获取当前是第几页
                var pageNumber = studentHomeworkTable.bootstrapTable('getOptions').pageNumber;
                //返回序号，注意index是从0开始的，所以要加上1
                return pageSize * (pageNumber - 1) + index + 1;
            }
        }, {
            field: 'questionName',
            title: '作业所选题目名称',
            align: 'center',
            width: 450,
            formatter: aFormatter //添加超链接的方法
        }, {
            field: 'courseName',
            title: '课程名称',
            align: 'center',
            width: 150
        }, {
            field: 'assignTime',
            title: '发布时间',
            align: 'center',
            width: 100
        }, {
            field: 'deadline',
            title: '截止时间',
            align: 'center',
            width: 100
        }]
    });
};


function aFormatter(value, row, index) {
    return [
        '<a href="' + "/homework/student/submitHomework/" + row.id + '">' + value + '</a>'
    ].join("")
}

function getHomeworkNumber() {
    $.ajax({
        url: "/homework/student/count",
        method: "get",
        dataType: "json",
        data: {courseId: checkParam($("#selectCourse").val())},
        success: function (data) {
            $("#unSubmitNumber").text(data.paramOne);
            $("#submittedNumber").text(data.paramTwo);
            $("#unMarkNumber").text(data.paramThree);
            $("#markNumber").text(data.paramFour);
        },
        error: function () {
            alert("出错了,请联系管理员");
        }
    });
}
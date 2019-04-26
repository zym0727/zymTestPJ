$("#teacherSeeHomeworkPage").attr("class", "nav-link active");

//这儿提前设置select的值，不然有个bug多次请求会显示该select的值不同，会出现""和1两者情况，文档加载则是null和""？？？
$("#selectCourse").val("");

$(function () {
    getTeacherSeeHomeworkNumber();

    initTheTable(1);

    $("#unManual").click(function () {
        $('#teacherSeeHomeworkTable').bootstrapTable("destroy");
        initTheTable(1)
    });

    $("#haveManual").click(function () {
        $('#teacherSeeHomeworkTable').bootstrapTable("destroy");
        initTheTable(2)
    });

    $("#unAutomatic").click(function () {
        $('#teacherSeeHomeworkTable').bootstrapTable("destroy");
        initTheTable(3)
    });

    $("#haveAutomatic").click(function () {
        $('#teacherSeeHomeworkTable').bootstrapTable("destroy");
        initTheTable(4)
    });

    $("#selectCourse").change(function () {
        getTeacherSeeHomeworkNumber();
        $('#teacherSeeHomeworkTable').bootstrapTable("refresh");
    })
});

var initTheTable = function (status) {
    var myTable = $('#teacherSeeHomeworkTable');
    myTable.bootstrapTable({
        url: '/homework/teacher/homeworkList',         //请求后台的URL（*）
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
                return '没有未手动批改过的作业了！';
            else if (status === 2)
                return '没有手动批改过的作业！';
            else if (status === 3)
                return '没有未自动批改过的作业了！';
            else if (status === 4)
                return '没有自动批改过的作业！';
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
                var teacherSeeHomeworkTable = $('#teacherSeeHomeworkTable');
                //获取每页显示的数量
                var pageSize = teacherSeeHomeworkTable.bootstrapTable('getOptions').pageSize;
                //获取当前是第几页
                var pageNumber = teacherSeeHomeworkTable.bootstrapTable('getOptions').pageNumber;
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
            field: 'studentName',
            title: '学生姓名',
            align: 'center',
            width: 150,
            formatter: aFormatter //添加超链接的方法
        }, {
            field: 'courseName',
            title: '课程',
            align: 'center',
            width: 150
        }, {
            field: 'classTime',
            title: '上课时间',
            align: 'center',
            width: 150
        }]
    });
};

function aFormatter(value, row, index) {
    return [
        '<a href="' + "/homework/teacher/studentHomework/" + row.id + '">' + value + '</a>'
    ].join("")
}

function getTeacherSeeHomeworkNumber() {
    $.ajax({
        url: "/homework/teacher/count",
        method: "get",
        dataType: "json",
        data: {courseId: checkParam($("#selectCourse").val())},
        success: function (data) {
            $("#unManualNumber").text(data.paramOne);
            $("#haveManualNumber").text(data.paramTwo);
            $("#unAutomaticNumber").text(data.paramThree);
            $("#haveAutomaticNumber").text(data.paramFour);
        },
        error: function () {
            alert("出错了,请联系管理员");
        }
    });
}
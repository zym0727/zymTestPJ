$("#studentHomeworkScorePage").attr("class", "nav-link active");

$("#selectCourse").val("");

$(function () {
    initTheTable();
    $("#selectCourse").change(function () {
        $('#studentScoreTable').bootstrapTable("destroy");
        initTheTable();
    });
});

var initTheTable = function () {
    $('#studentScoreTable').bootstrapTable({
        url: '/homework/student/homeworkScoreList',         //请求后台的URL（*）
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
                courseId : checkParam($('#selectCourse').val())
            }
        },
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 20, 30],        //可供选择的每页的行数（*）
        showColumns: true,                  //是否显示所有的列
        clickToSelect: true,                //是否启用点击选中行
        height: $(window).height() - 270,
        width: $(window).width(),
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        columns: [ {
            //field: 'id',
            title: '序号',
            align: 'center',
            width: 50,
            formatter: function (value, row, index) {
                var studentScoreTable = $('#studentScoreTable');
                //获取每页显示的数量
                var pageSize = studentScoreTable.bootstrapTable('getOptions').pageSize;
                //获取当前是第几页
                var pageNumber = studentScoreTable.bootstrapTable('getOptions').pageNumber;
                //返回序号，注意index是从0开始的，所以要加上1
                return pageSize * (pageNumber - 1) + index + 1;
            }
        }, {
            field: 'title',
            title: '作业标题',
            align: 'center',
            width: 300
        }, {
            field: 'courseName',
            title: '课程名称',
            align: 'center',
            width: 150
        }, {
            field: 'teacherName',
            title: '任课教师',
            align: 'center',
            width: 100
        }, {
            field: 'score',
            title: '成绩',
            align: 'center',
            width: 100
        }, {
            field: 'evaluate',
            title: '评价',
            align: 'center',
            width: 150
        }]
    });
};
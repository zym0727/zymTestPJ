$("#courseManage").attr("class","nav-link active");

$("#selectTeacher").val("");

$("#selectClass").val("");

$(function () {
    initTheTable();

    $("#courseQueryBtn").bind("click", function () {
        $('#courseTable').bootstrapTable("destroy");
        initTheTable();
    });

    $("#batchAddClass").bind("click", function (){
        $("#courseFile").click();
    });

    $("#courseFile").change(function () {
        var fileObj = document.getElementById("courseFile").files[0]; // js 获取文件对象
        if (typeof (fileObj) == "undefined" || fileObj.size <= 0) {
            alert("请选择Excel文件");
            return;
        }
        var select = $("#classFile");
        var files = select.val();
        var ex = files.substring(files.indexOf('.') + 1);
        var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");
        if (ex === "xls" || ex === "xlsx") {
            var formFile = new FormData();
            formFile.append("uploadFile", fileObj); //加入文件对象
            $.ajax({
                url: "/file/admin/class/upload",
                type: "Post",
                data: formFile,
                dataType: "json",
                cache: false,//上传文件无需缓存
                processData: false,//用于对data参数进行序列化处理 这里必须false
                contentType: false, //必须
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (result) {
                    if (result === "success")
                        alert("批量导入成功！");
                    else if (result === "repeat")
                        alert("有课程编号重复的数据，重复部分没有插入！");
                    $('#courseTable').bootstrapTable("destroy");
                    initTheTable();
                },
                error: function () {
                    alert("批量导入失败,务必按照模板上的Excel文件来导入！")
                }
            })
        } else {
            alert("文件格式不符,后缀名为xls或xlsx！")
        }
        select.val("");//删除原来文件
    });
});

var initTheTable = function () {
    var myTable = $('#courseTable');
    myTable.bootstrapTable({
        url: '/admin/courseTable/list',         //请求后台的URL（*）
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
                pageNumber: this.pageNumber,
                courseNumber: checkParam($("#courseNumber").val()),
                courseName: checkParam($("#courseName").val()),
                teacherId: checkParam($("#selectTeacher").val()),
                classIds: checkParam($("#selectClass").val()),
                classTime: checkParam($("#classTime").val()),
                semester: checkParam($("#semester").val())
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
                var courseTable = $('#courseTable');
                //获取每页显示的数量
                var pageSize = courseTable.bootstrapTable('getOptions').pageSize;
                //获取当前是第几页
                var pageNumber = courseTable.bootstrapTable('getOptions').pageNumber;
                //返回序号，注意index是从0开始的，所以要加上1
                return pageSize * (pageNumber - 1) + index + 1;
            }
        }, {
            field: 'courseNumber',
            title: '课程编号',
            align: 'center',
            width: 100
        }, {
            field: 'courseName',
            title: '课程名称',
            align: 'center',
            width: 100
        }, {
            field: 'teacherName',
            title: '任课教师',
            align: 'center',
            width: 100
        }, {
            field: 'classIds',
            title: '上课班级',
            align: 'center',
            width: 200
        }, {
            field: 'classTime',
            title: '上课时间',
            align: 'center',
            width: 130
        }, {
            field: 'semester',
            title: '上课学期',
            align: 'center',
            width: 100
        }, {
            field: 'operations',
            title: '操作',
            align: 'center',
            width: 130,
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

var updateCourseId;

window.operateEvents = {
    // 点击修改按钮执行的方法
    'click #btn_edit': function (e, value, row, index) {
        $("#updateCourseNumber").val("");
        $("#updateCourseName").val("");
        $("#updateSelectTeacher").selectpicker('val',"");
        $("#updateSelectClass").selectpicker('val',"");
        $("#updateClassTime").val("");
        $("#updateSemester").val("");
        $("#updateCredit").val("");
        $.ajax({
            url: "/admin/course/get/" + row.id,
            method: "get",
            dataType: "json",
            success: function (data) {
                $("#updateCourseNumber").val(data.courseNumber);
                $("#updateCourseName").val(data.courseName);
                $("#updateSelectTeacher").selectpicker('val',data.teacherId);
                $("#updateSelectClass").selectpicker('val',data.classIds.split(","));
                $("#updateClassTime").val(data.classTime);
                $("#updateSemester").val(data.semester);
                $("#updateCredit").val(data.credit);
                updateCourseId = row.id;
                $("#updateModal").modal("show");
            },
            error: function () {
                alert("出错了,请联系管理员");
            }
        });
    },
    // 点击删除按钮执行的方法
    'click #btn_delete': function (e, value, row, index) {
        if (confirm("你确定要删除这个课程吗，请务必先把相关的关联清除哦")) {
            $.ajax({
                url: "/admin/course/delete/" + row.id,
                method: "get",
                dataType: "json",
                success: function (data) {
                    if (data === "success") {
                        alert("删除成功！")
                    }
                    $('#courseTable').bootstrapTable("refresh");
                },
                error: function () {
                    alert("删除失败！");
                }
            })
        }
    }
};

$("#updateConfirmBtn").click(function () {
    var courseNumber = $("#updateCourseNumber").val();
    if (checkParam(courseNumber) === null) {
        alert("课程编号还没写哦");
        return;
    }
    var courseName = $("#updateCourseName").val();
    if (checkParam(courseName) === null) {
        alert("课程名称还没写哦");
        return;
    }
    var teacherId = $("#updateSelectTeacher").val();
    if (checkParam(teacherId) === null) {
        alert("任课教师还没选哦");
        return;
    }
    var classIds = $("#updateSelectClass").val();
    if (checkParam(classIds) === null) {
        alert("上课班级还没选哦");
        return;
    }
    var classTime = $("#updateClassTime").val();
    if (checkParam(classTime) === null) {
        alert("上课时间还没写哦");
        return;
    }
    var semester = $("#updateSemester").val();
    if (checkParam(semester) === null) {
        alert("上课学期还没写哦");
        return;
    }
    var credit = $("#updateCredit").val();
    if (checkParam(credit) === null) {
        alert("学分还没写哦");
        return;
    }
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    $.ajax({
        url: "/admin/course/update",
        method: "post",
        data: {
            id: updateCourseId,
            courseNumber: courseNumber,
            courseName: courseName,
            teacherId: teacherId,
            classIds: classIds.toString(),
            classTime: classTime,
            semester: semester,
            credit: credit
        },
        dataType: "json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if (data === "success") {
                alert("修改成功！");
                $('#courseTable').bootstrapTable("refresh");
            } else if (data === "repeat")
                alert("重复了，课程编号不能有重复！");
        },
        error: function () {
            alert("修改失败！");
        }
    });
    $("#updateModal").modal("hide");
});

$("#saveConfirmBtn").click(function () {
    var courseNumber = $("#saveCourseNumber").val();
    if (checkParam(courseNumber) === null) {
        alert("课程编号还没写哦");
        return;
    }
    var courseName = $("#saveCourseName").val();
    if (checkParam(courseName) === null) {
        alert("课程名称还没写哦");
        return;
    }
    var teacherId = $("#saveSelectTeacher").val();
    if (checkParam(teacherId) === null) {
        alert("任课教师还没选哦");
        return;
    }
    var classIds = $("#saveSelectClass").val();
    if (checkParam(classIds) === null) {
        alert("上课班级还没选哦");
        return;
    }
    var classTime = $("#saveClassTime").val();
    if (checkParam(classTime) === null) {
        alert("上课时间还没写哦");
        return;
    }
    var semester = $("#saveSemester").val();
    if (checkParam(semester) === null) {
        alert("上课学期还没写哦");
        return;
    }
    var credit = $("#saveCredit").val();
    if (checkParam(credit) === null) {
        alert("学分还没写哦");
        return;
    }
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    $.ajax({
        url: "/admin/course/add",
        method: "post",
        data: {
            courseNumber: courseNumber,
            courseName: courseName,
            teacherId: teacherId,
            classIds: classIds.toString(),
            classTime: classTime,
            semester: semester,
            credit: credit
        },
        dataType: "json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if (data === "success") {
                alert("添加成功！");
                $('#courseTable').bootstrapTable("refresh");
            } else if (data === "repeat")
                alert("重复了，课程编号不能有重复！");
        },
        error: function () {
            alert("添加失败！");
        }
    });
    $("#saveModal").modal("hide");
});

$("#addCourse").click(function () {
    $("#saveCourseNumber").val("");
    $("#saveCourseName").val("");
    $("#saveSelectTeacher").selectpicker('val',"");
    $("#saveSelectClass").selectpicker('val',"");
    $("#saveClassTime").val("");
    $("#saveSemester").val("");
    $("#saveCredit").val("");
    $("#saveModal").modal("show");
});

$("#batchDeleteCourse").click(function () {
    //获取所有被选中的记录
    var rows = $("#courseTable").bootstrapTable('getSelections');
    if (rows.length === 0) {
        alert("请先选择要删除的记录!");
        return;
    }
    if (!confirm("你确定要删除这个课程吗，请务必先把相关的关联清除哦"))
        return;
    var ids = '';
    for (var i = 0; i < rows.length; i++) {
        ids += rows[i]['id'] + ",";
    }
    ids = ids.substring(0, ids.length - 1);
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    $.ajax({
        url: "/admin/course/batchDelete",
        method: "post",
        data: {ids: ids},
        dataType: "json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if (data === "success") {
                alert("批量删除成功！");
                $('#courseTable').bootstrapTable("refresh");
            }
        },
        error: function () {
            alert("批量删除失败！");
        }
    });
});
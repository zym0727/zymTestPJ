$("#userManage").attr("class", "nav-link active");

$("#selectRole").val("");

$("#selectClass").val("");

$("#selectSex").val("");

$(function () {
    initTheTable();

    $("#userQueryBtn").bind("click", function () {
        $('#userTable').bootstrapTable("destroy");
        initTheTable();
    });

    $("#batchAddUser").bind("click", function () {
        $("#userFile").click();
    });

    $("#userFile").change(function () {
        var fileObj = document.getElementById("userFile").files[0]; // js 获取文件对象
        if (typeof (fileObj) == "undefined" || fileObj.size <= 0) {
            alert("请选择Excel文件");
            return;
        }
        var select = $("#userFile");
        var files = select.val();
        var ex = files.substring(files.indexOf('.') + 1);
        var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");
        if (ex === "xls" || ex === "xlsx") {
            var formFile = new FormData();
            formFile.append("uploadFile", fileObj); //加入文件对象
            $.ajax({
                url: "/file/admin/user/upload",
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
                        alert("有用户账号重复的数据，重复部分没有插入！");
                    else if (result === "error")
                        alert("批量导入有些数据错误，其中用户角色或启用有错误,务必按照模板上的Excel文件来导入！")
                    $('#userTable').bootstrapTable("destroy");
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
    var myTable = $('#userTable');
    myTable.bootstrapTable({
        url: '/admin/userTable/list',         //请求后台的URL（*）
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
                account: checkParam($("#account").val()),
                userName: checkParam($("#userName").val()),
                userNumber: checkParam($("#userNumber").val()),
                roleId: checkParam($("#selectRole").val()),
                classId: checkParam($("#selectClass").val()),
                sex: checkParam($("#selectSex").val())
            }
        },
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 20, 30],        //可供选择的每页的行数（*）
        showColumns: true,                  //是否显示所有的列
        clickToSelect: true,                //是否启用点击选中行
        height: $(window).height() - 250,
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
                var userTable = $('#userTable');
                //获取每页显示的数量
                var pageSize = userTable.bootstrapTable('getOptions').pageSize;
                //获取当前是第几页
                var pageNumber = userTable.bootstrapTable('getOptions').pageNumber;
                //返回序号，注意index是从0开始的，所以要加上1
                return pageSize * (pageNumber - 1) + index + 1;
            }
        }, {
            field: 'account',
            title: '账号名称',
            align: 'center',
            width: 150
        }, {
            field: 'password',
            title: '密码',
            align: 'center',
            width: 150
        }, {
            field: 'userName',
            title: '用户名称',
            align: 'center',
            width: 100
        }, {
            field: 'userNumber',
            title: '用户编号',
            align: 'center',
            width: 150
        }, {
            field: 'sexDetail',
            title: '性别',
            align: 'center',
            width: 70
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

var updateUserId;

window.operateEvents = {
    // 点击修改按钮执行的方法
    'click #btn_edit': function (e, value, row, index) {
        $("#updateRole").selectpicker('val', "");
        $("#updateAccount").val("");
        $("#updatePassword").val("");
        $("#updateUserName").val("");
        $("#updateUserNumber").val("");
        $("#updateClass").selectpicker('val', "");
        $("#updateSex").selectpicker('val', "");
        $("#updateTelephone").val("");
        $("#updateEnabled").selectpicker('val', "");
        $.ajax({
            url: "/admin/user/get/" + row.id,
            method: "get",
            dataType: "json",
            success: function (data) {
                $("#updateRole").selectpicker('val', data.roleId);
                $("#updateAccount").val(data.account);
                $("#updatePassword").val(data.password);
                $("#updateUserName").val(data.userName);
                $("#updateUserNumber").val(data.userNumber);
                $("#updateClass").selectpicker('val', data.classId);
                $("#updateSex").selectpicker('val', data.sex);
                $("#updateTelephone").val(data.telephone);
                $("#updateEnabled").selectpicker('val', data.enabled);
                updateUserId = row.id;
                $("#updateModal").modal("show");
            },
            error: function () {
                alert("出错了,请联系管理员");
            }
        });
    },
    // 点击删除按钮执行的方法
    'click #btn_delete': function (e, value, row, index) {
        if (confirm("你确定要删除这个用户吗，请务必先把相关的关联清除哦")) {
            $.ajax({
                url: "/admin/user/delete/" + row.id,
                method: "get",
                dataType: "json",
                success: function (data) {
                    if (data === "success") {
                        alert("删除成功！")
                    }
                    $('#userTable').bootstrapTable("refresh");
                },
                error: function () {
                    alert("删除失败！");
                }
            })
        }
    }
};

$("#updateConfirmBtn").click(function () {
    var roleId = $("#updateRole").val();
    if (checkParam(roleId) === null) {
        alert("角色还没选哦");
        return;
    }
    var account = $("#updateAccount").val();
    if (checkParam(account) === null) {
        alert("账号称还没写哦");
        return;
    }
    var password = $("#updatePassword").val();
    if (checkParam(password) === null) {
        alert("密码还没写哦");
        return;
    }
    var enabled = $("#updateEnabled").val();
    if (checkParam(enabled) === null) {
        alert("是否启用还没选哦");
        return;
    }
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    $.ajax({
        url: "/admin/user/update",
        method: "post",
        data: {
            id: updateUserId,
            roleId: roleId,
            account: account,
            password: password,
            userName: checkParam($("#updateUserName").val()),
            userNumber: checkParam($("#updateUserNumber").val()),
            classId: checkParam($("#updateClass").val()),
            sex: checkParam($("#updateSex").val()),
            telephone: checkParam($("#updateTelephone").val()),
            enabled: enabled
        },
        dataType: "json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if (data === "success") {
                alert("修改成功！");
                $('#userTable').bootstrapTable("refresh");
            } else if (data === "repeat")
                alert("重复了，账号名称不能有重复！");
        },
        error: function () {
            alert("修改失败！");
        }
    });
    $("#updateModal").modal("hide");
});

$("#saveConfirmBtn").click(function () {
    var roleId = $("#saveRole").val();
    if (checkParam(roleId) === null) {
        alert("角色还没选哦");
        return;
    }
    var account = $("#saveAccount").val();
    if (checkParam(account) === null) {
        alert("账号称还没写哦");
        return;
    }
    var password = $("#savePassword").val();
    if (checkParam(password) === null) {
        alert("密码还没写哦");
        return;
    }
    var enabled = $("#saveEnabled").val();
    if (checkParam(enabled) === null) {
        alert("是否启用还没选哦");
        return;
    }
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    $.ajax({
        url: "/admin/user/add",
        method: "post",
        data: {
            roleId: roleId,
            account: account,
            password: password,
            userName: checkParam($("#saveUserName").val()),
            userNumber: checkParam($("#saveUserNumber").val()),
            classId: checkParam($("#saveClass").val()),
            sex: checkParam($("#saveSex").val()),
            telephone: checkParam($("#saveTelephone").val()),
            enabled: enabled
        },
        dataType: "json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if (data === "success") {
                alert("添加成功！");
                $('#userTable').bootstrapTable("refresh");
            } else if (data === "repeat")
                alert("重复了，账号名称不能有重复！");
        },
        error: function () {
            alert("添加失败！");
        }
    });
    $("#saveModal").modal("hide");
});

$("#addUser").click(function () {
    $("#saveRole").selectpicker('val', "");
    $("#saveAccount").val("");
    $("#savePassword").val("");
    $("#saveUserName").val("");
    $("#saveUserNumber").val("");
    $("#saveClass").selectpicker('val', "");
    $("#saveSex").selectpicker('val', "");
    $("#saveTelephone").val("");
    $("#saveEnabled").selectpicker('val', "");
    $("#saveModal").modal("show");
});

$("#batchDeleteUser").click(function () {
    //获取所有被选中的记录
    var rows = $("#userTable").bootstrapTable('getSelections');
    if (rows.length === 0) {
        alert("请先选择要删除的记录!");
        return;
    }
    if (!confirm("你确定要删除这个用户吗，请务必先把相关的关联清除哦"))
        return;
    var ids = '';
    for (var i = 0; i < rows.length; i++) {
        ids += rows[i]['id'] + ",";
    }
    ids = ids.substring(0, ids.length - 1);
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    $.ajax({
        url: "/admin/user/batchDelete",
        method: "post",
        data: {ids: ids},
        dataType: "json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if (data === "success") {
                alert("批量删除成功！");
                $('#userTable').bootstrapTable("refresh");
            }
        },
        error: function () {
            alert("批量删除失败！");
        }
    });
});
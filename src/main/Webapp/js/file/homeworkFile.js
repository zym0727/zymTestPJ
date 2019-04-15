﻿$(function () {
    //初始化fileinput${pageContext.request.contextPath}/fileupload/${user.id}/${courseId}/${homeworkId}
    var fileInput = new FileInput();
    fileInput.Init("uploadFile", $("#pageContext").val() + "/file/homework/upload");
    $(".kv-upload-progress").remove();
    $("#submitButton").bind("click", function () {
        if ($("#deadline").val() < nowDateTime()) {
            alert("作业提交的截止时间已经过了");
            return;
        }
        var homeworkSubmit = $("#homeworkSubmit");
        if (homeworkSubmit.val() !== "") {
            $.ajax({
                url: "/homework/student/submit",
                method: "post",
                data: {
                    studentId: $("#userId").val(),
                    homeworkId: $("#homeworkId").val(),
                    answer: homeworkSubmit.val()
                },
                dataType: "json",
                beforeSend: function (xhr) {
                    var header = $("meta[name='_csrf_header']").attr("content");
                    var token = $("meta[name='_csrf']").attr("content");
                    xhr.setRequestHeader(header, token);
                },
                success: function (data) {
                    if (data === "success"){
                        alert("提交成功！");
                        window.history.go(-1);
                    }
                },
                error: function () {
                    alert("提交失败，请再次提交或者联系管理员！")
                }
            });
        } else
            $("#uploadFile").fileinput("upload");
    });
    if ($("#fileName").val() !== ""){
        $("#downloadFile").show();
    }
});
//初始化fileinput
var FileInput = function () {
    var oFile = new Object();
    //初始化fileinput控件（第一次初始化）
    oFile.Init = function (ctrlName, uploadUrl) {
        var control = $('#' + ctrlName);
        //初始化上传控件的样式
        control.fileinput({
            language: 'zh', //设置语言
            uploadUrl: uploadUrl, //上传的地址
            allowedFileExtensions: ['doc', 'docx', 'xls', 'xlsx', 'ppt', 'pptx', 'txt', 'rar', 'zip'], //接收的文件后缀
            uploadAsync: false, //默认异步上传
            showPreview: false,//是否显示预览区域
            showUpload: false, //是否显示上传按钮
            showRemove: true, //显示移除按钮
            showCaption: true, //是否显示标题
            showCancel: false, //是否显示取消按钮
            dropZoneEnabled: false, //是否显示拖拽区域
            maxFileSize: 10240,//单位为kb，如果为0表示不限制文件大小
            //minFileCount: 0,
            maxFileCount: 1, //表示允许同时上传的最大文件个数
            enctype: 'multipart/form-data',
            browseClass: "btn btn-primary", //按钮样式: btn-default、btn-primary、btn-danger、btn-info、btn-warning
            ajaxSettings: {             //配置回调函数
                beforeSend: function (xhr) {
                    var header = $("meta[name='_csrf_header']").attr("content");
                    var token = $("meta[name='_csrf']").attr("content");
                    xhr.setRequestHeader(header, token);
                }
            },
            uploadExtraData: function () {   //额外参数的关键点
                return {
                    courseId: $("#courseId").val(),
                    homeworkId: $("#homeworkId").val(),
                    userId: $("#userId").val()
                };
            }
        });

        //文件同步上传完成之后发生的事件
        control.on("filebatchuploadsuccess", function (event, data) {
            console.log(data.response);
            if (data.response === "success"){
                alert("提交作业成功");
                window.history.go(-1);
            }
        });

        //文件同步上传失败之后发生的事件
        control.on("filebatchuploaderror", function () {
            alert("作业提交失败了，请检查自己上交的作业再次尝试或联系管理员！")
        });
    };
    return oFile; //这里必须返回oFile对象，否则FileInput组件初始化不成功
};
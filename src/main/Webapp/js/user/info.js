$("#userInformation").attr("class", "nav-link active");

var userId, password;

$.ajax({
    url: "/user/info",
    method: "get",
    dataType: "json",
    success: function (data) {
        if (data !== null) {
            $('#feName').val(data.userName);
            $('#feAccount').val(data.account);
            $('#feNumber').val(data.userNumber);
            if (data.sex === 1)
                $('#feSex').val("男");
            else
                $('#feSex').val("女");
            $('#feClass').val(data.className);
            $('#feTelephone').val(data.telephone);
            userId = data.id;
            password = data.password;
        } else
            alert("用户信息加载失败！");
    },
    error: function () {
        alert("用户信息加载失败！");
    }
});

$('#updateInfo').click(function () {
    var feAccount = $('#feAccount').val();
    var fePassword = $('#fePassword').val();
    var feNewPassword = $('#feNewPassword').val();
    var feConfirmPassword = $('#feConfirmPassword').val();
    if (feAccount === '') {
        alert("账号为空！");
        return;
    }
    if (!checkInputPattern("^[\u4e00-\u9fa5a-zA-Z0-9]{3,20}$", "feAccount")) {
        alert("账号格式不对！长度至少3并小于20，并是字母、数字或汉字");
        return;
    }
    if (fePassword !== '') {
        if (feNewPassword === '') {
            alert("新密码为空！");
            return;
        }
        if (feConfirmPassword === '') {
            alert("确认密码为空！");
            return;
        }
        if (feConfirmPassword !== feNewPassword) {
            alert("确认密码和新密码不一样！");
            return;
        }
        if (!checkInputPattern("^[a-zA-Z0-9]{6,20}$", "fePassword")) {
            alert("原密码格式错误！长度至少6并小于20，并是字母、数字或汉字");
            return;
        }
        if (!checkInputPattern("^[a-zA-Z0-9]{6,20}$", "feNewPassword")) {
            alert("新密码格式错误！长度至少6并小于20，并是字母、数字或汉字");
            return;
        }
        if (!checkInputPattern("^[a-zA-Z0-9]{6,20}$", "feConfirmPassword")) {
            alert("确认密码格式错误！长度至少6并小于20，并是字母、数字或汉字");
            return;
        }
        if (password !== fePassword) {
            alert("原密码错误！");
            return;
        }
    } else if (feNewPassword !== '' || feConfirmPassword !== '') {
        alert("原密码不能为空！");
        return;
    }
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    $.ajax({
        url: "/user/update",
        method: "post",
        dataType: "json",
        data: {
            id: userId,
            account: checkParam(feAccount),
            password: checkParam(feNewPassword)
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if (data === "success") {
                alert("修改成功");
                window.location.reload();
            }
        },
        error: function () {
            alert("修改失功");
        }
    })
});
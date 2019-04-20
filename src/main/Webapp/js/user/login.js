function isAccountValid() {
    return !(isNullOrEmpty("username") || !checkInputPattern("^[a-zA-Z0-9]{3,20}$", "username"));

}

function showAccountInputSuggestion() {
    var usernameError = document.getElementById("usernameError");
    if (isNullOrEmpty("username") === true) usernameError.innerHTML = "账号为空";
    else if (checkInputPattern("^[a-zA-Z0-9]{3,20}$", "username") === false) usernameError.innerHTML = "账号格式有误";
    else usernameError.innerHTML = "";
}

function isPasswordValid() {
    return !(isNullOrEmpty("password") || !checkInputPattern("^[a-zA-Z0-9]{6,20}$", "password"));
}

function showPasswordInputSuggestion() {
    var passwordError = document.getElementById("passwordError");
    if (isNullOrEmpty("password") === true) passwordError.innerHTML = "密码为空";
    else if (checkInputPattern("^[a-zA-Z0-9]{6,20}$", "password") === false) passwordError.innerHTML = "密码格式有误";
    else passwordError.innerHTML = "";
}

function isRepasswordValid() {
    return !(isNullOrEmpty("rePassword") || !checkIfPasswordIsSame());
}

function showRePasswordInputSuggestion() {
    var rePasswordError = document.getElementById("rePasswordError");
    if (isNullOrEmpty("repassword") === true) rePasswordError.innerHTML = "请确认密码";
    else if (checkIfPasswordIsSame() === false) rePasswordError.innerHTML = "密码不一致";
    else rePasswordError.innerHTML = "";
}

function showAllPasswordSuggestion() {
    showPasswordInputSuggestion();
    showRePasswordInputSuggestion()
}

function checkIfPasswordIsSame() {
    return document.getElementById("password").value === document.getElementById("rePasswordError").value;
}

function satisfySubmit() {
    var submit = document.getElementById("submit");
    if (isAccountValid() && isPasswordValid()) {
        submit.setAttribute("value", "登录");
        submit.setAttribute("class", "btn btn-success");
        submit.removeAttribute("disabled")
    } else {
        submit.setAttribute("value", "不能登录，检查账号和密码格式");
        submit.setAttribute("class", "btn btn-danger");
        submit.setAttribute("disabled", "");
    }
}

function authorize() {
    var account = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    var success = document.getElementById("success");
    $.ajax({
        url: "/user/login/test",
        method: "POST",
        data: {account: account, password: password},
        dataType: "json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if (data === "find") {
                success.setAttribute("class", "text-success");
                success.innerHTML = "账号和密码正确，正在尝试登陆中...";
                var click = document.getElementById("submit");
                click.setAttribute("type", "submit");
                click.removeAttribute("onclick");
                click.click();
            }
            else if (data === "empty") {
                success.setAttribute("class", "text-danger");
                success.innerHTML = "登录失败，账号或者密码错误";
            }
        },
        error: function () {
            success.setAttribute("class", "text-danger");
            success.innerHTML = "系统出现问题，请联系管理员";
        }
    });
}
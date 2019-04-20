$(function () {
    if ($("#fileName").val() !== "") {
        $("#fileSubmit").show();
        $("#hiddenTwo").show();
    }else
        $("#hiddenOne").show();
});

function showScoreMarkInputSuggestion() {
    var scoreMarkError = document.getElementById("scoreMarkError");
    if (isNullOrEmpty("scoreMark") === true) scoreMarkError.innerHTML = "成绩为空";
    else if (checkInputPattern("^[0-9]{1,3}$", "scoreMark") === false) scoreMarkError.innerHTML = "成绩格式有误";
    else scoreMarkError.innerHTML = "";
}
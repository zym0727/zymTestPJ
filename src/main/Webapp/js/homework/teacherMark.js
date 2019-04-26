function showScoreMarkInputSuggestion() {
    var scoreMarkError = document.getElementById("scoreMarkError");
    if (isNullOrEmpty("scoreMark") === true) scoreMarkError.innerHTML = "成绩为空";
    else if ($("#scoreMark").val() === "100")
        scoreMarkError.innerHTML = "";
    else if (checkInputPattern("^[0-9]{1,2}$", "scoreMark") === false)
        scoreMarkError.innerHTML = "成绩格式有误";
    else
        scoreMarkError.innerHTML = "";
}
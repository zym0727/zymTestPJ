$("#scoreChart").attr("class", "nav-link active");

$("#selectCourse").val("");

$('#selectHomework').val("");

$('#selectClass').val("");

function getPie(theData) {
    clearCanvas('#pie-chart');
    clearCanvas('#line-chart');
    addPie();
    var pieChart = $('#pie-chart');
    if (pieChart.length > 0) {
        new Chart(pieChart, {
            type: 'pie',
            data: {
                labels: ["60以下", "60-70", "70-80", "80-90", "90-100"],
                datasets: [{
                    label: 'Users',
                    data: theData,
                    backgroundColor: [
                        'rgba(244, 88, 70, 0.5)',
                        'rgba(33, 150, 243, 0.5)',
                        'rgba(42, 185, 127, 0.5)',
                        'rgba(156, 39, 176, 0.5)',
                        'rgba(253, 178, 68, 0.5)'
                    ],
                    borderColor: [
                        'rgba(244, 88, 70, 0.5)',
                        'rgba(33, 150, 243, 0.5)',
                        'rgba(42, 185, 127, 0.5)',
                        'rgba(156, 39, 176, 0.5)',
                        'rgba(253, 178, 68, 0.5)'
                    ],
                    borderWidth: 1
                }]
            }
        });
    }
}

function getLine(theData) {
    clearCanvas('#pie-chart');
    clearCanvas('#line-chart');
    addLine();
    var lineChart = $('#line-chart');
    if (lineChart.length > 0) {
        new Chart(lineChart, {
            type: 'line',
            data: {
                labels: ["60以下", "60-65", "65-70", "70-75", "75-80", "80-85", "85-90", "90-95", "95-100"],
                datasets: [{
                    label: 'Users',
                    data: theData,
                    backgroundColor: 'rgba(66, 165, 245, 0.5)',
                    borderColor: '#2196F3',
                    borderWidth: 1
                }]
            },
            options: {
                legend: {
                    display: false
                },
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }]
                }
            }
        });
    }
}

function addPie() {
    $('.card-body').append('<canvas id="pie-chart" width="98%" height="45"></canvas>');
}

function addLine() {
    $('.card-body').append('<canvas id="line-chart" width="98%" height="45"></canvas>');
}

function clearCanvas(select) {
    var c = $(select);
    if (c !== undefined || c !== "")
        c.remove();
}

$(function () {
    $('#selectCourse').change(function () {
        var courseId = $('#selectCourse').val();
        $.ajax({
            url: "/homework/teacher/homeworkList/" + courseId,
            method: "get",
            dataType: "json",
            success: function (data) {
                if (data !== null) {
                    var selectHomework = $("#selectHomework");
                    selectHomework.find("option").remove();
                    $.each(data, function (i, item) {
                        var tempId = '<option  value="' + item.id + '">' + item.title + '(课程：'
                            + item.courseName + ',发布时间:' + item.assignTime + '截止时间：'
                            + item.deadline + ')' + '</option>';
                        selectHomework.append(tempId);
                    });
                    selectHomework.selectpicker('refresh');
                }
            },
            error: function () {
                alert("作业加载失败");
            }
        });
        $.ajax({
            url: "/homework/teacher/courseScore/" + courseId,
            method: "get",
            dataType: "json",
            success: function (data) {
                var zero = 0;
                data.forEach(function (t) {
                    if (t === 0)
                        zero++;
                });
                if (zero !== 5) {
                    $('#countTitle').text("该课程下所有作业平均成绩情况");
                    getPie(data);
                } else{
                    $('#countTitle').text("当前课程没有布置作业或者没有对应作业成绩哦");
                    clearCanvas('#pie-chart');
                    clearCanvas('#line-chart');
                }

            },
            error: function () {
                alert("该课程下所有作业平均成绩情况加载失败");
            }
        });
    });

    $('#selectHomework').change(function () {
        var selectHomeworkVal = $('#selectHomework').val();
        $.ajax({
            url: "/homework/teacher/majorClassList/" + selectHomeworkVal,
            method: "get",
            dataType: "json",
            success: function (data) {
                if (data !== null) {
                    var selectClass = $("#selectClass");
                    selectClass.find("option").remove();
                    $.each(data, function (i, item) {
                        var tempId = '<option  value="' + item.id + '">' + item.className + '</option>';
                        selectClass.append(tempId);
                    });
                    selectClass.selectpicker('refresh');
                }
            },
            error: function () {
                alert("班级加载失败");
            }
        });
        $.ajax({
            url: "/homework/teacher/score",
            method: "get",
            dataType: "json",
            data:{
                courseId: checkParam($('#selectCourse').val()),
                homeworkId:checkParam(selectHomeworkVal),
                classId:checkParam($('#selectClass').val())
            },
            success: function (data) {
                var zero = 0;
                data.forEach(function (t) {
                    if (t === 0)
                        zero++;
                });
                if (zero !== 9) {
                    $('#countTitle').text("该课程此作业学生成绩分布情况");
                    getLine(data);
                } else{
                    $('#countTitle').text("当前课程作业没有对应学生作业成绩哦");
                    clearCanvas('#pie-chart');
                    clearCanvas('#line-chart');
                }

            },
            error: function () {
                alert("该课程此作业学生成绩分布加载失败");
            }
        });
    });

    $('#selectClass').change(function () {
        var selectHomeworkVal = $('#selectHomework').val();
        if (selectHomeworkVal === "") {
            alert("请选好对应的作业再选对应的班级");
        }
        $.ajax({
            url: "/homework/teacher/score",
            method: "get",
            dataType: "json",
            data:{
                courseId: checkParam($('#selectCourse').val()),
                homeworkId:checkParam(selectHomeworkVal),
                classId:checkParam($('#selectClass').val())
            },
            success: function (data) {
                var zero = 0;
                data.forEach(function (t) {
                    if (t === 0)
                        zero++;
                });
                if (zero !== 9) {
                    $('#countTitle').text("该课程此作业下当前班级的学生成绩分布情况");
                    getLine(data);
                } else{
                    $('#countTitle').text("当前课程作业没有当前班级的学生作业成绩哦");
                    clearCanvas('#pie-chart');
                    clearCanvas('#line-chart');
                }

            },
            error: function () {
                alert("该课程此作业下当前班级的学生成绩分布加载失败");
            }
        });
    });
});
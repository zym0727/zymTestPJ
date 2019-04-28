$("#scoreChart").attr("class", "nav-link active");

$("#selectCourse").val("");

$('#selectHomework').val("");

$('#selectClass').val("");

function getPie(theData) {
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
    // var barChart = $('#bar-chart');
    //  data: [34, 70, 35, 70, 100],
    // if (barChart.length > 0) {
    //     new Chart(barChart, {
    //         type: 'bar',
    //         data: {
    //             labels: ["Red", "Blue", "Cyan", "Green", "Purple", "Orange"],
    //             datasets: [{
    //                 label: '# of Votes',
    //                 data: [12, 19, 3, 5, 2, 3],
    //                 backgroundColor: [
    //                     'rgba(244, 88, 70, 0.5)',
    //                     'rgba(33, 150, 243, 0.5)',
    //                     'rgba(0, 188, 212, 0.5)',
    //                     'rgba(42, 185, 127, 0.5)',
    //                     'rgba(156, 39, 176, 0.5)',
    //                     'rgba(253, 178, 68, 0.5)'
    //                 ],
    //                 borderColor: [
    //                     '#F45846',
    //                     '#2196F3',
    //                     '#00BCD4',
    //                     '#2ab97f',
    //                     '#9C27B0',
    //                     '#fdb244'
    //                 ],
    //                 borderWidth: 1
    //             }]
    //         },
    //         options: {
    //             legend: {
    //                 display: false
    //             },
    //             scales: {
    //                 yAxes: [{
    //                     ticks: {
    //                         beginAtZero: true
    //                     }
    //                 }]
    //             }
    //         }
    //     });
    // }
}

function addPie() {
    $('.card-body').append('<canvas id="pie-chart" width="98%" height="45"></canvas>');
}

function clearPieCanvas() {
    var c = $('#pie-chart');
    if (c === undefined || c === "") {
        addPie();
    } else {
        c.remove();
        addPie();
    }
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
                    console.log(t);
                    if (t === 0)
                        zero++;
                });
                if (zero !== 5) {
                    clearPieCanvas();
                    $('#countTitle').text("该课程下所有作业平均成绩情况");
                    getPie(data);
                } else{
                    $('#countTitle').text("当前课程没有布置作业或者没有对应作业成绩哦");
                    clearPieCanvas();
                }

            },
            error: function () {
                alert("该课程下所有作业平均成绩情况加载失败");
            }
        });
    });

    $('#selectHomework').change(function () {
        $.ajax({
            url: "/homework/teacher/majorClassList/" + $('#selectHomework').val(),
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
    });

    $('#selectClass').change(function () {
        if ($('#selectHomework').val() === "") {
            alert("请选好对应的作业再选对应的班级");
        }
    });
});
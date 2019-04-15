$("#homeworkAssign").attr("class","nav-link active");

$('#datetimepicker_start').datetimepicker({
    format: 'yyyy-mm-dd hh:ii',// 设置时间年月日时分的格式
    language:  'zh-CN',//汉化
    weekStart: 0, // 设置默认第-列为周几 如：0 周日  1 周一
    todayBtn:  1, //如果此值为true 或 "linked"，则在日期时间选择器组件的底部显示一个 "Today" 按钮用以选择当前日期
    autoclose:true, //选择日期后自动关闭
    todayHighlight: true,//如果为true, 高亮当前日期。
    startView: 2,  //日期时间选择器打开之后首先显示的视图。默认值：2, 'month'
    minView: 0,  //日期时间选择器所能够提供的最精确的时间选择视图。默认值：0, 'hour'
    minuteStep:10, //此数值被当做步进值用于构建小时视图
    startDate:new Date() //开始时间
});

$('#datetimepicker_end').datetimepicker({
    format: 'yyyy-mm-dd hh:ii',// 设置时间年月日时分的格式
    language:  'zh-CN',//汉化
    weekStart: 0, // 设置默认第-列为周几 如：0 周日  1 周一
    autoclose:true, //选择日期后自动关闭
    todayHighlight: true,//如果为true, 高亮当前日期。
    startView: 2,  //日期时间选择器打开之后首先显示的视图。默认值：2, 'month'
    minView: 0,  //日期时间选择器所能够提供的最精确的时间选择视图。默认值：0, 'hour'
    minuteStep:10, //此数值被当做步进值用于构建小时视图
    startDate:new Date() //开始时间
});
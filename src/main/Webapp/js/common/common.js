$(function () {
    /**
     * Sidebar Dropdown
     */
    $('.nav-dropdown-toggle').on('click', function (e) {
        e.preventDefault();
        $(this).parent().toggleClass('open');
    });

    // open sub-menu when an item is active.
    $('ul.nav').find('a.active').parent().parent().parent().addClass('open');

    /**
     * Sidebar Toggle
     */
    $('.sidebar-toggle').on('click', function (e) {
        e.preventDefault();
        $('body').toggleClass('sidebar-hidden');
    });

    /**
     * Mobile Sidebar Toggle
     */
    $('.sidebar-mobile-toggle').on('click', function () {
        $('body').toggleClass('sidebar-mobile-show');
    });
});

function checkParam(param) {
    if ($.trim(param) === "")
        return null;
    else
        return param;
}

function curDateTime() {
    var d = new Date();
    var year = d.getFullYear();
    var month = d.getMonth() + 1;
    month = month < 10 ? "0" + month : month;
    var date = d.getDate();
    date = date < 10 ? "0" + date : date;
    var hour = d.getHours();
    hour = hour < 10 ? "0" + hour : hour;
    var minute = d.getMinutes();
    minute = minute < 10 ? "0" + minute : minute;
    return year + "-" + month + "-" + date + " " + hour + ":" + minute;
}

function nowDateTime() {
    var d = new Date();
    var second = d.getSeconds();
    second = second < 10 ? "0" + second : second;
    return curDateTime() + ":" + second;
}
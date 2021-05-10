$(document).ready(function () {

})

$(document).on("click", "#addCourseBtn", function () {
    let title = $("#title").val();
    let teacher = $("#teacher").val();
    let courseIntro = $("#courseIntro").text();
    let teacherIntro = $("#teacherIntro").text();
    if (title === "") {
        alert("课程名称不能为空！");
    }
    let data = {
        title: title,
        teacher: teacher,
        courseIntro: courseIntro,
        teacherIntro: teacherIntro
    }
    if (confirm("确认添加吗？") === false) {
        return;
    }
    $.ajax({
        url: 'http://localhost:8080/admin/addCourse',
        type: 'POST',
        headers : { "Content-Type" : "application/json;charset=utf-8" },
        data: JSON.stringify(data),
        xhrFields: {withCredentials: true},
        success: res => {
            alert("添加成功！");
            window.location.href = "course.html";
        },
        fail: res => {
            console.log("添加失败，未知错误");
        }
    })
})
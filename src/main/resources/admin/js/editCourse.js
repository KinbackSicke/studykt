let courseId = "";

$(document).ready(function (options) {
    courseId = window.localStorage.getItem("editCourseId");
    getCourseDetail();
})


function getCourseDetail() {
    if (courseId === "" || courseId == null) {
        alert("课程不存在！");
        window.location.href = "course.html";
    }
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/admin/getCourse?courseId=" + courseId,
        xhrFields: {withCredentials: true},
        success: function (data) {
            if (data.status === 200) {
                console.log(data.data);
                setInfo(data.data);
            } else {
                alert("失败，" + data.data.errMsg);
            }
        },
        error: function (data) {
            alert("失败");
        }
    })
}

function setInfo(course) {
    $("#courseId").val(course.id);
    $("#title").val(course.title);
    $("#teacher").val(course.teacherName);
    $("#studyCount").val(course.studyCount);
    $("#favorCount").val(course.favorCount);
    $("#teacherIntro").text(course.teacherIntroduction);
    $("#courseIntro").text(course.introduction);
}

$("#submitBtn").on("click", updateCourse);

function updateCourse() {
    let id = $("#courseId").val();
    let title = $("#title").val();
    let teacher = $("#teacher").val();
    let studyCount = $("#studyCount").val();
    let teacherIntro = $("#teacherIntro").text();
    let courseIntro = $("#courseIntro").text();
    if (title === "" || title == null) {
        alert("课程标题不能为空!")
    }
    let data = {
        id: id,
        title: title,
        teacherName: teacher,
        studyCount: studyCount,
        teacherIntroduction: teacherIntro,
        introduction: courseIntro
    };
    $.ajax({
        url: "http://localhost:8080/admin/updateCourse",
        type: "POST",
        headers: {"Content-Type": "application/json;charset=utf-8"},
        xhrFields: {withCredentials: true},
        data: JSON.stringify(data),
        success: function (data) {
            if (data.status === 200) {
                alert("修改成功！");
                window.location.href = "course.html";
            } else if (data.status === 501) {
                alert("用户未登录");
                window.location.href = "login.html";
            } else {
                alert(data.data.errMsg);
            }
        },
        error: function (data) {
            alert("获取用户信息失败");
        }
    })

}
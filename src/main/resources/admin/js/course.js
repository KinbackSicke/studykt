$(document).ready(function () {
    getCourses();
})

function getCourses() {
    $.ajax({
        url: "http://localhost:8080/admin/allCourses",
        type: "POST",
        xhrFields: {withCredentials: true},
        success: function (data) {
            if (data.status === 200) {
                // console.log(data.data);
                loadCoursesInfo(data.data);
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

function loadCoursesInfo(data) {
    if (data == null || data.length === 0) {
        return;
    }
    let table = $("#dataTable tbody");
    table.empty();
    for (let i = 0; i < data.length; i++) {
        let course = data[i];
        let id = course.id;
        let intro = '';
        if (course.introduction != null) {
            intro = (course.introduction.length <= 15 ? course.introduction : course.introduction.substr(0, 15) + "...")
        }
        let dom = '<tr>\n' +
            '                  <td>' + course.id + '</td>\n' +
            '                  <td>' + course.title + '</td>\n' +
            '                  <td>' + intro + '</td>\n' +
            '                  <td>' + course.teacherName + '</td>\n' +
            '                  <td>' + course.studyCount + '</td>\n' +
            '                  <td>' + course.favorCount + '</td>\n' +
            '                  <td>' + course.createDate + '</td>\n' +
            '                  <td><a class="btn btn-primary" data-id="' + id + '" name="editBtn" href="#">修改</a>\n' +
            '                  <a class="btn btn-warning" data-id="' + id + '" name="deleteBtn" href="#">删除</a></td>\n' +
            '                </tr>';
        table.append($(dom));
    }
    $("#dataTable").DataTable();
}

$(document).on("click", "[name='editBtn']", function (e) {
    let courseId = $(this).attr("data-id");
    window.localStorage.setItem("editCourseId", courseId);
    window.location.href = "editCourse.html?courseId=" + courseId;
})

$(document).on("click", "[name='deleteBtn']", function (e) {
    let courseId = $(this).attr("data-id");
    if (confirm("确认删除吗？") === false) {
        return;
    }
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/admin/deleteCourse?courseId=" + courseId,
        xhrFields: {withCredentials: true},
        success: function (data) {
            if (data.status === 200) {
                alert("删除成功！");
                getCourses();
            } else {
                alert("删除失败，" + data.data.errMsg);
            }
        },
        error: function (data) {
            alert("删除失败");
        }
    })
})
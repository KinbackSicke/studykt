$(document).ready(function () {
    getProblems();
})


function getProblems() {
    $.ajax({
        url: "http://localhost:8080/admin/allProblems",
        type: "POST",
        xhrFields: {withCredentials: true},
        success: function (data) {
            if (data.status === 200) {
                console.log(data.data);
                loadProblemInfo(data.data);
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

function loadProblemInfo(data) {
    if (data == null || data.length === 0) {
        return;
    }
    let table = $("#dataTable tbody");
    table.empty();
    for (let i = 0; i < data.length; i++) {
        let problem = data[i];
        let id = problem.id;
        let type = (problem.type === 0 ? "选择题" : "问答题");
        let content = "";
        if (problem.content != null) {
            content = (problem.content.length <= 40 ? problem.content : problem.content.substr(0, 40) + "...");
        }
        let dom = '<tr>\n' +
            '                  <td>' + problem.id + '</td>\n' +
            '                  <td>' + type + '</td>\n' +
            '                  <td>' + problem.category + '</td>\n' +
            '                  <td>' + problem.pIndex + '</td>\n' +
            '                  <td>' + content + '</td>\n' +
            '                  <td><a class="btn btn-primary" data-id="' + id + '" name="editProblemBtn" href="#">修改</a>\n' +
            '                  <a class="btn btn-warning" data-id="' + id + '" name="deleteProblemBtn" href="#">删除</a></td>\n' +
            '                </tr>';
        table.append($(dom));
    }
    $("#dataTable").DataTable();
}

$(document).on("click", "[name='editProblemBtn']", function (e) {
    let problemId = $(this).attr("data-id");
    window.localStorage.setItem("editProblemId", problemId);
    window.location.href = "editProblem.html?courseId=" + problemId;
})

$(document).on("click", "[name='deleteProblemBtn']", function (e) {
    let problemId = $(this).attr("data-id");
    if (confirm("确认删除吗？") === false) {
        return;
    }
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/admin/deleteProblem?problemId=" + problemId,
        xhrFields: {withCredentials: true},
        success: function (data) {
            if (data.status === 200) {
                alert("删除成功！");
                getProblems();
            } else {
                alert("删除失败，" + data.data.errMsg);
            }
        },
        error: function (data) {
            alert("删除失败");
        }
    })
})
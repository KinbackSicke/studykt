let problemId = "";
$(document).ready(function () {
    problemId = window.localStorage.getItem("editProblemId");
    getProblemInfo();
})


function getProblemInfo() {
    if (problemId === "" || problemId == null ) {
        alert("无法获取试题信息！");
        window.location.href = "problem.html";
    }
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/admin/getProblem?problemId=" + problemId,
        xhrFields: {withCredentials: true},
        success: function (data) {
            if (data.status === 200) {
                console.log(data.data);
                setProblemInfo(data.data);
            } else {
                alert("获取信息失败，" + data.data.errMsg);
            }
        },
        error: function () {
            alert("获取信息失败");
        }
    })
}

function setProblemInfo(data) {
    if (data == null) {
        return;
    }
    $("#problemId").val(data.id);
    $("#type").val(data.type);
    $("#category").val(data.category);
    $("#pIndex").val(data.pIndex);
    $("#anoIndex").val(data.anoIndex);
    $("#content").val(data.content);
    let options = data.options == null ? "" : data.options.join("|");
    $("#options").val(options);
    $("#analysis").val(data.analysis);
}

$("#submitBtn").on("click", updateProblem);

function updateProblem() {
    let id = $("#problemId").val();
    let type = $("#type").val();
    let category = $("#category").val();
    let pIndex = $("#pIndex").val();
    let anoIndex = $("#anoIndex").val();
    let content = $("#content").val();
    let options = $("#options").val().split("|");
    let analysis = $("#analysis").val();
    let data = {
        id: id,
        type: type,
        category: category,
        pIndex: pIndex,
        anoIndex: anoIndex,
        content: content,
        options: options,
        analysis: analysis,
    }
    $.ajax({
        url: "http://localhost:8080/admin/updateProblem",
        type: "POST",
        headers: { "Content-Type": "application/json;charset=utf-8" },
        xhrFields: { withCredentials: true },
        data: JSON.stringify(data),
        success: function (data) {
            if (data.status === 200) {
                alert("修改成功！");
                window.location.href = "problem.html";
            } else if (data.status === 501) {
                alert("用户未登录");
                window.location.href = "login.html";
            } else {
                alert(data.data.errMsg);
            }
        },
        error: function (data) {
            alert("更新失败");
        }
    })
}
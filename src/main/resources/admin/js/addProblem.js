$(document).ready(function () {
    $("#anoIndex").val(0);
})


$(document).on("click", "#addProblemBtn", addProblem);

function addProblem() {
    let type = $("#type").val();
    let category = $("#category").val();
    let pIndex = $("#pIndex").val();
    let anoIndex = $("#anoIndex").val();
    let content = $("#content").val();
    let options = $("#options").val().split("|");
    let analysis = $("#analysis").val();
    let data = {
        type: type,
        category: category,
        pIndex: pIndex,
        anoIndex: anoIndex,
        content: content,
        options: options,
        analysis: analysis,
    }
    $.ajax({
        url: "http://localhost:8080/admin/addProblem",
        type: "POST",
        headers: { "Content-Type": "application/json;charset=utf-8" },
        xhrFields: { withCredentials: true },
        data: JSON.stringify(data),
        success: function (data) {
            if (data.status === 200) {
                alert("添加成功！");
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
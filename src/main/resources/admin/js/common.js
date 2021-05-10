$(Document).ready(function () {
    loginCheck();
});

$("#logout").on("click", function () {
    let username = window.localStorage.getItem("admin");
    if (username === "" || username == null) {
        return false;
    }
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/adminLogin/logout?username=" + username,
        xhrFields: { withCredentials: true },
        success: function(data) {
            if (data.status === 200) {
                window.localStorage.removeItem("admin");
                window.localStorage.removeItem("uniqueToken");
                window.location.href = "login.html";
            } else {
                alert("退出登录失败，" + data.data.errMsg);
            }
        },
        error: function(data) {
            alert("退出登录失败");
        }
    });
    return false;
});

function loginCheck() {
    let admin = window.localStorage.getItem("admin");
    // console.log(admin);
    if (admin === "" || admin == null) {
        alert("请先登录！");
        window.location.href = "login.html";
        return false;
    }
}
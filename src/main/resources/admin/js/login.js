$("#loginBtn").on("click", function () {
    let username = $("#account").val();
    let password = $("#exampleInputPassword1").val();
    if (username == null || username === "") {
        alert("账号不能为空！");
        return false;
    }
    if (password == null || password === "") {
        alert("密码不能为空！");
        return false;
    }

    $.ajax({
        url: "http://localhost:8080/adminLogin/login",
        type: "POST",
        xhrFields: { withCredentials: true },
        data: {
            "username": username,
            "password": password
        },
        success: function(data) {
            // console.log(data);
            if (data.status === 200) {
                alert("登录成功!");
                window.localStorage.setItem("admin", data.data.username);
                window.localStorage.setItem("uniqueToken", data.data.uniqueToken);
                window.location.href = "index.html";
            } else {
                alert("登录失败，" + data.data.errMsg);
            }
        },
        error: function(data) {
            alert("登录失败");
        }
    });
    return false;
})
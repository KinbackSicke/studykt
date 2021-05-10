$(document).ready(function () {
    getUsers();
});

function getUsers() {
    $.ajax({
        url: "http://localhost:8080/admin/allUsers",
        type: "POST",
        xhrFields: {withCredentials: true},
        success: function (data) {
            if (data.status === 200) {
                // console.log(data.data);
                loadUsersInfo(data.data);
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
    });
    return false;
}

function loadUsersInfo(userList) {
    if (userList == null) {
        return;
    }
    let table = $("#dataTable tbody");
    table.empty();
    for (let i = 0; i < userList.length; i++) {
        let user = userList[i];
        let openid = user.openid;
        let gender = user.gender === 0 ? '女' : '男';
        let dom = '<tr>\n' +
            '                  <td>' + user.openid + '</td>\n' +
            '                  <td>' + user.nickName + '</td>\n' +
            '                  <td>' + gender + '</td>\n' +
            '                  <td>' + user.country + '</td>\n' +
            '                  <td>' + user.province + '</td>\n' +
            '                  <td>' + user.city + '</td>\n' +
            '                  <td><a class="btn btn-warning" name="deleteBtn" id="' + openid + '" href="#">删除</a></td>\n' +
            '                </tr>';
        table.append($(dom));
    }
    $("#dataTable").DataTable();
}

$(document).on("click", "[name='deleteBtn']", function (e) {
    console.log(e);
    let openid = e.target.id;
    if (confirm("确认删除吗？") === false) {
        return;
    }
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/admin/deleteUser?userId=" + openid,
        xhrFields: {withCredentials: true},
        success: function (data) {
            if (data.status === 200) {
                alert("删除成功！");
                getUsers();
            } else {
                alert("删除失败，" + data.data.errMsg);
            }
        },
        error: function (data) {
            alert("删除失败");
        }
    })
})




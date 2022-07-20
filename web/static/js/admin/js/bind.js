$('.ui.form').form({
    fields : {
        username : {
            identifier: 'username',
            rules: [{
                type : 'empty',
                prompt: '请输入用户名'
            }]
        },
        password : {
            identifier: 'password',
            rules: [{
                type : 'empty',
                prompt: '请输入密码'
            }]
        }
    }
});

$('#login').click(function () {
    var username=$("#username").val();
    var password=$("#password").val();
    $.ajax({
        type : "POST",
        data:{ "username": username, "password": password,"providerId":sessionStorage.getItem("providerId"),"providerUserId":sessionStorage.getItem("providerUserId"),"displayName":sessionStorage.getItem("displayName"),"imageUrl":sessionStorage.getItem("imageUrl")},
        url : sessionStorage.getItem("requestUrl")+"/blog-auth-server/oauth/bind",
        async: false,
        success : function (res) {
            //返回成功
            if (res.code==200){
                getAccessToken(sessionStorage.getItem("providerId"),sessionStorage.getItem("providerUserId"));
            }else{
                alert("注册/绑定异常，请重试！");
                $("#fail").attr("hidden",false);
            }
        },
        error: function(result) {
            alert("注册/绑定异常，请重试！");
            $("#fail").attr("hidden",false);
        }
    });
});

//拿着服务商的唯一id换本方系统的token
function getAccessToken(providerId,providerUserId) {
    $.ajax({
        type : "POST",
        data:{ "providerId": providerId, "providerUserId": providerUserId,"client_id":"mugu","client_secret":"123","grant_type":"social" },
        url : sessionStorage.getItem("requestUrl")+"/blog-auth-server/oauth/token",
        async: false,
        success : function (res) {
            //返回成功
            if (res.code==200){
                //跳转到后台首页
                sessionStorage.setItem("access_token",res.data.access_token);
                sessionStorage.setItem("refresh_token",res.data.refresh_token);
                sessionStorage.setItem("nickName",res.data.nick_name);
                sessionStorage.setItem("avatar",res.data.avatar);
                window.location.href="./index.html";
            }
            //code=1002 该用户未注册绑定
            else if(res.code==1002){
                //跳转到注册绑定页面
                window.location.href="./bind.html";
            }
            else{
                $("#fail").attr("hidden",false);
            }
        },
        error: function(result) {
            $("#fail").attr("hidden",false);
        }
    });
}


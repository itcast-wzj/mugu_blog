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

//请求的url
sessionStorage.setItem("requestUrl","http://127.0.0.1/api");

$('#login').click(function () {
    var username=$("#username").val();
    var password=$("#password").val();
    $.ajax({
        type : "POST",
        data:{ username: username, password: password,client_id:"mugu",client_secret:"123",grant_type:"password" },
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
                //表单跳转
                $("#form1").attr("action","./index.html");
            }else{
                $("#fail").attr("hidden",false);
            }
        },
        error: function(result) {
            $("#fail").attr("hidden",false);
        }
    });
});
// $('#newblog-container').load(/*[[@{/footer/newblog}]]*/"/footer/newblog");
var accessToken = sessionStorage.getItem("access_token");
var nickName = sessionStorage.getItem("nickName");
var avatar = sessionStorage.getItem("avatar");
//没有token，则直接跳转到首页
if (accessToken == null||sessionStorage.getItem("requestUrl")==null) {
    window.location = "./login.html";
}

var typeId;
function typeFuc(param){
    typeId=param;
}

//获取分类列表
function listTypes(pageNum,pageSize){
    var param={
        "pageNum": pageNum,
        "pageSize": pageSize
    };

    //请求分类接口，进行处理
    $.ajax({
        type: "POST",
        headers: {
            "Authorization":"Bearer "+accessToken
        },
        data: JSON.stringify(param),
        url: sessionStorage.getItem("requestUrl") + "/blog-article/admin/type/list",
        contentType:"application/json;charset=utf-8",
        async: true,
        success: function (res) {
            //返回成功
            if (res.code==200){
                var list=res.data.result;
                for (var i = 0; i <list.length ; i++) {
                    var str=list[i];
                    var html="<div class=\"item\" data-value="+str.id+" onclick=typeFuc("+str.id+")"+">"+str.name+"</div>";
                    $("#menu").append(html);
                }
            }else{
                alert(res.msg);
            }
        },
        error: function (result) {
            alert("网络超时！")
        }
    });
}

listTypes(1,100)



function next(pageNum,pageSize) {
    listArticles(pageNum,pageSize);
}

//获取文章列表
function listArticles(pageNum,pageSize){
    //请求分类接口，进行处理
    $.ajax({
        type: "POST",
        headers: {
            "Authorization":"Bearer "+accessToken
        },
        data: JSON.stringify({
            "pageNum": pageNum,
            "pageSize": pageSize,
            "title": $("#title").val(),
            "typeId": typeId
        }),
        url: sessionStorage.getItem("requestUrl") + "/blog-article/admin/article/list",
        contentType:"application/json;charset=utf-8",
        async: true,
        success: function (res) {
            //返回成功
            if (res.code==200){
                var list=res.data.result;
                var total=res.data.total;
                $("#article").text("");
                $("#next").text("");
                for (var i = 0; i <list.length ; i++) {
                    var str=list[i];
                    var html="<tr align=\"center\">\n" +
                        "            <td>"+str.id+"</td>\n" +
                        "            <td>"+str.title+"</td>\n" +
                        "            <td>"+str.typeName+"</td>\n" +
                        "            <td>"+(str.isRecommend==1?"是":"否")+"</td>\n" +
                        "            <td>"+(str.status==1?"草稿":"已发布")+"</td>\n" +
                        "            <td>"+str.updateTime+"</td>\n" +
                        "            <td>\n" +
                        "              <!--th:href=\"@{/admin/types/{id}/input(id=${type.id})}\"-->\n" +
                        "              <a href='./blogs-input.html?id="+'"'+str.articleId+'"'+"' class=\"ui mini teal basic button\">编辑</a>\n" +
                        "              <a href=\"#\" onclick=delById("+'"'+str.articleId+'"'+") class=\"ui mini red basic button\">删除</a>\n" +
                        "            </td>\n" +
                        "          </tr>";
                    $("#article").append(html);
                }

                var next="<tr>\n" +
                    "            <th colspan=\"7\">\n" +
                    "              <div class=\"ui inverted divided stackable grid\">\n" +
                    "                <div class=\"three wide column\" align=\"center\">\n" +
                    "                  <a class=\"item\" onclick='next("+(pageNum-1<=0?1:pageNum-1)+","+(pageSize)+")'>上一页</a>\n" +
                    "                </div>\n" +
                    "\n" +
                    "                <div class=\"ten wide column\" align=\"center\">\n" +
                    "                  <p>第 <span id=\"page\">"+pageNum+"</span> 页，共 <span id=\"pageSzie\">"+res.data.pages+"</span> 页，有 <span id=\"total\">"+res.data.total+"</span> 篇文章</p>\n" +
                    "                </div>\n" +
                    "\n" +
                    "                <div class=\"three wide column\" align=\"center\">\n" +
                    "                  <a class=\"item\" onclick='next("+(pageNum+1>total?total:pageNum+1)+","+(pageSize)+")'>下一页</a>\n" +
                    "                </div>\n" +
                    "              </div>\n" +
                    "              <div align=\"center\">\n" +
                    "                <a href=\"#\">\n" +
                    "                  <button type=\"button\" class=\"ui teal button m-mobile-wide m-margin-top\" onclick=\"window.location='./blogs-input.html'\"><i class=\"pencil icon\"></i>新增</button>\n" +
                    "                </a>\n" +
                    "              </div>\n" +
                    "            </th>\n" +
                    "          </tr>";
                $("#next").append(next);

            }else{
                alert(res.msg);
            }
        },
        error: function (result) {
            alert("文章列表加载失败，请刷新重新加载.......")
        }
    });
}

listArticles(1,10);

function delById(id) {
    var params={
            "id": id
        }

    //请求分类接口，进行处理
    $.ajax({
        type: "POST",
        headers: {
            "Authorization":"Bearer "+accessToken
        },
        data: JSON.stringify(params),
        url: sessionStorage.getItem("requestUrl") + "/blog-article/admin/article/del",
        contentType:"application/json;charset=utf-8",
        async: false,
        success: function (res) {
            //返回成功
            if (res.code==200){
                // alert("文章删除成功！")
                $("#msg").attr("hidden",false);
                window.location="./blogs.html";
            }else{
                alert(res.msg);
            }
        },
        error: function (result) {
            alert("文章列表加载失败，请刷新重新加载.......")
        }
    });
}



// 运行时间统计
function secondToDate(second) {
    if (!second) {
        return 0;
    }
    var time = new Array(0, 0, 0, 0, 0);
    if (second >= 365 * 24 * 3600) {
        time[0] = parseInt(second / (365 * 24 * 3600));
        second %= 365 * 24 * 3600;
    }
    if (second >= 24 * 3600) {
        time[1] = parseInt(second / (24 * 3600));
        second %= 24 * 3600;
    }
    if (second >= 3600) {
        time[2] = parseInt(second / 3600);
        second %= 3600;
    }
    if (second >= 60) {
        time[3] = parseInt(second / 60);
        second %= 60;
    }
    if (second > 0) {
        time[4] = second;
    }
    return time;
}
function setTime() {
    /*此处为网站的创建时间*/
    var create_time = Math.round(new Date(Date.UTC(2020, 01, 25, 15, 15, 15)).getTime() / 1000);
    var timestamp = Math.round((new Date().getTime() + 8 * 60 * 60 * 1000) / 1000);
    currentTime = secondToDate((timestamp - create_time));
    currentTimeHtml = currentTime[0] + '年' + currentTime[1] + '天'
        + currentTime[2] + '时' + currentTime[3] + '分' + currentTime[4]
        + '秒';
    document.getElementById("htmer_time").innerHTML = currentTimeHtml;
}
setInterval(setTime, 1000);




$('.menu.toggle').click(function () {
    $('.m-item').toggleClass('m-mobile-hide');
});

$('.ui.dropdown').dropdown({
    on : 'hover'
});

//消息提示关闭初始化
$('.message .close')
    .on('click', function () {
        $(this)
            .closest('.message')
            .transition('fade');
    });

$('#clear-btn')
    .on('click', function() {
        $('.ui.type.dropdown')
            .dropdown('clear');
        typeId=null;

        $("#title").val("");
    });

function page(obj) {
    $("[name='page']").val($(obj).data("page"));
    loaddata();
}

//搜索
$("#search-btn").click(function () {
    //获取条件
    listArticles(1,10);
});
function loaddata() {
    $("#table-container").load(/*[[@{/admin/blogs/search}]]*/"/admin/blogs/search",{
        title : $("[name='title']").val(),
        typeId : $("[name='typeId']").val(),
        page : $("[name='page']").val()
    });
}

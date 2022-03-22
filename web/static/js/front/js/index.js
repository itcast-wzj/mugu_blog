// $('#newblog-container').load(/*[[@{/footer/newblog}]]*/"/footer/newblog");
sessionStorage.setItem("requestUrl","http://127.0.0.1/api");
//获取推荐的文章
function listRecommend() {
    $.ajax({
        type: "POST",
        // headers: {
        //     "Authorization":"Bearer "+accessToken
        // },
        data: JSON.stringify({"pageNum": 1, "pageSize": 12, "isRecommend": 1}),
        url: sessionStorage.getItem("requestUrl") + "/blog-article/front/article/list",
        contentType: "application/json;charset=utf-8",
        async: true,
        success: function (res) {
            //返回成功
            if (res.code == 200) {
                var list = res.data.result;
                for (var i = 0; i < list.length; i++) {
                    var str = list[i];
                    var html = "<div class=\"m-margin-tb-tiny four wide column\">\n" +
                        "            <a href='./blog.html?id="+str.articleId+"' class=\"class_outer\">\n" +
                        "                <img src=\"" + str.imgUrl + "\" alt=\"\" class=\"ui rounded image\" style='max-height: 10%'>\n" +
                        "                <span class=\"class_cover\" >\n" +
                        "                     <h4 class=\"m-font-size-blog-text m-margin-tb-tiny\" href='./blog.html?id="+str.articleId+"'>" + str.title + "</h4>\n" +
                        "                  </span>\n" +
                        "            </a>\n" +
                        "        </div>";
                    $("#recommend").append(html);
                }
            } else {
                alert(res.msg);
            }
        },
        error: function (result) {
            console.log("网络超时！");
        }
    });
}

listRecommend();

function next(pageNum,pageSize,status) {
    listArticles(pageNum,pageSize,status);
}
//分页获取文章
function listArticles(pageNum,pageSize,status) {
    $.ajax({
        type: "POST",
        data: JSON.stringify({"pageNum": pageNum, "pageSize": pageSize, "status": status}),
        url: sessionStorage.getItem("requestUrl") + "/blog-article/front/article/list",
        contentType: "application/json;charset=utf-8",
        async: true,
        success: function (res) {
            //返回成功
            if (res.code == 200) {
                var list = res.data.result;
                var total=res.data.pages;
                console.log("total="+total);
                $("#article").text("");
                $("#next").text("");
                for (var i = 0; i < list.length; i++) {
                    var str = list[i];
                    var html = "\n" +
                        "                        <div class=\"eleven wide column \">\n" +
                        "                            <h3 class=\"ui header\" ><a href='./blog.html?id="+str.articleId+"' class=\"m-black\">"+str.title+"</a></h3>\n" +
                        "                            <p class=\"m-text m-margin-top-max\">"+str.describe+"</p>\n" +
                        "                            <div class=\"ui m-margin-top-max grid\">\n" +
                        "                                <div class=\"eleven wide column\">\n" +
                        "                                    <div class=\"ui mini horizontal link list\">\n" +
                        "                                        <div class=\"item\">\n" +
                        "                                            <img src=\"../static/images/me.jpg\"  alt=\"\" class=\"ui avatar image\">\n" +
                        "                                            <div class=\"content\"><a href=\"#\" target=\"_blank\" class=\"header\" >"+str.authorName+"</a></div>\n" +
                        "                                        </div>\n" +
                        "                                        <div class=\"item\">\n" +
                        "                                            <i class=\"calendar icon\"></i><span>"+str.createTime+"</span>\n" +
                        "                                        </div>\n" +
                        "                                        <div class=\"item\">\n" +
                        "                                            <i class=\"eye icon\"></i> <span>"+str.viewNum+"</span>\n" +
                        "                                        </div>\n" +
                        "                                        <div class=\"item\">\n" +
                        "                                            <i class=\"comment outline icon\"></i> <span>"+str.commentNum+"</span>\n" +
                        "                                        </div>\n" +
                        "                                    </div>\n" +
                        "                                </div>\n" +
                        "                                <div class=\"right aligned five wide column\">\n" +
                        "                                    <a href=\"#\" target=\"_blank\" class=\"ui teal basic label m-padded-tiny m-text-thin\">"+str.typeName+"</a>\n" +
                        "                                </div>\n" +
                        "                            </div>\n" +
                        "                        </div>\n" +
                        "                        <!--博文图片-->\n" +
                        "                        <div class=\"five wide column\">\n" +
                        "                            <a href='./blog.html?id="+str.articleId+"'>\n" +
                        "                                <img src=\""+str.imgUrl+"\" alt=\"\" class=\"ui rounded image\" style='max-height:80%'>\n" +
                        "                            </a>\n" +
                        "                        </div><HR style=\"FILTER: alpha(opacity=0,finishopacity=100,style=1)\" width=\"100%\" color=#987cb9 SIZE=3>\n";
                    $("#article").append(html);
                }

                var next="<div class=\"three wide column\" align=\"center\">\n" +
                    "                    <a class=\"item\"  onclick='next("+(pageNum-1<=0?1:pageNum-1)+","+(pageSize)+","+(status)+")'>上一页</a>\n" +
                    "                </div>\n" +
                    "\n" +
                    "                <div class=\"ten wide column\" align=\"center\">\n" +
                    "                    <p><span></span> "+pageNum+" <span></span></p>\n" +
                    "                </div>\n" +
                    "\n" +
                    "                <div class=\"three wide column\" align=\"center\">\n" +
                    "                    <a class=\"item\" onclick='next("+(pageNum+1>total?total:pageNum+1)+","+(pageSize)+","+(status)+")'>下一页</a>\n" +
                    "                </div>";
                $("#next").append(next);
            } else {
                alert(res.msg);
            }
        },
        error: function (result) {
            console.log("网络超时！");
        }
    });
}

listArticles(1,10,2);


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
    $('.m-item').toggleClass('m-mobile-show');

});

// 显示公众号
$('.tencent').popup({
    popup: $('.tencent-qr'),
    position: 'bottom center'
});

// 显示微信
$('.wechat').popup({
    popup: $('.wechat-qr'),
    position: 'bottom center'
});

// 显示QQ
$('.qq').popup({
    popup: $('.qq-qr'),
    position: 'bottom center'
});

// 显示邮箱
$('.email').popup();

// 显示CSDN
$('.csdn').popup();

// 显示github
$('.github').popup();

// 导航栏显示
var waypoint = new Waypoint({
    element: document.getElementById('waypoint'),
    handler: function (direction) {
        if (direction == 'down') {
            $('#nav').show(500);
        } else {
            $('#nav').hide(500);
        }
        console.log('Scrolled to waypoint!  ' + direction);
    }
})
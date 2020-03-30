<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!DOCTYPE html>
<html lang="zh-CN">
<%@ include file="header.jsp" %>
<body>
<jsp:include page="navigation.jsp" flush="true"/>

<style>
    #floater {
        float: left;
        height: 50%;
        margin-bottom: -50px;
    }

    #top {
        float: right;
        width: 100%;
        text-align: center;
    }

    #content {
        clear: both;
        height: 100px;
        position: relative;
    }
</style>


<div class="list-group-item item_article" style="border: aliceblue">

    <!--用户信息-->
    <div class="row">
        <div class="col-xs-2 col-md-2"></div>
        <div class="col-xs-3 col-md-1">
            <img class="img-circle iv_article" src="${requestScope.igPost.userHead}" alt="头像" style="height:80px;"/>
        </div>
        <div class="col-xs-4 col-md-6" >
            <div id="top">
                <h3></h3>
            </div>
            <div id="floater"></div>
            <div id="content"><a href = "timeline?username=${requestScope.igPost.userName}">${requestScope.igPost.userName}</a></div>
        </div>
        <div class="col-xs-2 col-md-2"></div>
    </div>

    <!--图片-->
    <div class="row">
        <div class="col-xs-2"></div>
        <div class="col-xs-6">
            <img class="iv_article img-rounded" src="${requestScope.igPost.smallImg}">
        </div>
    </div>

    <!--内容-->
    <div class="row">
        <div class="col-xs-2"></div>
        <div class="col-xs-7">
            <strong style="color: black"> &nbsp;</strong>
            <p class="list-group-item-text div_article_content" >
                <strong>${requestScope.igPost.nickName}</strong> ${requestScope.igPost.text}
            </p>
        </div>
    </div>


    <div class="row">
        <div class="col-xs-2"></div>
        <div class="col-xs-7">
            <h6 style="color: gainsboro"> ${requestScope.timeText}</h6>
        </div>
    </div>


</div>
</body>
</html>
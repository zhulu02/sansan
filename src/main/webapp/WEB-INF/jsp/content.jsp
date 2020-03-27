<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  ~ Copyright © 2018 Hunan Antdu Software Co., Ltd.. All rights reserved.
  ~ 版权所有©2018湖南蚁为软件有限公司。保留所有权利。
  --%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>Bootstrap 101 Template</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://cdn.jsdelivr.net/npm/html5shiv@3.7.3/dist/html5shiv.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/respond.js@1.4.2/dest/respond.min.js"></script>
    <![endif]-->
</head>
<body>

<style type="text/css">
    body{
        padding-top: 50px;
    }
    .carousel{
        height: 500px;
        background-color: #000;
    }

    .carousel .item{
        height: 500px;
        background-color: #000;
    }
    .carousel img{
        width: 100%;
    }
    .casousel-caption p{
        margin-bottom: 20px;
        font-size: 20px;
        line-height: 1.8;
    }
    .img-rounded {
        display: block;
        height: auto;
        max-width: 100%;
    }
</style>
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>


<nav class="navbar navbar-default navbar-fixed-top navbar-inverse ">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Instagram</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="#">探索 </a></li>
                <li><a href="#">关注用户</a></li>
                <li class="active"><a href="#">个人主页<span class="sr-only">(current)</span></a></li>
            </ul>
        </div>
    </div>
</nav>
<!-- 功能模块 -->
<!-- 右侧 -->
<div class="col-xs-3 div_record">
    <!-- 用户信息 -->
    <div class="jumbotron div_userinfo">
        <img class="iv_user_head img-circle" src="img/ic_p5.jpg">
        <div style="display: inline-block; margin-left: 12px;font-size: 18px;">梁世杰</div>
    </div>
    <!-- 随手记录 -->
    <div style="display: flex;">
        <div style="flex: 1"><hr></div>
        <div style="text-align: center;line-height: 48px;color: #34374C">记录美好的心情</div>
        <div style="flex: 1"><hr></div>
    </div>
    <input type="text" class="form-control" placeholder="标题:美好的一天...">
    <br>
    <textarea class="form-control" rows="3" name=textarea placeholder="内容:今天捡到一分钱！！！^_^"></textarea>
    <br>
    <div class="div_save">
        <button type="button" class="btn btn-primary btn_save_record">save</button>
    </div>
    <hr>
    <!-- 小功能列表 -->
    <div class="row div_little_func">
        <div class="col-xs-4">
            <button class="btn btn-default btn-cricle btn_login" data-toggle="modal" data-target="#loginModal">登</button>
        </div>
        <div class="col-xs-4">
            <button class="btn btn-default btn-cricle btn_stay">留</button>
        </div>
        <div class="col-xs-4">
            <button class="btn btn-default btn-cricle btn_write">写</button>
        </div>
    </div>
</div>
</div>
</body>
</html>
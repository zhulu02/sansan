<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>
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
    .iv_function {
        display: block;
        height: auto;
        max-width: 100%;
    }
</style>
<!--导航栏-->
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
            <a class="navbar-brand" href="#">三三</a>
        </div>


        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <c:choose>
                    <c:when test="${requestScope.column_id == '0'}">
                        <li class="active"><a href="explore">探索<span class="sr-only">(current)</span> </a></li>
                        <li><a href="#">用户</a></li>
                    </c:when>
                    <c:otherwise >
                        <li><a href="explore">探索</a></li>
                        <li  class="active"><a href="#">用户<span class="sr-only">(current)</span></a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>

</nav>



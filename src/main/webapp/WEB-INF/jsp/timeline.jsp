<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@ include file="header.jsp" %>
<body>
<jsp:include page="navigation.jsp" flush="true"/>

<div class="row div_function" style="height: 20px;">&nbsp;</div>

<div class="row div_function">
    <!--mc是电脑端控制的样式，xs是手机端控制的样式-->
    <div class="col-md-2 col-xs-2"></div>

    <div class="col-md-2 col-xs-4">
        <img class="img-circle iv_article" src="${requestScope.igUser.headImgBase64}" alt="头像"/>
    </div>
    <div class="col-md-6 col-xs-4">
        <h2>${requestScope.igUser.username}</h2>
        <h3></h3>
        <h4>${requestScope.igUser.nickName}</h4>
    </div>
    <div class="col-md-2 col-xs-2"></div>


</div>



<div class="row " style="height: 30px;">&nbsp;</div>


<c:forEach items="${requestScope.lists}" var="list" >

    <div class="row div_function">

      <div class="col-xs-2"></div>

        <c:forEach items="${list}" var="igPost">
            <div class="col-xs-3">
                <!--style="height: 350px;"-->
                <div class="panel panel-default" id="${igPost.link}"  >
                    <div class="panel-body">
                        <a href="content?id=${igPost.id}"><img class="iv_function" src="${igPost.smallImg} "></a>
                    </div>
                </div>
            </div>
        </c:forEach>
        <div class="col-xs-1"></div>
    </div>
</c:forEach>

</body>
</html>
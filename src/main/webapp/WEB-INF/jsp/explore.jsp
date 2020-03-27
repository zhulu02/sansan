<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@ include file="header.jsp" %>
<body>
<jsp:include page="navigation.jsp" flush="true"/>



<c:forEach items="${requestScope.igPosts}" var="igPost" varStatus="num">
    <c:choose>
    <c:when test="${(num.count )% 3} ==1">
    <div class="row div_function">
        <div class="col-xs-4">
            <div class="panel panel-default" id="${igPost.link}" style="height:448px;">
                <div class="panel-body">
                    <img class="iv_function" src="${igPost.smallImg}">
                </div>
            </div>
        </div>
    </c:when>

    <c:when test="${(num.count )% 3} ==1">
        <div class="col-xs-4">
            <div class="panel panel-default" id="${igPost.link}" style="height:448px;">
                <div class="panel-body">
                    <img class="iv_function" src="${igPost.smallImg}">
                </div>
            </div>
        </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="col-xs-4">
            <div class="panel panel-default" id="${igPost.link}" style="height:448px;">
                <div class="panel-body">
                    <img class="iv_function" src="${igPost.smallImg}">
                </div>
            </div>
        </div>
    </c:otherwise>
    </c:choose>
</c:forEach>


</body>
</html>
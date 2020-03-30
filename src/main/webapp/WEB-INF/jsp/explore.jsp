<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@ include file="header.jsp" %>
<body>
<jsp:include page="navigation.jsp" flush="true"/>

<c:forEach items="${requestScope.lists}" var="list" >

    <div class="row div_function">


        <c:forEach items="${list}" var="igPost">
            <div class="col-xs-4">
                <!--style="height: 470px;"-->
                <div class="panel panel-default" >
                    <div class="panel-body">
                       <a href="content?id=${igPost.id}"><img class="iv_function" src="${igPost.smallImg} "></a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</c:forEach>



</body>
</html>
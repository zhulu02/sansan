<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@ include file="header.jsp" %>
<body>
<jsp:include   page="navigation.jsp" flush="true"/>

<div class="container div_divider">
    <!-- 分割线 -->
    <hr class="hr_1">
    welcome !
    <hr class="hr_2">
    <div class="row">
        <!-- 文章列表 -->
        <div class="col-xs-9">
            <div class="list-group div_article">
                <!-- 子头栏 -->
                <a href="#" class="list-group-item active item_article_first">
                    <h4 class="list-group-item-heading">
                        ${requestScope.message}
                        ${requestScope.igPost.text}
                    </h4>
                </a>

                <c:forEach items="${requestScope.igPosts}" var="igPost">

                    <div class="list-group-item item_article">
                        <div class="row">
                            <div class="div_center col-xs-9">
                                <div class="list-group-item-heading div_article_title">
                                    <strong>
                                            ${igPost.publishTime }
                                    </strong>
                                </div>
                                <p class="list-group-item-text div_article_content">
                                        ${igPost.text }
                                </p>
                            </div>
                            <!-- 右侧图片，信息 -->
                            <div class="col-xs-3 div_right_info">
                                <img class="iv_article img-rounded" src=" ${igPost.imgPath }">
                            </div>
                        </div>
                    </div>
                </c:forEach>

            </div>
        </div>
    </div>
</div>
</body>
</html>
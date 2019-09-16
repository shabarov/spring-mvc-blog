<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<head>
    <title>Mikhail's Blog</title>
    <meta name="keywords" content="Mikhail Shabarov Blog Java" />
    <meta name="description" content="Mikhail Shabarov Blog" />
    <link href="<c:url value="/resources/css/templatemo_style.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/s3slider.css" />" rel="stylesheet">
</head>
<html>
<body>

<div id="templatemo_wrapper">

    <div id="templatemo_menu">
        <ul>
            <li><a href="/index">Home</a></li>
            <li><a href="/newpost">New Post</a></li>
            <li><a href="/admin" class="current">Admin</a></li>
            <li><a href="/account">Account</a></li>
        </ul>
    </div>

    <div id="templatemo_left_column">

        <div id="templatemo_header">
            <div id="site_title">
                <h1><a href="http://www.templatemo.com" target="_parent">Mikhail's <strong>Blog</strong><span>Java Practice</span></a></h1>
            </div>
        </div>

        <div id="templatemo_sidebar">

            <c:if test="${username ne null}">
                <h5>Logged in as: <strong>'${username}'</strong></h5>
                <a href="<c:url value="/j_spring_security_logout" />" id="1">Logout</a>
            </c:if>

            <div class="cleaner_h40"></div>

            <h4>Categories</h4>
            <ul class="templatemo_list">
                <c:forEach items="${categories}" var="cat">
                    <li><a href="/posts/${cat.id}">${cat.name}</a></li>
                </c:forEach>
            </ul>
            <div class="cleaner_h40"></div>
        </div> <!-- end of templatemo_sidebar -->
    </div> <!-- end of templatemo_left_column -->

    <div id="templatemo_right_column">

        <div id="templatemo_main">

            <h1>Administration console</h1>

            <p><em>Here you easily can manage your blog:</em><p>
            <ul style="font-size: medium">
                <li><a href="/admin/categories">Manage categories</a></li>
                <li><a href="/admin/posts">Manage posts</a></li><td/>
                <li><a href="/admin/comments">Manage comments</a></li>
            </ul>
        </div>

        <div class="cleaner"></div>
    </div>
    <!-- end of templatemo_main -->
    <div class="cleaner_h20"></div>

    <div id="templatemo_footer">

        Copyright © 2016 <a href="#">Mikhail Shabarov</a> |
        <a href="http://www.iwebsitetemplate.com" target="_parent">Website Templates</a> by <a
            href="http://www.templatemo.com" target="_parent">Free CSS Templates</a>

    </div>

    <div class="cleaner"></div>
</div> <!-- end of warpper -->

</body>
</html>
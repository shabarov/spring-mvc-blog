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

    <style>
        .error {
            color: #ff0000;
        }

        .errorblock {
            color: #000;
            background-color: #ffEEEE;
            border: 3px solid #ff0000;
            padding: 8px;
            margin: 16px;
        }
    </style>

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

            <div class="cleaner_h20"></div>

            <c:choose>
                <c:when test="${isCreate}">
                    <c:set var="path" value="/admin/post/create"/>
                </c:when>
                <c:otherwise>
                    <c:set var="path" value="/admin/post/edit"/>
                </c:otherwise>
            </c:choose>

            <h3>Post management form:</h3>

            <div id="gallery">
                <ul>
                    <c:forEach items="${posts}" var="post">
                        <li>
                            <h5 id="postId${post.id}">${post.title}</h5>
                            <p>${post.summary}</p>
                            <p><a href="/admin/post/edit/${post.id}">Edit post</a>&nbsp; &nbsp;<a href="/admin/post/delete/${post.id}">Delete post</a></p>
                            <div class="cleaner"></div>
                        </li>
                    </c:forEach>
                </ul>
            </div>

            <p><em>Please enter a post data in the form below.</em></p>
            <%--Put form here--%>
            <form:form commandName="post" action="${path}" method="POST">

                <form:errors path="*" cssClass="errorblock" element="div" id="editForm" />
                <c:choose>
                    <c:when test="${not isCreate}">
                        <h3 style="color: #6d9e69">Edit post <strong>'${post.title}'</strong></h3>
                    </c:when>
                    <c:otherwise>
                        <h3 style="color: #6d9e69">Create new post</h3>
                    </c:otherwise>
                </c:choose>

                <c:if test="${not isCreate}">
                    <form:hidden path="id"/>
                </c:if>

                <label for="author">Title:</label><br><form:input path="title" type="text" id="author" name="title" class="required input_field" />
                <div class="cleaner_h10"></div>

                <label for="text">Summary:</label><br><form:textarea path="summary" id="text" name="summary" rows="5" cols="60" class="required"/>
                <div class="cleaner_h10"></div>

                <label for="text">Body:</label><br><form:textarea path="body" id="text" name="body" rows="20" cols="60" class="required"/>
                <div class="cleaner_h10"></div>

                <label for="text">Category:</label><br>
                <form:select path="category" items="${categories}" itemLabel="name" itemValue="id"/>
                <input type="submit" value="Submit">

            </form:form>
        </div>
    </div>
    <!-- end of templatemo_main -->
    <div class="cleaner_h20"></div>

    <div id="templatemo_footer">

        Copyright © 2016 <a href="#">Mikhail Shabarov</a> |
        <a href="http://www.iwebsitetemplate.com" target="_parent">Website Templates</a> by <a href="http://www.templatemo.com" target="_parent">Free CSS Templates</a>

    </div>

    <div class="cleaner"></div>
</div> <!-- end of warpper -->

</body>
</html>


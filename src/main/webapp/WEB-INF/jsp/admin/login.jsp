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
            <li><a href="/admin">Admin</a></li>
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

            <h2>Login console</h2>

            <c:if test="${username ne null}">
                <div style="color: blue;">
                    Your account '${username}' successfully created. Please login.
                </div>
            </c:if>

            <p>Please enter your login and password</p>

            <c:if test="${not empty error}">
                <div style="color: red;">
                    Login failed, try again.
                </div>
            </c:if>

            <form action="<c:url value='j_spring_security_check'/>" method='POST'>

                <table>
                    <tr>
                        <td>Login:</td>
                        <td><input type='text' name='j_username' value=''>
                        </td>
                    </tr>
                    <tr>
                        <td>Password:</td>
                        <td><input type='password' name='j_password'/>
                        </td>
                    </tr>
                    <tr>
                        <th></th>
                        <td>
                            <input id="remember_me" name="_spring_security_remember_me" type="checkbox"/>
                            <label for="remember_me" class="inline">Remember me</label>
                        </td>
                    </tr>
                    <tr>
                        <td colspan='2'>
                            <input name="submit" type="submit" value="Login"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan='2'>
                            <input name="reset" type="reset" value="Reset"/>
                        </td>
                    </tr>
                </table>

            </form>

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

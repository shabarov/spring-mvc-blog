<%@include file="header.jsp" %>
<html>
<body>

<div id="templatemo_wrapper">

    <div id="templatemo_menu">
        <ul>
            <li><a href="/index" class="current">Home</a></li>
            <li><a href="/newpost">New Post</a></li>
            <li><a href="/admin">Admin</a></li>
            <li><a href="/account">Account</a></li>
        </ul>
    </div>

    <div id="templatemo_left_column">

        <jsp:include page="logo.jsp"/>
        <div id="templatemo_sidebar">

            <c:choose>
                <c:when test="${username ne null}">
                    <h5>Logged in as: <strong>'${username}'</strong></h5>
                    <a href="<c:url value="/j_spring_security_logout" />" id="1">Logout</a>
                </c:when>
                <c:otherwise>
                    <h5>You're not logged in yet</h5>
                </c:otherwise>
            </c:choose>

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

            <c:forEach items="${posts}" var="post">

                <div class="post_section">

                    <span class="comment"><a href="/post/${post.id}">${fn:length(post.comments)}</a></span>

                    <h2><a href="/post/${post.id}">${post.title}</a></h2>
                        ${post.postDate} | <strong>Author:</strong> John | <strong>Category:</strong> <a
                        href="/blog?category=${post.category.id}">${post.category.name}</a>
                    <img src="/image${post.imagePath}" alt="no image"/></a>

                    <p>${post.summary}</p>
                    <a href="/post/${post.id}">Read more...</a>

                </div>
            </c:forEach>

        </div>

        <div class="cleaner"></div>
    </div>
    <!-- end of templatemo_main -->

    <jsp:include page="footer.jsp"/>

    <div class="cleaner_h20"></div>

</div> <!-- end of warpper -->

</body>
</html>
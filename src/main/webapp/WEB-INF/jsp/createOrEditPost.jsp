<%@include file="header.jsp" %>
<html>
<body>

<div id="templatemo_wrapper">

    <div id="templatemo_menu">
        <ul>
            <li><a href="/index">Home</a></li>
            <li><a href="/newpost" class="current">New Post</a></li>
            <li><a href="/admin">Admin</a></li>
            <li><a href="/account">Account</a></li>
        </ul>
    </div>

    <div id="templatemo_left_column">

        <jsp:include page="logo.jsp"/>

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

            <h1>Create/Edit post</h1>

            <p><em>Please enter a post data in the form below.</em></p>

            <c:choose>
                <c:when test="${isCreate}">
                    <c:set var="path" value="/updatepost/create"/>
                </c:when>
                <c:otherwise>
                    <c:set var="path" value="/updatepost/edit"/>
                </c:otherwise>
            </c:choose>

            <form:form commandName="post"
                       action="${path}"
                       method="POST" enctype="multipart/form-data">
                <form:errors path="*" cssClass="errorblock" element="div" />
                <c:choose>
                    <c:when test="${not isCreate}">
                        <h3 style="color: #6d9e69">Edit post <h2>'${post.title}'</h2></h3>
                    </c:when>
                    <c:otherwise>
                        <h3 style="color: #6d9e69">Create new post:</h3>
                    </c:otherwise>
                </c:choose>

                <c:if test="${not isCreate}">
                    <form:hidden path="id"/>
                </c:if>

                <label for="author">Title:</label><br>
                <form:input path="title" id="author" class="required input_field"/>
                <form:errors path="title" cssClass="error"/>
                <div class="cleaner_h10"></div>

                <label for="text">Summary:</label><br>
                    <form:textarea path="summary" id="text" name="summary" rows="5" cols="60" class="required"/>
                <form:errors path="summary"/>
                <div class="cleaner_h10"></div>

                <label for="text">Body:</label><br>
                    <form:textarea path="body" id="text" name="body" rows="20" cols="60" class="required"/>
                <form:errors path="body"/>
                <div class="cleaner_h10"></div>

                <label for="text">Category:</label><br>
                <form:select path="category" items="${categories}" itemLabel="name" itemValue="id"/>
                <div class="cleaner_h10"></div>

                <label for="text">Please select a file to upload : <br><input type="file" name="file" />

                <input type="submit" value="Submit">

            </form:form>

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

<%@include file="header.jsp" %>
<html>
<body>

<div id="templatemo_wrapper">

    <div id="templatemo_menu">
        <ul>
            <li><a href="/index">Home</a></li>
            <li><a href="/newpost">New Post</a></li>
            <li><a href="/admin">Admin</a></li>
            <li><a href="/account" class="current">Account</a></li>
        </ul>
    </div>

    <div id="templatemo_left_column">

        <jsp:include page="logo.jsp"/>
        <div id="templatemo_sidebar">

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

            <c:choose>
                <c:when test="${isLogged}">
                    <%--If user is logged in, then paint a LOGOUT and EDIT ACCOUNT links--%>
                    <c:set var="path" value="/account/edit"/>
                    <%--<c:if test="${isEdited}">
                        <p style="color: #058edc">Your account has been changed successfully.</p>
                    </c:if>--%>
                    <c:choose>
                        <c:when test="${isEdited}">
                            <p style="color: #058edc">Your account has been changed successfully.</p>
                        </c:when>
                    </c:choose>

                    <h3>You are logged in with a <strong>'${user.name}'</strong> account</h3>
                    <p>You can <a href="#userForm">edit your account</a> using a form below or <a href="<c:url value="/j_spring_security_logout" />" id="1">Logout</a> from your account. </p>
                </c:when>
                <c:otherwise>
                    <c:set var="path" value="/account/create"/>
                    <h3>You've not registered yet. Use registration form below to register, or make a <a href="/login">Login</a></h3>
                </c:otherwise>
            </c:choose>

            <form:form commandName="user"
                       action="${path}"
                       method="POST"
                       id="userForm">
                <form:errors path="*" cssClass="errorblock" element="div" />
                <c:choose>
                    <c:when test="${isLogged}">
                        <h3 style="color: #6d9e69">Edit account <h2>'${user.name}'</h2></h3>
                    </c:when>
                    <c:otherwise>
                        <h3 style="color: #6d9e69">Register a new user</h3>
                    </c:otherwise>
                </c:choose>

                <c:if test="${isLogged}">
                    <form:hidden path="userId"/>
                </c:if>

                <label for="author">Name:</label><br>
                <form:input path="name" id="author" class="required input_field"/>
                <form:errors path="name" cssClass="error"/>
                <div class="cleaner_h10"></div>

                <label for="author">Password:</label><br>
                <form:password path="password" id="author" class="required input_field"/>
                <form:errors path="password" cssClass="error"/>
                <div class="cleaner_h10"></div>

                <label for="author">E-mail:</label><br>
                <form:input path="email" id="author" class="required input_field"/>
                <form:errors path="email" cssClass="error"/>
                <div class="cleaner_h10"></div>

                <input type="submit" value="Submit">
            </form:form>

        </div>
    </div>
    <!-- end of templatemo_main -->
    <div class="cleaner_h20"></div>

    <jsp:include page="footer.jsp"/>

    <div class="cleaner"></div>
</div> <!-- end of warpper -->

</body>
</html>
<%@include file="header.jsp" %>

<head>
    <title>Posts</title>
    <link href="<c:url value="/resources/css/hearts.css"/>" rel="stylesheet" type="text/css" />
    <script>
        function createLike(postId) {
            var checkBox = document.getElementById("likeCheckbox");
            var request = new XMLHttpRequest();
            var path = '';
            var likeCounter = document.getElementById("likeCounter");
            if (checkBox.checked == true){
                path = "/like/create/post/" + postId;
                likeCounter.textContent = parseInt(likeCounter.textContent) + 1;
            } else {
                path = "/like/delete/post/" + postId;
                var currentLikesCount = parseInt(likeCounter.textContent);
                if (currentLikesCount > 0) {
                    likeCounter.textContent = parseInt(likeCounter.textContent) - 1;
                }
            }
            request.open("POST", path);
            request.send();
        }
    </script>
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

            <div class="post_section">

                <h2>${post.title}</h2>

                ${post.postDate} | <strong>Author:</strong> John | <strong>Category:</strong>
                <a href="/posts/${post.category.id}">${post.category.name}</a>

                <a href="http://www.templatemo.com/page/1" target="_parent"><img src="/resources/images/templatemo_image_01.jpg"
                                                                                 alt="image"/></a>

                <p>${post.body}</p>

                <label class="heart">Like <b id="likeCounter">${likeCount}</b>
                    <c:choose>
                        <c:when test="${isLiked}">
                            <input id="likeCheckbox" type="checkbox" checked onClick="createLike(${post.id})">
                        </c:when>
                        <c:otherwise>
                            <input id="likeCheckbox" type="checkbox" onClick="createLike(${post.id})">
                        </c:otherwise>
                    </c:choose>
                    <span class="checkmark"></span>
                </label>


            </div>

            <div class="comment_tab">Comments:</div>

            <div id="comment_section">
                <ol class="comments first_level">
                    <c:choose>
                        <c:when test="${empty post.comments}">
                            <p style="color: #ed1709">No comments yet...</p>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${post.comments}" var="comment">

                                <li>
                                    <div class="comment_box commentbox1">
                                        <div class="gravatar">
                                            <img src="/resources/images/avator.png" alt="author"/>
                                        </div>
                                        <div class="comment_text">
                                            <div class="comment_author">${comment.author}<span
                                                    class="date">${comment.commentDate}</span>
                                                    <p>${comment.email}</p>
                                            </div>
                                            <p>${comment.body}</p>
                                        </div>
                                        <div class="cleaner"></div>
                                    </div>
                                </li>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </ol>
            </div>
        </div>

        <%--<a href="/newcomment?post=${post.id}">Leave a comment...</a>--%>

        <div id="comment_form">
            <h3>Leave a comment</h3>


            <form:form commandName="comment"
                       action="/newcomment/${post.id}"
                       method="POST">
                <form:errors path="*" cssClass="errorblock" element="div" />
                <div class="form_row">
                    <label><strong>Name</strong> (required)</label>
                    <br/>
                    <form:input path="author"/>
                </div>
                <div class="form_row">
                    <label><strong>Email</strong> (required, will not be published)</label>
                    <br/>
                    <form:input path="email"/>
                </div>
                <div class="form_row">
                    <label><strong>Comment</strong></label>
                    <br/>
                    <form:textarea path="body" rows="10" cols="60"/>
                </div>
                <input type="submit" name="Submit" value="Submit" class="submit_btn"/>
            </form:form>

        </div>

        <div class="cleaner"></div>
    </div>
    <!-- end of templatemo_main -->
    <div class="cleaner_h20"></div>

    <jsp:include page="footer.jsp"/>

    <div class="cleaner"></div>
</div> <!-- end of warpper -->

</body>
</html>

package ru.shabarov.blog.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.shabarov.blog.entity.Comment;
import ru.shabarov.blog.service.CommentService;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

/**
 * http://localhost:8080/CommentsWS?wsdl
 * Use JaxWsPortProxyFactoryBean on client side
 */
@Component
@WebService(serviceName = "CommentsWS")
public class CommentsWS {

    @Autowired
    private CommentService commentService;

    @WebMethod
    public List<Comment> getAllComments() {
        return commentService.getAll();
    }
}

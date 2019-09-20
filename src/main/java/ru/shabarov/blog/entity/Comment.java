package ru.shabarov.blog.entity;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

@Entity
@Table(name = "comments")
@Proxy(lazy=false)
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentId", nullable = false)
    private Long commentId;

    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "commentDate", nullable = false, length = 20)
    private String commentDate;

    @Column(name = "author", nullable = false, length = 255)
    private String author;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId_fk", nullable = false)
    private Post post;

    public Comment() {

    }

    public Comment(Long id, String body, String commentDate, String author, String email, Post post) {
        this.commentId = id;
        this.body = body;
        this.commentDate = commentDate;
        this.author = author;
        this.email = email;
        this.post = post;
    }

    public Long getId() {
        return commentId;
    }

    public void setId(Long id) {
        this.commentId = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(commentId, comment.commentId) &&
                Objects.equals(body, comment.body) &&
                Objects.equals(commentDate, comment.commentDate) &&
                Objects.equals(author, comment.author) &&
                Objects.equals(email, comment.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, body, commentDate, author, email);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", body='" + body + '\'' +
                ", commentDate='" + commentDate + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setCurrentDate() {
        Date date = new Date();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.commentDate = format.format(calendar.getTime());
    }
}

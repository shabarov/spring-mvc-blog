package ru.shabarov.blog.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.Set;

@Proxy(lazy=false) //TODO: workaround to avoid org.hibernate.LazyInitializationException within calls of PostExportService
@Entity
@Table(name = "posts")
@JsonIgnoreProperties(value = { "category", "comments"})
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postId", nullable = false)
    private Long postId;

    @Column(name = "title", nullable = false, length = 255)
    @Size(min=5, max=20, message = "Post title must be between 5 and 20 chars long.")
    private String title;

    @Column(name = "summary", nullable = false)
    private String summary;

    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "postDate", nullable = false, length = 20)
    private String postDate;

    @Column(name = "imagePath", nullable = false, length = 255)
    private String imagePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId_fk", nullable = false)
    private Category category;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "post")
    private Set<Comment> comments;

    public Post() {
    }

    public Post(Long id) {
        this.postId = id;
    }

    public Post(Long id, String title, String summary, String body, String postDate, Category category, String imagePath) {
        this.postId = id;
        this.title = title;
        this.summary = summary;
        this.body = body;
        this.postDate = postDate;
        this.category = category;
        this.imagePath = imagePath;
    }

    public Long getId() {
        return postId;
    }

    public void setId(Long id) {
        this.postId = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public void setCurrentDate() {
        Date date = new Date();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.postDate = format.format(calendar.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(postId, post.postId) &&
                Objects.equals(title, post.title) &&
                Objects.equals(summary, post.summary) &&
                Objects.equals(body, post.body) &&
                Objects.equals(postDate, post.postDate) &&
                Objects.equals(imagePath, post.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, title, summary, body, postDate, imagePath);
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", body='" + body + '\'' +
                ", postDate='" + postDate + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", category=" + category +
                ", comments=" + comments +
                '}';
    }
}

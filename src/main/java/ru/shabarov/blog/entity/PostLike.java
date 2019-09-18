package ru.shabarov.blog.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "post_likes")
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "likeId", nullable = false)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId_fk", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId_fk", nullable = false)
    private User user;

    public PostLike() {
    }

    public PostLike(Long likeId, Long postId, Long userId) {
        this.likeId = likeId;
        this.post = new Post(postId);
        this.user = new User(userId);
    }

    public PostLike(Long likeId, Post post, User user) {
        this.likeId = likeId;
        this.post = post;
        this.user = user;
    }

    public PostLike(Post post, User user) {
        this.post = post;
        this.user = user;
    }

    public Long getLikeId() {
        return likeId;
    }

    public void setLikeId(Long likeId) {
        this.likeId = likeId;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostLike postLike = (PostLike) o;
        return Objects.equals(likeId, postLike.likeId) &&
                Objects.equals(post, postLike.post) &&
                Objects.equals(user, postLike.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(likeId, post, user);
    }

    @Override
    public String toString() {
        return "PostLike{" +
                "likeId=" + likeId +
                ", post=" + post +
                ", user=" + user +
                '}';
    }
}

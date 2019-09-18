package ru.shabarov.blog.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.shabarov.blog.entity.Post;
import ru.shabarov.blog.entity.PostLike;
import ru.shabarov.blog.entity.User;

import java.sql.*;
import java.util.Collections;
import java.util.List;

/**
 * Dao for post likes based on Spring jdbc template
 */
public class PostLikeDao implements AbstractDao<PostLike> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Long create(PostLike entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql = "INSERT INTO post_likes(postId_fk, userId_fk) VALUES (?, ?)";
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, String.valueOf(entity.getPost().getId()));
                ps.setString(2, String.valueOf(entity.getUser().getUserId()));
                return ps;
            }
        }, keyHolder);

        return (long) keyHolder.getKey();
    }

    @Override
    public void delete(PostLike entity) {
        final String sqlPostOnly = "DELETE FROM post_likes WHERE postId_fk = ?";
        Post post = entity.getPost();
        User user = entity.getUser();
        if (user != null) {
            final String sql = sqlPostOnly + " AND userId_fk = ?";
            jdbcTemplate.update(sql, new Object[]{post.getId(), user.getUserId()});
        } else {
            jdbcTemplate.update(sqlPostOnly, new Object[]{post.getId()});
        }
    }

    @Override
    public PostLike edit(PostLike entity) {
        return null;
    }

    @Override
    public PostLike getById(Long id) {
        return null;
    }

    @Override
    public List<PostLike> getAll() {
        return null;
    }

    @Override
    public Long getQuantity() {
        return null;
    }

    @Override
    public List<PostLike> getByAttributeId(Long id, String attribute, String attributeIdName) {
        if ("postId_fk".equals(attributeIdName)) {
            return jdbcTemplate.query("SELECT * FROM post_likes a where a.postId_fk = ?",
                    new Object[]{id}, new RowMapper<PostLike>() {
                @Override
                public PostLike mapRow(ResultSet resultSet, int i) throws SQLException {
                    PostLike postLike = new PostLike();
                    postLike.setLikeId(resultSet.getLong(1));
                    postLike.setPost(new Post(resultSet.getLong(2)));
                    postLike.setUser(new User(resultSet.getLong(3)));
                    return postLike;
                }
            });
        }
        return Collections.emptyList();
    }

    @Override
    public List<PostLike> getByName(String name) {
        return null;
    }

    @Override
    public void executeQuery(String query) {
    }
}

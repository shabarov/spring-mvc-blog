package ru.shabarov.blog.rmi;

import ru.shabarov.blog.entity.Post;

import java.util.List;

public interface PostExportService {
    List<Post> getAllPosts();
}

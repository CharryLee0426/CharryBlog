package com.lichenxidian.blog.dao;

import com.lichenxidian.blog.po.Blog;
import com.lichenxidian.blog.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBlog(Blog blog, Sort sort);
    List<Comment> findByBlogAndParentCommentNull(Blog blog, Sort sort);
    void deleteByBlog(Blog blog);
}

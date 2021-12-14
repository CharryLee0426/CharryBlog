package com.lichenxidian.blog.service;

import com.lichenxidian.blog.po.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId);
    Comment saveComment(Comment comment);
}

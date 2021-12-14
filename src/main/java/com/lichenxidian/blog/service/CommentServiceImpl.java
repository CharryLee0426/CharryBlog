package com.lichenxidian.blog.service;

import com.lichenxidian.blog.dao.BlogRepository;
import com.lichenxidian.blog.dao.CommentRepository;
import com.lichenxidian.blog.po.Comment;
import com.lichenxidian.blog.util.CommentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BlogRepository blogRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        List<Comment> comments = commentRepository
                .findByBlogAndParentCommentNull(blogRepository.getById(blogId), sort);
        return CommentUtils.eachComment(comments);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        // 如果是某个评论的回复，那么就要建立回复和父评论的关系
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) {
            comment.setParentComment(commentRepository.getById(parentCommentId));
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }
}

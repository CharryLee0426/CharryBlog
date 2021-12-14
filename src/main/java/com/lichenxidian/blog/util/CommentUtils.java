package com.lichenxidian.blog.util;

import com.lichenxidian.blog.po.Comment;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class CommentUtils {
    // 用来存放迭代找出的所有子代的集合
    private static List<Comment> tempReplies = new ArrayList<>();

    public static List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentsView.add(c);
        }

        // 合并评论的各层子节点到第一级子节点上。
        combineChildren(commentsView);
        return commentsView;
    }

    private static void combineChildren(List<Comment> comments) {
        for (Comment comment : comments) {
            List<Comment> replies = comment.getReplyComments();
            for (Comment reply : replies) {
                // 循环找出子节点放入 tempReplies 中
                recursively(reply);
            }
            comment.setReplyComments(tempReplies);
            // 清空临时存放区
            tempReplies = new ArrayList<>();
        }
    }

    // 这里有一个递归逻辑
    private static void recursively(Comment comment) {
        tempReplies.add(comment);
        if (comment.getReplyComments().size() > 0) {
            List<Comment> replies = comment.getReplyComments();
            for (Comment reply : replies) {
                tempReplies.add(reply);
                if (reply.getReplyComments().size() > 0) {
                    recursively(reply);
                }
            }
        }
    }
}

package com.lichenxidian.blog.service;

import com.lichenxidian.blog.po.Blog;
import com.lichenxidian.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Blog getBlog(Long id);
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
    Blog saveBlog(Blog blog);
    Blog updateBlog(Long id, Blog blog);
    void deleteBlog(Long id);
    Page<Blog> listBlog(Pageable pageable);
    List<Blog> listRecommendBlogTop(Integer size);
    Page<Blog> listBlog(Pageable pageable, String query);
    Blog getBlogAndConvert(Long id);
    Page<Blog> listBlog(Pageable pageable, Long tagId);
    Map<String, List<Blog>> archiveBlog();
    Long countBlog();
}

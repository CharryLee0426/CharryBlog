package com.lichenxidian.blog.service;

import com.lichenxidian.blog.NotFoundException;
import com.lichenxidian.blog.dao.BlogRepository;
import com.lichenxidian.blog.dao.CommentRepository;
import com.lichenxidian.blog.po.Blog;
import com.lichenxidian.blog.po.Type;
import com.lichenxidian.blog.util.MarkdownUtils;
import com.lichenxidian.blog.util.MyBeanUtils;
import com.lichenxidian.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService{
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getById(id);
    }

    // 这里要实现一个动态查询过程
    // 这里之所以要用 BookQuery 是因为前端传过来的是 title 和 typeId。不能生成一个
    // Blog 对象。
    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                if (blog.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null) {     // add
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } else {        // edit
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.getById(id);
        if (b == null) {
            throw new NotFoundException("Blog Not Found!");
        }

        BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog));
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        commentRepository.deleteByBlog(blogRepository.getById(id));
        blogRepository.deleteById(id);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, String query) {
        return blogRepository.findByQuery(query, pageable);
    }

    @Override
    public Blog getBlogAndConvert(Long id) {
        Blog blog = blogRepository.getById(id);
        if (blog == null) {
            throw new NotFoundException("blog not found!");
        }
        blog.setViews(blog.getViews() + 1);     // 浏览时会给数据库浏览次数加1
        updateBlog(id, blog);                   // 向数据库中更新
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHTMLExtensions(content));   // 直接操作 blog 会改变数据库内容
        return b;
    }

    // 通过 Tag, Blog, Tag-Blog 表关联实现查询
    @Override
    public Page<Blog> listBlog(Pageable pageable, Long tagId) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        }, pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogRepository.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year, blogRepository.listBlogByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }
}

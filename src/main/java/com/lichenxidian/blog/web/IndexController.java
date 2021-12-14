package com.lichenxidian.blog.web;

import com.lichenxidian.blog.service.BlogService;
import com.lichenxidian.blog.service.CommentService;
import com.lichenxidian.blog.service.TagService;
import com.lichenxidian.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
   @Autowired
   private BlogService blogService;

   @Autowired
   private TypeService typeService;

   @Autowired
   private TagService tagService;

   @Autowired
   private CommentService commentService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                        Pageable pageable,
                        Model model) {
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        return "index";
    }

    // search
    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                         Pageable pageable,
                         Model model,
                         @RequestParam String query) {
        model.addAttribute("page", blogService.listBlog(pageable, "%"+query+"%"));
        model.addAttribute("query", query);
        return "search";
    }

    // blog-info
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,
                       Model model) {
        model.addAttribute("blog", blogService.getBlogAndConvert(id));
        model.addAttribute("comments", commentService.listCommentByBlogId(id));
        return "blog";
    }

    // footer
    @GetMapping("/footer/newblog")
    public String newBlogs(Model model) {
        model.addAttribute("newBlogs", blogService.listRecommendBlogTop(3));
        return "_fragments :: newBlogList";
    }
}

package com.lichenxidian.blog.web.admin;

import com.lichenxidian.blog.po.Blog;
import com.lichenxidian.blog.po.User;
import com.lichenxidian.blog.service.BlogService;
import com.lichenxidian.blog.service.TagService;
import com.lichenxidian.blog.service.TypeService;
import com.lichenxidian.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 7, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                        Pageable pageable,
                        BlogQuery blog,
                        Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        System.out.println(blogService.listBlog(pageable, blog));
        return "admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 7, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                Pageable pageable,
                        BlogQuery blog,
                        Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("blog", new Blog());
        return "admin/blog-input";
    }

    // 这里新增和修改共用了一个方法，对于分类和标签也可以改写成类似的方法
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if (blog.getId() == null) {
            b = blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }
        if (b == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/blogs";
    }

    // edit 这里不成功的原因是跳转页面跳错了
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model,
                            Pageable pageable,
                            BlogQuery blog) {
//        model.addAttribute("page", blogService.listBlog(pageable, blog));
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        Blog b = blogService.getBlog(id);
        b.init();
        model.addAttribute("blog", b);
        return "admin/blog-input";
    }

    // delete
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "delete success");
        return "redirect:/admin/blogs";
    }
}

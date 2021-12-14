package com.lichenxidian.blog.web.admin;

import com.lichenxidian.blog.po.Tag;
import com.lichenxidian.blog.service.TagService;
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
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String list(@PageableDefault(size = 6, sort = {"id"}, direction = Sort.Direction.ASC)
                       Pageable pageable,
                       Model model,
                       HttpSession session) {
        model.addAttribute("page", tagService.listTag(pageable));
        model.addAttribute("activeuser", session.getAttribute("user"));
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model,
                        HttpSession session) {
        model.addAttribute("tag", new Tag());
        model.addAttribute("activeuser", session.getAttribute("user"));
        return "admin/tag-input";
    }

    @PostMapping("/tags")
    public String post(Tag tag, RedirectAttributes attributes) {
        if (tagService.getTagByname(tag.getName()) != null) {
            // error
        }

        Tag t = tagService.saveTag(tag);

        if (t == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }

        return "redirect:/admin/tags";
    }

    // edit
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id,
                            Model model,
                            HttpSession session) {
        model.addAttribute("tag", tagService.getTag(id));
        model.addAttribute("activeuser", session.getAttribute("user"));
        return "admin/tag-input";
    }

    @PostMapping("/tags/{id}")
    public String editPost(@PathVariable Long id, Tag tag, RedirectAttributes attributes) {
        if (tagService.getTagByname(tag.getName()) != null) {
            // error
        }

        Tag t = tagService.updateTag(id, tag);

        if (t == null) {
            // error
        } else {
            // success
        }

        return "redirect:/admin/tags";
    }

    // delete
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id) {
        tagService.deleteTag(id);
        return "redirect:/admin/tags";
    }
}

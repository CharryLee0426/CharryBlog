package com.lichenxidian.blog.web.admin;

import com.lichenxidian.blog.po.Type;
import com.lichenxidian.blog.service.TypeService;
import com.sun.org.apache.xpath.internal.operations.Mod;
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
public class TypeController {
    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 6, sort = {"id"}, direction = Sort.Direction.ASC)
                        Pageable pageable,
                        Model model,
                        HttpSession session) {
        model.addAttribute("page", typeService.listType(pageable));
        model.addAttribute("activeuser", session.getAttribute("user"));
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model,
                        HttpSession session) {
        model.addAttribute("type", new Type());
        model.addAttribute("activeuser", session.getAttribute("user"));
        return "admin/type-input";
    }

    @PostMapping("/types")
    public String post(Type type, RedirectAttributes attributes) {
        if (typeService.getTypeByName(type.getName()) != null) {
            // error
        }

        Type t = typeService.saveType(type);
        if (t == null) {
            // error
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            // tips
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/types";
    }

    // edit
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id,
                            Model model,
                            HttpSession session) {
        model.addAttribute("type", typeService.getType(id));
        model.addAttribute("activeuser", session.getAttribute("user"));
        return "admin/type-input";
    }

    @PostMapping("/types/{id}")
    public String editPost(@PathVariable Long id, Type type, RedirectAttributes attributes) {
        if (typeService.getTypeByName(type.getName()) != null) {
            // error
        }

        Type t = typeService.updateType(id, type);
        if (t == null) {
            // error
        } else {
            // success
        }
        return "redirect:/admin/types";
    }

    //delete
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id) {
        typeService.deleteType(id);
        return "redirect:/admin/types";
        // 这里还可以加一些后端校验
    }
}

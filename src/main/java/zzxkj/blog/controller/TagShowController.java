package zzxkj.blog.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import zzxkj.blog.dto.FirstPageBlog;
import zzxkj.blog.pojo.Tag;
import zzxkj.blog.service.BlogService;
import zzxkj.blog.service.TagService;

import java.util.List;


@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String tags(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                       @PathVariable Long id, Model model) {
        List<Tag> tags = tagService.getAllTag();
        model.addAttribute("tags", tags);
        int size = 8;
        String sort = "update_time"+" DESC";
        PageHelper.startPage(pageNum,size);
        List<FirstPageBlog> blogs = blogService.getByTagId(id);
        PageInfo<FirstPageBlog> page = new PageInfo<>(blogs);
        if (id == -1) {
            id = tags.get(0).getId();
        }
        model.addAttribute("page", page);
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}

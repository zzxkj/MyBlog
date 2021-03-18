package zzxkj.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import zzxkj.blog.dto.DetailedBlog;
import zzxkj.blog.pojo.Blog;
import zzxkj.blog.service.BlogService;

import java.util.List;
import java.util.Map;

@Controller
public class ArchiveShowController {
    /*    @Autowired
        BlogService blogService;
        @GetMapping("/archives")
        public String archive(Model model){
            model.addAttribute("archiveMap",blogService.archiveBlog());
            Map<String, List<Blog>> blogs = blogService.archiveBlog();
            System.out.println("**********+***********");
            for (Map.Entry entry : blogs.entrySet()){
                System.out.println(entry.getKey());
            }
            System.out.println("**********+***********");
            model.addAttribute("blogCount",blogService.countBlog());
            return "archives";
        }*/
    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model) {
        model.addAttribute("archiveMap", blogService.archiveBlog());
        model.addAttribute("blogCount", blogService.countBlog());
        Map<String, List<DetailedBlog>> blogs = blogService.archiveBlog();
        System.out.println("**********+***********");
        for (Map.Entry entry : blogs.entrySet()) {
            System.out.println(entry.getKey());
        }
        System.out.println("**********+***********");
        return "archives";
    }
}

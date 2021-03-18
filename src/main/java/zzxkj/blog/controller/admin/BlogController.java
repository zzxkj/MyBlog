package zzxkj.blog.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import zzxkj.blog.WorkingException.NotFoundException;
import zzxkj.blog.dto.BlogQuery;
import zzxkj.blog.dto.ShowBlog;
import zzxkj.blog.pojo.Blog;
import zzxkj.blog.pojo.User;
import zzxkj.blog.service.BlogService;
import zzxkj.blog.service.TagService;
import zzxkj.blog.service.TypeService;
import zzxkj.blog.util.MyBeanUtils;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;


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
    public String blogs(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum, Model model) {
        int size = 8;
        String sort = "update_time"+" ASC";
        PageHelper.startPage(pageNum,size);
        List<BlogQuery> Blogs = blogService.getAllBlog();
        PageInfo<BlogQuery> page = new PageInfo<>(Blogs);
        model.addAttribute("types", typeService.getAllType());
        model.addAttribute("page", page);
        return "admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String search(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum, Model model) {
        int size = 8;
        String sort = "update_time"+" DESC";
        PageHelper.startPage(pageNum,size);
        List<BlogQuery> Blogs = blogService.getAllBlog();
        PageInfo<BlogQuery> page = new PageInfo<>(Blogs);
        model.addAttribute("page", page);
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model) {
        model.addAttribute("types", typeService.getAllType());
        model.addAttribute("tags", tagService.getAllTag());
        model.addAttribute("blog", new Blog());
        return "admin/blogs-input";
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("types", typeService.getAllType());
        model.addAttribute("tags", tagService.getAllTag());
        ShowBlog blog = blogService.getBlogById(id);
        blog.init(tagService.getTagsByBlogId(id));
        model.addAttribute("blog", blog);
        return "admin/blogs-input";
    }

    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes redirectAttributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        //设置blog的type
        blog.setType(typeService.getType(blog.getType().getId()));
        //设置blog中typeId属性
        blog.setTypeId(blog.getType().getId());
        //给blog中的List<Tag>赋值
        blog.setTags(tagService.getTagByString(blog.getId(),blog.getTagIds()));
        //设置用户id
        blog.setUserId(blog.getUser().getId());
        int flag;
        if (blog.getFlag() == null || "".equals(blog.getFlag())) {
            //if("".equals(blog.getFlag())) System.out.println("是空字符串");
            blog.setFlag("原创");
        }
        if (blog.getId() == null) {
            flag = blogService.saveBlog(blog);
        } else {
             ShowBlog b = blogService.getBlogById(blog.getId());
             if (b == null) {
                 throw new NotFoundException("震惊！！！本不该存在该博客！！！！");
             }
             BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog));
             blog.setUpdateTime(new Date());
             flag = blogService.updateBlog(blog);
        }
        if (flag == 0) {
            redirectAttributes.addFlashAttribute("message", "操作失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "操作成功");
        }
        redirectAttributes.addFlashAttribute("message", "操作成功");
        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        blogService.deleteBlog(id);
        redirectAttributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/blogs";
    }


}

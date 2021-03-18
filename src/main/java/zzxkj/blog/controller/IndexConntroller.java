package zzxkj.blog.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zzxkj.blog.WorkingException.NotFoundException;
import zzxkj.blog.dto.DetailedBlog;
import zzxkj.blog.dto.FirstPageBlog;
import zzxkj.blog.pojo.Comment;
import zzxkj.blog.service.BlogService;
import zzxkj.blog.service.CommentService;
import zzxkj.blog.service.TagService;
import zzxkj.blog.service.TypeService;
import zzxkj.blog.util.MarkdownUtils;

import java.util.List;

@Controller
public class IndexConntroller {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum, Model model) {
        int size = 8;
        String sort = "update_time"+" DESC";
        PageHelper.startPage(pageNum,size);
        List<FirstPageBlog> blogs = blogService.getAllFirstPageBlog();
        PageInfo<FirstPageBlog> page = new PageInfo<FirstPageBlog>(blogs);
        model.addAttribute("totalElements",blogService.countBlog());
        model.addAttribute("page", page);
        model.addAttribute("types", typeService.listTypeTop(6));/*分类栏数目*/
        model.addAttribute("tags", tagService.listTagTop(10));/*标签栏数目*/
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        return "index";
    }

    @PostMapping("/search")
    public String search(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,  @RequestParam String query, Model model) {
        int size = 8;
        String sort = "update_time"+" DESC";
        PageHelper.startPage(pageNum,size);
        List<FirstPageBlog> blogs = blogService.getSearchBlog(query);
        PageInfo<FirstPageBlog> page = new PageInfo<FirstPageBlog>(blogs);
        model.addAttribute("page", page);
        model.addAttribute("query", query);
        model.addAttribute("totalElements",blogs==null?0:blogs.size());
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        DetailedBlog blog = blogService.getBlogAndIncrease(id);
        if (blog == null) throw new NotFoundException("此篇博客不存在，或被移除");
        //将md格式转换成html格式
        String HTMLContent = MarkdownUtils.markdownToHtmlExtensitons(blog.getContent());//MarkDown文本转成HTML文本
        blog.setContent(HTMLContent);
/*        List<Comment> comments = commentService.listCommentByBlogId(id);
        model.addAttribute("comments", comments);

        System.out.println("******************************");
        System.out.println(comments.toString());
        System.out.println("******************************");*/

        model.addAttribute("blog", blog);
        //System.out.println(comments.toString());
        System.out.println(blog.isCommentable());
        return "blog";
    }

    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        model.addAttribute("newblogs", blogService.listRecommendBlogTop(3));
        return "_fragments  :: newblogList";
    }
}

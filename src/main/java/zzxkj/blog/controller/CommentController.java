package zzxkj.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import zzxkj.blog.dto.DetailedBlog;
import zzxkj.blog.pojo.Comment;
import zzxkj.blog.pojo.User;
import zzxkj.blog.service.BlogService;
import zzxkj.blog.service.CommentService;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;
    @Autowired
    BlogService blogService;

    /*配置文件中定义写死的头像*/
    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        model.addAttribute("comments", commentService.listFinCommentByBlogId(blogId));
       // model.addAttribute("comments",commentService.findByBlogId(blogId));
        System.out.println(commentService.findByBlogId(blogId).toString());
        return "blog :: commentList";
    }


    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        Long blogId = comment.getBlog().getId();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            //管理员头像
            comment.setAvatar(user.getAvatar());
            //管理员评论
            comment.setAdminComment(true);
        } else {
            comment.setAvatar(avatar);
            comment.setAdminComment(false);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }

}

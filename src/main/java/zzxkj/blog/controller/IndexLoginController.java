package zzxkj.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class IndexLoginController {

    @GetMapping("/blog/logout")
    public String BlogLogin(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }
}

package zzxkj.blog.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import zzxkj.blog.pojo.Type;
import zzxkj.blog.service.TypeService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String type(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                       Model model) {
        int size = 8;
        String sort = "update_time"+" ASC";
        PageHelper.startPage(pageNum,size);
        List<Type> types = typeService.getAllType();
        PageInfo<Type> page = new PageInfo<>(types);
        model.addAttribute("page", page);
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String AddInput(Model model) {
        model.addAttribute("type", new Type());/*前端可用type*/
        return "admin/types-input";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }


    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes redirectAttributes) {
        Type tk = typeService.getTypeByName(type.getName());
        if (tk != null) {
            result.rejectValue("name", "nameError", tk.getName() + "分类已存在");
        }

        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Integer t = typeService.saveType(type);
        if (t == null||t == 0) {
            redirectAttributes.addFlashAttribute("message", "新增失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String editpost(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        Type tk = typeService.getTypeByName(type.getName());
        if (tk != null) {
            result.rejectValue("name", "nameError", tk.getName() + "分类已存在");
        }

        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Integer t = typeService.updateType(type);
        if (t == null || t == 0) {
            redirectAttributes.addFlashAttribute("message", "更新失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        typeService.deleteType(id);
        redirectAttributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }

}

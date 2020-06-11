package com.yimian.web.page;

import com.yimian.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;
import java.util.Date;
import java.util.UUID;

/**
 * PageController
 * chengshaohua
 *
 * @date 2020/6/10 14:34
 */
@Controller
@Slf4j
public class PageController {
    @Value("${spring.application.name}")
    private String sysAppName;

    @ModelAttribute
    public void addModelAttribute(Model model) {
        model.addAttribute("requestTime", new Date());
    }

    @ModelAttribute(name = "sysAppName")
    public String addModelAttribute() {
        return sysAppName;
    }

    @GetMapping("layout")
    public String layout() {
        return "layout/layout";
    }

    @GetMapping({"/", "home"})
    public String home(Model model) {
        model.addAttribute("echarts_title", "标题-echarts");
        return "home";
    }

    @GetMapping("login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("uuidPage", UUID.randomUUID().toString());
        return modelAndView;
    }

    @PostMapping("/login")
    public String doLogin(@Valid User user, BindingResult result, RedirectAttributes redirect) {
        redirect.addAttribute("userUUID", UUID.randomUUID().toString());
        redirect.addFlashAttribute("userName", user.getUserName());
        return "redirect:/home";
    }
}

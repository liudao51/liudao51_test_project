package com.liudao51.test.shiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {

    @RequestMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("msg", "hello, shiro!");
        return "index";
    }

    @RequestMapping({"/user/login"})
    public String login(Model model) {
        return "user/login";
    }

    @RequestMapping({"/user/doLogin"})
    public String doLogin(Model model) {
        return "user/doLogin";
    }

    @RequestMapping({"/user/view"})
    public String userView(Model model) {
        return "user/view";
    }

    @RequestMapping({"/user/add"})
    public String userAdd(Model model) {
        return "user/add";
    }
}

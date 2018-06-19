package com.hao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @RequestMapping(value = "/login")
    public String login(HttpSession session, String username, String password) throws Exception {
        System.out.println(username);
        System.out.println(password);
        session.setAttribute("username", username);
        return "redirect:/queryItems.action";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) throws Exception {
        session.invalidate();
        return "redirect:/queryItems.action";
    }
}

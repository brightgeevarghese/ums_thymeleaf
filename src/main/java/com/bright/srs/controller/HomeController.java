package com.bright.srs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/home")
public class HomeController {

    @GetMapping
    public String home(Model model) {
        model.addAttribute("username", "bright");
        return "home_page";
    }
}

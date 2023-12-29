package com.sample.FreeCommunity.home;

import com.sample.FreeCommunity.FreeCommunityApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/"})
    public String home() {
        return "home";
    }
}
package com.sample.FreeCommunity.user.controller;

import com.sample.FreeCommunity.user.DTO.SignInFormDTO;
import com.sample.FreeCommunity.user.entity.UserEntity;
import com.sample.FreeCommunity.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class LogInController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping({"/logIn"})
    public String logInMenu() {
        return "users/logIn";
    }

    @PostMapping({"/logIn"})
    public String logIn() {

        return "home";
    }
    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "users/logIn";
    }
}
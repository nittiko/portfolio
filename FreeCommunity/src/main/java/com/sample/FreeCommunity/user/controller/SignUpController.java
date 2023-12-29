package com.sample.FreeCommunity.user.controller;

import com.sample.FreeCommunity.user.DTO.SignUpFormDTO;
import com.sample.FreeCommunity.user.entity.UserEntity;
import com.sample.FreeCommunity.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@Controller
@RequiredArgsConstructor
@RequestMapping({"/signUp"})
public class SignUpController {

    private final UserService userService;
    private final Argon2PasswordEncoder passwordEncoder;

    @GetMapping
    public String signUpMenu( Model model) {
        model.addAttribute("SignUpFormDTO", new SignUpFormDTO());
        return "users/signUp";
    }

    @PostMapping
    public String signUp(@Valid SignUpFormDTO signUpFormDTO,
                                BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "users/signUp";
        }

        try {
            UserEntity user = UserEntity.SignUpUser(signUpFormDTO, passwordEncoder);
            signUpFormDTO.emailValidation();
            signUpFormDTO.passwordCheck();
            userService.saveUser(user);
        } catch (IllegalStateException e) {
            e.getStackTrace();
            return "users/signUp";
        }
        return "redirect:/";
    }

    @PostMapping({"/validateDuplicateEmail"})
    public String validateDuplicateEmail(String mail){
        if(userService.validateDuplicateEmail(mail)){
            return "forward:/mail";
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 가입된 이메일입니다.");    }
}
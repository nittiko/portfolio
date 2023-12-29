package com.sample.FreeCommunity.user.controller;

import com.sample.FreeCommunity.mail.service.MailService;
import com.sample.FreeCommunity.user.DTO.SignInFormDTO;
import com.sample.FreeCommunity.user.entity.UserEntity;
import com.sample.FreeCommunity.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@Controller
@RequiredArgsConstructor
@RequestMapping({"/signIn"})
public class SignInController {

    private final UserService userService;
    private final MailService mailService;
    private final Argon2PasswordEncoder passwordEncoder;

    @GetMapping
    public String signInMenu( Model model) {
        model.addAttribute("SignInFormDTO", new SignInFormDTO());
        return "users/signIn";
    }

    @PostMapping
    public String signIn(@Valid SignInFormDTO signInFormDTO,
                                BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "users/signIn";
        }

        try {
            UserEntity user = UserEntity.SignInUser(signInFormDTO, passwordEncoder);
            signInFormDTO.emailValidation();
            signInFormDTO.passwordCheck();
            userService.saveUser(user);
        } catch (IllegalStateException e) {
            e.getStackTrace();
            return "users/signIn";
        }
        return "redirect:/";
    }

    @PostMapping({"/validateDuplicateEmail"})
    public String validateDuplicateEmail(String email){
        System.out.println("email " + email );
        System.out.println("userService.validateDuplicateEmail " + userService.validateDuplicateEmail(email));
        if(userService.validateDuplicateEmail(email)){
            int number = mailService.sendMail(email);
            return String.valueOf(number);
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 가입된 이메일입니다.");    }
}
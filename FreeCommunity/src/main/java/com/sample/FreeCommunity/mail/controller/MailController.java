package com.sample.FreeCommunity.mail.controller;

import com.sample.FreeCommunity.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @ResponseBody
    @PostMapping("/mail")
    public String sendMail(String mail) {
        int number = mailService.sendMail(mail);
        String num = "" + number;
        return num;
    }
}
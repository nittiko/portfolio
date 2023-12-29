package com.sample.FreeCommunity.board.bookReading.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping({"/bookReading"})
@Controller
public class BookReadingController {

    @GetMapping({"/"})
    public String bookReadingHome() {
        return "boards/bookReading";
    }
}

package com.dinchan.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController

public class HomeController {
    public String HomeController() {
        return "Welcome to Dinchan";
    }
}

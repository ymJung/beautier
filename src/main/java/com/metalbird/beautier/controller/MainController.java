package com.metalbird.beautier.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController {

    @GetMapping("/")
    public ModelAndView main() {
        return new ModelAndView("/price/index");
    }
}

package com.pedrik.validator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/validate/cpf/{document}")
    public void validateCpf(@PathVariable String document) {

    }

    @PostMapping("/validate/rg/{document}")
    public void validateRg(@PathVariable String document) {

    }
}

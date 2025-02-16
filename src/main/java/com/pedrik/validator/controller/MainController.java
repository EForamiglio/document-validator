package com.pedrik.validator.controller;

import com.pedrik.validator.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @Autowired
    private ValidatorService validatorService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/validate/{document}")
    public ResponseEntity<Boolean> validateCpf(@PathVariable String document) {
        boolean valid = validatorService.run(document);
        return ResponseEntity.ok(valid);
    }
}

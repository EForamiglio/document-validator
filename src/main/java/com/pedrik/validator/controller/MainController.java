package com.pedrik.validator.controller;

import com.pedrik.validator.controller.dto.DocumentDto;
import com.pedrik.validator.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MainController {

    @Autowired
    private ValidatorService validatorService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateDocument(@RequestBody DocumentDto request) {
        boolean valid = validatorService.run(request.getDocument(), request.getType());
        return ResponseEntity.ok(valid);
    }
}

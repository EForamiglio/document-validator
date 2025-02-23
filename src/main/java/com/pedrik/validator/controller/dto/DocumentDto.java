package com.pedrik.validator.controller.dto;

import lombok.Getter;

@Getter
public class DocumentDto {
    private String document;
    private String type;

    public String getDocument() {
        return document;
    }

    public String getType() {
        return type;
    }
}

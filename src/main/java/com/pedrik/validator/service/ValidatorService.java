package com.pedrik.validator.service;

import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class ValidatorService {

    private int count = 0;
    private boolean stateFinal = false;

    public boolean run(final String document) {
        try {
            validate(document.replace(".", ""));

            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public void validate(final String document) {
        document.toCharArray();

        checkNumsLogic();
    }

    private void checkNumsLogic() {

    }

    private void checkCharacters(final char symbol) {

    }
}

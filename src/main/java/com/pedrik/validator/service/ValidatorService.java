package com.pedrik.validator.service;

import com.pedrik.validator.exception.InvalidDocumentExeception;
import org.springframework.stereotype.Service;

@Service
public class ValidatorService {

    private int sumRg;
    private int poundRg;

    public boolean run(final String document) {
        try {
            sumRg = 0;
            poundRg = 2;

            isValidDocument(document);

            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private void isValidDocument(String document) throws Exception {
        String cleanDoc = document.replace(".", "").replace("-", "");

        if(document.length() == 9) {
            isValidRG(cleanDoc, 0);
        } else if (document.length() == 11) {
            isValidCpf();
        } else {
            throw new InvalidDocumentExeception("Documento com muitos caracteres");
        }
    }

    public boolean isValidRG(String rg, int index) {


        if (index == rg.length()-1) {
            int dv = (sumRg %11 == 0) ? 0 : 11-(sumRg %11);

            if(dv == 10 && String.valueOf(rg.charAt(8)).toLowerCase().equals("x")) {
                return true;
            }else if (dv == Integer.parseInt(String.valueOf(rg.charAt(8)))) {
                return true;
            }

            return false;
        }

        char currentChar = rg.charAt(index);

        switch (currentChar) {
            case '0': case '1': case '2': case '3': case '4':
            case '5': case '6': case '7': case '8': case '9':
                if (index !=8) {
                    sumRg += Integer.parseInt(String.valueOf(currentChar)) * poundRg;
                    poundRg++;
                }
                return isValidRG(rg, index + 1);

            default:
                return false;
        }
    }

    private void isValidCpf() {
    }
}

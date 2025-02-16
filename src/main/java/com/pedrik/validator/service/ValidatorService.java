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

            return isValidDocument(document);
        } catch (Exception ex) {
            return false;
        }
    }

    private boolean isValidDocument(String document) throws Exception {
        String cleanDoc = document.replace(".", "").replace("-", "");

        if(cleanDoc.length() == 9) {
            return isValidRG(cleanDoc, 0);
        } else if (cleanDoc.length() == 11) {
            return isValidCpf();
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

    private boolean isValidCpf() {
        return false;
    }
}

package com.pedrik.validator.service;

import org.springframework.stereotype.Service;

import com.pedrik.validator.exception.InvalidDocumentExeception;

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

        if (cleanDoc.length() == 9) {
            return isValidRG(cleanDoc, 0);
        } else if (cleanDoc.length() == 11) {
            return isValidCpf(cleanDoc);
        } else {
            throw new InvalidDocumentExeception("Documento com muitos caracteres");
        }
    }

    public boolean isValidRG(String rg, int index) {
        if (index == rg.length() - 1) {
            int dv = (sumRg % 11 == 0) ? 0 : 11 - (sumRg % 11);

            if (dv == 10 && String.valueOf(rg.charAt(8)).toLowerCase().equals("x")) {
                return true;
            } else if (dv == Integer.parseInt(String.valueOf(rg.charAt(8)))) {
                return true;
            }

            return false;
        }

        char currentChar = rg.charAt(index);

        switch (currentChar) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                if (index != 8) {
                    sumRg += Integer.parseInt(String.valueOf(currentChar)) * poundRg;
                    poundRg++;
                }
                return isValidRG(rg, index + 1);

            default:
                return false;
        }
    }

    public boolean isValidCpf(String cpf) {
        State state = State.START;
        int sum1 = 0, sum2 = 0;
        int checkDigit1 = -1, checkDigit2 = -1;
        int[] weights1 = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weights2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        while (true) {
            switch (state) {
                case START:
                    if (cpf.length() != 11) {
                        state = State.INVALID;
                    } else {
                        state = State.LENGTH_CHECK;
                    }
                    break;

                case LENGTH_CHECK:
                    if (cpf.matches("(\\d)\\1{10}")) {
                        state = State.INVALID;
                    } else {
                        state = State.REPEATED_DIGITS_CHECK;
                    }
                    break;

                case REPEATED_DIGITS_CHECK:
                    for (int i = 0; i < 9; i++) {
                        int digit = Character.getNumericValue(cpf.charAt(i));
                        sum1 += digit * weights1[i];
                        sum2 += digit * weights2[i];
                    }
                    state = State.CALCULATE_SUM;
                    break;

                case CALCULATE_SUM:
                    checkDigit1 = (sum1 % 11 < 2) ? 0 : 11 - (sum1 % 11);
                    sum2 += checkDigit1 * weights2[9];
                    checkDigit2 = (sum2 % 11 < 2) ? 0 : 11 - (sum2 % 11);
                    state = State.CHECK_DIGITS;
                    break;

                case CHECK_DIGITS:
                    if (checkDigit1 == Character.getNumericValue(cpf.charAt(9))
                            && checkDigit2 == Character.getNumericValue(cpf.charAt(10))) {
                        state = State.VALID;
                    } else {
                        state = State.INVALID;
                    }
                    break;

                case VALID:
                    return true;

                case INVALID:
                    return false;
            }
        }
    }

    private enum State {
        START, LENGTH_CHECK, REPEATED_DIGITS_CHECK, CALCULATE_SUM, CHECK_DIGITS, VALID, INVALID
    }
}

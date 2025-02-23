package com.pedrik.validator.service;

import org.springframework.stereotype.Service;

@Service
public class ValidatorService {

    private int sumRg = 0;
    private int poundRg = 2;

    public boolean run(final String document, final String type) {
        try {
            return isValidDocument(document, type);
        } catch (Exception ex) {
            return false;
        }
    }

    private boolean isValidDocument(String document, String type) {
        if(type.equalsIgnoreCase("rg")) {
            return isValidRG(document);
        } else {
            return isValidCpf(document);
        }
    }

    public boolean isValidRG(String document) {
        sumRg = 0;
        poundRg = 2;
        RgState currentState = RgState.Q0;
        boolean hasFirstPoint = false;
        boolean hasSecondPoint = false;
        boolean hasFinalScore = false;

        for (char c : document.toCharArray()) {
            switch (currentState) {
                case Q0:
                    currentState = (Character.isDigit(c)) ? RgState.Q1 : RgState.ERROR;
                    incrementRgSum(c);
                    break;
                case Q1:
                    currentState = (Character.isDigit(c)) ? RgState.Q2 : RgState.ERROR;
                    incrementRgSum(c);
                    break;
                case Q2:
                    if (Character.isDigit(c)) {
                        currentState = RgState.Q3;
                        incrementRgSum(c);
                    } else if (c == '.' && !hasFirstPoint) {
                        currentState = RgState.Q2;
                        hasFirstPoint = true;
                    } else {
                        currentState = RgState.ERROR;
                    }
                    break;
                case Q3:
                    currentState = (Character.isDigit(c)) ? RgState.Q4 : RgState.ERROR;
                    incrementRgSum(c);
                    break;
                case Q4:
                    currentState = (Character.isDigit(c)) ? RgState.Q5 : RgState.ERROR;
                    incrementRgSum(c);
                    break;
                case Q5:
                    if (Character.isDigit(c)) {
                        currentState = RgState.Q6;
                        incrementRgSum(c);
                    } else if (c == '.' && !hasSecondPoint && hasFirstPoint) {
                        currentState = RgState.Q5;
                        hasSecondPoint = true;
                    } else {
                        currentState = RgState.ERROR;
                    }
                    break;
                case Q6:
                    currentState = (Character.isDigit(c)) ? RgState.Q7 : RgState.ERROR;
                    incrementRgSum(c);
                    break;
                case Q7:
                    currentState = (Character.isDigit(c)) ? RgState.Q8 : RgState.ERROR;
                    incrementRgSum(c);
                    break;
                case Q8:
                    if (Character.isDigit(c)) {
                        currentState = RgState.Q9;
                    } else if (c == '-' && !hasFinalScore && hasSecondPoint) {
                        currentState = RgState.Q8;
                        hasFinalScore = true;
                    } else {
                        currentState = RgState.ERROR;
                    }
                    break;
                default:
                    currentState = RgState.ERROR;
            }

            if (currentState == RgState.ERROR) break;
        }

        if(hasSecondPoint && !hasFinalScore) {
            return false;
        }

        if (currentState == RgState.Q9) {
            int dv = (sumRg % 11 == 0) ? 0 : 11 - (sumRg % 11);

            if (dv == 10 && String.valueOf(document.charAt(8)).equalsIgnoreCase("x")) {
                return true;
            } else if (dv == Integer.parseInt(String.valueOf(document.charAt(document.length()-1)))) {
                return true;
            }
        }
        return false;
    }

    public void incrementRgSum(char c) {
        sumRg += Integer.parseInt(String.valueOf(c)) * poundRg;
        poundRg++;
    }

    public boolean isValidCpf(String cpf) {
        State state = State.REPEATED_DIGITS_CHECK;
        int sum1 = 0, sum2 = 0;
        int checkDigit1 = -1, checkDigit2 = -1;
        int[] weights1 = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weights2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        while (true) {
            switch (state) {
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
        REPEATED_DIGITS_CHECK, CALCULATE_SUM, CHECK_DIGITS, VALID, INVALID
    }

    private enum RgState {
        Q0, Q1, Q2, Q3, Q4, Q5, Q6, Q7, Q8, Q9, ERROR
    }
}

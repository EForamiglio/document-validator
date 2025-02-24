package com.pedrik.validator.service;

import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class ValidatorServiceTest {

    private final ValidatorService validatorService = new ValidatorService();

    @ParameterizedTest
    @MethodSource("validRgs")
    @DisplayName("Deve retornar true para RG válidos")
    void shouldReturnTrueForValidRg(String document) {
        assertTrue(validatorService.run(document, "RG"));
    }

    @ParameterizedTest
    @MethodSource("invalidRgs")
    @DisplayName("Deve retornar false para RG inválidos")
    void shouldReturnFalseForInvalidRg(String document) {
        assertFalse(validatorService.run(document, "RG"));
    }

    @ParameterizedTest
    @MethodSource("generateCpf")
    @DisplayName("Deve retornar true para CPF válidos")
    void shouldReturnTrueForValidCpf(String document) {
        assertTrue(validatorService.run(document, "CPF"));
    }

    @ParameterizedTest
    @MethodSource("generateCpf")
    @DisplayName("Deve retornar false para CPF inválidos")
    void shouldReturnFalseForInvalidCpf(String document) {
        assertFalse(validatorService.run(document, "CPF"));
    }

    static Stream<String> validRgs() {
        return Stream.of(
                "369020935", "154325995", "366359836", "176709514",
                "118780207", "460230566", "38.986.556-4", "37.551.083-7",
                "32.177.020-1", "23.398.080-5", "50.787.390-7", "32.660.693-2"
        );
    }

    static Stream<String> invalidRgs() {
        return Stream.of("25.625.AAA-0", "37.700.000-X", "293285222", "18362", "37.787067-5", "37787.067-5",
                "37.787.0675", "12.345.678-9", "12345678-0", "12.345678-9", "12.345.6789", "123.456.789-0",
                "12.345.678-90", "12.345.67-89", "12.345.678-XX", "12.345.678-0A", "12.345.678-0", "12.345.678-000");
    }

    static Stream<String> generateCpf() {
        return Stream.generate(() -> {
            Random rand = new Random();
            boolean formatado = rand.nextBoolean();

            int num1 = rand.nextInt(1000);
            int num2 = rand.nextInt(1000);
            int num3 = rand.nextInt(1000);

            String n1 = String.format("%03d", num1);
            String n2 = String.format("%03d", num2);
            String n3 = String.format("%03d", num3);

            String nums = n1 + n2 + n3;
            int dig1 = cpfDigit(nums, -1);
            int dig2 = cpfDigit(nums, dig1);

            if (formatado) {
                return String.format("%s.%s.%s-%d%d", n1, n2, n3, dig1, dig2);
            } else {
                return n1 + n2 + n3 + dig1 + dig2;
            }
        }).limit(120);
    }

    private static int cpfDigit(String nums, int n4) {
        StringBuilder numList = new StringBuilder(nums);
        if (n4 != -1) {
            numList.append(n4);
        }

        int x = 0;
        int j = 0;
        for (int i = (n4 != -1 ? 11 : 10); i >= 2; i--, j++) {
            x += Character.getNumericValue(numList.charAt(j)) * i;
        }

        int y = x % 11;
        return (y < 2) ? 0 : 11 - y;
    }

}

package com.pedrik.validator.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    @MethodSource("validCpfs")
    @DisplayName("Deve retornar true para CPF válidos")
    void shouldReturnTrueForValidCpf(String document) {
        assertTrue(validatorService.run(document, "CPF"));
    }

    @ParameterizedTest
    @MethodSource("invalidCpfs")
    @DisplayName("Deve retornar false para CPF inválidos")
    void shouldReturnFalseForInvalidCpf(String document) {
        assertFalse(validatorService.run(document, "CPF"));
    }

    static Stream<String> validRgs() {
        return Stream.of("25.625.843-0", "37.787.067-5", "293285639", "183622534");
    }

    static Stream<String> invalidRgs() {
        return Stream.of("25.625.AAA-0", "37.700.000-X", "293285222", "18362", "37.787067-5", "37787.067-5",
                "37.787.0675");
    }

    static Stream<String> validCpfs() {
        return Stream.of("207.621.060-18", "16799662009", "46248418080");
    }

    static Stream<String> invalidCpfs() {
        return Stream.of("43018578033", "046.961.388-11");
    }
}


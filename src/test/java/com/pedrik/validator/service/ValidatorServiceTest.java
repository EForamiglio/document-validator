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
    @MethodSource("validDocuments")
    @DisplayName("Deve retornar true para documentos válidos")
    void shouldReturnTrueForValidDocument(String document) {
        assertTrue(validatorService.run(document));
    }

    @ParameterizedTest
    @MethodSource("invalidDocuments")
    @DisplayName("Deve retornar false para documentos inválidos")
    void shouldReturnFalseForInvalidDocument(String document) {
        assertFalse(validatorService.run(document));
    }

    // Valid documents source
    static Stream<String> validDocuments() {
        return Stream.of("25.625.843-0", "37.787.067-5", "293285639", "183622534", /*RG*/
              /*CPF*/  "207.621.060-18", "16799662009", "46248418080");
    }

    // Invalid documents source
    static Stream<String> invalidDocuments() {
        return Stream.of("25.625.AAA-0", "37.700.000-X", "293285222", "18362", /*RG*/
               /*CPF*/ "43018578033", "046.961.388-11");
    }
}


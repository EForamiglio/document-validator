package com.pedrik.validator.service;

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

    static Stream<String> validCpfs() {
        return Stream.of(
            "812.337.770-33",
            "322.569.870-94",
            "452.465.880-76",
            "182.072.660-66",
            "687.844.490-04",
            "876.013.630-82",
            "961.901.790-02",
            "325.291.520-04",
            "432.393.050-03",
            "71197227008",
            "39565039022",
            "26289206036",
            "71821901096",
            "72646061048",
            "61668231000",
            "60551095067",
            "95555362013",
            "81396410066",
            "01602323097",
            "31917787057"
        );
    }
    
    static Stream<String> invalidCpfs() {
        return Stream.of(
                "812.37.770-33",
            "322.569.870-95",
            "452.465.880-AA",
            "182.072.660.66",
            "687.84.490-04",
            "876.013.63-82",
            "961.901.790-s2",
            "325.21.520-01",
            "432.393.050-3",
            "7119722700ZZ8",
            "395650390A2",
            "26289206035",
            "71821901016",
            "72646061028",
            "616682310V0",
            "60551095017",
            "95555362043",
            "81396410Q60",
            "01602323091",
            "3191778AA58"
        );
    }

}
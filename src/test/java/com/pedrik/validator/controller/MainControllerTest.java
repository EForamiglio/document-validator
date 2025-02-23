package com.pedrik.validator.controller;

import com.pedrik.validator.service.ValidatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MainControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ValidatorService validatorService;

    @InjectMocks
    private MainController mainController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
    }

    @Test
    void shouldReturnTrueForValidDocument() throws Exception {
        String document = "12345678909";

        when(validatorService.run(document, any(String.class))).thenReturn(true);

        mockMvc.perform(post("/validate/{document}", document))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void shouldReturnFalseForInvalidDocument() throws Exception {
        String document = "11111111111";

        when(validatorService.run(document, any(String.class))).thenReturn(false);

        mockMvc.perform(post("/validate/{document}", document))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
}


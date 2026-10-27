package com.example.apimiddlepractice.controller;

import com.example.apimiddlepractice.exception.handler.GlobalExceptionHandler;
import com.example.apimiddlepractice.repository.HouseRepository;
import com.example.apimiddlepractice.service.HouseService;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class HouseSummaryControllerTests {
    private final HouseRepository repository = mock(HouseRepository.class);
    private final HouseService service = new HouseService(repository);
    private final MockMvc mvc = MockMvcBuilders.standaloneSetup(new HouseController(service))
            .setControllerAdvice(new GlobalExceptionHandler()).build();

    @Test
    void monthlyEndpointReturnsJson() throws Exception {
        when(repository.selectCategorySummary(any(), any())).thenReturn(List.of());
        mvc.perform(get("/api/houses/summary/monthly").param("month", "2026-10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.month").value("2026-10"))
                .andExpect(jsonPath("$.income").value(0))
                .andExpect(jsonPath("$.expense").value(0))
                .andExpect(jsonPath("$.balance").value(0));
    }

    @Test
    void categoriesEndpointReturnsEmptyArrayForEmptyMonth() throws Exception {
        when(repository.selectCategorySummary(any(), any())).thenReturn(List.of());
        mvc.perform(get("/api/houses/summary/categories").param("month", "2026-10"))
                .andExpect(status().isOk()).andExpect(content().json("[]"));
    }

    @Test
    void bothEndpointsRejectMissingOrInvalidMonth() throws Exception {
        for (String endpoint : List.of("monthly", "categories")) {
            mvc.perform(get("/api/houses/summary/" + endpoint))
                    .andExpect(status().isBadRequest());
            mvc.perform(get("/api/houses/summary/" + endpoint).param("month", "2026-13"))
                    .andExpect(status().isBadRequest());
        }
        verifyNoInteractions(repository);
    }
}

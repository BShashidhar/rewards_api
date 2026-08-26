package com.charter.rewards.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.YearMonth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/** Integration tests for the HTTP rewards endpoint and seeded database. */
@SpringBootTest
@AutoConfigureMockMvc
class RewardsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void returnsMonthlyAndTotalRewardsForSeededCustomer() throws Exception {
        LocalDate currentDate = LocalDate.now();

        mockMvc.perform(get("/api/rewards/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("Alice Johnson"))
                .andExpect(jsonPath("$.monthlyRewards", hasSize(3)))
            .andExpect(jsonPath("$.monthlyRewards[0].month")
                .value(YearMonth.from(currentDate.minusMonths(2)).toString()))
                .andExpect(jsonPath("$.monthlyRewards[0].points").value(115))
                .andExpect(jsonPath("$.totalPoints").value(365));
    }

    @Test
    void returnsNotFoundForUnknownCustomer() throws Exception {
        mockMvc.perform(get("/api/rewards/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Customer not found: 999"));
    }

    @Test
    void rejectsNonPositiveCustomerId() throws Exception {
        mockMvc.perform(get("/api/rewards/0"))
                .andExpect(status().isBadRequest());
    }
}

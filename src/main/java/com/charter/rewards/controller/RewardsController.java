package com.charter.rewards.controller;

import com.charter.rewards.dto.RewardsResponse;
import com.charter.rewards.service.RewardsService;
import jakarta.validation.constraints.Positive;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Exposes customer rewards through a RESTful HTTP endpoint. */
@RestController
@RequestMapping("/api/rewards")
@Validated
@Tag(name = "Rewards", description = "Customer rewards point operations")
public class RewardsController {

    private final RewardsService rewardsService;

    /** Creates a controller backed by the rewards service. */
    public RewardsController(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    /** Returns monthly and total points for a customer. */
    @GetMapping("/{customerId}")
        @Operation(summary = "Get customer rewards", description = "Returns points grouped by transaction month and the total.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rewards returned successfully"),
            @ApiResponse(responseCode = "400", description = "Customer ID must be positive"),
            @ApiResponse(responseCode = "404", description = "Customer was not found")
        })
        public ResponseEntity<RewardsResponse> getRewards(
            @Parameter(description = "Customer identifier", example = "1")
            @PathVariable @Positive Long customerId) {
        return ResponseEntity.ok(rewardsService.getRewards(customerId));
    }
}

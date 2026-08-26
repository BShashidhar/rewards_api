package com.charter.rewards.service;

import com.charter.rewards.dto.RewardsResponse;
import java.math.BigDecimal;

/** Defines customer reward calculation operations. */
public interface RewardsService {

    /** Returns monthly and total reward points for a customer. */
    RewardsResponse getRewards(Long customerId);

    /** Calculates reward points for one purchase amount. */
    int calculatePoints(BigDecimal amount);
}

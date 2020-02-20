package com.artemsirosh.hitbtc.view;

import com.artemsirosh.hitbtc.model.Period;
import lombok.*;

import java.time.Instant;
import java.util.Optional;

/**
 * Created on 04 Jan, 2020.
 *
 * @author Artemis A. Sirosh
 */
@AllArgsConstructor
@Value
public class CandleFilter {

    private Period period;
    private Instant from;
    private Instant till;

    public Optional<Period> getPeriod() {
        return Optional.ofNullable(period);
    }
}

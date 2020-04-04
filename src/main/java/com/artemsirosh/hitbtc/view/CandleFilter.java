package com.artemsirosh.hitbtc.view;

import com.artemsirosh.hitbtc.model.Period;
import lombok.*;

import java.time.Instant;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * Created on 04 Jan, 2020.
 *
 * Allows to filter candle by:
 * <ul>
 *     <li>{@link CandleFilter#period} -- candle period</li>
 *     <li>{@link CandleFilter#from} -- since date-time</li>
 *     <li>{@link CandleFilter#till} -- date-time until</li>
 * </ul>
 *
 * @author Artemis A. Sirosh
 */
@AllArgsConstructor
@Value
public class CandleFilter {

    public final static BiFunction<CandleFilter, Period, CandleFilter> periodModifier =
        (filter, value) -> new CandleFilter(value, filter.from, filter.till);

    public final static BiFunction<CandleFilter, Instant, CandleFilter> fromDTModifier =
        (filter, value) -> new CandleFilter(filter.period, value, filter.till);

    public final static BiFunction<CandleFilter, Instant, CandleFilter> tillDTModifier =
        (filter, value) -> new CandleFilter(filter.period, filter.from, value);

    Period period;
    Instant from;
    Instant till;
}

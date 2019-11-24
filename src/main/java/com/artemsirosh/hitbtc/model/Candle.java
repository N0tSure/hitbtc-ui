package com.artemsirosh.hitbtc.model;

import lombok.Data;

import java.time.Instant;

/**
 * Created on 24 Nov, 2019.
 *
 * Candles are used for representation of a specific symbol as
 * <a href="https://en.wikipedia.org/wiki/Open-high-low-close_chart">OHLC</a>
 * chart.
 *
 * @author Artemis A. Sirosh
 */
@Data
public class Candle {

    /**
     * Candle's timestamp. This is timestamp represent a candle's opening.
     */
    private final Instant timestamp;

    /**
     * Candle's open price.
     */
    private final String open;

    /**
     * Candle's closing price.
     */
    private final String close;

    /**
     * Lowest price for the period.
     */
    private final String min;

    /**
     * Highest price for the period.
     */
    private final String max;

    /**
     * Volume in base currency.
     */
    private final String volume;

    /**
     * Volume in quote currency.
     */
    private final String volumeQuote;
}

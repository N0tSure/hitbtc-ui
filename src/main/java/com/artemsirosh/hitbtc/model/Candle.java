package com.artemsirosh.hitbtc.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Created on 24 Nov, 2019.
 *
 * Candles are using for representation of a specific symbol as
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
    private Instant timestamp;

    /**
     * Candle's open price.
     */
    private String open;

    /**
     * Candle's closing price.
     */
    private String close;

    /**
     * The lowest price for the period.
     */
    private String min;

    /**
     * The highest price for the period.
     */
    private String max;

    /**
     * Volume in base currency.
     */
    private String volume;

    /**
     * Volume in quote currency.
     */
    private String volumeQuote;
}

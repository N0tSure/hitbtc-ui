package com.artemsirosh.hitbtc.model;

/**
 * Created on 24 Nov, 2019.
 *
 * Temporal periods. This is the next periods:
 * <ul>
 *     <li>M1 -- one minute</li>
 *     <li>M3 -- three minutes</li>
 *     <li>M5 -- five minutes</li>
 *     <li>M15 -- fifteen minutes</li>
 *     <li>M30 -- half hour</li>
 *     <li>H1 -- one hour</li>
 *     <li>H4 -- four hour</li>
 *     <li>D1 -- one hour</li>
 *     <li>D7 -- one week</li>
 *     <li>1M -- one month</li>
 * </ul>
 *
 * @author Artemis A. Sirosh
 */
public enum Period {
    M1, M3, M5, M15, M30, H1, H4, D1, D7, MO("1M");

    private final String value;

    Period(String value) {
        this.value = value;
    }

    Period() {
        this.value = this.name();
    }

    @Override
    public String toString() {
        return value;
    }
}

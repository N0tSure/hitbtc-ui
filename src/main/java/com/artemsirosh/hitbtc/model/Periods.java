package com.artemsirosh.hitbtc.model;

/**
 * Created on 24 Nov, 2019.
 *
 * Temporal periods.
 *
 * @author Artemis A. Sirosh
 */
public enum Periods {
    M1, M3, M5, M15, M30, H1, H4, D1, D7, MO("1M");

    private final String value;

    Periods(String value) {
        this.value = value;
    }

    Periods() {
        this.value = this.name();
    }

    @Override
    public String toString() {
        return value;
    }
}

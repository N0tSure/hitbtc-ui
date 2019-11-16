package com.artemsirosh.hitbtc.model;

import lombok.Data;

/**
 * Created on 16 Nov, 2019.
 *
 * Currency symbols (<a href="http://www.investopedia.com/terms/c/currencypair.asp">currency pairs</a>)
 * traded on exchange. The first listed currency of a symbol is called the base
 * currency, and the second currency is called the quote currency. The currency
 * pair indicates how much of the quote currency is needed to purchase one unit
 * of the base currency.
 *
 * See in a HitBTC <a href="https://api.hitbtc.com/#symbols>API Docs</a>.
 *
 * @author Artemis A. Sirosh
 */
@Data
public class Symbol {

    /**
     * Symbol (currency pair) identifier, for example, {@code ETHBTC}.
     * Note: description will simply use symbol in the future.
     */
    private String id;

    /**
     * Name (code) of base currency, for example, {@code ETH}.
     */
    private String baseCurrency;

    /**
     * Name of quote currency.
     */
    private String quoteCurrency;

    /**
     * Symbol quantity should be divided by this value without residue.
     */
    private String quantityIncrement;

    /**
     * Symbol price should be divided by this value without residue.
     */
    private String tickSize;

    /**
     * Default fee rate.
     */
    private String takeLiquidityRate;

    /**
     * Default fee rate for market making trades.
     */
    private String provideLiquidityRate;

    /**
     * Name of currency value of charged fee.
     */
    private String feeCurrency;

}

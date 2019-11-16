package com.artemsirosh.hitbtc;

import com.artemsirosh.hitbtc.client.SymbolClient;
import com.artemsirosh.hitbtc.model.Symbol;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 16 Nov, 2019.
 *
 * @author Artemis A. Sirosh
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class SymbolClientIT {

    @Autowired
    private SymbolClient symbolClient;

    @Test
    void shouldReturnUSDollarBTC() {
        final Symbol btcUsd = symbolClient.getSymbol("BTCUSD");

        assertThat(btcUsd).isNotNull();
        assertThat(btcUsd).extracting(Symbol::getBaseCurrency).isEqualTo("BTC");
        assertThat(btcUsd).extracting(Symbol::getQuoteCurrency).isEqualTo("USD");
    }

    @Test
    void shouldReturnSymbols() {
        final List<Symbol> symbols = symbolClient.getSymbols();

        assertThat(symbols).isNotNull();
        assertThat(symbols).isNotEmpty();
        assertThat(symbols).hasSizeGreaterThan(100);

    }
}

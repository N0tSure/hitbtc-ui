package com.artemsirosh.hitbtc.client;

import com.artemsirosh.hitbtc.model.Candle;
import com.artemsirosh.hitbtc.model.Periods;
import com.artemsirosh.hitbtc.model.SortDirection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 24 Nov, 2019.
 *
 * @author Artemis A. Sirosh
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class CandleClientIT {

    @Autowired
    private CandleClient candleClient;

    @Test
    void shouldReturnEtheriumBitcoinCandles() {
        List<Candle> candles = candleClient.getCandles(
            "ETHBTC", Periods.M30, SortDirection.ASC,
            Instant.parse("2019-11-17T00:00:00.000Z"), Instant.parse("2019-11-17T12:00:00.000Z"),
            1, 0);

        assertThat(candles).isNotEmpty();
        assertThat(candles).hasSize(1);
        assertThat(candles.get(0)).extracting(Candle::getVolume).isEqualTo("219.7503");
        assertThat(candles.get(0)).extracting(Candle::getVolumeQuote).isEqualTo("4.7245377077");
    }
}

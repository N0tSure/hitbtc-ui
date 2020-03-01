package com.artemsirosh.hitbtc.client;

import com.artemsirosh.hitbtc.model.Candle;
import com.artemsirosh.hitbtc.model.Period;
import com.artemsirosh.hitbtc.model.SortDirection;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 24 Nov, 2019.
 *
 * Possible unavailability of {@code https://hitbtc.com} made this test brittle.
 * To avoid that before all test HTTP connection should be checked.
 *
 * @author Artemis A. Sirosh
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class CandleClientIT {

    private static final String ETHERIUM_TO_BTC = "ETHBTC";

    @Autowired
    private CandleClient candleClient;

    /**
     * Check HTTP connection to a HitBTC.
     */
    @BeforeAll
    static void checkInetConnection() {
        try {
            final URL hitbtcUrl = new URL("https://hitbtc.com");
            final HttpURLConnection connection = (HttpURLConnection) hitbtcUrl.openConnection();
            connection.connect();

            Assumptions.assumeTrue(
                connection.getResponseCode() == HttpURLConnection.HTTP_OK,
                "Connection to " + hitbtcUrl + " failed with status: " + connection.getResponseCode()
            );

            connection.disconnect();

        } catch (IOException exc) {
            Assumptions.assumeTrue(false, exc.getMessage());
        }
    }

    @Test
    void shouldReturnEtheriumBitcoinCandles() {
        List<Candle> candles = candleClient.getCandles(
            ETHERIUM_TO_BTC, Period.M30.toString(), SortDirection.ASC,
            Instant.parse("2019-11-17T00:00:00.000Z"), Instant.parse("2019-11-17T12:00:00.000Z"),
            1, 0);

        assertThat(candles).isNotEmpty();
        assertThat(candles).hasSize(1);
        assertThat(candles.get(0)).extracting(Candle::getVolume).isEqualTo("219.7503");
        assertThat(candles.get(0)).extracting(Candle::getVolumeQuote).isEqualTo("4.7245377077");
    }

    @Test
    void shouldReturnCandlesForMonth() {
        final Optional<Candle> monthCandle = candleClient.getCandles(
            ETHERIUM_TO_BTC, Period.MO.toString(), SortDirection.ASC,
            Instant.parse("2019-11-17T00:00:00.000Z"), Instant.parse("2019-12-17T00:00:00.000Z"),
            1, 0
        ).stream().findFirst();

        assertThat(monthCandle).isPresent();
    }

    @Test
    void shouldReturnLastCandles() {
        final List<Candle> lastCandles = candleClient.getCandles(
            ETHERIUM_TO_BTC, null, null, null, null, 100, 0
        );

        assertThat(lastCandles).isNotEmpty();
    }
}

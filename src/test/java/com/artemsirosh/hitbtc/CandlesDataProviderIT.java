package com.artemsirosh.hitbtc;

import com.artemsirosh.hitbtc.model.Candle;
import com.artemsirosh.hitbtc.model.Period;
import com.artemsirosh.hitbtc.view.CandleFilter;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * Created on 05 Apr, 2020.
 *
 * @author Artemis A. Sirosh
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CandlesDataProviderIT {

    private static final String BTC_USD_COIN = "BTCUSDC";

    @Autowired
    private BiFunction<Query<Candle, CandleFilter>, String, List<Candle>> candleDataProvider;

    @Test
    void shouldRetrieveThousandCandles() {
        final List<Candle> actual = candleDataProvider.apply(new Query<>(), BTC_USD_COIN);
        Assertions.assertThat(actual)
            .isNotNull()
            .hasSize(1000);
    }

    @Test
    void shouldRetrieveSecondPage() {
        final List<Candle> actual = candleDataProvider.apply(
            new Query<>(1000, 1000, Collections.emptyList(), null, null),
            BTC_USD_COIN
        );

        Assertions.assertThat(actual)
            .hasSize(1000);
    }

    @Test
    void shouldRetrieveAFirstMonthPeriodCandle() {
        final CandleFilter filter = new CandleFilter(Period.MO, null, null);
        final List<Candle> actual = candleDataProvider.apply(
            new Query<>(0, 1, Collections.emptyList(), null, filter),
            BTC_USD_COIN
        );

        Assertions.assertThat(actual)
            .hasSize(1).element(0)
            .matches(
                candle -> Instant.parse("2018-12-01T00:00:00.000Z").equals(candle.getTimestamp()),
                "Candle's timestamp"
            );
    }


    @Test
    void shouldSortCandlesInProperOrder() {
        final CandleFilter filter = new CandleFilter(Period.MO, null, null);
        final List<QuerySortOrder> sortOrders =
            Collections.singletonList(new QuerySortOrder("", SortDirection.DESCENDING));

        final Optional<Instant> newestInstant = candleDataProvider.apply(
            new Query<>(0, 1, sortOrders, null, filter),
            BTC_USD_COIN
        ).stream().map(Candle::getTimestamp).findFirst();

        final Optional<Instant> oldestCandles = candleDataProvider.apply(
            new Query<>(10, 1, sortOrders, null, filter),
            BTC_USD_COIN
        ).stream().map(Candle::getTimestamp).findFirst();

        Assumptions.assumeTrue(newestInstant.isPresent(), "Newest candles not retrieved");
        Assumptions.assumeTrue(oldestCandles.isPresent(), "Oldest candles not retrieved");

        Assertions.assertThat(newestInstant.get()).isAfter(oldestCandles.get());
    }
}

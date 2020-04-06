package com.artemsirosh.hitbtc;

import com.artemsirosh.hitbtc.client.CandleClient;
import com.artemsirosh.hitbtc.client.SymbolClient;
import com.artemsirosh.hitbtc.model.Candle;
import com.artemsirosh.hitbtc.model.Period;
import com.artemsirosh.hitbtc.model.SortDirection;
import com.artemsirosh.hitbtc.model.Symbol;
import com.artemsirosh.hitbtc.view.CandleFilter;
import com.artemsirosh.hitbtc.view.InstantPicker;
import com.vaadin.flow.data.provider.*;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@EnableFeignClients
@SpringBootApplication
public class HitBTCUiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HitBTCUiApplication.class, args);
    }

    @Bean @Qualifier("symbolsProvider")
    Supplier<List<Symbol>> symbolsProvider(SymbolClient symbolClient) {
        return symbolClient::getSymbols;
    }

    @Bean
    BiFunction<Query<Candle, CandleFilter>, String, List<Candle>> candleDataProvider(final CandleClient candleClient) {
        final Function<Query<Candle, CandleFilter>, SortDirection> sortingDirectionParser =
            query -> query.getSortOrders().stream()
                .map(SortOrder::getDirection)
                .map(SortDirection.modelConverter)
                .findFirst()
                .orElse(SortDirection.ASC);

        return  (query, symbol) -> {
            final CandleFilter filter = query.getFilter().orElse(new CandleFilter(null, null, null));
            return candleClient.getCandles(
                symbol,
                Optional.ofNullable(filter.getPeriod()).map(Period::toString).orElse(null),
                sortingDirectionParser.apply(query),
                filter.getFrom(),
                filter.getTill(),
                Math.min(query.getLimit(), 1000),
                query.getOffset()
            );
        };
    }

    @Bean
    @UIScope
    InstantPicker fromInstantPicker() {
        return new InstantPicker(
            "Since timestamp",
            (date, time, datePicker, timePicker) -> {
                datePicker.setMax(date);
                timePicker.setMax(time.toString());
            });
    }

    @Bean
    @UIScope
    InstantPicker tillInstantPicker() {
        return new InstantPicker(
            "Till timestamp",
            (date, time, datePicker, timePicker) -> {
                datePicker.setMin(date);
                timePicker.setMin(time.toString());
            });
    }

}

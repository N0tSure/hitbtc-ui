package com.artemsirosh.hitbtc.view;

import com.artemsirosh.hitbtc.client.CandleClient;
import com.artemsirosh.hitbtc.model.Candle;
import com.artemsirosh.hitbtc.model.Period;
import com.artemsirosh.hitbtc.model.SortDirection;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.*;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * Created at 21-11-2019
 *
 * @author Artem Sirosh 'Artem.Sirosh@t-systems.com'
 */
@Slf4j
@Tag("div")
@Route(layout = MainLayout.class, value = "candles")
@UIScope
@SpringComponent
public class CandlesView extends Composite<VerticalLayout> implements HasUrlParameter<String> {

    private final Consumer<String> symbolParamProcessor;
    private final CandleClient candleClient;

    private String symbol;

    public CandlesView(CandleClient candleClient, InstantPicker fromInstantPicker, InstantPicker tillInstantPicker) {
        this.candleClient = candleClient;
        this.symbolParamProcessor = s -> {};

        final ComboBox<Period> periodsSelect = new ComboBox<>();
        periodsSelect.setItems(Period.values());
        periodsSelect.setValue(Period.M30);
        periodsSelect.setLabel("Time periods");

        final HorizontalLayout filters = new HorizontalLayout();
        filters.add(periodsSelect, fromInstantPicker, tillInstantPicker);

//        final Grid<Candle> grid = new Grid<>(Candle.class);
//        grid.setColumns();
//        grid.addColumn(Candle::getTimestamp).setHeader("Opening Date");
//        grid.addColumn(Candle::getOpen).setHeader("Opening Price");
//        grid.addColumn(Candle::getClose).setHeader("Closing Price");
//        grid.addColumn(Candle::getMax).setHeader("Maximum Price");
//        grid.addColumn(Candle::getMin).setHeader("Minimum Price");
//        grid.addColumn(Candle::getVolume).setHeader("Base Currency Volume");
//        grid.addColumn(Candle::getVolumeQuote).setHeader("Quote Currency Volume");

        final BiFunction<Query<Candle, CandleFilter>, CandleFilter, List<Candle>> filterCandles = (query, filter) ->
            candleClient.getCandles(
                symbol,
                filter.getPeriod().orElse(Period.MO),
                query.getSortOrders().stream()
                    .map(QuerySortOrder::getDirection)
                    .map(SortDirection.modelConverter)
                    .findFirst()
                    .orElse(SortDirection.ASC),
                filter.getFrom(),
                filter.getTill(),
                query.getLimit(),
                query.getOffset()
            );

        final Function<Query<Candle, CandleFilter>, List<Candle>> getCandles = query -> candleClient.getCandles(
            symbol,
            Period.MO,
            SortDirection.ASC,
            null,
            null,
            query.getLimit(),
            query.getOffset()
        );

        final Function<Query<Candle, CandleFilter>, List<Candle>> queryCandlesByFilter = query ->
            query.getFilter()
                .map(filter -> filterCandles.apply(query, filter))
                .orElse(getCandles.apply(query));

        final IntBinaryOperator pageSizeEvaluator = Math::max;

        final CallbackDataProvider<Candle, CandleFilter> callbackDataProvider = DataProvider.fromFilteringCallbacks(
            query -> queryCandlesByFilter.apply(query).stream(),
            query -> pageSizeEvaluator.applyAsInt(queryCandlesByFilter.apply(query).size(), query.getLimit())
        );
        final ConfigurableFilterDataProvider<Candle, Void, CandleFilter> provider =
            callbackDataProvider.withConfigurableFilter();
//        filterDataProvider.setFilter();



        fromInstantPicker.addValueChangeListener(event -> new )

        final H3 symbolName = new H3();
        this.getContent().add(symbolName, filters);

    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        Optional.ofNullable(parameter)
            .ifPresent(symbolParamProcessor);
    }
}

package com.artemsirosh.hitbtc.view;

import com.artemsirosh.hitbtc.model.Candle;
import com.artemsirosh.hitbtc.model.Period;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
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
    private final ConfigurableFilterDataProvider<Candle, Void, CandleFilter> provider;

    private String symbol;
    private CandleFilter candleFilter;

    public CandlesView(
        BiFunction<Query<Candle, CandleFilter>, String, List<Candle>> candleDataProvider,
        InstantPicker fromInstantPicker, InstantPicker tillInstantPicker
    ) {

        final H3 symbolName = new H3();
        final Consumer<String> symbolNameUpdater = symbolName::setText;
        this.symbolParamProcessor = symbolNameUpdater.andThen(s -> this.symbol = s);

        final ComboBox<Period> periodsSelect = new ComboBox<>();
        periodsSelect.setItems(Period.values());
        periodsSelect.setValue(Period.MO);
        periodsSelect.setLabel("Time periods");
        periodsSelect.addValueChangeListener(event -> setCandleFilter(CandleFilter.periodModifier, event.getValue()));

        fromInstantPicker.addValueChangeListener(tillInstantPicker.getListener());
        fromInstantPicker.addValueChangeListener(
            event -> setCandleFilter(CandleFilter.fromDTModifier, event.getValue())
        );

        tillInstantPicker.addValueChangeListener(fromInstantPicker.getListener());
        tillInstantPicker.addValueChangeListener(
            event -> setCandleFilter(CandleFilter.tillDTModifier, event.getValue())
        );

        final HorizontalLayout filters = new HorizontalLayout();
        filters.add(periodsSelect, fromInstantPicker, tillInstantPicker);


        final CallbackDataProvider<Candle, CandleFilter> callbackDataProvider = DataProvider.fromFilteringCallbacks(
            query -> candleDataProvider.apply(loggingAround(query), symbol).stream(),
            query -> candleDataProvider.apply(query, symbol).size()
        );

        provider = callbackDataProvider.withConfigurableFilter();

        final Grid<Candle> grid = new Grid<>(Candle.class);
        grid.setColumns();
        grid.addColumn(Candle::getTimestamp).setKey("ts").setSortable(true).setHeader("Opening Date");
        grid.addColumn(Candle::getOpen).setHeader("Opening Price");
        grid.addColumn(Candle::getClose).setHeader("Closing Price");
        grid.addColumn(Candle::getMax).setHeader("Maximum Price");
        grid.addColumn(Candle::getMin).setHeader("Minimum Price");
        grid.addColumn(Candle::getVolume).setHeader("Base Currency Volume");
        grid.addColumn(Candle::getVolumeQuote).setHeader("Quote Currency Volume");
        grid.setDataProvider(provider);
        grid.setPageSize(100);

        this.getContent().add(symbolName, filters, grid);
    }

    private <T> void setCandleFilter(BiFunction<CandleFilter, T, CandleFilter> filterValueChanger, T value) {
        final CandleFilter filter = Optional.ofNullable(this.candleFilter)
            .orElseGet(() -> new CandleFilter(null, null, null));

        this.candleFilter = filterValueChanger.apply(filter, value);
        this.provider.setFilter(this.candleFilter);
        log.info("Update value: {}", value);
    }

    private Query<Candle, CandleFilter> loggingAround(Query<Candle, CandleFilter> query) {
        log.info(
            "Query offset: {}, limit: {}, sorts: {} and filter {}",
            query.getOffset(), query.getLimit(), query.getSortOrders(), query.getFilter()
        );
        return query;
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        Optional.ofNullable(parameter)
            .ifPresent(symbolParamProcessor);
    }

}

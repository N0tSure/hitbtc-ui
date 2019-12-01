package com.artemsirosh.hitbtc.view;

import com.artemsirosh.hitbtc.client.CandleClient;
import com.artemsirosh.hitbtc.model.Candle;
import com.artemsirosh.hitbtc.model.Periods;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

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

    public CandlesView(CandleClient candleClient) {
        this.candleClient = candleClient;
        this.symbolParamProcessor = s -> {};

        final ComboBox<Periods> periodsSelect = new ComboBox<>();
        periodsSelect.setItems(Periods.values());
        periodsSelect.setValue(Periods.M30);
        periodsSelect.setLabel("Time periods");



        final HorizontalLayout filters = new HorizontalLayout();
        filters.add(periodsSelect);

//        final Grid<Candle> grid = new Grid<>(Candle.class);
//        grid.setColumns();
//        grid.addColumn(Candle::getTimestamp).setHeader("Opening Date");
//        grid.addColumn(Candle::getOpen).setHeader("Opening Price");
//        grid.addColumn(Candle::getClose).setHeader("Closing Price");
//        grid.addColumn(Candle::getMax).setHeader("Maximum Price");
//        grid.addColumn(Candle::getMin).setHeader("Minimum Price");
//        grid.addColumn(Candle::getVolume).setHeader("Base Currency Volume");
//        grid.addColumn(Candle::getVolumeQuote).setHeader("Quote Currency Volume");

        final H3 symbolName = new H3();
        this.getContent().add(symbolName, filters);

    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        Optional.ofNullable(parameter)
            .ifPresent(symbolParamProcessor);
    }
}

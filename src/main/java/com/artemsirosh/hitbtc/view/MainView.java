package com.artemsirosh.hitbtc.view;

import com.artemsirosh.hitbtc.model.Symbol;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * Created on 17 Nov, 2019.
 *
 * @author Artemis A. Sirosh
 */
@Slf4j
@Route
@UIScope
@SpringComponent
public class MainView extends Composite<VerticalLayout> {

    public MainView(Symbols symbols, SymbolDetails details) {
        final HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        header.setPadding(true);
        header.add(new H1("HitBTC UI"));

        final VerticalLayout navBar = new VerticalLayout();
        navBar.setWidth("200px");

        final HorizontalLayout content = new HorizontalLayout();
        content.setWidth("100%");

        final HorizontalLayout center = new HorizontalLayout();
        center.setWidth("100%");
        center.add(navBar, content);
        center.setFlexGrow(1, navBar);

        final HorizontalLayout footer = new HorizontalLayout();
        footer.setWidth("100%");
        footer.setPadding(true);


        final Grid<Symbol> grid = new Grid<>(Symbol.class);
        final ComboBox<String> filterSelect = new ComboBox<>("Find symbol");
        filterSelect.setItems(symbols.getSymbols().stream().map(Symbol::getId));
        filterSelect.setClearButtonVisible(true);

        grid.setColumns();
        grid.addColumn(Symbol::getId).setComparator(Symbol::getId).setHeader(filterSelect);
        grid.addColumn(Symbol::getBaseCurrency).setComparator(Symbol::getBaseCurrency).setHeader("Base Currency");

        grid.addColumn(Symbol::getQuoteCurrency).setComparator(Symbol::getQuoteCurrency).setHeader("Quote Currency");
        ListDataProvider<Symbol> symbolListDataProvider = DataProvider.ofCollection(symbols.getSymbols());
        filterSelect.addValueChangeListener(event -> {
           final SerializablePredicate<Symbol> filter = Optional.ofNullable(event.getValue())
               .map(id -> (SerializablePredicate<Symbol>) (symbol -> symbol.getId().startsWith(id)))
               .orElse(symbol -> true);
           symbolListDataProvider.setFilter(filter);
        });
        grid.setDataProvider(symbolListDataProvider);
        grid.asSingleSelect().addValueChangeListener(event -> details.update(event.getValue()));

        final VerticalLayout table = new VerticalLayout();
        table.add(new H2("Symbols"), grid);

        content.add(table, details);

        getContent().setSizeFull();
        getContent().setPadding(false);
        getContent().setSpacing(false);
        getContent().add(header, center, footer);
        getContent().expand(center);

    }
}

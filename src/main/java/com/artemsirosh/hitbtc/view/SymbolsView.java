package com.artemsirosh.hitbtc.view;

import com.artemsirosh.hitbtc.model.Symbol;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created at 21-11-2019
 *
 * This view contains a table with available {@link Symbol}s.
 *
 * @author Artem Sirosh 'Artem.Sirosh@t-systems.com'
 */
@Tag("div")
@Route(layout = MainLayout.class, value = "")
@UIScope
@SpringComponent
public class SymbolsView extends Composite<HorizontalLayout> {

    public SymbolsView(
        @Qualifier("symbolsProvider") final Supplier<List<Symbol>> symbolsProvider,
        final SymbolDetails symbolDetails
    ) {
        final ListDataProvider<Symbol> symbolListDataProvider = DataProvider.ofCollection(symbolsProvider.get());

        final ComboBox<String> filterSelect = new ComboBox<>("Find symbol");
        filterSelect.setItems(symbolListDataProvider.fetch(new Query<>()).map(Symbol::getId));
        filterSelect.setClearButtonVisible(true);
        filterSelect.addValueChangeListener(event -> {
            final SerializablePredicate<Symbol> filter = Optional.ofNullable(event.getValue())
                .map(id -> (SerializablePredicate<Symbol>) (symbol -> symbol.getId().startsWith(id)))
                .orElse(symbol -> true);
            symbolListDataProvider.setFilter(filter);
        });

        final Grid<Symbol> grid = new Grid<>(Symbol.class);

        grid.setColumns();
        grid.addColumn(Symbol::getId).setComparator(Symbol::getId).setHeader(filterSelect);
        grid.addColumn(Symbol::getBaseCurrency).setComparator(Symbol::getBaseCurrency).setHeader("Base Currency");
        grid.addColumn(Symbol::getQuoteCurrency).setComparator(Symbol::getQuoteCurrency).setHeader("Quote Currency");

        grid.setDataProvider(symbolListDataProvider);
        grid.asSingleSelect().addValueChangeListener(event -> symbolDetails.update(event.getValue()));

        this.getContent().setWidth("100%");
        this.getContent().add(new VerticalLayout(new H2("Symbols"), grid), symbolDetails);
    }
}

package com.artemsirosh.hitbtc.view;

import com.artemsirosh.hitbtc.model.Symbol;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created on 17 Nov, 2019.
 *
 * Represents {@link Symbol} details pane.
 *
 * @author Artemis A. Sirosh
 */
@UIScope
@SpringComponent
public class SymbolDetails extends Composite<VerticalLayout> {

    private static final SymbolProperty[] SYMBOL_PROPERTIES = {
        new SymbolProperty(
            "Base Currency",
            "Name (code) of base currency",
            Symbol::getBaseCurrency
        ),
        new SymbolProperty(
            "Quote Currency",
            "Name of quote currency",
            Symbol::getQuoteCurrency
        ),
        new SymbolProperty(
            "Quantity Increment",
            "Symbol quantity should be divided by this value without residue",
            Symbol::getQuantityIncrement
        ),
        new SymbolProperty(
            "Tick Size",
            "Symbol price should be divided by this value without residue",
            Symbol::getTickSize
        ),
        new SymbolProperty(
            "Take Liquidity Rate",
            "Default fee rate",
            Symbol::getTakeLiquidityRate
        ),
        new SymbolProperty(
            "Provide Liquidity Rate",
            "Default fee rate for market making trades",
            Symbol::getProvideLiquidityRate
        ),
        new SymbolProperty(
            "Fee Currency",
            "Name of currency value of charged fee",
            Symbol::getFeeCurrency
        )
    };

    private final Consumer<Symbol> panelRefresher;

    private final BiFunction<SymbolProperty, Consumer<Component>, Consumer<Symbol>> propertyRegisterer = (property, registrar) -> {
        final Div description = new Div();
        description.add(property.description);
        description.getStyle().set("color", "var(--lumo-secondary-text-color)");
        description.getStyle().set("font-size", "var(--lumo-font-size-s)");

        final Span name = new Span(property.name);
        final Span content = new Span();
        content.getStyle().set("padding", "var(--lumo-space-wide-m)");

        final Div container = new Div(new Div(name, content), description);
        registrar.accept(container);

        return symbol -> content.setText(property.propertyAccessor.apply(symbol));
    };

    public SymbolDetails() {

        final Consumer<Component> componentRegisterer = component -> this.getContent().add(component);

        final H3 detailsTitle = new H3();
        componentRegisterer.accept(detailsTitle);

        final Button close = new Button("Close");
        close.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addClickListener(event -> this.hide());

        final Anchor candlesAnchor = new Anchor("", new Button("Candles"));
        final Consumer<Symbol> anchorRegisterer = symbol -> {
            final String path = RouteConfiguration.forSessionScope().getUrl(CandlesView.class, symbol.getId());
            candlesAnchor.setHref(path);
        };

        this.panelRefresher = Arrays.stream(SYMBOL_PROPERTIES)
            .map(symbolProperty -> propertyRegisterer.apply(symbolProperty, componentRegisterer))
            .reduce(Consumer::andThen)
            .orElse(symbol -> {})
            .andThen(symbol -> detailsTitle.setText(symbol.getId()))
            .andThen(anchorRegisterer);

        getContent().setVisible(false);
        getContent().add(new HorizontalLayout(close, candlesAnchor));
    }

    private void hide() {
        getContent().setVisible(false);
    }

    private void show(Symbol symbol) {
        this.panelRefresher.accept(symbol);
        this.getContent().setVisible(true);
    }

    /**
     * Updates Symbol details panel data using given {@link Symbol}. In case of
     * {@code null} panel will be hided.
     * @param symbol using to update panel, may be {@code null}.
     */
    void update(Symbol symbol) {
        if (symbol != null) {
            this.show(symbol);
        } else {
            this.hide();
        }
    }

    private static final class SymbolProperty {
        private final String name;
        private final String description;
        private final Function<Symbol, String> propertyAccessor;

        private SymbolProperty(String name, String description, Function<Symbol, String> propertyAccessor) {
            this.name = name;
            this.description = description;
            this.propertyAccessor = propertyAccessor;
        }
    }
}

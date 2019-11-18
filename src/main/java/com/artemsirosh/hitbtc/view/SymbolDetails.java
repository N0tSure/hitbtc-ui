package com.artemsirosh.hitbtc.view;

import com.artemsirosh.hitbtc.model.Symbol;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.util.Optional;
import java.util.function.Consumer;

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

    private final H3 symbolId;
    private final Span baseCurrency;
    private final Span quiteCurrency;
    private final Span quantityIncrement;
    private final Span tickSize;
    private final Span takeLiquidityRate;
    private final Span provideLiquidityRate;
    private final Span feeCurrency;

    public SymbolDetails() {
        this.symbolId = new H3();
        this.baseCurrency = new Span();
        this.quiteCurrency = new Span();
        this.quantityIncrement = new Span();
        this.tickSize = new Span();
        this.takeLiquidityRate = new Span();
        this.provideLiquidityRate = new Span();
        this.feeCurrency = new Span();

        final Button close = new Button("Close");
        close.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addClickListener(event -> this.hide());

        getContent().setVisible(false);
        getContent().add(
            this.symbolId,
            this.baseCurrency,
            this.quiteCurrency,
            this.quantityIncrement,
            this.tickSize,
            this.takeLiquidityRate,
            this.provideLiquidityRate,
            this.feeCurrency,
            close
        );
    }

    private void hide() {
        getContent().setVisible(false);
    }

    private void show(Symbol symbol) {
        this.symbolId.setText(symbol.getId());
        this.baseCurrency.setText(symbol.getBaseCurrency());
        this.quiteCurrency.setText(symbol.getQuoteCurrency());
        this.quantityIncrement.setText(symbol.getQuantityIncrement());
        this.tickSize.setText(symbol.getTickSize());
        this.takeLiquidityRate.setText(symbol.getTakeLiquidityRate());
        this.provideLiquidityRate.setText(symbol.getProvideLiquidityRate());
        this.feeCurrency.setText(symbol.getFeeCurrency());

        this.getContent().setVisible(true);
    }

    void update(Symbol symbol) {
        Optional.ofNullable(symbol)
            .map(s -> (Consumer<Symbol>) this::show)
            .orElse(s -> this.hide()).accept(symbol);
    }
}

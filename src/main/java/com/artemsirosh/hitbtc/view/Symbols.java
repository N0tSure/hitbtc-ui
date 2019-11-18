package com.artemsirosh.hitbtc.view;

import com.artemsirosh.hitbtc.model.Symbol;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Supplier;

/**
 * Created on 17 Nov, 2019.
 *
 * @author Artemis A. Sirosh
 */
@Slf4j
@Value
@VaadinSessionScope
@SpringComponent
public class Symbols {

    private final List<Symbol> symbols;

    public Symbols(Supplier<List<Symbol>> symbolSupplier) {
        this.symbols = symbolSupplier.get();
    }

}

package com.artemsirosh.hitbtc.client;

import com.artemsirosh.hitbtc.model.Symbol;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created on 16 Nov, 2019.
 *
 * Client for HitBTC Public API endpoint {@code /symbol}.
 *
 * @author Artemis A. Sirosh
 */
@FeignClient(name = "symbols", url = "https://api.hitbtc.com/api/2/public/symbol")
public interface SymbolClient {

    /**
     * Returns particular {@link Symbol} by itself {@code id}.
     * @param id Symbols identifier
     * @return a {@link Symbol} instance
     */
    @GetMapping("/{id}")
    Symbol getSymbol(@PathVariable("id") String id);

    /**
     * Returns all symbols.
     * NOTE: can return about {@code 1000} instances.
     * @return list of symbols
     */
    @GetMapping
    List<Symbol> getSymbols();

}

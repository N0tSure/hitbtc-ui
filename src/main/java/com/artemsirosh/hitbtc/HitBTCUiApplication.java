package com.artemsirosh.hitbtc;

import com.artemsirosh.hitbtc.client.SymbolClient;
import com.artemsirosh.hitbtc.model.Symbol;
import com.artemsirosh.hitbtc.view.InstantPicker;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.function.Supplier;

@EnableFeignClients
@SpringBootApplication
public class HitBTCUiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HitBTCUiApplication.class, args);
    }

    @Bean
    Supplier<List<Symbol>> symbolsSupplier(SymbolClient symbolClient) {
        return symbolClient::getSymbols;
    }

    @Bean
    @VaadinSessionScope
    ListDataProvider<Symbol> symbolListDataProvider(SymbolClient client) {
        return DataProvider.ofCollection(client.getSymbols());
    }

    @Bean
    @UIScope
    InstantPicker fromInstantPicker() {
        return new InstantPicker("Since timestamp", (localDate, localTime) -> {});
    }

    @Bean
    @UIScope
    InstantPicker tillInstantPicker() {
        return new InstantPicker("Till timestamp", (localDate, localTime) -> {});
    }

}

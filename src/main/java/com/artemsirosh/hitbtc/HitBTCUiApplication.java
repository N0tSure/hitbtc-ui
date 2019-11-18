package com.artemsirosh.hitbtc;

import com.artemsirosh.hitbtc.client.SymbolClient;
import com.artemsirosh.hitbtc.model.Symbol;
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

}

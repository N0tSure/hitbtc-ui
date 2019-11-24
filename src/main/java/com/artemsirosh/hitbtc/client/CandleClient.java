package com.artemsirosh.hitbtc.client;

import com.artemsirosh.hitbtc.model.Candle;
import com.artemsirosh.hitbtc.model.Periods;
import com.artemsirosh.hitbtc.model.SortDirection;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.List;

/**
 * Created on 24 Nov, 2019.
 *
 * @author Artemis A. Sirosh
 */
@FeignClient(name = "candles", url = "https://api.hitbtc.com/api/2/public/candles")
public interface CandleClient {

    @GetMapping("/{symbol}")
    List<Candle> getCandles(
        @PathVariable("symbol") String symbol, @RequestParam("period") Periods period,
        @RequestParam("sort") SortDirection sortDirection,
        @RequestParam("from") Instant from, @RequestParam("till") Instant till,
        @RequestParam("limit") int limit, @RequestParam("offset") int offset
    );
}

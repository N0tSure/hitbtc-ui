package com.artemsirosh.hitbtc.client;

import com.artemsirosh.hitbtc.model.Candle;
import com.artemsirosh.hitbtc.model.SortDirection;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.List;

/**
 * Created on 24 Nov, 2019.
 *
 * Allows to retrieve {@link Candle}s from HitBTC.
 *
 * @author Artemis A. Sirosh
 */
@FeignClient(name = "candles", url = "https://api.hitbtc.com/api/2/public/candles")
public interface CandleClient {

    /**
     * Makes query for {@link Candle}s to HitBTC API.
     *
     * @param symbol the {@link com.artemsirosh.hitbtc.model.Symbol} identifier
     * @param period of time for a candle
     * @param sortDirection direction of record sorting
     * @param from timestamp of lower query bound
     * @param till timestamp of upper query bound
     * @param limit amount of records requested
     * @param offset of records query
     * @return a list of {@link Candle}
     */
    @GetMapping("/{symbol}")
    List<Candle> getCandles(
        @PathVariable("symbol") String symbol,
        @RequestParam(value = "period", required = false) String period,
        @RequestParam(value = "sort", required = false) SortDirection sortDirection,
        @RequestParam(value = "from", required = false) Instant from,
        @RequestParam(value = "till", required = false) Instant till,
        @RequestParam("limit") int limit, @RequestParam("offset") int offset
    );
}

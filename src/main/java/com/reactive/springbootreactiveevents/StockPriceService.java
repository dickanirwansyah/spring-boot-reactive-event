package com.reactive.springbootreactiveevents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

@Service
public class StockPriceService {

    @Autowired Utilities utilities;

    public Flux<List<StockPrice>> getStockPricesData(List<StockPrice> stockPriceList){
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(3));
        interval.subscribe((i) -> stockPriceList
                        .forEach(stock -> setStockPrice(stock)));

        Flux<List<StockPrice>> transactionFlux = Flux
                .fromStream(Stream.generate(() -> stockPriceList));

        return Flux.zip(interval, transactionFlux)
                .map(Tuple2::getT2);
    }

    private StockPrice setStockPrice(StockPrice stockPrice){
        stockPrice.setPrice(utilities.getRandomDoubleBetweenRange(1000, 500));
        stockPrice.setStatus(utilities.getStatus());
        stockPrice.setChange(utilities.getRandomDoubleBetweenRange(800, 7000));
        stockPrice.setValue(utilities.getRandomDoubleBetweenRange(100, 200));
        return stockPrice;
    }
}

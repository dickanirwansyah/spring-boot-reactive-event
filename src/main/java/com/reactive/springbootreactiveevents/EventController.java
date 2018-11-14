package com.reactive.springbootreactiveevents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
public class EventController {

    private List<StockPrice> stockPrices = new ArrayList<>();

    @Autowired Utilities utilities;
    @Autowired StockPriceService stockPriceService;

    @PostConstruct
    public void initializer(){
        StockPrice stockPrice1 = new StockPrice("Bank Mandiri",
                utilities.getRandomDoubleBetweenRange(1000, 2000),
                utilities.getRandomDoubleBetweenRange(2, 10),
                utilities.getRandomDoubleBetweenRange(10, 20),
                utilities.getStatus());

        StockPrice stockPrice2 = new StockPrice("Bank BCA",
                utilities.getRandomDoubleBetweenRange(3000, 6000),
                utilities.getRandomDoubleBetweenRange(2, 9),
                utilities.getRandomDoubleBetweenRange(7, 18),
                utilities.getStatus());

        StockPrice stockPrice3 = new StockPrice("Bank BTPN",
                utilities.getRandomDoubleBetweenRange(8000, 10000),
                utilities.getRandomDoubleBetweenRange(8, 9),
                utilities.getRandomDoubleBetweenRange(8, 17),
                utilities.getStatus());

        stockPrices.add(stockPrice1);
        stockPrices.add(stockPrice2);
        stockPrices.add(stockPrice3);
    }


    @RequestMapping(value = "/stock-events",
    method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<List<StockPrice>> eventFluxStockPrice(){
        return stockPriceService
                .getStockPricesData(stockPrices);
    }
}

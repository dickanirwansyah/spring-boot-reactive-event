package com.reactive.springbootreactiveevents;

import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockPrice {

    private String companyName;
    private String price;
    private String change;
    private String value;
    private String status;

}

package io.classpath.graphqlapp.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerOrderSummary {

    private String customerName;
    private Long totalOrders;

}

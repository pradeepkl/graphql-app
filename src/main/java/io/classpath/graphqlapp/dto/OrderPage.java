package io.classpath.graphqlapp.dto;

import io.classpath.graphqlapp.model.Order;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@AllArgsConstructor
public class OrderPage {

    private List<Order> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
}

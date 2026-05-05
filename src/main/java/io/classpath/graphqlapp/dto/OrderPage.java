package io.classpath.graphqlapp.dto;

import io.classpath.graphqlapp.model.Order;
import lombok.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = PRIVATE)
public class OrderPage {

    private List<Order> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
}

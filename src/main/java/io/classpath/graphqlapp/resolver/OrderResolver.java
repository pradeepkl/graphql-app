package io.classpath.graphqlapp.resolver;

import io.classpath.graphqlapp.model.Order;
import io.classpath.graphqlapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class OrderResolver {

    private final OrderService orderService;

    @QueryMapping
    public Set<Order> orders(){
        return this.orderService.fetchAllOrders();
    }


}

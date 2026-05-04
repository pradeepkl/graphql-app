package io.classpath.graphqlapp.resolver;

import io.classpath.graphqlapp.model.LineItem;
import io.classpath.graphqlapp.model.Order;
import io.classpath.graphqlapp.service.LineItemService;
import io.classpath.graphqlapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class OrderResolver {

    private final OrderService orderService;
    private final LineItemService lineItemService;

    @QueryMapping
    public Set<Order> orders(){
        return this.orderService.fetchAllOrders();
    }

    @QueryMapping
    public Order order(@Argument Long id){
        return this.orderService.getOrder(id);
    }

    @SchemaMapping(typeName = "Order", field = "lineItems")
    public List<LineItem> lineItems(Order order){
        return this.lineItemService.getByOrderId(order.getId());
    }



}

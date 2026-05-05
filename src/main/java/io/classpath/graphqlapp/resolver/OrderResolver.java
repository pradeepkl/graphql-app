package io.classpath.graphqlapp.resolver;

import io.classpath.graphqlapp.dto.CustomerOrderSummary;
import io.classpath.graphqlapp.dto.OrderPage;
import io.classpath.graphqlapp.model.OrderSortField;
import io.classpath.graphqlapp.model.LineItem;
import io.classpath.graphqlapp.model.Order;
import io.classpath.graphqlapp.model.Product;
import io.classpath.graphqlapp.service.LineItemService;
import io.classpath.graphqlapp.service.OrderService;
import io.classpath.graphqlapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class OrderResolver {

    private final OrderService orderService;
    private final LineItemService lineItemService;
    private final ProductService productService;

    @QueryMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN', 'MANAGER', 'STORE_MANAGER')")
    public Set<Order> orders(){
        return this.orderService.fetchAllOrders();
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN', 'MANAGER', 'STORE_MANAGER')")
    public Order order(@Argument Long id){
        return this.orderService.getOrder(id);
    }

    @SchemaMapping(typeName = "Order", field = "lineItems")
    public List<LineItem> lineItems(Order order){
        return this.lineItemService.getByOrderId(order.getId());
    }

    @SchemaMapping(typeName = "LineItem", field = "product")
    public Product product(LineItem lineItem){
        return this.productService.getProduct(lineItem.getProductId());
    }

    @QueryMapping
    public Set<Order> ordersByCustomer(@Argument String name){
        System.out.println("Inside the ordersByCustomer method ::");
        return this.orderService.findByCustomerName(name);
    }


    @QueryMapping
    public Set<Order> ordersByDateRange(@Argument String start, @Argument String end){
        return this.orderService.findByDateRange(start, end);
    }

    @QueryMapping
    public Set<CustomerOrderSummary> topCustomers(@Argument int limit) {
        return this. orderService.getTopCustomers(limit);
    }

    @QueryMapping
    public OrderPage ordersPagedSorted(@Argument int offset, @Argument int limit, @Argument String direction ){
        return this.orderService.ordersPagedSorted(offset, limit, direction);
    }

}

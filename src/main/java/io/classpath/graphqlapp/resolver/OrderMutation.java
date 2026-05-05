package io.classpath.graphqlapp.resolver;

import io.classpath.graphqlapp.model.Order;
import io.classpath.graphqlapp.model.OrderInput;
import io.classpath.graphqlapp.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class OrderMutation {

    private final OrderService orderService;

    @MutationMapping
    public Order createOrder(@Valid @Argument OrderInput input){
        System.out.println(" Inside the controller method");
        return this.orderService.save(input);
    }

    @MutationMapping
    public Order updateOrder(@Valid @Argument OrderInput input){
        return this.orderService.update(input);
    }

    @MutationMapping
    public Boolean deleteOrder(@Argument Long id){
        return this.orderService.delete(id);
    }
}

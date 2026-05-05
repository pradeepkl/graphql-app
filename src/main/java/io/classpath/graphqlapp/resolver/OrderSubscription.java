package io.classpath.graphqlapp.resolver;

import io.classpath.graphqlapp.model.Order;
import io.classpath.graphqlapp.publisher.OrderPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Controller
@RequiredArgsConstructor
public class OrderSubscription {

    private final OrderPublisher orderPublisher;

    @SubscribeMapping
    public Flux<Order> orderCreated(){
        System.out.println("Inside the subscription:: ");
        return this.orderPublisher.getOrders();
    }

}

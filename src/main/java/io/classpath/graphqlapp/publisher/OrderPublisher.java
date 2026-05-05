package io.classpath.graphqlapp.publisher;

import io.classpath.graphqlapp.model.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Component
public class OrderPublisher {

    private final Sinks.Many<Order> sink = Sinks.many().multicast().onBackpressureBuffer();

    public void publish(Order order) {
        sink.tryEmitNext(order);
    }

    public Flux<Order> getOrders(){
        System.out.println("Inside the get Orders method:::::::::::::");
        return sink.asFlux();
    }
}

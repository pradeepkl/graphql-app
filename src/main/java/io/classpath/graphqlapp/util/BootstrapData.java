package io.classpath.graphqlapp.util;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import io.classpath.graphqlapp.model.Order;
import io.classpath.graphqlapp.repo.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import java.util.Random;
import java.time.LocalDate;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class BootstrapData {

    private final OrderJpaRepository repository;
    private final Faker faker = new Faker();
    private final Random random = new Random();

    @EventListener(ApplicationReadyEvent.class)
    public void loadOrders(){
        if(this.repository.count() > 0) return;

        IntStream.range(0, 10).forEach(index -> {
            Name name = faker.name();
            Order order = Order.builder()
                    .customerName(name.firstName())
                    .email(name.fullName()+"@"+faker.internet().domainName())
                    .createdDate(LocalDate.now().minusDays(random.nextInt(10))).build();
            this.repository.save(order);
        });
    }
}

package io.classpath.graphqlapp.util;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import io.classpath.graphqlapp.model.LineItem;
import io.classpath.graphqlapp.model.Order;
import io.classpath.graphqlapp.model.Product;
import io.classpath.graphqlapp.repo.LineItemJpaRepository;
import io.classpath.graphqlapp.repo.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.time.LocalDate;
import java.util.stream.IntStream;
import io.classpath.graphqlapp.repo.ProductJpaRepository;

@Component
@RequiredArgsConstructor
public class BootstrapData {

    private final OrderJpaRepository repository;
    private final ProductJpaRepository productRepository;
    private final LineItemJpaRepository lineItemJpaRepository;

    private final Faker faker = new Faker();
    private final Random random = new Random();

    @EventListener(ApplicationReadyEvent.class)
    public void loadOrders(){
        if(this.repository.count() > 0) return;


        //creating products
        List<Product> products = IntStream.range(0,5)
                        .mapToObj(i -> Product.builder().name(faker.commerce().productName()).build())
                .map(productRepository::save)
                        .toList();

        IntStream.range(0, 10).forEach(index -> {
            Name name = faker.name();
            Order order = Order.builder()
                    .customerName(name.firstName())
                    .email(name.fullName()+"@"+faker.internet().domainName())
                    .createdDate(LocalDate.now().minusDays(random.nextInt(10))).build();

            Order savedOrder = this.repository.save(order);

            IntStream.range(0,3)
                    .mapToObj( j -> {
                       Product randomproduct = products.get(random.nextInt(products.size()));
                       return LineItem.builder()
                               .qty(random.nextInt(5) + 1)
                               .orderId(savedOrder.getId())
                               .productId(randomproduct.getId())
                               .build();
                    })
                    .forEach(lineItemJpaRepository::save);
        });

        System.out.println("Fake data loaded successfully");
    }
}

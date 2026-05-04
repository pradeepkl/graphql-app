package io.classpath.graphqlapp.service;

import io.classpath.graphqlapp.dto.CustomerOrderSummary;
import io.classpath.graphqlapp.model.Order;
import io.classpath.graphqlapp.repo.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderJpaRepository orderRepository;

    public Set<Order> fetchAllOrders(){
        return Set.copyOf(this.orderRepository.findAll());
    }

    public Order getOrder(Long id){
        return this.orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("invalid order id passed "));
    }

    public Set<Order> findByCustomerName(String name){
        return Set.copyOf(this.orderRepository.findByCustomerName(name));
    }

    public Set<CustomerOrderSummary> getTopCustomers(int limit){
        return Set.copyOf(this.orderRepository.findTopCustomers().stream().limit(limit).toList());
    }




    public Set<Order> findByDateRange(String start, String end){
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        return Set.copyOf(this.orderRepository.findByCreatedDateBetween(startDate, endDate));
    }
}

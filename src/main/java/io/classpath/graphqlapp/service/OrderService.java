package io.classpath.graphqlapp.service;

import io.classpath.graphqlapp.dto.CustomerOrderSummary;
import io.classpath.graphqlapp.dto.OrderPage;
import io.classpath.graphqlapp.dto.OrderSortField;
import io.classpath.graphqlapp.model.Order;
import io.classpath.graphqlapp.repo.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

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

    //In this method, we will form the query and pass to the repository
    public OrderPage getOrdersPagedSorted(int offset, int limit, OrderSortField sortBy, String direction){
        int page = offset/limit;
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? ASC: DESC;
        Pageable pageable = PageRequest.of(page, limit,  sortDirection, sortBy.name());
        Page<Order> result = this.orderRepository.findAll(pageable);

        return new OrderPage(result.getContent(), result.getNumber(), result.getSize(), result.getTotalElements(), result.getTotalPages());
    }
}

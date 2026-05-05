package io.classpath.graphqlapp.service;

import io.classpath.graphqlapp.dto.CustomerOrderSummary;
import io.classpath.graphqlapp.dto.OrderPage;
import io.classpath.graphqlapp.model.LineItem;
import io.classpath.graphqlapp.model.OrderInput;
import io.classpath.graphqlapp.model.OrderSortField;
import io.classpath.graphqlapp.model.Order;
import io.classpath.graphqlapp.publisher.OrderPublisher;
import io.classpath.graphqlapp.repo.LineItemJpaRepository;
import io.classpath.graphqlapp.repo.OrderJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderJpaRepository orderRepository;
    private final LineItemJpaRepository lineItemRepository;
    private final OrderPublisher orderPublisher;

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
    public OrderPage ordersPagedSorted(int offset, int limit, String direction){
        System.out.println("offset" + offset + " limit "+ limit + " direction "+ direction);
        int page = offset / limit;
        Pageable pageable = PageRequest.of(page, limit);

        Page<Order> result = this.orderRepository.findAll(pageable);

        List<Order> orders = result.getContent();
        int number = result.getNumber();
        int size = result.getSize();
        long totalNumberOfElements = result.getTotalElements();
        int totalPages = result.getTotalPages();

        OrderPage orderPage = OrderPage.builder().totalPages(totalPages).totalElements(totalNumberOfElements).content(orders).size(size).page(number).build();


       return orderPage;
    }

    public Order save(OrderInput input){
        Order order = Order.builder().customerName(input.getCustomerName()).email(input.getEmail()).createdDate(LocalDate.now()).build();
        // first save the order and then use the id to populate the line-items
        Order savedOrder = this.orderRepository.save(order);

        if(input.getItems() != null){
            List<LineItem> items = input.getItems().stream().map( i ->
                    LineItem.builder()
                            .id(i.getId())
                            .orderId(savedOrder.getId())
                            .productId(i.getProductId())
                            .qty(i.getQty())
                            .build()).toList();

            this.lineItemRepository.saveAll(items);
        }
        System.out.println("Came inside the save order of order service ::::");
        this.orderPublisher.publish(savedOrder);
        return savedOrder;
    }

    @Transactional
    public Order update(OrderInput input){
        Order order = this.orderRepository.findById(input.getId()).orElseThrow(() -> new IllegalArgumentException("invalid orderId passed"));
        order.setCustomerName(input.getCustomerName());
        order.setEmail(input.getEmail());

        // delete all the old line items for the current order
        this.lineItemRepository.deleteAll(lineItemRepository.findByOrderId(order.getId()));

        //add the new line items
        if(input.getItems()!= null){
            List<LineItem> items = input.getItems().stream().map(i -> LineItem.builder()
                    .id(i.getId())
                    .orderId(order.getId())
                    .productId(i.getProductId())
                    .qty(i.getQty())
                    .build()).toList();
            this.lineItemRepository.saveAll(items);
        }
        return this.orderRepository.save(order);
    }

    public Boolean delete(Long id){
        if(!this.orderRepository.existsById(id)){
            throw new IllegalArgumentException("invalid order id passed ");
        }
        List<LineItem> lineItems = this.lineItemRepository.findByOrderId(id);
        this.lineItemRepository.deleteAll(lineItems);
        this.orderRepository.deleteById(id);
        return true;
    }
}

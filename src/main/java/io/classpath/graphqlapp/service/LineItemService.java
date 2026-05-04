package io.classpath.graphqlapp.service;

import io.classpath.graphqlapp.model.LineItem;
import io.classpath.graphqlapp.model.Order;
import io.classpath.graphqlapp.repo.LineItemJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LineItemService {

    private final LineItemJpaRepository lineItemRepository;

    public List<LineItem> getByOrderId(Long orderId){
        return this.lineItemRepository.findByOrderId(orderId);
    }

    public int itemCount(Order order){
        List<LineItem> lineItems =  this.lineItemRepository.findByOrderId(order.getId());
        return lineItems.size();

    }
}

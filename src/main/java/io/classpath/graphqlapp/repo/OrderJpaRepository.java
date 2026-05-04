package io.classpath.graphqlapp.repo;

import io.classpath.graphqlapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrderJpaRepository extends JpaRepository<Order, Long> {

    Set<Order> findByCustomerName(String name);
}

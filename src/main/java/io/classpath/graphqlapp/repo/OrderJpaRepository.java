package io.classpath.graphqlapp.repo;

import io.classpath.graphqlapp.dto.CustomerOrderSummary;
import io.classpath.graphqlapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface OrderJpaRepository extends JpaRepository<Order, Long> {

    Set<Order> findByCustomerName(String name);
    Set<Order> findByCreatedDateBetween(LocalDate start, LocalDate end);


    @Query("""
     SELECT new io.classpath.graphqlapp.dto.CustomerOrderSummary(o.customerName, COUNT(o))
         FROM Order o
         GROUP BY o.customerName
         order by COUNT(o) DESC  
    """)
    List<CustomerOrderSummary> findTopCustomers();

}

package io.classpath.graphqlapp.repo;

import io.classpath.graphqlapp.model.LineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineItemJpaRepository extends JpaRepository<LineItem, Long> {

    List<LineItem> findByOrderId(Long orderId);
}

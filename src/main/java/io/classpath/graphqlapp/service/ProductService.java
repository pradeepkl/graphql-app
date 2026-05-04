package io.classpath.graphqlapp.service;

import io.classpath.graphqlapp.model.Product;
import io.classpath.graphqlapp.repo.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductJpaRepository productrepository;

    public Set<Product> fetchALl(){
        return Set.copyOf(this.productrepository.findAll());
    }

    public Product getProduct(Long id){
        return this.productrepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product id"));
    }
}

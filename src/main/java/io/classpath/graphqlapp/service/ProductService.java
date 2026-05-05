package io.classpath.graphqlapp.service;

import io.classpath.graphqlapp.exception.ProductNotFroundException;
import io.classpath.graphqlapp.model.Product;
import io.classpath.graphqlapp.model.ProductInput;
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
        return this.productrepository.findById(id).orElseThrow(() -> new ProductNotFroundException(id));
    }

    public Product create(ProductInput input) {
        Product product = Product.builder().name(input.getName()).build();
        return this.productrepository.save(product);
    }

    public Product update(ProductInput input){
        Product product = this.productrepository.findById(input.getId()).orElseThrow(() -> new ProductNotFroundException(input.getId()));
        product.setName(input.getName());
        return this.productrepository.save(product);
    }

    public boolean delete(long id){
        this.productrepository.deleteById(id);
        return true;
    }
}

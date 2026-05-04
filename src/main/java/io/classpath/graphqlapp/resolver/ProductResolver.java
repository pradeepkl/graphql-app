package io.classpath.graphqlapp.resolver;

import io.classpath.graphqlapp.model.Product;
import io.classpath.graphqlapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class ProductResolver {

    private final ProductService productService;

    @QueryMapping
    public Set<Product> products(){
        return this.productService.fetchALl();
    }
}

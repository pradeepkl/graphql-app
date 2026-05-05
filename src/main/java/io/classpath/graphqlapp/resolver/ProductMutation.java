package io.classpath.graphqlapp.resolver;

import io.classpath.graphqlapp.model.Product;
import io.classpath.graphqlapp.model.ProductInput;
import io.classpath.graphqlapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ProductMutation {

    private final ProductService productService;

    @MutationMapping
    public Product createProduct(@Argument ProductInput input) {

        return this.productService.create(input);
    }

}

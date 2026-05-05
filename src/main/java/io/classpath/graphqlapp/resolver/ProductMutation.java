package io.classpath.graphqlapp.resolver;

import io.classpath.graphqlapp.model.Product;
import io.classpath.graphqlapp.model.ProductInput;
import io.classpath.graphqlapp.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ProductMutation {

    private final ProductService productService;

    @MutationMapping
    public Product createProduct(@Valid @Argument ProductInput input) {

        return this.productService.create(input);
    }

    @MutationMapping
    public Product updateProduct(@Argument ProductInput input ){
        return this.productService.update(input);
    }

    @MutationMapping
    public Boolean deleteProduct(@Argument Long id ){
        return this.productService.delete(id);
    }
}

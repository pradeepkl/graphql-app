package io.classpath.graphqlapp.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInput {

    private Long id;

    @NotBlank(message = "Product name is required")
    private String name;
}

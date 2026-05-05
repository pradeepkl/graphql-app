package io.classpath.graphqlapp.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LineItemInput {

    private Long id;
    private Long productId;

    @Min(value = 2, message = "Minimum qty should be 2")
    @Max(value = 10, message = "Max qty can be 10")
    private Integer qty;
}

package io.classpath.graphqlapp.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LineItemInput {

    private Long id;
    private Long productId;

    private Integer qty;
}

package io.classpath.graphqlapp.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInput {

    private Long id;

    private String name;
}

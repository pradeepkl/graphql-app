package io.classpath.graphqlapp.model;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderInput {

    private Long id;

    private String customerName;

    private String email;

    private List<LineItemInput> items;
}

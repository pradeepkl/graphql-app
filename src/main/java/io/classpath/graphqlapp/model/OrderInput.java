package io.classpath.graphqlapp.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderInput {

    private Long id;

    @NotBlank(message = "Customer name cannot be blank")
    private String customerName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotEmpty(message = "At least one line item is required")
    private List<LineItemInput> items;
}

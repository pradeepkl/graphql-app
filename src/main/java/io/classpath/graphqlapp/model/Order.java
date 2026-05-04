package io.classpath.graphqlapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PRIVATE;

@Entity
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@Table(name="orders")
@Builder
@ToString
@EqualsAndHashCode
@Setter
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private String customerName;
    private String email;
    private LocalDate createdDate;

}

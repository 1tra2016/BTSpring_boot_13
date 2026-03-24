package springboot.ss11.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
public class Supplies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String specification;
    private String provider;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false)
    private Integer quantity;

    private boolean isDeleted;
}

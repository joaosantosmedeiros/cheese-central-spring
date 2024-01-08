package joao.pedro.productsapi.infrastructure.persistence.category;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "CATEGORY", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity {

    public CategoryEntity(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String name;
}

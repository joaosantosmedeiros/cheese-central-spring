package joao.pedro.productsapi.infrastructure.config.db.schema;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "CATEGORIES", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
@Getter
@Setter
@NoArgsConstructor
public class CategoryEntity {

    public CategoryEntity(String name) {
        this.name = name;
    }
    public CategoryEntity(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<ProductEntity> products;
}

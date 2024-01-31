package joao.pedro.productsapi.infrastructure.config.db.schema;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "PRODUCTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String name;
    private String decription;
    private String imageUrl;
    private Double price;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoryId")
    private CategoryEntity category;
}

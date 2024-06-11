package joao.pedro.productsapi.infrastructure.config.db.schema;

import jakarta.persistence.*;
import joao.pedro.productsapi.entity.product.model.Product;
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
    private UUID id;
    @Column(nullable = false)
    private String name;
    private String decription;
    private String imageUrl;
    private Double price;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoryId")
    private CategoryEntity category;

    public Product toProduct() {
        return new Product(
                this.id,
                this.name,
                this.decription,
                this.imageUrl,
                this.price,
                this.category.toCategory()
        );
    }
}

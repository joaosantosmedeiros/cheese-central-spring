package joao.pedro.productsapi.infrastructure.config.db.schema;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "CARTS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartEntity {

    @Id
    private UUID id;
    @Column(nullable = false)
    private boolean isActive;

    @ManyToOne(optional = false)
    @JoinColumn(name = "accountId")
    private AccountEntity account;

    @OneToMany(mappedBy = "cart")
    private List<CartProductEntity> cartProducts;
}

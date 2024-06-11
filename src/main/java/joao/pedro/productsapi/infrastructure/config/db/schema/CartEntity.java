package joao.pedro.productsapi.infrastructure.config.db.schema;

import jakarta.persistence.*;
import joao.pedro.productsapi.entity.cart.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public Cart toCart() {
        return new Cart(
                this.id,
                this.isActive,
                this.account.toAccount(),
                this.cartProducts.stream().map(CartProductEntity::toCartProduct).collect(Collectors.toList())
        );
    }
}

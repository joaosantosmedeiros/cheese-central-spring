package joao.pedro.productsapi.entity.cart.model;

import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.entity.cartProduct.model.CartProduct;
import joao.pedro.productsapi.infrastructure.config.db.schema.CartEntity;
import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class Cart {
    private UUID id;
    private boolean isActive;
    private Account account;
    private List<CartProduct> cartProducts;


    public Cart(boolean isActive, Account account, List<CartProduct> cartProducts) {
        this.id = UUID.randomUUID();
        this.isActive = isActive;
        this.account = account;
        this.cartProducts = cartProducts;
    }

    public CartEntity toCartEntity() {
        return new CartEntity(
                this.id,
                this.isActive,
                this.account != null ? this.account.toAccountEntity() : null,
                this.cartProducts != null ?
                        this.cartProducts.stream().map(CartProduct::toCartProductEntity).collect(Collectors.toList())
                        :
                        null
        );
    }
}

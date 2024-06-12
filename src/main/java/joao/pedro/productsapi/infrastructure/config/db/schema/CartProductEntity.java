package joao.pedro.productsapi.infrastructure.config.db.schema;

import jakarta.persistence.*;
import joao.pedro.productsapi.entity.cart.model.Cart;
import joao.pedro.productsapi.entity.cartProduct.model.CartProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "CART_PRODUCTS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartProductEntity {

    @Id
    private UUID id;
    private int amount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cartId")
    private CartEntity cart;

    @ManyToOne(optional = false)
    @JoinColumn(name = "productId")
    private ProductEntity product;

    public CartProduct toCartProduct() {
        return new CartProduct(
                this.id,
                this.amount,
                this.cart != null ? new Cart(
                        this.cart.getId(),
                        this.cart.isActive(),
                        null,
                        null
                ) : null,
                this.product.toProduct()
        );
    }
}

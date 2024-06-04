package joao.pedro.productsapi.entity.cartProduct.model;

import java.util.Objects;
import java.util.UUID;

public class FetchedCartProduct {

    private final UUID id;
    private final UUID cartId;
    private final UUID productId;
    private final int amount;

    public FetchedCartProduct(CartProduct cartProduct) {
        this.id = cartProduct.getId();
        this.cartId = cartProduct.getCart().getId();
        this.productId = cartProduct.getProduct().getId();
        this.amount = cartProduct.getAmount();
    }

    public UUID getId() {
        return id;
    }

    public UUID getCartId() {
        return cartId;
    }

    public UUID getProductId() {
        return productId;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "FetchedCartProduct{" +
                "id=" + id +
                ", cartId=" + cartId +
                ", productId=" + productId +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FetchedCartProduct that = (FetchedCartProduct) o;
        return amount == that.amount && Objects.equals(id, that.id) && Objects.equals(cartId, that.cartId) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cartId, productId, amount);
    }
}

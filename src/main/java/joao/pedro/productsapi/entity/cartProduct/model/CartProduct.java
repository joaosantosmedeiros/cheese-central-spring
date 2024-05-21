package joao.pedro.productsapi.entity.cartProduct.model;

import java.util.UUID;

public class CartProduct {
    private UUID id;
    private int amount;
    private UUID cartId;
    private UUID productId;

    public CartProduct(UUID id, int amount, UUID cartId, UUID productId) {
        this.id = id;
        this.amount = amount;
        this.cartId = cartId;
        this.productId = productId;
    }

    public CartProduct(int amount, UUID cartId, UUID productId) {
        this.id = UUID.randomUUID();
        this.amount = amount;
        this.cartId = cartId;
        this.productId = productId;
    }

    public UUID getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public UUID getCartId() {
        return cartId;
    }

    public void setCartId(UUID cartId) {
        this.cartId = cartId;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }
}

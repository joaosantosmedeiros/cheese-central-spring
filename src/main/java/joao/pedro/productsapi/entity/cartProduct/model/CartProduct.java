package joao.pedro.productsapi.entity.cartProduct.model;

import joao.pedro.productsapi.entity.cart.model.Cart;
import joao.pedro.productsapi.entity.product.model.Product;

import java.util.UUID;

public class CartProduct {
    private UUID id;
    private int amount;
    private Cart cart;
    private Product product;

    public CartProduct(UUID id, int amount, Cart cart, Product product) {
        this.id = id;
        this.amount = amount;
        this.cart = cart;
        this.product = product;
    }

    public CartProduct(int amount, Cart cart, Product product) {
        this.id = UUID.randomUUID();
        this.amount = amount;
        this.cart = cart;
        this.product = product;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

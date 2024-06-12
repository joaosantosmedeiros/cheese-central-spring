package joao.pedro.productsapi.entity.orderProduct.model;

import joao.pedro.productsapi.entity.order.model.Order;
import joao.pedro.productsapi.entity.product.model.Product;
import joao.pedro.productsapi.infrastructure.config.db.schema.OrderProductEntity;

import java.util.Objects;
import java.util.UUID;

public class OrderProduct {
    private UUID id;
    private Order order;
    private Product product;
    private int amount;
    private double price;

    public OrderProduct(Order order, Product product, int amount, double price) {
        this.id = UUID.randomUUID();
        this.order = order;
        this.product = product;
        this.amount = amount;
        this.price = price;
    }

    public OrderProduct(UUID id, Order order, Product product, int amount, double price) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.amount = amount;
        this.price = price;
    }

    public OrderProductEntity toOrderProductEntity() {
        return new OrderProductEntity(
                this.id,
                this.order.toOrderEntity(),
                this.product.toProductEntity(),
                this.amount,
                this.price
        );
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderProduct that = (OrderProduct) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "id=" + id +
                ", order=" + order +
                ", product=" + product +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }
}

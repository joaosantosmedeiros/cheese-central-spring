package joao.pedro.productsapi.infrastructure.config.db.schema;

import jakarta.persistence.*;
import joao.pedro.productsapi.entity.orderProduct.model.OrderProduct;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "ORDER_PRODUCTS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderProductEntity {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "productId")
    private ProductEntity product;

    private int amount;

    private double price;

    public OrderProduct toOrderProduct() {
        return new OrderProduct(
                this.order.toOrder(),
                this.product.toProduct(),
                this.amount,
                this.price
        );
    }
}

package joao.pedro.productsapi.entity.orderProduct.model;

import joao.pedro.productsapi.entity.product.model.Product;

import java.util.UUID;

public record DetailedOrderProduct(UUID id, Product product, int amount, double price) {
    public DetailedOrderProduct(OrderProduct orderProduct) {
        this(orderProduct.getId(), orderProduct.getProduct(), orderProduct.getAmount(), orderProduct.getPrice());
    }
}

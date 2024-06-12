package joao.pedro.productsapi.entity.orderProduct.gateway;

import joao.pedro.productsapi.entity.order.model.Order;
import joao.pedro.productsapi.entity.orderProduct.model.OrderProduct;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderProductGateway {

    OrderProduct create(OrderProduct orderProduct);
    Optional<OrderProduct> findById(UUID id);
    List<OrderProduct> findByOrder(Order order);
}

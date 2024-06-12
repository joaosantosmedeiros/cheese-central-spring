package joao.pedro.productsapi.infrastructure.gateways.orderProduct;

import joao.pedro.productsapi.entity.order.model.Order;
import joao.pedro.productsapi.entity.orderProduct.gateway.OrderProductGateway;
import joao.pedro.productsapi.entity.orderProduct.model.OrderProduct;
import joao.pedro.productsapi.infrastructure.config.db.repository.OrderProductRepository;
import joao.pedro.productsapi.infrastructure.config.db.schema.OrderProductEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class OrderProductDatabaseGateway implements OrderProductGateway {

    private OrderProductRepository orderProductRepository;

    @Override
    public OrderProduct create(OrderProduct orderProduct) {
        orderProductRepository.save(orderProduct.toOrderProductEntity());
        return orderProduct;
    }

    @Override
    public Optional<OrderProduct> findById(UUID id) {
        return orderProductRepository.findById(id).map(OrderProductEntity::toOrderProduct);
    }

    @Override
    public List<OrderProduct> findByOrder(Order order) {
        return orderProductRepository.findByOrder(order.toOrderEntity()).stream()
                .map(OrderProductEntity::toOrderProduct).collect(Collectors.toList());
    }
}

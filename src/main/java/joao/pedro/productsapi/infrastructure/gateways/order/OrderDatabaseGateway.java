package joao.pedro.productsapi.infrastructure.gateways.order;

import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.entity.order.gateway.OrderGateway;
import joao.pedro.productsapi.entity.order.model.FetchedOrder;
import joao.pedro.productsapi.entity.order.model.Order;
import joao.pedro.productsapi.infrastructure.config.db.repository.OrderRepository;
import joao.pedro.productsapi.infrastructure.config.db.schema.OrderEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class OrderDatabaseGateway implements OrderGateway {

    private final OrderRepository orderRepository;

    @Override
    public Order create(Order order) {
        orderRepository.save(order.toOrderEntity());
        return order;
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return orderRepository.findById(id).map(OrderEntity::toOrder);
    }

    @Override
    public List<Order> findByAccount(Account account) {
        return orderRepository.findByAccount(account.toAccountEntity())
                .stream().map(orderEntity -> {
                    var payment = orderEntity.getPayment();
                    payment.setOrder(null);
                    orderEntity.setPayment(payment);
                    return orderEntity.toOrder();
                }).collect(Collectors.toList());
    }
}

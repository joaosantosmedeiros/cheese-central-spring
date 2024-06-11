package joao.pedro.productsapi.entity.order.gateway;

import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.entity.order.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderGateway {

    Order create(Order order);
    Optional<Order> findById(UUID id);
    List<Order> findByAccount(Account account);
}

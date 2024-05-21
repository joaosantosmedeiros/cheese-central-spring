package joao.pedro.productsapi.entity.cart.gateway;

import joao.pedro.productsapi.entity.cart.model.Cart;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartGateway {
    Cart create(Cart cart);

    Optional<Cart> findById(UUID id);

    Optional<Cart> findByAccountId(UUID accountId);

    List<Cart> findAllByAccountId(UUID accountId);

    void delete(Cart cart);
}

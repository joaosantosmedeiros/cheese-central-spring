package joao.pedro.productsapi.infrastructure.gateways.cart;

import joao.pedro.productsapi.entity.cart.gateway.CartGateway;
import joao.pedro.productsapi.entity.cart.model.Cart;
import joao.pedro.productsapi.infrastructure.config.db.repository.CartRepository;
import joao.pedro.productsapi.infrastructure.config.db.schema.AccountEntity;
import joao.pedro.productsapi.infrastructure.config.db.schema.CartEntity;
import joao.pedro.productsapi.infrastructure.config.db.schema.CartProductEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CartDatabaseGateway implements CartGateway {

    private CartRepository cartRepository;

    @Override
    public Cart create(Cart cart) {
        CartEntity cartEntity = new CartEntity(
                cart.getId(),
                cart.isActive(),
                cart.getAccount().toAccountEntity(),
                null
        );

        cartRepository.save(cartEntity);
        return cart;
    }

    @Override
    public Optional<Cart> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<Cart> findActiveByAccountId(UUID id) {
        return cartRepository.findByAccountIdAndIsActiveTrue(id).map(cartEntity -> new Cart(
                cartEntity.getId(),
                cartEntity.isActive(),
                cartEntity.getAccount().toAccount(),
                cartEntity.toCart().getCartProducts()
        ));
    }

    @Override
    public List<Cart> findByAccountId(UUID accountId) {
        return cartRepository.findByAccountId(accountId).stream().map(cartEntity -> new Cart(
                cartEntity.getId(),
                cartEntity.isActive(),
                cartEntity.getAccount().toAccount(),
                cartEntity.getCartProducts().stream().map(CartProductEntity::toCartProduct).collect(Collectors.toList())
        )).collect(Collectors.toList());
    }

    @Override
    public void delete(Cart cart) {
        CartEntity cartEntity = new CartEntity(
                cart.getId(),
                cart.isActive(),
                cart.getAccount().toAccountEntity(),
                null
        );

        cartEntity.setActive(false);
        cartRepository.save(cartEntity);
    }
}

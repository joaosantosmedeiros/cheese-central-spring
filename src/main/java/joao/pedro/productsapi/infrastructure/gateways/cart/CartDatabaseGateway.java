package joao.pedro.productsapi.infrastructure.gateways.cart;

import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.entity.cart.gateway.CartGateway;
import joao.pedro.productsapi.entity.cart.model.Cart;
import joao.pedro.productsapi.infrastructure.config.db.repository.CartRepository;
import joao.pedro.productsapi.infrastructure.config.db.schema.AccountEntity;
import joao.pedro.productsapi.infrastructure.config.db.schema.CartEntity;
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
                new AccountEntity(
                        cart.getAccount().getId(),
                        cart.getAccount().getUsername(),
                        cart.getAccount().getEmail(),
                        cart.getAccount().getPassword(),
                        cart.getAccount().isDeleted(),
                        cart.getAccount().getRole(),
                        List.of()
                )
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
                new Account(
                        cartEntity.getAccount().getId(),
                        cartEntity.getAccount().getUsername(),
                        cartEntity.getAccount().getEmail(),
                        cartEntity.getAccount().getPassword(),
                        cartEntity.getAccount().isDeleted(),
                        cartEntity.getAccount().getRole(),
                        List.of()
                )
        ));
    }

    @Override
    public List<Cart> findByAccountId(UUID accountId) {
        return cartRepository.findByAccountId(accountId).stream().map(cartEntity -> new Cart(
                cartEntity.getId(),
                cartEntity.isActive(),
                new Account(
                        cartEntity.getAccount().getId(),
                        cartEntity.getAccount().getUsername(),
                        cartEntity.getAccount().getEmail(),
                        cartEntity.getAccount().getPassword(),
                        cartEntity.getAccount().isDeleted(),
                        cartEntity.getAccount().getRole(),
                        List.of()
                )
        )).collect(Collectors.toList());
    }

    @Override
    public void delete(Cart cart) {

    }
}

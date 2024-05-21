package joao.pedro.productsapi.entity.cartProduct.gateway;

import joao.pedro.productsapi.entity.cartProduct.model.CartProduct;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartProductGateway {
    CartProduct create(CartProduct cartProduct);

    Optional<CartProduct> findById(UUID id);

    List<CartProduct> findByCartId(UUID cartId);

    CartProduct update(CartProduct cartProduct);

    void delete(CartProduct cartProduct);
}

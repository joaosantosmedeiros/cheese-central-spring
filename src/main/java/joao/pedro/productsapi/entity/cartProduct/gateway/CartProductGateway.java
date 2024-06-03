package joao.pedro.productsapi.entity.cartProduct.gateway;

import joao.pedro.productsapi.entity.cart.model.Cart;
import joao.pedro.productsapi.entity.cartProduct.model.CartProduct;
import joao.pedro.productsapi.entity.product.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartProductGateway {
    CartProduct create(CartProduct cartProduct);

    Optional<CartProduct> findByProductAndCart(Product product, Cart cart);

    Optional<CartProduct> findById(UUID id);

    List<CartProduct> findByCartId(UUID cartId);

    CartProduct update(CartProduct cartProduct);

    void delete(CartProduct cartProduct);
}

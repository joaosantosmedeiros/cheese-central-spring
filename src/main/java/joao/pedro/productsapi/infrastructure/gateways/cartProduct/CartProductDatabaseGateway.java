package joao.pedro.productsapi.infrastructure.gateways.cartProduct;

import joao.pedro.productsapi.entity.cart.model.Cart;
import joao.pedro.productsapi.entity.cartProduct.gateway.CartProductGateway;
import joao.pedro.productsapi.entity.cartProduct.model.CartProduct;
import joao.pedro.productsapi.entity.category.model.Category;
import joao.pedro.productsapi.entity.product.model.Product;
import joao.pedro.productsapi.infrastructure.config.db.repository.CartProductRepository;
import joao.pedro.productsapi.infrastructure.config.db.schema.CartEntity;
import joao.pedro.productsapi.infrastructure.config.db.schema.CartProductEntity;
import joao.pedro.productsapi.infrastructure.config.db.schema.ProductEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CartProductDatabaseGateway implements CartProductGateway {

    private final CartProductRepository repository;

    @Override
    public CartProduct create(CartProduct cartProduct) {
        CartProductEntity cartProductEntity = toCartProductEntity(cartProduct);
        repository.save(cartProductEntity);
        return cartProduct;
    }

    @Override
    public Optional<CartProduct> findByProductAndCart(Product product, Cart cart) {
        var cartProductEntity = repository.findByCartIdAndProductId(cart.getId(), product.getId());
        return cartProductEntity.map(this::toCartProduct);

    }

    @Override
    public Optional<CartProduct> findById(UUID id) {
        return repository.findById(id).map(this::toCartProduct);
    }

    @Override
    public List<CartProduct> findByCart(Cart cart) {
        return repository.findByCart(cart.toCartEntity()).stream().map(this::toCartProduct)
                .collect(Collectors.toList());
    }

    @Override
    public CartProduct update(CartProduct cartProduct) {
        CartProductEntity cartProductEntity = toCartProductEntity(cartProduct);
        repository.save(cartProductEntity);
        return cartProduct;
    }

    @Override
    public void delete(CartProduct cartProduct) {
        repository.delete(toCartProductEntity(cartProduct));
    }

    private CartProduct toCartProduct(CartProductEntity cartProductEntity){
        ProductEntity productEntity = cartProductEntity.getProduct();
        CartEntity cartEntity = cartProductEntity.getCart();

        return new CartProduct(
                cartProductEntity.getId(),
                cartProductEntity.getAmount(),
                new Cart(
                        cartEntity.getId(),
                        cartEntity.isActive(),
                        null,
                        null
                ),
                new Product(
                        productEntity.getId(),
                        productEntity.getName(),
                        productEntity.getDecription(),
                        productEntity.getImageUrl(),
                        productEntity.getPrice(),
                        new Category(
                                productEntity.getCategory().getId(),
                                productEntity.getCategory().getName()
                        )
                )
        );
    }

    private CartProductEntity toCartProductEntity(CartProduct cartProduct){
        Cart cart = cartProduct.getCart();
        Product product = cartProduct.getProduct();

        return new CartProductEntity(
                cartProduct.getId(),
                cartProduct.getAmount(),
                new CartEntity(
                        cart.getId(),
                        cart.isActive(),
                        null,
                        null
                ),
                new ProductEntity(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getImageUrl(),
                        product.getPrice(),
                        null
                )
        );
    }
}

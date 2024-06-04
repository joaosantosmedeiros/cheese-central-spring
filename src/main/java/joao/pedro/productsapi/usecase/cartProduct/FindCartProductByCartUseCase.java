package joao.pedro.productsapi.usecase.cartProduct;

import joao.pedro.productsapi.entity.cartProduct.gateway.CartProductGateway;
import joao.pedro.productsapi.entity.cartProduct.model.FetchedCartProduct;

import java.util.UUID;

public class FindCartProductByCartUseCase {

    private final CartProductGateway cartProductGateway;

    public FindCartProductByCartUseCase(CartProductGateway cartProductGateway) {
        this.cartProductGateway = cartProductGateway;
    }

    public Output execute(Input input) {
        var cartProducts = cartProductGateway.findByCartId(input.cartId).stream().map(FetchedCartProduct::new)
                .toList().toArray(new FetchedCartProduct[0]);

        return new Output(cartProducts);
    }

    public record Input(UUID cartId) {}
    public record Output(FetchedCartProduct[] data) {}
}

package joao.pedro.productsapi.usecase.cartProduct;

import joao.pedro.productsapi.entity.cartProduct.gateway.CartProductGateway;
import joao.pedro.productsapi.entity.cartProduct.model.FetchedCartProduct;

import java.util.List;
import java.util.UUID;

public class FindCartProductByCartUseCase {

    private final CartProductGateway cartProductGateway;

    public FindCartProductByCartUseCase(CartProductGateway cartProductGateway) {
        this.cartProductGateway = cartProductGateway;
    }

    public Output execute(Input input) {
        var cartProducts = cartProductGateway.findByCartId(input.cartId).stream().map(FetchedCartProduct::new).toList();
        return new Output(cartProducts);
    }

    public record Input(UUID cartId) {}
    public record Output(List<FetchedCartProduct> data) {}
}

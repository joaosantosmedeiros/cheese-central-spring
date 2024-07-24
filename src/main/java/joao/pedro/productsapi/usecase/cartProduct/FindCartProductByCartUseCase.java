package joao.pedro.productsapi.usecase.cartProduct;

import joao.pedro.productsapi.entity.cart.model.Cart;
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
        var cartProducts = cartProductGateway.findByCart(input.cart).stream().map(FetchedCartProduct::new).toList();
        return new Output(cartProducts);
    }

    public record Input(Cart cart) {}
    public record Output(List<FetchedCartProduct> data) {}
}

package joao.pedro.productsapi.usecase.cart;

import joao.pedro.productsapi.entity.cart.gateway.CartGateway;
import joao.pedro.productsapi.entity.cart.model.Cart;
import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;

import java.util.UUID;

public class FindActiveCartByAccountIdUseCase {

    private final CartGateway cartGateway;

    public FindActiveCartByAccountIdUseCase(CartGateway cartGateway) {
        this.cartGateway = cartGateway;
    }

    public Output execute(Input input) {
        var cart = cartGateway.findActiveByAccountId(input.accountId);
        if(cart.isEmpty()){
            throw new EntityNotFoundException("Cart");
        }

        return new Output(cart.get());
    }

    public record Input(UUID accountId){};
    public record Output(Cart data){}
}

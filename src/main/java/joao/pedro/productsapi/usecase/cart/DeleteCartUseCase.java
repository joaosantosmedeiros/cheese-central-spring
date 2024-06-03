package joao.pedro.productsapi.usecase.cart;

import joao.pedro.productsapi.entity.cart.gateway.CartGateway;
import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;

import java.util.UUID;

public class DeleteCartUseCase {

    private final CartGateway cartGateway;

    public DeleteCartUseCase(CartGateway cartGateway){
        this.cartGateway = cartGateway;
    }

    public void execute(Input input) {
        var cart = cartGateway.findActiveByAccountId(input.accountId);
        if(cart.isEmpty()){
            throw new EntityNotFoundException("Cart");
        }

        cartGateway.delete(cart.get());
    }

    public record Input(UUID accountId){}
}

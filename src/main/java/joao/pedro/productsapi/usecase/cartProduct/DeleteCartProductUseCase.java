package joao.pedro.productsapi.usecase.cartProduct;

import joao.pedro.productsapi.entity.cartProduct.gateway.CartProductGateway;
import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;
import joao.pedro.productsapi.entity.exceptions.NotAuthorizedException;

import java.util.UUID;

public class DeleteCartProductUseCase {

    private final CartProductGateway cartProductGateway;

    public DeleteCartProductUseCase(CartProductGateway cartProductGateway) {
        this.cartProductGateway = cartProductGateway;
    }

    public void execute(Input input) {
        var cartProductExists = cartProductGateway.findById(input.cartProductId).orElseThrow(() -> new EntityNotFoundException("CartProduct"));
        if(cartProductExists.getCart().getId() != input.cartId){
            throw new NotAuthorizedException();
        }

        cartProductGateway.delete(cartProductExists);
    }

    public record Input(UUID cartProductId, UUID cartId) {}
}

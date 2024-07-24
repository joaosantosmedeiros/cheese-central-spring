package joao.pedro.productsapi.usecase.cartProduct;

import joao.pedro.productsapi.entity.cartProduct.gateway.CartProductGateway;
import joao.pedro.productsapi.entity.cartProduct.model.CartProduct;
import joao.pedro.productsapi.entity.cartProduct.model.FetchedCartProduct;
import joao.pedro.productsapi.entity.exceptions.BadRequestException;
import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;
import joao.pedro.productsapi.entity.exceptions.NotAuthorizedException;

import java.util.UUID;

public class UpdateCartProductUseCase {

    private final CartProductGateway cartProductGateway;

    public UpdateCartProductUseCase(CartProductGateway cartProductGateway) {
        this.cartProductGateway = cartProductGateway;
    }

    public Output execute(Input input) {
        var cartProduct = cartProductGateway.findById(input.cartProductId()).orElseThrow(() -> new EntityNotFoundException("CartProduct"));

        if(input.amount < 1) {
            throw new BadRequestException("Amount must be positive.");
        }

        if(cartProduct.getCart().getId() != input.cartId()){
            throw new NotAuthorizedException();
        }

        cartProduct.setAmount(input.amount);

        return new Output(new FetchedCartProduct(cartProductGateway.update(cartProduct)));
    }

    public record Input(UUID cartProductId, UUID cartId, int amount) {}
    public record Output(FetchedCartProduct data) {}
}

package joao.pedro.productsapi.usecase.cartProduct;

import joao.pedro.productsapi.entity.cartProduct.gateway.CartProductGateway;
import joao.pedro.productsapi.entity.cartProduct.model.CartProduct;
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
        var cartProductExists = cartProductGateway.findById(input.cartProductId());
        if(cartProductExists.isEmpty()){
            throw new EntityNotFoundException("CartProduct");
        }

        if(input.amount < 1) {
            throw new BadRequestException("Amount must be positive.");
        }

        if(cartProductExists.get().getCart().getId() != input.cartId()){
            throw new NotAuthorizedException();
        }

        CartProduct cartProduct = cartProductExists.get();
        cartProduct.setAmount(input.amount);

        return new Output(cartProductGateway.update(cartProduct));
    }

    public record Input(UUID cartProductId, UUID cartId, int amount) {}
    public record Output(CartProduct data) {}
}

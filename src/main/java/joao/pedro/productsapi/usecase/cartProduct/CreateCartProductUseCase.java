package joao.pedro.productsapi.usecase.cartProduct;

import joao.pedro.productsapi.entity.cart.model.Cart;
import joao.pedro.productsapi.entity.cartProduct.gateway.CartProductGateway;
import joao.pedro.productsapi.entity.cartProduct.model.CartProduct;
import joao.pedro.productsapi.entity.exceptions.EntityAlreadyExistsException;
import joao.pedro.productsapi.entity.product.model.Product;

public class CreateCartProductUseCase {

    private final CartProductGateway cartProductGateway;

    public CreateCartProductUseCase(CartProductGateway cartProductGateway) {
        this.cartProductGateway = cartProductGateway;
    }

    public Output execute(Input input) {
        var cartProductExists = cartProductGateway.findByProductAndCart(input.product, input.cart());
        if(cartProductExists.isPresent()){
            throw new EntityAlreadyExistsException("CartProduct");
        }

        CartProduct cartProduct = new CartProduct(input.amount, input.cart, input.product);
        return new Output(cartProductGateway.create(cartProduct));
    }

    public record Input(int amount, Cart cart, Product product) {}
    public record Output(CartProduct data) {}
}

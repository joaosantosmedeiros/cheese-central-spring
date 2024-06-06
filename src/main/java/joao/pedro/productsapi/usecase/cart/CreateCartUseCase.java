package joao.pedro.productsapi.usecase.cart;

import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.entity.cart.gateway.CartGateway;
import joao.pedro.productsapi.entity.cart.model.Cart;
import joao.pedro.productsapi.entity.product.model.Product;

import java.util.List;

public class CreateCartUseCase {

    private final CartGateway cartGateway;

    public CreateCartUseCase(CartGateway cartGateway) {
        this.cartGateway = cartGateway;
    }

    public Output execute(Input input){
        List<Cart> carts = cartGateway.findByAccountId(input.account.getId());
        for(Cart cart : carts) {
            if(cart.isActive()){
                return new Output(cart);
            }
        }

        return new Output(cartGateway.create(new Cart(true, input.account, null)));
    }

    public record Input(Product product, Account account, int amount){}
    public record Output(Cart data){}
}

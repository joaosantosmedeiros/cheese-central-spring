package joao.pedro.productsapi.infrastructure.controllers.cart;

import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.entity.cart.model.Cart;
import joao.pedro.productsapi.entity.cartProduct.model.FetchedCartProduct;
import joao.pedro.productsapi.infrastructure.config.db.schema.AccountEntity;
import joao.pedro.productsapi.usecase.account.FindAccountByEmailUseCase;
import joao.pedro.productsapi.usecase.cart.FindActiveCartByAccountIdUseCase;
import joao.pedro.productsapi.usecase.cartProduct.FindCartProductByCartUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ViewCartController {

    private final FindAccountByEmailUseCase findAccountByEmailUseCase;
    private final FindActiveCartByAccountIdUseCase findActiveCartByAccountIdUseCase;
    private final FindCartProductByCartUseCase findCartProductByCartUseCase;

    @GetMapping("/cart/view")
    public ResponseEntity<Response> viewItems() {
        var tokenEmail = ((AccountEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();
        Account account = findAccountByEmailUseCase.execute(new FindAccountByEmailUseCase.Input(tokenEmail)).data();
        Cart cart = findActiveCartByAccountIdUseCase.execute(new FindActiveCartByAccountIdUseCase.Input(account.getId())).data();

        var cartProducts = findCartProductByCartUseCase.execute(new FindCartProductByCartUseCase.Input(cart.getId()));

        return ResponseEntity.ok(new Response(
                "Showing found cart items.",
                true,
                cartProducts.data()
        ));
    }

    public record Response(
            String message,
            boolean status,
            FetchedCartProduct[] data
    ) {}
}

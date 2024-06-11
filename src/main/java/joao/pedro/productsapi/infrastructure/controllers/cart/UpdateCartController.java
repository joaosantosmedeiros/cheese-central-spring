package joao.pedro.productsapi.infrastructure.controllers.cart;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import joao.pedro.productsapi.entity.cartProduct.model.CartProduct;
import joao.pedro.productsapi.entity.cartProduct.model.FetchedCartProduct;
import joao.pedro.productsapi.infrastructure.config.db.schema.AccountEntity;
import joao.pedro.productsapi.usecase.account.FindAccountByEmailUseCase;
import joao.pedro.productsapi.usecase.cart.FindActiveCartByAccountIdUseCase;
import joao.pedro.productsapi.usecase.cartProduct.UpdateCartProductUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class UpdateCartController {

    private final UpdateCartProductUseCase updateCartProductUseCase;
    private final FindAccountByEmailUseCase findAccountByEmailUseCase;
    private final FindActiveCartByAccountIdUseCase findActiveCartByAccountIdUseCase;

    @PutMapping("/cart/cartProduct/{cartProductId}")
    public ResponseEntity<Response> updateCart(@RequestBody @Valid Request request, @PathVariable UUID cartProductId) {
        var tokenEmail = ((AccountEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();
        var account = findAccountByEmailUseCase.execute(new FindAccountByEmailUseCase.Input(tokenEmail)).data();
        var cart = findActiveCartByAccountIdUseCase.execute(new FindActiveCartByAccountIdUseCase.Input(account.getId())).data();

        var cartProduct = updateCartProductUseCase.execute(new UpdateCartProductUseCase.Input(cartProductId, cart.getId(), request.amount));

        return ResponseEntity.status(HttpStatus.OK).body(new Response(
                "CartProduct updated successfully.",
                true,
                cartProduct.data()
        ));
    }

    public record Request(@Positive int amount) {}
    public record Response(String message, boolean status, FetchedCartProduct data) {}
}

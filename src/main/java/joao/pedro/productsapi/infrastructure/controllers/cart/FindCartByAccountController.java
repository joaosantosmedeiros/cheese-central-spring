package joao.pedro.productsapi.infrastructure.controllers.cart;

import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.entity.cart.model.FetchedCart;
import joao.pedro.productsapi.infrastructure.config.db.schema.AccountEntity;
import joao.pedro.productsapi.usecase.account.FindAccountByEmailUseCase;
import joao.pedro.productsapi.usecase.cart.FindActiveCartByAccountIdUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class FindCartByAccountController {

    private FindAccountByEmailUseCase findAccountByEmailUseCase;
    private FindActiveCartByAccountIdUseCase findActiveCartByAccountIdUseCase;

    @GetMapping("/cart")
    public ResponseEntity<Response> findCart() {
        var tokenEmail = ((AccountEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();
        Account account = findAccountByEmailUseCase.execute(new FindAccountByEmailUseCase.Input(tokenEmail)).data();
        var cart = findActiveCartByAccountIdUseCase.execute(new FindActiveCartByAccountIdUseCase.Input(account.getId())).data();

        return ResponseEntity.status(HttpStatus.OK).body(new Response(
              "Showing found cart",
              true,
              new FetchedCart(cart)
        ));
    }

    public record Response(
            String message,
            boolean status,
            FetchedCart data
    ){}
}

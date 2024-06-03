package joao.pedro.productsapi.infrastructure.controllers.cart;

import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.infrastructure.config.db.schema.AccountEntity;
import joao.pedro.productsapi.usecase.account.FindAccountByEmailUseCase;
import joao.pedro.productsapi.usecase.cart.DeleteCartUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class DeleteCartController {

    private DeleteCartUseCase deleteCartUseCase;
    private FindAccountByEmailUseCase findAccountByEmailUseCase;

    @DeleteMapping("/cart")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        var tokenEmail = ((AccountEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();
        Account account = findAccountByEmailUseCase.execute(new FindAccountByEmailUseCase.Input(tokenEmail)).data();

        deleteCartUseCase.execute(new DeleteCartUseCase.Input(account.getId()));
    }
}

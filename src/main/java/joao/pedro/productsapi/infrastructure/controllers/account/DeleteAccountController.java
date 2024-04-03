package joao.pedro.productsapi.infrastructure.controllers.account;

import joao.pedro.productsapi.entity.exceptions.NotAuthorizedException;
import joao.pedro.productsapi.infrastructure.config.db.schema.AccountEntity;
import joao.pedro.productsapi.usecase.account.DeleteAccountUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class DeleteAccountController {

    private DeleteAccountUseCase deleteAccountUseCase;

    @DeleteMapping("/account/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable(value = "email") String email) {
        var tokenEmail = ((AccountEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();
        if(!tokenEmail.equals(email)){
            throw new NotAuthorizedException();
        }

        deleteAccountUseCase.execute(new DeleteAccountUseCase.Input(email));
    }
}

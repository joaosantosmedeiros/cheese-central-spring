package joao.pedro.productsapi.infrastructure.controllers.account;

import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.infrastructure.config.db.schema.AccountEntity;
import joao.pedro.productsapi.infrastructure.dtos.StandardResponse;
import joao.pedro.productsapi.usecase.account.UpdateAccountUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UpdateAccountController {

    private final UpdateAccountUseCase updateAccountUseCase;

    @PutMapping("/account")
    public ResponseEntity<StandardResponse<Object>> updateAccount(@RequestBody Request request) {
        var account = (AccountEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var updatedAccount = updateAccountUseCase.execute(new UpdateAccountUseCase.Input(
                new Account(request.username, account.getEmail(), request.password, account.isDeleted(), account.getRole(), null)
        ));
        return ResponseEntity.ok(new StandardResponse<>(
                "Account updated successfully",
                true,
                updatedAccount
        ));
    }

    public record Request(
            String username,
            String password
    ) {}
}

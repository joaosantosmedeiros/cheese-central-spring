package joao.pedro.productsapi.infrastructure.controllers.account;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import joao.pedro.productsapi.infrastructure.config.db.schema.AccountEntity;
import joao.pedro.productsapi.usecase.account.DeleteAccountUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Accounts", description = "Operations related to accounts.")
public class DeleteAccountController {

    private DeleteAccountUseCase deleteAccountUseCase;

    @DeleteMapping("/account")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Account deletion", summary = "Delete an account.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Success."
            ),
    })
    public void deleteAccount() {
        var tokenEmail = ((AccountEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();
        deleteAccountUseCase.execute(new DeleteAccountUseCase.Input(tokenEmail));
    }
}

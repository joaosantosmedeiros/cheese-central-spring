package joao.pedro.productsapi.infrastructure.controllers.account;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.entity.account.model.FetchedAccount;
import joao.pedro.productsapi.infrastructure.config.db.schema.AccountEntity;
import joao.pedro.productsapi.infrastructure.config.springdoc.schemas.DefaultErrorResponseSchema;
import joao.pedro.productsapi.infrastructure.config.springdoc.schemas.UpdateAccountSchema;
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
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Accounts", description = "Operations related to accounts.")
public class UpdateAccountController {

    private final UpdateAccountUseCase updateAccountUseCase;

    @PutMapping("/account")
    @Operation(description = "Account update.", summary = "Update an account.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json", schema = @Schema(implementation= UpdateAccountSchema.class))
    ))
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Success"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid data provided.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultErrorResponseSchema.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Account not found.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultErrorResponseSchema.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Username already in use.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultErrorResponseSchema.class)
                    )
            )
    })
    public ResponseEntity<StandardResponse<FetchedAccount>> updateAccount(@RequestBody Request request) {
        var account = (AccountEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var updatedAccount = updateAccountUseCase.execute(new UpdateAccountUseCase.Input(
                new Account(request.username, account.getEmail(), request.password, account.isDeleted(), account.getRole(), null)
        ));
        return ResponseEntity.ok(new StandardResponse<>(
                "Account updated successfully",
                true,
                updatedAccount.data()
        ));
    }

    public record Request(
            String username,
            String password
    ) {}
}

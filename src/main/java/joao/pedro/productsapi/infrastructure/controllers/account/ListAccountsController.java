package joao.pedro.productsapi.infrastructure.controllers.account;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import joao.pedro.productsapi.entity.account.model.FetchedAccount;
import joao.pedro.productsapi.infrastructure.config.springdoc.schemas.DefaultErrorResponseSchema;
import joao.pedro.productsapi.infrastructure.dtos.StandardResponse;
import joao.pedro.productsapi.usecase.account.ListAccountsUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SecurityRequirement(name = "bearer-key")
@Tag(name = "Accounts", description = "Operations related to accounts.")
@RestController
@AllArgsConstructor
public class ListAccountsController {

    private ListAccountsUseCase listAccountsUseCase;

    @GetMapping("/account")
    @Operation(description = "Accounts listing.", summary = "List all accounts." )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Success."
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden access",
                    content = @Content()
            ),
    })
    public ResponseEntity<StandardResponse<List<FetchedAccount>>> listAccounts() {
        var fetchedAccounts = listAccountsUseCase.execute()
                .data()
                .stream()
                .map(FetchedAccount::new)
                .toList();

        return ResponseEntity.ok(new StandardResponse<>(
                "Listing accounts.",
                true,
                fetchedAccounts
        ));
    }

}

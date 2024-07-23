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
import joao.pedro.productsapi.usecase.account.FindAccountByEmailUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "bearer-key")
@Tag(name = "Accounts", description = "Operations related to accounts.")
@RestController
@AllArgsConstructor
public class FindAccountByEmailController {

    private final FindAccountByEmailUseCase findAccountByEmailUseCase;

    @GetMapping("/account/{email}")
    @Operation(description = "Account detailing", summary = "Find an account" )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Success."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Account not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultErrorResponseSchema.class)
                    )
            ),
    })
    public ResponseEntity<StandardResponse<FetchedAccount>> findAccount(@PathVariable(value = "email") String email) {
       var output = findAccountByEmailUseCase.execute(new FindAccountByEmailUseCase.Input(email));

       return ResponseEntity.ok(new StandardResponse<>(
               "Account found successfully.",
               true,
               new FetchedAccount(output.data())
       ));
    }
}

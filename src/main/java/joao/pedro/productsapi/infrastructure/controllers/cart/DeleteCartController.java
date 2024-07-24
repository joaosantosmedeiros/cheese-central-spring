package joao.pedro.productsapi.infrastructure.controllers.cart;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import joao.pedro.productsapi.infrastructure.config.db.schema.AccountEntity;
import joao.pedro.productsapi.infrastructure.config.springdoc.schemas.DefaultErrorResponseSchema;
import joao.pedro.productsapi.usecase.cart.DeleteCartUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Tag(name = "Carts", description = "Operation related to carts.")
@SecurityRequirement(name = "bearer-key")
public class DeleteCartController {

    private DeleteCartUseCase deleteCartUseCase;

    @DeleteMapping("/cart")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Cart delete", summary = "Delete a cart", responses = {
            @ApiResponse(responseCode = "204", description = "Success."),
            @ApiResponse(responseCode = "403", description = "Forbidden access.", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Cart not found.",
                    content = @Content(schema = @Schema(implementation = DefaultErrorResponseSchema.class))),
    })
    public void delete() {
        var account = ((AccountEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).toAccount();
        deleteCartUseCase.execute(new DeleteCartUseCase.Input(account.getId()));
    }
}

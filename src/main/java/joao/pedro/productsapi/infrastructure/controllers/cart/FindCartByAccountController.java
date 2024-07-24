package joao.pedro.productsapi.infrastructure.controllers.cart;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.entity.cart.model.FetchedCart;
import joao.pedro.productsapi.infrastructure.config.db.schema.AccountEntity;
import joao.pedro.productsapi.infrastructure.config.springdoc.schemas.DefaultErrorResponseSchema;
import joao.pedro.productsapi.infrastructure.dtos.StandardResponse;
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
@Tag(name = "Carts", description = "Operation related to carts.")
@SecurityRequirement(name = "bearer-key")
public class FindCartByAccountController {

    private FindActiveCartByAccountIdUseCase findActiveCartByAccountIdUseCase;

    @GetMapping("/cart")
    @Operation(description = "Cart search", summary = "Search for the user's cart", responses = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "403", description = "Forbidden access.", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Cart not found.",
                    content = @Content(schema = @Schema(implementation = DefaultErrorResponseSchema.class)))
    })
    public ResponseEntity<StandardResponse<FetchedCart>> findCart() {
        var account = ((AccountEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).toAccount();
        var cart = findActiveCartByAccountIdUseCase.execute(new FindActiveCartByAccountIdUseCase.Input(account.getId())).data();

        return ResponseEntity.status(HttpStatus.OK).body(new StandardResponse<>(
              "Showing found cart",
              true,
              new FetchedCart(cart)
        ));
    }
}

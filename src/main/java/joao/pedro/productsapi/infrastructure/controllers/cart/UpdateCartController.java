package joao.pedro.productsapi.infrastructure.controllers.cart;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import joao.pedro.productsapi.entity.cartProduct.model.CartProduct;
import joao.pedro.productsapi.entity.cartProduct.model.FetchedCartProduct;
import joao.pedro.productsapi.infrastructure.config.db.schema.AccountEntity;
import joao.pedro.productsapi.infrastructure.config.springdoc.schemas.DefaultErrorResponseSchema;
import joao.pedro.productsapi.infrastructure.config.springdoc.schemas.UpdateCartSchema;
import joao.pedro.productsapi.infrastructure.dtos.StandardResponse;
import joao.pedro.productsapi.usecase.account.FindAccountByEmailUseCase;
import joao.pedro.productsapi.usecase.cart.FindActiveCartByAccountIdUseCase;
import joao.pedro.productsapi.usecase.cartProduct.UpdateCartProductUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@Tag(name = "Carts", description = "Operation related to carts.")
@SecurityRequirement(name = "bearer-key")
public class UpdateCartController {

    private final UpdateCartProductUseCase updateCartProductUseCase;
    private final FindActiveCartByAccountIdUseCase findActiveCartByAccountIdUseCase;

    @PutMapping("/cart/cartProduct/{cartProductId}")
    @Operation(description = "Cart Product update", summary = "Update a cart product.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = UpdateCartSchema.class))),
            responses = {
                @ApiResponse(responseCode = "200", description = "Success."),
                @ApiResponse(responseCode = "400", description = "Invalid data provided", content = @Content()),
                @ApiResponse(responseCode = "403", description = "Forbidden access.", content = @Content()),
                @ApiResponse(responseCode = "404", description = "Cart/CartProduct not found.",
                        content = @Content(mediaType = "application/json",schema = @Schema(implementation = DefaultErrorResponseSchema.class))),
            }
    )
    public ResponseEntity<StandardResponse<FetchedCartProduct>> updateCart(@RequestBody @Valid Request request, @PathVariable UUID cartProductId) {
        var account = ((AccountEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).toAccount();
        var cart = findActiveCartByAccountIdUseCase.execute(new FindActiveCartByAccountIdUseCase.Input(account.getId())).data();

        var cartProduct = updateCartProductUseCase.execute(new UpdateCartProductUseCase.Input(cartProductId, cart.getId(), request.amount));

        return ResponseEntity.status(HttpStatus.OK).body(new StandardResponse<>(
                "CartProduct updated successfully.",
                true,
                cartProduct.data()
        ));
    }

    public record Request(@Positive int amount) {}
}

package joao.pedro.productsapi.infrastructure.controllers.cart;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import joao.pedro.productsapi.entity.cart.model.Cart;
import joao.pedro.productsapi.infrastructure.config.db.schema.AccountEntity;
import joao.pedro.productsapi.infrastructure.config.springdoc.schemas.DefaultErrorResponseSchema;
import joao.pedro.productsapi.usecase.cart.FindActiveCartByAccountIdUseCase;
import joao.pedro.productsapi.usecase.cartProduct.DeleteCartProductUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@Tag(name = "Carts", description = "Operation related to carts.")
@SecurityRequirement(name = "bearer-key")
public class DeleteCartProductController {

    private final FindActiveCartByAccountIdUseCase findActiveCartByAccountIdUseCase;
    private final DeleteCartProductUseCase deleteCartProductUseCase;

    @DeleteMapping("/cart/cartProduct/{cartProductId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "CartProduct delete", summary = "Delete a cart item", responses = {
            @ApiResponse(responseCode = "204", description = "Success."),
            @ApiResponse(responseCode = "403", description = "Forbiden access.", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Cart/CartProduct not found.",
                    content = @Content(schema = @Schema(implementation = DefaultErrorResponseSchema.class))),
    })
    public void deleteItem(@PathVariable UUID cartProductId) {
        var account = ((AccountEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).toAccount();
        Cart cart = findActiveCartByAccountIdUseCase.execute(new FindActiveCartByAccountIdUseCase.Input(account.getId())).data();

        deleteCartProductUseCase.execute(new DeleteCartProductUseCase.Input(
                cartProductId,
                cart.getId()
        ));
    }
}

package joao.pedro.productsapi.infrastructure.controllers.cart;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.entity.cart.model.Cart;
import joao.pedro.productsapi.entity.cartProduct.model.FetchedCartProduct;
import joao.pedro.productsapi.infrastructure.config.db.schema.AccountEntity;
import joao.pedro.productsapi.infrastructure.config.springdoc.schemas.DefaultErrorResponseSchema;
import joao.pedro.productsapi.infrastructure.dtos.StandardResponse;
import joao.pedro.productsapi.usecase.account.FindAccountByEmailUseCase;
import joao.pedro.productsapi.usecase.cart.FindActiveCartByAccountIdUseCase;
import joao.pedro.productsapi.usecase.cartProduct.FindCartProductByCartUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Carts", description = "Operation related to carts.")
@SecurityRequirement(name = "bearer-key")
public class ViewCartController {

    private final FindActiveCartByAccountIdUseCase findActiveCartByAccountIdUseCase;
    private final FindCartProductByCartUseCase findCartProductByCartUseCase;

    @GetMapping("/cart/view")
    @Operation(description = "Cart detail", summary = "Detail a cart", responses = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "403", description = "Forbidden access.", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Cart not found.",
                    content = @Content(schema = @Schema(implementation = DefaultErrorResponseSchema.class))),
    })
    public ResponseEntity<StandardResponse<List<FetchedCartProduct>>> viewItems() {
        var account = ((AccountEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).toAccount();
        Cart cart = findActiveCartByAccountIdUseCase.execute(new FindActiveCartByAccountIdUseCase.Input(account.getId())).data();
        var cartProducts = findCartProductByCartUseCase.execute(new FindCartProductByCartUseCase.Input(cart)).data();

        return ResponseEntity.ok(new StandardResponse<>(
                "Showing found cart items.",
                true,
                cartProducts
        ));
    }
}

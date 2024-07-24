package joao.pedro.productsapi.infrastructure.controllers.cart;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import joao.pedro.productsapi.entity.cart.model.FetchedCart;
import joao.pedro.productsapi.infrastructure.config.db.schema.AccountEntity;
import joao.pedro.productsapi.infrastructure.config.springdoc.schemas.CreateCartSchema;
import joao.pedro.productsapi.infrastructure.config.springdoc.schemas.DefaultErrorResponseSchema;
import joao.pedro.productsapi.infrastructure.dtos.StandardResponse;
import joao.pedro.productsapi.usecase.cart.CreateCartUseCase;
import joao.pedro.productsapi.usecase.cartProduct.CreateCartProductUseCase;
import joao.pedro.productsapi.usecase.product.FindProductByIdUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@AllArgsConstructor
@RestController
@Tag(name = "Carts", description = "Operation related to carts.")
@SecurityRequirement(name = "bearer-key")
public class CreateCartController {

    private FindProductByIdUseCase findProductByIdUseCase;
    private CreateCartUseCase createCartUseCase;
    private CreateCartProductUseCase createCartProductUseCase;

    @PostMapping("/cart")
    @Operation(description = "Cart creation", summary = "Create a cart",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = CreateCartSchema.class))),
            responses = {
                @ApiResponse(responseCode = "201", description = "Success."),
                @ApiResponse(responseCode = "400", description = "Invalid data provided.", content = @Content()),
                @ApiResponse(responseCode = "403", description = "Forbidden access.", content = @Content()),
                @ApiResponse(responseCode = "404", description = "Product not found.",
                        content = @Content(schema = @Schema(implementation = DefaultErrorResponseSchema.class))),
                @ApiResponse(responseCode = "409", description = "Product already added in cart.",
                        content = @Content(schema = @Schema(implementation = DefaultErrorResponseSchema.class))),
            }
    )
    public ResponseEntity<StandardResponse<FetchedCart>> create(@RequestBody @Valid Request request) {
        var account = ((AccountEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).toAccount();
        var product = findProductByIdUseCase.execute(new FindProductByIdUseCase.Input(request.productId)).data();
        var cart = createCartUseCase.execute(new CreateCartUseCase.Input(account)).data();

        createCartProductUseCase.execute(new CreateCartProductUseCase.Input(request.amount, cart, product));

        return ResponseEntity.status(HttpStatus.CREATED).body(new StandardResponse<>(
                "Cart created successfully",
                true,
                new FetchedCart(cart)
        ));
    }

    public record Request(UUID productId, @Positive int amount) {}
}

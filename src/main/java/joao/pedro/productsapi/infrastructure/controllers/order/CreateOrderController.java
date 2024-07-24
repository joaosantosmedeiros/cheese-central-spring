package joao.pedro.productsapi.infrastructure.controllers.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import joao.pedro.productsapi.entity.order.model.FetchedOrder;
import joao.pedro.productsapi.infrastructure.config.db.schema.AccountEntity;
import joao.pedro.productsapi.infrastructure.config.springdoc.schemas.DefaultErrorResponseSchema;
import joao.pedro.productsapi.infrastructure.dtos.StandardResponse;
import joao.pedro.productsapi.usecase.cart.DeleteCartUseCase;
import joao.pedro.productsapi.usecase.cart.FindActiveCartByAccountIdUseCase;
import joao.pedro.productsapi.usecase.order.CreateOrderUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Tag(name = "Orders", description = "Operations related to orders.")
@SecurityRequirement(name = "bearer-key")
public class CreateOrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final FindActiveCartByAccountIdUseCase findActiveCartByAccountIdUseCase;
    private final DeleteCartUseCase deleteCartUseCase;

    @PostMapping("/orders")
    @Operation(description = "Order creation", summary = "Create an order", responses = {
            @ApiResponse(responseCode = "201", description = "Success."),
            @ApiResponse(responseCode = "400", description = "Invalid data provided.",
                    content = @Content(schema = @Schema(implementation = DefaultErrorResponseSchema.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden access.", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Cart not found.",
                    content = @Content(schema = @Schema(implementation = DefaultErrorResponseSchema.class))),
    })
    public ResponseEntity<StandardResponse<FetchedOrder>> create() {
        var account = ((AccountEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).toAccount();
        var cart = findActiveCartByAccountIdUseCase.execute(new FindActiveCartByAccountIdUseCase.Input(account.getId())).data();
        var order = createOrderUseCase.execute(new CreateOrderUseCase.Input(cart)).data();

        deleteCartUseCase.execute(new DeleteCartUseCase.Input(account.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(new StandardResponse<>(
                "Order created successfully.",
                true,
                order
        ));
    }

}

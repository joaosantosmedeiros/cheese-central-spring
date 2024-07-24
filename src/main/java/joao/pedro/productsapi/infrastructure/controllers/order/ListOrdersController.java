package joao.pedro.productsapi.infrastructure.controllers.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import joao.pedro.productsapi.entity.order.model.FetchedOrder;
import joao.pedro.productsapi.infrastructure.config.db.schema.AccountEntity;
import joao.pedro.productsapi.infrastructure.dtos.StandardResponse;
import joao.pedro.productsapi.usecase.order.ListOrdersByAccountUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
@Tag(name = "Orders", description = "Operations related to orders.")
@SecurityRequirement(name = "bearer-key")
public class ListOrdersController {

    private final ListOrdersByAccountUseCase listOrdersByAccountUseCase;

    @GetMapping
    @Operation(description = "Orders listing", summary = "List all user's orders.", responses = {
            @ApiResponse(responseCode = "200", description = "Success."),
            @ApiResponse(responseCode = "403", description = "Forbidden access.", content = @Content()),
    })
    public ResponseEntity<StandardResponse<List<FetchedOrder>>> listOrders() {
        var account = ((AccountEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).toAccount();

        List<FetchedOrder> orders = listOrdersByAccountUseCase.execute(new ListOrdersByAccountUseCase.Input(account)).data();

        return ResponseEntity.ok(new StandardResponse<>(
                "Showing found orders.",
                true,
                orders
        ));
    }

}

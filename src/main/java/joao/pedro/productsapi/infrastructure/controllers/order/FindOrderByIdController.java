package joao.pedro.productsapi.infrastructure.controllers.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import joao.pedro.productsapi.entity.enums.Role;
import joao.pedro.productsapi.entity.exceptions.NotAuthorizedException;
import joao.pedro.productsapi.entity.order.model.DetailedOrder;
import joao.pedro.productsapi.infrastructure.config.db.schema.AccountEntity;
import joao.pedro.productsapi.infrastructure.config.springdoc.schemas.DefaultErrorResponseSchema;
import joao.pedro.productsapi.infrastructure.dtos.StandardResponse;
import joao.pedro.productsapi.usecase.order.FindOrderByIdUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@Tag(name = "Orders", description = "Operations related to orders.")
@SecurityRequirement(name = "bearer-key")
public class FindOrderByIdController {

    private final FindOrderByIdUseCase findOrderByIdUseCase;

    @GetMapping("/orders/{id}")
    @Operation(description = "Order detail", summary = "Detail an order", responses = {
            @ApiResponse(responseCode = "200", description = "Success." ),
            @ApiResponse(responseCode = "403", description = "User tries to access another user order", content = @Content(schema = @Schema(implementation = DefaultErrorResponseSchema.class))),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content(schema = @Schema(implementation = DefaultErrorResponseSchema.class))),
    })
    public ResponseEntity<StandardResponse<DetailedOrder>> findOrderById(@PathVariable UUID id) {
        var account = ((AccountEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        var order = findOrderByIdUseCase.findById(new FindOrderByIdUseCase.Input(id)).data();

        if(!account.getRole().equals(Role.ADMIN) && !account.getId().equals(order.getAccount().id())) {
            throw new NotAuthorizedException();
        }

        return ResponseEntity.ok(new StandardResponse<>(
                "Order found successfully",
                true,
                order
        ));
    }
}

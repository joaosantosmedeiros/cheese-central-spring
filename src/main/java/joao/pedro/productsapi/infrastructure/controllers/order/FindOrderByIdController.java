package joao.pedro.productsapi.infrastructure.controllers.order;

import joao.pedro.productsapi.entity.enums.Role;
import joao.pedro.productsapi.entity.exceptions.NotAuthorizedException;
import joao.pedro.productsapi.entity.order.model.DetailedOrder;
import joao.pedro.productsapi.infrastructure.config.db.schema.AccountEntity;
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
public class FindOrderByIdController {

    private final FindOrderByIdUseCase findOrderByIdUseCase;

    @GetMapping("/orders/{id}")
    public ResponseEntity<Response> findOrderById(@PathVariable UUID id) {
        var account = ((AccountEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        var order = findOrderByIdUseCase.findById(new FindOrderByIdUseCase.Input(id)).data();

        if(!account.getRole().equals(Role.ADMIN) && !account.getId().equals(order.getAccount().id())) {
            throw new NotAuthorizedException();
        }

        return ResponseEntity.ok(new Response(
                "Order found successfully",
                true,
                order
        ));
    }

    public record Response(
            String message,
            boolean status,
            DetailedOrder data
    ) {}
}

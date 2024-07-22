package joao.pedro.productsapi.infrastructure.controllers.order;

import joao.pedro.productsapi.entity.order.model.FetchedOrder;
import joao.pedro.productsapi.infrastructure.config.db.schema.AccountEntity;
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
public class ListOrdersController {

    private final ListOrdersByAccountUseCase listOrdersByAccountUseCase;

    @GetMapping
    public ResponseEntity<Response> listOrders() {
        var account = ((AccountEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).toAccount();

        List<FetchedOrder> orders = listOrdersByAccountUseCase.execute(new ListOrdersByAccountUseCase.Input(account)).data();

        return ResponseEntity.ok(new Response(
                "Showing found orders.",
                true,
                orders
        ));
    }

    public record Response(String message, boolean status, List<FetchedOrder> data) {}
}

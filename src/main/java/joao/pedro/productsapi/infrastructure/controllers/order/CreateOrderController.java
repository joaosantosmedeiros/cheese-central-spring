package joao.pedro.productsapi.infrastructure.controllers.order;

import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.entity.cart.model.Cart;
import joao.pedro.productsapi.entity.order.model.FetchedOrder;
import joao.pedro.productsapi.infrastructure.config.db.schema.AccountEntity;
import joao.pedro.productsapi.usecase.account.FindAccountByEmailUseCase;
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
public class CreateOrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final FindAccountByEmailUseCase findAccountByEmailUseCase;
    private final FindActiveCartByAccountIdUseCase findActiveCartByAccountIdUseCase;
    private final DeleteCartUseCase deleteCartUseCase;

    @PostMapping("/order")
    public ResponseEntity<Response> create() {
        var tokenEmail = ((AccountEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();
        Account account = findAccountByEmailUseCase.execute(new FindAccountByEmailUseCase.Input(tokenEmail)).data();
        Cart cart = findActiveCartByAccountIdUseCase.execute(new FindActiveCartByAccountIdUseCase.Input(account.getId())).data();

        var order = createOrderUseCase.execute(new CreateOrderUseCase.Input(cart)).data();

        deleteCartUseCase.execute(new DeleteCartUseCase.Input(account.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(new Response(
                "Order created successfully.",
                true,
                order
        ));
    }

    public record Response(String message, boolean status, FetchedOrder data) {}
}

package joao.pedro.productsapi.infrastructure.controllers.cart;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import joao.pedro.productsapi.entity.cart.model.Cart;
import joao.pedro.productsapi.entity.cart.model.FetchedCart;
import joao.pedro.productsapi.infrastructure.config.db.schema.AccountEntity;
import joao.pedro.productsapi.usecase.account.FindAccountByEmailUseCase;
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
public class CreateCartController {

    private FindProductByIdUseCase findProductByIdUseCase;
    private FindAccountByEmailUseCase findAccountByEmailUseCase;
    private CreateCartUseCase createCartUseCase;
    private CreateCartProductUseCase createCartProductUseCase;

    @PostMapping("/cart")
    public ResponseEntity<Response> create(@RequestBody @Valid Request request) {
        var tokenEmail = ((AccountEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();
        var account = findAccountByEmailUseCase.execute(new FindAccountByEmailUseCase.Input(tokenEmail)).data();
        var product = findProductByIdUseCase.execute(new FindProductByIdUseCase.Input(request.productId)).data();
        var cart = createCartUseCase.execute(new CreateCartUseCase.Input(product, account, request.amount)).data();

        createCartProductUseCase.execute(new CreateCartProductUseCase.Input(request.amount, cart, product));

        return ResponseEntity.status(HttpStatus.CREATED).body(new Response(
                "Cart created successfully",
                true,
                new FetchedCart(cart)
        ));
    }

    public record Request(UUID productId, @Positive
    int amount) {}
    public record Response(String message, boolean status, FetchedCart data){}
}

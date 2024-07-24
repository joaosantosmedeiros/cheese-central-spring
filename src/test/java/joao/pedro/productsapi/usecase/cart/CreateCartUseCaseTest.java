package joao.pedro.productsapi.usecase.cart;

import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.entity.cart.gateway.CartGateway;
import joao.pedro.productsapi.entity.cart.model.Cart;
import joao.pedro.productsapi.entity.enums.Role;
import joao.pedro.productsapi.entity.product.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CreateCartUseCaseTest {

    @Mock
    private CartGateway cartGateway;

    @Autowired
    @InjectMocks
    private CreateCartUseCase createCartUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("It should return the active cart")
    @Test
    void createCartAlreadyActive() {
        Account account = new Account(
                "user",
                "mail@mail.com",
                "pass",
                false,
                Role.USER,
                List.of()
        );
        var activeCart = new Cart(true, account, null);

        when(cartGateway.findByAccountId(account.getId())).thenReturn(List.of(
                new Cart(false, account, null),
                activeCart
        ));

        var cart = createCartUseCase.execute(new CreateCartUseCase.Input(account)).data();

        assertEquals(activeCart, cart);
    }

    @DisplayName("It should create a new cart if no active cart is found")
    @Test
    void createCartInactive() {
        Account account = new Account(
                "user",
                "mail@mail.com",
                "pass",
                false,
                Role.USER,
                List.of()
        );
        var carts = List.of(new Cart(false, account, null), new Cart(false, account, null));

        when(cartGateway.findByAccountId(account.getId())).thenReturn(carts);

        var cart = createCartUseCase.execute(new CreateCartUseCase.Input(account)).data();

        assertNotEquals(carts.get(0), cart);
        assertNotEquals(carts.get(1), cart);
    }
}
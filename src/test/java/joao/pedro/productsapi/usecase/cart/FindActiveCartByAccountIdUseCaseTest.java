package joao.pedro.productsapi.usecase.cart;

import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.entity.cart.gateway.CartGateway;
import joao.pedro.productsapi.entity.cart.model.Cart;
import joao.pedro.productsapi.entity.enums.Role;
import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FindActiveCartByAccountIdUseCaseTest {

    @Mock
    private CartGateway cartGateway;

    @Autowired
    @InjectMocks
    private FindActiveCartByAccountIdUseCase findActiveCartByAccountIdUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("It should find an active cart correctly")
    void findActiveCartSuccess() {
        Cart cart = new Cart(true, new Account(
                "name",
                "mail@mail.com",
                "password",
                false,
                Role.USER,
                List.of()
        ));
        when(cartGateway.findActiveByAccountId(cart.getAccount().getId())).thenReturn(Optional.of(cart));

        var foundCart = findActiveCartByAccountIdUseCase.execute(
                new FindActiveCartByAccountIdUseCase.Input(cart.getAccount().getId())
        ).data();

        assertEquals(cart, foundCart);
    }

    @Test
    @DisplayName("It should throw if no active cart is found")
    void findActiveCartNotFound() {
        when(cartGateway.findActiveByAccountId(any())).thenReturn(Optional.empty());

        var thrown = assertThrows(EntityNotFoundException.class, () -> {
            findActiveCartByAccountIdUseCase.execute(new FindActiveCartByAccountIdUseCase.Input(UUID.randomUUID()));
        });

        assertEquals("Cart not found.", thrown.getMessage());
    }
}
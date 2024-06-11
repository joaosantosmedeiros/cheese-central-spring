package joao.pedro.productsapi.usecase.cart;

import joao.pedro.productsapi.entity.cart.gateway.CartGateway;
import joao.pedro.productsapi.entity.cart.model.Cart;
import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DeleteCartUseCaseTest {

    @Mock
    private CartGateway cartGateway;

    @Autowired
    @InjectMocks
    private DeleteCartUseCase deleteCartUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("It should delete a cart correctly")
    public void deleteCartSuccess() {
        Cart cart = new Cart(true, null, null);
        when(cartGateway.findActiveByAccountId(any())).thenReturn(Optional.of(cart));

        deleteCartUseCase.execute(new DeleteCartUseCase.Input(UUID.randomUUID()));

        verify(cartGateway, times(1)).delete(cart);
    }

    @Test
    @DisplayName("It should throw if no active cart is found")
    public void deleteCartError() {
        when(cartGateway.findActiveByAccountId(any())).thenReturn(Optional.empty());

        var thrown = assertThrows(EntityNotFoundException.class, () -> {
            deleteCartUseCase.execute(new DeleteCartUseCase.Input(UUID.randomUUID()));
        });

        assertEquals("Cart not found.", thrown.getMessage());
    }
}
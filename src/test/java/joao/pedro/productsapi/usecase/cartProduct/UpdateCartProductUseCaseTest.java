package joao.pedro.productsapi.usecase.cartProduct;

import joao.pedro.productsapi.entity.cart.model.Cart;
import joao.pedro.productsapi.entity.cartProduct.gateway.CartProductGateway;
import joao.pedro.productsapi.entity.cartProduct.model.CartProduct;
import joao.pedro.productsapi.entity.exceptions.BadRequestException;
import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;
import joao.pedro.productsapi.entity.exceptions.NotAuthorizedException;
import joao.pedro.productsapi.entity.product.model.Product;
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
import static org.mockito.Mockito.when;

class UpdateCartProductUseCaseTest {

    @Mock
    private CartProductGateway cartProductGateway;

    @InjectMocks
    @Autowired
    private UpdateCartProductUseCase updateCartProductUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("It should update a cart correctly")
    public void updateCartCorrectly() {
        int amount = 12;
        CartProduct cartProduct = new CartProduct(
                5,
                new Cart(
                        true,
                        null,
                        null
                ),
                new Product("name", "desc", "image", 12.2, null)
        );

        when(cartProductGateway.findById(cartProduct.getId())).thenReturn(Optional.of(cartProduct));
        cartProduct.setAmount(amount);
        when(cartProductGateway.update(cartProduct)).thenReturn(cartProduct);

        var output = updateCartProductUseCase.execute(new UpdateCartProductUseCase.Input(
                cartProduct.getId(),
                cartProduct.getCart().getId(),
                amount
        ));

        assertEquals(cartProduct, output.data());
    }

    @Test
    @DisplayName("It should throw if no cart is found")
    public void updateCartErrorNoCartFound() {
        when(cartProductGateway.findById(any())).thenReturn(Optional.empty());

        var thrown = assertThrows(EntityNotFoundException.class, () -> {
            updateCartProductUseCase.execute(new UpdateCartProductUseCase.Input(
                    UUID.randomUUID(),
                    UUID.randomUUID(),
                    12
            ));
        });

        assertEquals("CartProduct not found.", thrown.getMessage());
    }

    @Test
    @DisplayName("It should throw if a negative amount is provided")
    public void updateCartErrorInvalidAmount() {
        when(cartProductGateway.findById(any())).thenReturn(Optional.of(new CartProduct(UUID.randomUUID(), 12, null, null)));

        var thrown = assertThrows(BadRequestException.class, () -> {
            updateCartProductUseCase.execute(new UpdateCartProductUseCase.Input(
                    UUID.randomUUID(),
                    UUID.randomUUID(),
                    0
            ));
        });

        assertEquals("Amount must be positive.", thrown.getMessage());
    }

    @Test
    @DisplayName("It should throw if the cartProduct is not in user's cart")
    public void updateCartErrorUnauthorized() {
        int amount = 12;
        CartProduct cartProduct = new CartProduct(
                5,
                new Cart(
                        true,
                        null,
                        null
                ),
                new Product("name", "desc", "image", 12.2, null)
        );

        when(cartProductGateway.findById(cartProduct.getId())).thenReturn(Optional.of(cartProduct));
        cartProduct.setAmount(amount);
        when(cartProductGateway.update(cartProduct)).thenReturn(cartProduct);

        var thrown = assertThrows(NotAuthorizedException.class, () -> {
            updateCartProductUseCase.execute(new UpdateCartProductUseCase.Input(
                    cartProduct.getId(),
                    UUID.randomUUID(),
                    amount
            ));
        }) ;

        assertEquals("Not authorized.", thrown.getMessage());
    }
}

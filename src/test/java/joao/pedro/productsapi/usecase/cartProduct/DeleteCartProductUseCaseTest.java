package joao.pedro.productsapi.usecase.cartProduct;

import joao.pedro.productsapi.entity.cart.model.Cart;
import joao.pedro.productsapi.entity.cartProduct.gateway.CartProductGateway;
import joao.pedro.productsapi.entity.cartProduct.model.CartProduct;
import joao.pedro.productsapi.entity.category.gateway.CategoryGateway;
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
import static org.mockito.Mockito.*;

class DeleteCartProductUseCaseTest {

    @Mock
    private CartProductGateway cartProductGateway;

    @InjectMocks
    @Autowired
    private DeleteCartProductUseCase deleteCartProductUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("It should delete a cart product successfully")
    public void deleteCartProductSuccess() {
        CartProduct cartProduct = new CartProduct(
                2,
                new Cart(true, null, null),
                new Product("name", "desc", "imageUrl", 12d, null));

        when(cartProductGateway.findById(cartProduct.getId())).thenReturn(Optional.of(cartProduct));

        deleteCartProductUseCase.execute(new DeleteCartProductUseCase.Input(cartProduct.getId(), cartProduct.getCart().getId()));

        verify(cartProductGateway, times(1)).delete(any());
    }

    @Test
    @DisplayName("It should throw if no cart product is found")
    public void deleteCartProductErrorNotFound() {
        when(cartProductGateway.findById(any())).thenReturn(Optional.empty());

        var thrown = assertThrows(EntityNotFoundException.class, () -> {
            deleteCartProductUseCase.execute(new DeleteCartProductUseCase.Input(UUID.randomUUID(), UUID.randomUUID()));
        });


        assertEquals("CartProduct not found.", thrown.getMessage());
    }

    @Test
    @DisplayName("It should throw if the user does not owns the cart")
    public void deleteCartProductErrorUnauthorized() {
        CartProduct cartProduct = new CartProduct(
                2,
                new Cart(true, null, null),
                new Product("name", "desc", "imageUrl", 12d, null));

        when(cartProductGateway.findById(cartProduct.getId())).thenReturn(Optional.of(cartProduct));

        var thrown = assertThrows(NotAuthorizedException.class, () -> {
            deleteCartProductUseCase.execute(new DeleteCartProductUseCase.Input(cartProduct.getId(), UUID.randomUUID()));
        });

        assertEquals("Not authorized.", thrown.getMessage());
    }
}
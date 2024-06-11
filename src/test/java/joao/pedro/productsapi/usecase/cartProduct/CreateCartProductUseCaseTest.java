package joao.pedro.productsapi.usecase.cartProduct;

import joao.pedro.productsapi.entity.cart.model.Cart;
import joao.pedro.productsapi.entity.cartProduct.gateway.CartProductGateway;
import joao.pedro.productsapi.entity.cartProduct.model.CartProduct;
import joao.pedro.productsapi.entity.exceptions.EntityAlreadyExistsException;
import joao.pedro.productsapi.entity.product.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CreateCartProductUseCaseTest {

    @Mock
    private CartProductGateway cartProductGateway;

    @InjectMocks
    @Autowired
    private CreateCartProductUseCase createCartProductUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("It should create a cart product successfully")
    public void createCartProductSuccess() {
        var cart = new Cart(true, null, null);
        var product = new Product("name", "desc", "image", 4.44, null);
        CartProduct expected = new CartProduct(5, cart, product);

        when(cartProductGateway.findByProductAndCart(any(), any())).thenReturn(Optional.empty());
        when(cartProductGateway.create(any())).thenReturn(expected);

        CartProduct cartProduct = createCartProductUseCase.execute(new CreateCartProductUseCase.Input(
                5,
                cart,
                product
        )).data();

        assertEquals(expected, cartProduct);
    }

    @Test
    @DisplayName("It should throw if cart product already exists")
    public void createCartProductError() {

        when(cartProductGateway.findByProductAndCart(any(), any())).thenReturn(Optional.of(new CartProduct(1, null, null)));

        var thrown = assertThrows(EntityAlreadyExistsException.class, () -> {
            createCartProductUseCase.execute(new CreateCartProductUseCase.Input(
                    5,
                    null,
                    null
            ));
        });

        assertEquals("CartProduct already exists.", thrown.getMessage());
    }
}